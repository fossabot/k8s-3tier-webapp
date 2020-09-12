package webapp.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import webapp.tier.cache.memcached.GetMemcached;
import webapp.tier.cache.memcached.SetMemcached;
import webapp.tier.cache.redis.GetRedis;
import webapp.tier.cache.redis.PublishRedis;
import webapp.tier.cache.redis.SetRedis;
import webapp.tier.db.mysql.DeleteMysql;
import webapp.tier.db.mysql.InsertMysql;
import webapp.tier.db.mysql.SelectMysql;
import webapp.tier.mq.rabbitmq.GetRabbitmq;
import webapp.tier.mq.rabbitmq.PutRabbitmq;
import webapp.tier.mq.rabbitmq.PutRabbitmqConsumer;

@Controller
public class ApplicationController {

	@Inject
	InsertMysql insmsg;

	@Inject
	SelectMysql selmsg;

	@Inject
	DeleteMysql delmsg;

	@Inject
	GetRabbitmq getmq;

	@Inject
	PutRabbitmq putmq;

	@Inject
	PutRabbitmqConsumer putmqb;

	@Inject
	GetMemcached getmemcache;

	@Inject
	SetMemcached setmemcache;

	@Inject
	GetRedis getrediscache;

	@Inject
	SetRedis setrediscache;

	@Inject
	PublishRedis publishrediscache;

	Logger logger = LoggerFactory.getLogger(ApplicationController.class);

	@RequestMapping("/")
	public String index(Model model) {
		return "index";
	}

	@RequestMapping("InsertMysql")
	public String insertDb(Model model) throws Exception {
		logger.info("InsertMysql");
		String msg = insmsg.insertMysql();
		model.addAttribute("insertMysql", msg);
		return "insertmysql";
	}

	@RequestMapping("SelectMysql")
	public String selectMysql(Model model) throws SQLException, NamingException {
		logger.info("SelectMysql");
		List<String> allMessage = selmsg.selectMsg();
		model.addAttribute("allMessageList", allMessage);
		return "selectmysql";
	}

	@RequestMapping("DeleteMysql")
	public String deleteMysql() throws SQLException, NamingException {
		logger.info("DeleteMysql");
		delmsg.deleteMsg();
		return "deletemysql";
	}

	@RequestMapping("GetRabbitmq")
	public String getMq(Model model) throws Exception {
		logger.info("GetRabbitmq");
		String telegram = getmq.getMessageQueue();
		model.addAttribute("getRabbitmq", telegram);
		return "getrabbitmq";
	}

	@RequestMapping("PutRabbitmq")
	public String putMq(Model model) throws IOException, TimeoutException {
		logger.info("PutRabbitmq");
		String telegram = putmq.putMessageQueue();
		model.addAttribute("putRabbitmq", telegram);
		return "putrabbitmq";
	}

	@RequestMapping("PutRabbitmqConsumer")
	public String putMqBatch(Model model) throws IOException, TimeoutException {
		logger.info("PutRabbitmqConsumer");
		String telegram = putmqb.putMessageQueueConsumer();
		model.addAttribute("putRabbitmqConsumer", telegram);
		return "putrabbitmqconsumer";
	}

	@RequestMapping("GetMemcached")
	public String getMemcached(Model model) {
		logger.info("GetMemcached");
		String cache = getmemcache.getMemcached();
		model.addAttribute("getMemcached", cache);
		return "getmemcached";
	}

	@RequestMapping("SetMemcached")
	public String setMemcached(Model model) throws Exception {
		logger.info("SetMemcached");
		String cache = setmemcache.setMemcached();
		model.addAttribute("setMemcached", cache);
		return "setmemcached";
	}

	@RequestMapping("GetRedis")
	public String getRedis(Model model) {
		logger.info("GetRedis");
		List<String> cache = getrediscache.getRedis();
		model.addAttribute("getRedisList", cache);
		return "getredis";
	}

	@RequestMapping("SetRedis")
	public String setRedis(Model model) {
		logger.info("SetRedis");
		String cache = setrediscache.setRedis();
		model.addAttribute("setRedis", cache);
		return "setredis";
	}

	@RequestMapping("PublishRedis")
	public String publishRedis(Model model) {
		logger.info("PublishRedis");
		String cache = publishrediscache.publishRedis();
		model.addAttribute("publishRedis", cache);
		return "publishredis";
	}
}
