package com.org.jms.wellnessapp;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.org.jms.hrapp.Employee;

public class WellnessApp {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext initialContext = new InitialContext();
		Topic topic = (Topic) initialContext.lookup("topic/myTopic");
		try(ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
				JMSContext context = connectionFactory.createContext()){
			JMSConsumer consumer1 = context.createSharedConsumer(topic, "sharedConsumer");
			JMSConsumer consumer2 = context.createSharedConsumer(topic, "sharedConsumer");
			
			for (int i = 0; i < 10; i+=2) {
				Message message1 = consumer1.receive();
				Employee employee1 = message1.getBody(Employee.class);
				System.out.println("1 : "+employee1.getFirstName());
				
				Message message2 = consumer2.receive();
				Employee employee2 = message2.getBody(Employee.class);
				System.out.println("2 : "+employee2.getFirstName());
			}
		}
	}
}