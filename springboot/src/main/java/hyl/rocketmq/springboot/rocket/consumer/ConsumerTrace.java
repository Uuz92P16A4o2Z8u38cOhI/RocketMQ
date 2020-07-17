package hyl.rocketmq.springboot.rocket.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * 注意:
 * 默认情况下 Producer 和 Consumer 的消息轨迹功能是开启的且 trace-topic 为 RMQ_SYS_TRACE_TOPIC Consumer 端的消息轨迹
 * trace-topic 可以在配置文件中配置 rocketmq.consumer.customized-trace-topic 配置项，不需要为在每个 @RocketMQMessageListener 配置。
 *
 * 注意:
 * 可以不用为每个 @RocketMQMessageListener 注解配置 AK/SK，在配置文件中配置 rocketmq.consumer.access-key 和 rocketmq.consumer.secret-key 配置项，
 * 这两个配置项的值就是默认值
 */
@Slf4j
@Service
@RocketMQMessageListener(
        topic = "test-topic-convertAndSend",
        consumerGroup = "my-consumer_convertAndSend",
        enableMsgTrace = true,
        customizedTraceTopic = "my-trace-topic",
        accessKey = "AK",
        secretKey = "SK"
)
public class ConsumerTrace implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        log.info("message:" + s);
    }
}
