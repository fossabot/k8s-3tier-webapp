package spring.web.mq.rabbitmq;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import spring.web.util.CreateId;
import spring.web.util.GetConfig;

public class PutRabbitmq extends HttpServlet {
	Logger logger = LoggerFactory.getLogger(PutRabbitmq.class);
    private static String queuename = GetConfig.getResourceBundle("queue.name");
    private static String username = GetConfig.getResourceBundle("jms.username");
    private static String password = GetConfig.getResourceBundle("jms.password");
    private static String host = GetConfig.getResourceBundle("jms.host");
    private static String vhost = GetConfig.getResourceBundle("jms.vhost");
    private static String message = GetConfig.getResourceBundle("common.message");
    private static String splitkey = GetConfig.getResourceBundle("jms.split.key");

    public String putMessageQueue() {
	ConnectionFactory connectionFactory = new ConnectionFactory();
	connectionFactory.setUsername(username);
	connectionFactory.setPassword(password);
	connectionFactory.setHost(host);
	connectionFactory.setVirtualHost(vhost);
	String fullmsg = null;

	try {
	    Connection connection = connectionFactory.newConnection();
	    Channel channel = connection.createChannel();

	    String id = String.valueOf(CreateId.createid());

	    StringBuilder buf = new StringBuilder();
	    buf.append(id);
	    buf.append(splitkey);
	    buf.append(message);
	    String body = buf.toString();

	    channel.basicPublish("", queuename, null, body.getBytes());

		fullmsg = "Set id: " + id+ ", msg:" + message;
		logger.info(fullmsg);

	    channel.close();
	    connection.close();

	} catch (Exception e) {
		e.printStackTrace();
	}
	return fullmsg;
    }
}
