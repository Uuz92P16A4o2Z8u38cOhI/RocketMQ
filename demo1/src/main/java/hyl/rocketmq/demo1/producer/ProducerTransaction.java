package hyl.rocketmq.demo1.producer;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

public class ProducerTransaction {
    public static void main(String[] args) throws Exception {
        TransactionMQProducer producer = new TransactionMQProducer("my-transaction-producer");
        producer.setNamesrvAddr("127.0.0.1:9876");

        // 回调
        producer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object arg) {
                LocalTransactionState state = null;
                //msg-4返回COMMIT_MESSAGE
                if(message.getKeys().equals("msg-1")){
                    state = LocalTransactionState.COMMIT_MESSAGE;
                }
                //msg-5返回ROLLBACK_MESSAGE
                else if(message.getKeys().equals("msg-2")){
                    state = LocalTransactionState.ROLLBACK_MESSAGE;
                }else{
                    //这里返回unknown的目的是模拟执行本地事务突然宕机的情况（或者本地执行成功发送确认消息失败的场景）
                    state = LocalTransactionState.UNKNOW;
                }
                System.out.println(message.getKeys() + ",state:" + state);
                return state;
            }

            /**
             * 事务消息的回查方法
             */
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                if (null != messageExt.getKeys()) {
                    String keys = messageExt.getKeys();
                    if ("msg-3".equals(keys)) {
                        System.out.println("msg-3 unknow");
                        return LocalTransactionState.UNKNOW;
                    } else if ("msg-4".equals(keys)) {
                        System.out.println("msg-4 COMMIT_MESSAGE");
                        return LocalTransactionState.COMMIT_MESSAGE;
                    } else if ("msg-5".equals(keys)) {//查询到本地事务执行失败，需要回滚消息。
                        System.out.println("msg-5 ROLLBACK_MESSAGE");
                        return LocalTransactionState.ROLLBACK_MESSAGE;
                    }
                }
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });

        producer.start();

        //模拟发送5条消息
        for (int i = 1; i < 6; i++) {
            try {
                Message msg = new Message("transactionTopic", null, "msg-" + i, ("测试，这是事务消息！ " + i).getBytes());
                producer.sendMessageInTransaction(msg, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
