//package com.lsc.test.handler;
//
//import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.apache.rocketmq.spring.annotation.ConsumeMode;
//import org.apache.rocketmq.spring.annotation.MessageModel;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
//@RocketMQMessageListener(topic = "topic1",consumerGroup = "test1",consumeMode= ConsumeMode.ORDERLY,messageModel= MessageModel.CLUSTERING)
////@Service
//@ConditionalOnProperty(prefix = "rocketmq", value = "enable", havingValue = "true")
//public class MqListener implements RocketMQListener<MessageExt> , RocketMQPushConsumerLifecycleListener {
//    @Override
//    public void onMessage(MessageExt MessageExt) {
//        System.out.println(new Date()+"         "+"MqListener    "+Thread.currentThread().getName()+"   "+MessageExt.getQueueId());
//    }
//
//
//    @Override
//    public void prepareStart(DefaultMQPushConsumer consumer) {
//        consumer.setInstanceName("MqListener");
//    }
//}
