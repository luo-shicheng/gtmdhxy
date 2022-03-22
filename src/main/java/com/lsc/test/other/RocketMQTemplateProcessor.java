package com.lsc.test.other;


import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;


//@Component
public class RocketMQTemplateProcessor implements BeanPostProcessor {

    private final MessageQueueSelector backUpSelector=new SelectMessageQueueByHash();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof RocketMQTemplate){
            RocketMQTemplate rocketMQTemplate = (RocketMQTemplate) bean;
            rocketMQTemplate.setMessageQueueSelector((mqs, msg, arg) -> {
                String str = (String) arg;
                int i ;
                try{
                    i=Integer.parseInt(str);
                }catch (Exception e){
                    return backUpSelector.select(mqs,  msg,  arg);
                }
                return mqs.get(i % mqs.size());
            });
        }
        return bean;
    }
}
