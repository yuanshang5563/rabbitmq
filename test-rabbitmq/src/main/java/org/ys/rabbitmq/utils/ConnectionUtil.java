package org.ys.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {
	
	/**
	 * 获取连接
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setPort(5672);
		factory.setUsername("yuanshang");
		factory.setPassword("123456");
		factory.setVirtualHost("/virtual_host");
		return factory.newConnection();
	}
}
