package hyl.rocketmq.springboot;

import hyl.rocketmq.springboot.rocket.consumer.ConsumerOne;
import hyl.rocketmq.springboot.rocket.producer.ProducerOne;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;

@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

    @Resource
    private RocketMQTemplate rocketMQTemplate;
    ProducerOne producerOne = new ProducerOne();
    ConsumerOne consumerOne = new ConsumerOne();
    /*public void run(String... args) throws Exception {
//        producerOne.send();
    }*/

  /*  public void run(String... args) throws Exception {
        try {
            // Build a SpringMessage for sending in transaction
            Message msg =  MessageBuilder.withPayload();
            // In sendMessageInTransaction(), the first parameter transaction name ("test")
            // must be same with the @RocketMQTransactionListener's member field 'transName'
            rocketMQTemplate.sendMessageInTransaction("test-topic", msg, null);
        } catch (MQClientException e) {
            e.printStackTrace(System.out);
        }
    }*/

    // Define transaction listener with the annotation @RocketMQTransactionListener
    @RocketMQTransactionListener
    class TransactionListenerImpl implements RocketMQLocalTransactionListener {

        @Override
        public RocketMQLocalTransactionState executeLocalTransaction(org.springframework.messaging.Message message, Object o) {
            // ... local transaction process, return bollback, commit or unknown
            return RocketMQLocalTransactionState.UNKNOWN;
        }

        @Override
        public RocketMQLocalTransactionState checkLocalTransaction(org.springframework.messaging.Message message) {
            // ... check transaction status and return bollback, commit or unknown
            return RocketMQLocalTransactionState.COMMIT;
        }
    }
}
