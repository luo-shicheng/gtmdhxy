package com.lsc.test.handler;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.Date;


@RocketMQTransactionListener
@Service
@ConditionalOnProperty(prefix = "rocketmq", value = "enabled", havingValue = "true")
public class TransactionMqListener implements RocketMQLocalTransactionListener {
    private int i=0;
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        System.out.println("mq 处理本地消息时间： "+new Date());
        if(arg instanceof Integer && arg.equals(1)){
            return RocketMQLocalTransactionState.UNKNOWN;
        }
        return RocketMQLocalTransactionState.COMMIT;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        i++;
        System.out.println(msg);
        if(i%3==0){
            return RocketMQLocalTransactionState.COMMIT;
        }else {
            System.out.println("mq 本地回查失败： "+new Date());
            return RocketMQLocalTransactionState.UNKNOWN;
        }

    }
}
