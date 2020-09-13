package webapp.tier.mq;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

@SuppressWarnings("deprecation")
class ActiveMqServiceTest {

	private static SimpleNamingContextBuilder builder;

	@BeforeAll
	public static void setupAll() throws JMSException, IllegalStateException, NamingException, SQLException {
		builder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();

		ResultSet result = mock(ResultSet.class);
		Statement stsm = mock(Statement.class);
		java.sql.Connection conn = mock(java.sql.Connection.class);
		DataSource ds = mock(DataSource.class);
		when(stsm.executeQuery(anyString())).thenReturn(result);
		when(ds.getConnection()).thenReturn(conn);
		when(conn.createStatement()).thenReturn(stsm);
		builder.bind("jdbc/postgres", ds);

		MessageProducer pro = mock(MessageProducer.class);
		MessageConsumer con = mock(MessageConsumer.class);
		Session session = mock(Session.class);
		javax.jms.Connection jmsconn = mock(javax.jms.Connection.class);
		ConnectionFactory cf = mock(ConnectionFactory.class);
		when(session.createProducer(ArgumentMatchers.any())).thenReturn(pro);
		when(session.createConsumer(ArgumentMatchers.any())).thenReturn(con);
		when(jmsconn.createSession(false, Session.AUTO_ACKNOWLEDGE)).thenReturn(session);
		when(cf.createConnection()).thenReturn(jmsconn);
		builder.bind("jms/QueueConnectionFactory", cf);
		Queue q = mock(Queue.class);
		builder.bind("jms/ActiveMQueue", q);
		Topic t = mock(Topic.class);
		builder.bind("jms/ActiveMQTopic", t);
	}

	@Test
	void testgetConnection() throws Exception {
		ActiveMqService svc = new ActiveMqService();
		svc.getConnection();
	}

	@Test
	void testcloseAllNull() throws Exception {
		ActiveMqService svc = new ActiveMqService();
		try {
			svc.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testputActiveMq() throws Exception {
		ActiveMqService svc = new ActiveMqService();
		String result = svc.putActiveMq();
		assertThat(result, is(notNullValue()));
		assertThat(result, containsString("Set"));
	}

	@Test
	void testgetActiveMqWithNoData() throws Exception {
		ActiveMqService svc = new ActiveMqService();
		String result = svc.getActiveMq();
		assertThat(result, is(notNullValue()));
		assertThat(result, is("No Data"));
	}

	@Test
	void testpublishActiveMq() throws Exception {
		ActiveMqService svc = new ActiveMqService();
		String result = svc.publishActiveMq();
		assertThat(result, is(notNullValue()));
		assertThat(result, containsString("Publish"));
	}
}
