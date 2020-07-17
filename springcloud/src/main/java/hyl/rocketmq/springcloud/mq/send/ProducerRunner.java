package hyl.rocketmq.springcloud.mq.send;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.util.HashMap;
import java.util.Map;

public class ProducerRunner implements CommandLineRunner {
    @Autowired
    private MessageChannel output; // 获取name为output的binding
    @Override
    public void run(String... args) throws Exception {
        Map<String, Object> headers = new HashMap<>();
        headers.put(MessageConst.PROPERTY_TAGS, "tagStr");
//        Message message = MessageBuilder.createMessage(msg, new MessageHeaders(headers));
//        output.send(message);
    }
   /* public class RocketMQProducer {
        DefaultMQProducer producer = new DefaultMQProducer("producer_group");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        Message msg = new Message("test-topic", "tagStr", "message from rocketmq producer".getBytes());
        producer.send(msg);
    }*/
}