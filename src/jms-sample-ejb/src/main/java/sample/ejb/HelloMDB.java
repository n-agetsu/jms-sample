package sample.ejb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "/queue/HelloQueue")
})
public class HelloMDB implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            // for test
            // kill jms server process during sleep, then other cluster instance
            // detected process fault and handover message consume.

            // When cluster member fault detected, following logged server.log
            // 2018-06-16 14:22:49,297 INFO  [org.jboss.messaging.core.impl.postoffice.MessagingPostOffice] (Incoming-7,127.0.0.1:55300) JBoss Messaging is failing over for failed node 0. If there are many messages to reload this may take some time...
            System.out.println("HelloMDB: sleep 15 sec ...");
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TextMessage text = (TextMessage) message;
        try {
            System.out.println("HelloMDB: " + text.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}