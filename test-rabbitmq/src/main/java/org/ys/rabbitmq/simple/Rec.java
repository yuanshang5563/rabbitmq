package org.ys.rabbitmq.simple;

import java.io.IOException;

import org.ys.rabbitmq.utils.ConnectionUtil;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Rec {
	private static final String QUEUE_NAME = "simpe_queue";
	
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);		
		
		channel.basicQos(1);
		
		Consumer consumer = new DefaultConsumer(channel){

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
				try {
					System.out.println("recv msg=" + new String(body, "UTF-8"));
				} finally {
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
			
		};
		
		boolean autoAck = false;
		channel.basicConsume(QUEUE_NAME,autoAck,consumer);
	}
}
