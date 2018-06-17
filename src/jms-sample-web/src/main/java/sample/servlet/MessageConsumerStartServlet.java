package sample.servlet;

import sample.ejb.MessageConsumer;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MessageConsumerStartServlet extends HttpServlet {

    @EJB
    private MessageConsumer consumer;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        consumer.start();
        response.setContentType("text/plain; charset=utf-8");
        response.getWriter().write("Start MessageConsumer for HelloQueue");
    }
}
