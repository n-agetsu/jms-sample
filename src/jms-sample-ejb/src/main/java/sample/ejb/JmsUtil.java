package sample.ejb;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

class JmsUtil {
    static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    static void close(Session session) {
        if (session != null) {
            try {
                session.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
