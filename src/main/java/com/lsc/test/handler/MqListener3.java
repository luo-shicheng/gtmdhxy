package com.lsc.test.handler;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@RocketMQMessageListener(topic = "topic1",selectorExpression="tag1",consumerGroup = "test1",consumeMode= ConsumeMode.CONCURRENTLY,messageModel= MessageModel.CLUSTERING)
@Service
@ConditionalOnProperty(prefix = "rocketmq", value = "enable", havingValue = "true")
public class MqListener3 implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    @Override
    public void onMessage(MessageExt messageExt) {
        System.out.println(new Date()+"         "+"MqListener3    "+Thread.currentThread().getName()+"   "+messageExt.getQueueId()+ "   "+new String(messageExt.getBody()));
//        if(Objects.isNull(messageExt.getProperty("myId"))){
//            throw new NullPointerException();
//        }else {
//            System.out.println(new Date()+"         "+"MqListener3    "+Thread.currentThread().getName()+"   "+messageExt.getQueueId()+"    "+new String(messageExt.getBody()));
//        }
    }


    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.setInstanceName("MqListener3");
    }
}
