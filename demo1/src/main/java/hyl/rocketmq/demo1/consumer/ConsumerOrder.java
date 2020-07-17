package hyl.rocketmq.demo1.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class ConsumerOrder {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("my-consumer");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("orderTopic", "*");
        // 最大线程数1
//        consumer.setConsumeThreadMax(1);
        // 最小线程数
//        consumer.setConsumeThreadMin(1);
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.println(new String(msg.getBody()) + " Thread:" + Thread.currentThread().getName() + " queueid:" + msg.getQueueId());
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

        consumer.start();
        System.out.println("Consumer start...");
    }
}
