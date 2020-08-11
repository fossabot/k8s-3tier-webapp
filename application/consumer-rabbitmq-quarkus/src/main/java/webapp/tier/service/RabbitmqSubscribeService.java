package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

@ApplicationScoped
public class RabbitmqSubscribeService extends RabbitmqService {

	private static final Logger LOG = Logger.getLogger(RabbitmqSubscribeService.class.getSimpleName());

	protected RabbitmqDeliverSubscriber createRabbitmqDeliverSubscriber(Channel channel) {
		return new RabbitmqDeliverSubscriber(channel);
	}

	@Override
	public void run() {
		try (Connection conn = getConnection();
				Channel channel = conn.createChannel()) {
			subscribeRabbitmq(conn, channel, createRabbitmqDeliverSubscriber(channel));
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Subscribe Errorr.", e);
		}
	}
}
