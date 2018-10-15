package org.ys.rabbitmq.direct;


import org.ys.rabbitmq.utils.ConnectionUtil;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {
	private static final String EXCHANGE_NAME = "direct_queue";
	
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		
		//声明路由器和路由器的类型
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
		
        String security = "error";
		String msg = "simple msg!";
		channel.confirmSelect();
		
		System.out.println("send msg=" + msg);
		channel.basicPublish(EXCHANGE_NAME, security,null,msg.getBytes("UTF-8"));
		
		if(!channel.waitForConfirms()){
		    System.out.println("send message failed.");
		}
		channel.close();
		connection.close();
	}
}
