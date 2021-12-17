package com.kafka;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MfTwitterClient
{
    static Logger LOG = LoggerFactory.getLogger(MfTwitterClient.class.getName());

    public static void main(String ...ar) throws InterruptedException
    {
        /** Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream */
        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100000);

        Client twitterClient = createTwitterClient(msgQueue);

        twitterClient.connect();

        // on a different thread, or multiple different threads....
        while (!twitterClient.isDone()) {
            String msg = msgQueue.take();
            LOG.info("Message: "+msg);
        }

    }

    public static Client createTwitterClient(BlockingQueue<String> msgQueue)
    {
        String consumerKey="tR8x6EZ9j2bWTByAvf9CyMrUC";
        String consumerSecret="QCdEqYE40s3Fu0O3qpYj4HF4pHPUBCMtYQe5LRXqrKjkcymmWs";
        String token="67892850-P8AMdw4WTiuarH48MCy2LwJKWVR9mdvhTK3FSL2Lc";
        String secret="hzIY0K1Ll5j570XwM0r2j8AhCeW5DK08gSlmPdesBFduK";
        /** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
        // Optional: set up some followings and track terms
        List<String> terms = Lists.newArrayList("kafka");
        hosebirdEndpoint.trackTerms(terms);

        // These secrets should be read from a config file
        Authentication hosebirdAuth = new OAuth1(consumerKey, consumerSecret, token, secret);

        ClientBuilder builder = new ClientBuilder()
                .name("Hosebird-Client-01")                              // optional: mainly for the logs
                .hosts(hosebirdHosts)
                .authentication(hosebirdAuth)
                .endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue));

        return builder.build();
    }
}
