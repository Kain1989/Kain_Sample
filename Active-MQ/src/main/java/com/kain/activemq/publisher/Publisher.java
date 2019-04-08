package com.kain.activemq.publisher;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created on 4/4/2018.
 */
public class Publisher {
    private static final int SEND_NUMBER = 100;

    public static void main(String[] args) {
        ConnectionFactory connectionFactory;
        Connection connection = null;
        Session session;
        Destination destination;
        MessageProducer producer;
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.TRUE,
                    Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("FirstQueue");
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            sendMessage(session, producer);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != connection)
                    connection.close();
            } catch (Throwable ignore) {
            }
        }
    }

    public static void sendMessage(Session session, MessageProducer producer)
            throws Exception {
        while (true) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input = reader.readLine();
            if (input.equalsIgnoreCase("stop")) {
                break;
            }
            TextMessage message = session.createTextMessage("ActiveMq send message:" + input);
            producer.send(message);
            session.commit();
            System.out.println("ActiveMq send message:" + input);
        }
    }
}
