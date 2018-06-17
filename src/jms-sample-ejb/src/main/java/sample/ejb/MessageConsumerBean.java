package sample.ejb;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jms.*;

@Local
@Stateless
public class MessageConsumerBean implements MessageConsumer {

    // TODO: SLSB should not have state
    private static QueueConnection conn;
    private static QueueSession session;

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory cf;

    @Resource(mappedName = "/queue/HelloQueue")
    private Queue queue;

    private volatile boolean isRunning;

    @Override
    public void start() {
        if (isRunning) {
            System.out.println("HelloQueue-receiver-thread already running");
            return;
        }

        try {
            this.conn = (QueueConnection) cf.createConnection();
            this.session = conn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            final QueueReceiver receiver = session.createReceiver(queue);
            conn.start();
            this.isRunning = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Message message = receiver.receive(5000);
                            if (message != null) {
                                TextMessage text = (TextMessage) message;
                                try {
                                    System.out.println("MessageConsumerBean: " + text.getText() +
                                            ", instance: " + System.getProperty("jboss.home.dir"));
                                } catch (JMSException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                System.out.println("no message HelloQueue");
                            }
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }

                        if (!isRunning) {
                            JmsUtil.close(session);
                            JmsUtil.close(conn);
                            System.out.println("stop message consume loop");
                            break;
                        }
                    }
                }
            }, "HelloQueue-receiver-thread").start();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        this.isRunning = false;
    }
}
