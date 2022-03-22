package com.lsc.test.other;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.support.DefaultRocketMQListenerContainer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

//@Component
public class ProducerPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof DefaultRocketMQListenerContainer){
            DefaultRocketMQListenerContainer container = (DefaultRocketMQListenerContainer) bean;
            DefaultMQPushConsumer consumer = container.getConsumer();
            consumer.setPullInterval(2000);
            consumer.setPullBatchSize(1);
            return container;
        }
        return bean;
    }
}
