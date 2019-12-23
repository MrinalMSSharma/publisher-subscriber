package com.org.jms.payrollapp;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.org.jms.hrapp.Employee;

public class PayrollApp {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext initialContext = new InitialContext();
		Topic topic = (Topic) initialContext.lookup("topic/myTopic");
		try(ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
				JMSContext context = connectionFactory.createContext()){
			JMSConsumer consumer = context.createConsumer(topic);
			Message message = consumer.receive();
			Employee employee = message.getBody(Employee.class);
			
			System.out.println(employee.getFirstName());
		}
	}
}