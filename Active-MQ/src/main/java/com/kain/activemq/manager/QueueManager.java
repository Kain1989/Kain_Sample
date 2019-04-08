package com.kain.activemq.manager;

import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * Created on 4/4/2018.
 */
public class QueueManager {

    public static void main(String[] args) throws Exception {
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:" + 61616 + "admin");
        JMXConnector connector = JMXConnectorFactory.connect(url, null);
        connector.connect();
        MBeanServerConnection connection = connector.getMBeanServerConnection();

        ObjectName name = new ObjectName("local" + ":BrokerName=localhost,Type=Broker");
        BrokerViewMBean mBean = (BrokerViewMBean) MBeanServerInvocationHandler.newProxyInstance(connection,
                name, BrokerViewMBean.class, true);

        for (ObjectName queueName : mBean.getQueues()) {
            QueueViewMBean queueMBean = (QueueViewMBean) MBeanServerInvocationHandler
                    .newProxyInstance(connection, queueName, QueueViewMBean.class, true);
            System.out.println("\n------------------------------\n");

            System.out.println("States for queue --- " + queueMBean.getName());

            System.out.println("Size --- " + queueMBean.getQueueSize());

            System.out.println("Number of consumers --- " + queueMBean.getConsumerCount());

            System.out.println("Number of dequeue ---" + queueMBean.getDequeueCount());
        }

    }
}
