package com.lsc.test.config;

import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQMessageConverter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@AutoConfigureBefore(RocketMQAutoConfiguration.class)
//@ImportAutoConfiguration(RocketMQAutoConfiguration.class)
public class TransactionMqTemplateConfig {

    public static final String TRANSACTION_MQ_TEMPLATE="transaction_mq_template";

    @Bean
    public TransactionMQProducer transactionMQProducer(){
        TransactionMQProducer transactionMQProducer = new TransactionMQProducer();
        return transactionMQProducer;
    }

    @Bean(TRANSACTION_MQ_TEMPLATE)
    public RocketMQTemplate rocketMQTemplate(RocketMQMessageConverter rocketMQMessageConverter,
                                             TransactionMQProducer transactionMQProducer) {
        RocketMQTemplate rocketMQTemplate = new RocketMQTemplate();
        rocketMQTemplate.setProducer(transactionMQProducer);
        rocketMQTemplate.setMessageConverter(rocketMQMessageConverter.getMessageConverter());
        return rocketMQTemplate;
    }
}
