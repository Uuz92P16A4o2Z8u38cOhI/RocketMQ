package hyl.rocketmq.springboot.rocket.producer;

import hyl.rocketmq.springboot.entity.OrderPaidEvent;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.math.BigDecimal;

public class ProducerOne {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void send(){
        //send message synchronously
        rocketMQTemplate.convertAndSend("test-topic-convertAndSend", "Hello, World!");
        //send spring message
        rocketMQTemplate.send("test-topic-send", MessageBuilder.withPayload("Hello, World! I'm from spring message").build());
        //send messgae asynchronously
        rocketMQTemplate.asyncSend("test-topic-asyncSend", new OrderPaidEvent("T_001", new BigDecimal("88.00")), new SendCallback() {
            @Override
            public void onSuccess(SendResult var1) {
                System.out.printf("async onSucess SendResult=%s %n", var1);
            }

            @Override
            public void onException(Throwable var1) {
                System.out.printf("async onException Throwable=%s %n", var1);
            }

        });
        //Send messages orderly
        rocketMQTemplate.syncSendOrderly("orderly_topic",MessageBuilder.withPayload("Hello, World").build(),"hashkey");

        //rocketMQTemplate.destroy(); // notes:  once rocketMQTemplate be destroyed, you can not send any message again with this rocketMQTemplate
    }
}
