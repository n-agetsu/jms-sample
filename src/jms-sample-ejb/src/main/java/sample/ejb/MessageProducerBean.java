package sample.ejb;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jms.*;

@Local
@Stateless
public class MessageProducerBean implements MessageProducer {

    @Resource(mappedName="java:/ConnectionFactory")
    private ConnectionFactory cf;

    @Resource(mappedName="/queue/HelloQueue")
    private Queue queue;

    public void send(String msg) {
        QueueConnection conn = null;
        QueueSession session = null;
        try {
            conn = (QueueConnection) cf.createConnection();
            session = conn.createQueueSession(true, -1);
            QueueSender sender = session.createSender(queue);
            sender.send(session.createTextMessage(msg + ", send from: " + System.getProperty("jboss.home.dir")));
            session.commit();
        } catch (JMSException e) {
            try {
                session.rollback();
            } catch (JMSException e2) {
                e2.printStackTrace();
            }
        } finally {
            JmsUtil.close(session);
            JmsUtil.close(conn);
        }
    }
}

