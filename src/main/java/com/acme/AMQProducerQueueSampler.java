package com.acme;

import javax.jms.Connection;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;


/**
 * Very Simple Queue Producer sending messages to given queue.
 * 
 * 
 * TODO:
 *   - do better logging 
 * 
 */

public class AMQProducerQueueSampler implements JavaSamplerClient {

	private Connection connection;
	private TextMessage message;
	private MessageProducer messageProducer;
	
	
	@Override
	public Arguments getDefaultParameters() {
		Arguments args = new Arguments();
		args.addArgument("connectionUrl", "tcp://localhost:61616");
		args.addArgument("destinationName", "JMeter-test-queue");
		return args;
		
	}
	
	/**
	 * Called once.
	 * 
	 */
	
	@Override
	public void setupTest(JavaSamplerContext javaSamplerContext) {
		
		String connectionUrl = javaSamplerContext.getParameter("connectionUrl");
		
		if(connectionUrl.equals("")){
			connectionUrl = "tcp://localhost:61616";
		}
		
		
		
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(connectionUrl);
		try {
		
		connection = connectionFactory.createConnection();
		
		
		String destinationName = javaSamplerContext.getParameter("destinationName");
		
		if(destinationName.equals("")){
			destinationName="JMeter-test-queue";
		}
		
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		
		//get/create a queue
		Destination destination = session.createQueue(destinationName);
		
		message = session.createTextMessage();
		message.setText("AMQProducerQueueSampler text message");
		
		
		//create a producer 
		messageProducer = session.createProducer(destination);
		
		
		
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		}
	}

	/**
	 * Called for every loop (defined in the thread group)
	 */
	
	@Override
	public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
		
		SampleResult result = new SampleResult();
		result.setDataType(SampleResult.TEXT);
		result.setSampleLabel("AMQ Producer Sample Result");
		
		try{
		
		//Sampler was not init correctly	
		if(	messageProducer == null){
			result.setResponseMessage("MessageProducer Not Initialised");
			result.setSuccessful(false);
		}	
		

		// start timer
		result.sampleStart();
			
		messageProducer.send(message);
		
		result.sampleEnd();
		result.setSuccessful(true);
		
		
		} catch (Exception ex){
			result.setSuccessful(false);
			ex.printStackTrace(System.out);
			
		}
		
		return result;
	}

	/**
	 * Only called once
	 */

	@Override
	public void teardownTest(JavaSamplerContext javaSamplerContext) {
		try {
			if(messageProducer != null){
				messageProducer.close();
			}
			if(connection != null){
				connection.close();
			}
	
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		}

	}

}
