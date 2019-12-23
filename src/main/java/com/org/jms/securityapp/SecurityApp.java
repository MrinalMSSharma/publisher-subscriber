package com.org.jms.securityapp;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.org.jms.hrapp.Employee;

public class SecurityApp {

	public static void main(String[] args) throws NamingException, JMSException, InterruptedException {

		InitialContext initialContext = new InitialContext();
		Topic topic = (Topic) initialContext.lookup("topic/myTopic");
		try(ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
				JMSContext context = connectionFactory.createContext()){
			context.setClientID("securityApp");
			JMSConsumer consumer = context.createDurableConsumer(topic, "subscription");
			consumer.close();
			
			Thread.sleep(10000);
			
			consumer = context.createDurableConsumer(topic, "subscription");
			Message message = consumer.receive();
			Employee employee = message.getBody(Employee.class);
			
			System.out.println(employee.getFirstName());
			
			consumer.close();
			context.unsubscribe("subscription");
		}
	}
}