package org.ys.rabbitmq.direct;

import java.io.IOException;

import org.ys.rabbitmq.utils.ConnectionUtil;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Rec1 {
	private static final String EXCHANGE_NAME = "direct_queue";
	
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		
		//声明路由器和类型
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
		
		channel.basicQos(1);
		
		String queueName = channel.queueDeclare().getQueue();
        //定义要监听的级别
        String[] severities = {"info", "warning", "error"};
        //根据绑定键绑定
        for (String severity : severities) {
            channel.queueBind(queueName, EXCHANGE_NAME, severity);
        }
		
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
		channel.basicConsume(queueName,autoAck,consumer);
	}
}
