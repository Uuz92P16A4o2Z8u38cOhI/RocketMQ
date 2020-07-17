package hyl.rocketmq.springboot.rocket.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RocketMQMessageListener(topic = "test-topic-convertAndSend", consumerGroup = "my-consumer_convertAndSend")
public class ConsumerOne implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("received message: {}", message);
    }
}
