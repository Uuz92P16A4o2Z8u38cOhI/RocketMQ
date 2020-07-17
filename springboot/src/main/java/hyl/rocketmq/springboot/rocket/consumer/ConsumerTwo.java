package hyl.rocketmq.springboot.rocket.consumer;

import hyl.rocketmq.springboot.entity.OrderPaidEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RocketMQMessageListener(topic = "test-topic-asyncSend", consumerGroup = "my-consumer_asyncSend")
public class ConsumerTwo implements RocketMQListener<OrderPaidEvent> {

    @Override
    public void onMessage(OrderPaidEvent orderPaidEvent) {
        log.info("received orderPaidEvent: {}", orderPaidEvent);
    }
}
