package sample.servlet;

import sample.ejb.MessageProducer;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class MessageSendServlet extends HttpServlet {

    private AtomicInteger count = new AtomicInteger();

    @EJB
    private MessageProducer producer;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException {

        producer.send("message " + count.incrementAndGet());

        response.setContentType("text/plain; charset=utf-8");
        response.getWriter().write("send message No " + count.get());
    }
}
