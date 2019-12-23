package com.org.jms.hrapp;

import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class HRApp {

	public static void main(String[] args) throws NamingException {

		InitialContext initialContext = new InitialContext();
		Topic topic = (Topic) initialContext.lookup("topic/myTopic");
		try(ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
				JMSContext context = connectionFactory.createContext()){
			Employee employee = new Employee();
			employee.setId(123);
			employee.setFirstName("Mrinal");
			employee.setLastName("Sharma");
			employee.setDesignation("Engineer");
			employee.setEmail("mrinal@sharma.com");
			employee.setPhoneNumber("123456");
			
			for (int i = 0; i < 10; i++) {
				context.createProducer().send(topic, employee);
			}
			System.out.println("Message sent");
		}
	}
}