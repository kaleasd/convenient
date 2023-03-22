package com.gyh.servicejmsconsumer.component;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * 消息消费者（Queue模式）
 * */
@Component
public class ConsumerQueue {
    /**
     * 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
     * @param text
     * */
    @JmsListener(destination = "1", containerFactory = "jmsListenerContainerQueue")
    public void receiveQueue(TextMessage text, Session session) throws JMSException {
        try {
            System.out.println("消费者受到的queue1报文为：" + text.getText());

            //提交完事务后，再确认。因为哪怕下次有事件再来，插库会失败。
            text.acknowledge();

        } catch (Exception e) {
            session.recover();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
