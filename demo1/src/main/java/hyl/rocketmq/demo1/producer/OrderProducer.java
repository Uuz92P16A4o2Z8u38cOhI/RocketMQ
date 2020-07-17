package hyl.rocketmq.demo1.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

public class OrderProducer {
    public static void main(String[] args)throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("my-order-producer");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        for (int i = 0; i < 5; i++) {
            Message message = new Message("orderTopic", ("hello!" + i).getBytes());
            producer.send(
                    // 要发的那条消息
                    message,
                    // queue 选择器 ，向 topic中的哪个queue去写消息
                    new MessageQueueSelector() {
                        // 手动 选择一个queue
                        @Override
                        public MessageQueue select(
                                // 当前topic 里面包含的所有queue
                                List<MessageQueue> mqs,
                                // 具体要发的那条消息
                                Message msg,
                                // 对应到 send（） 里的 args，也就是2000前面的那个0
                                // 实际业务中可以把0换成实际业务系统的主键，比如订单号啥的，然后这里做hash进行选择queue等。
                                // 能做的事情很多，我这里做演示就用第一个queue，所以不用arg。
                                Object arg) {
                            // 向固定的一个queue里写消息，比如这里就是向第一个queue里写消息
                            MessageQueue queue = mqs.get(0);
                            // 选好的queue
                            return queue;
                        }
                    },
                    // 自定义参数：0
                    // 2000代表2000毫秒超时时间
                    0, 2000);
        }
    }
}
