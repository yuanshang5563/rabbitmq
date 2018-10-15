package org.ys.rabbitmq.simple;


import org.ys.rabbitmq.utils.ConnectionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {
	private static final String QUEUE_NAME = "simpe_queue";
	
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		
		try {
			String msg = "simple msg!";
			channel.txSelect();
			
			System.out.println("send msg=" + msg);
			channel.basicPublish("", QUEUE_NAME,null,msg.getBytes("UTF-8"));
			
			channel.txCommit();
			
			channel.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			channel.txRollback();
		}
	}
}
