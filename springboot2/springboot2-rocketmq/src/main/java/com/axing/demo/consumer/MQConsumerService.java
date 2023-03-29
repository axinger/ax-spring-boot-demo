package com.axing.demo.consumer;

import com.alibaba.fastjson2.JSON;
import com.axing.demo.config.Topic;
import com.axing.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
public class MQConsumerService {

    // topic需要和生产者的topic一致，consumerGroup属性是必须指定的，内容可以随意
    // selectorExpression的意思指的就是tag，默认为“*”，不设置的话会监听所有消息
    @Service
    @RocketMQMessageListener(
            consumerGroup = "Con_Group_One",
            topic = Topic.RLT_TEST_TOPIC,
            selectorExpression = "tag1")
    public class ConsumerSendTag1 implements RocketMQListener<User> {
        // 监听到消息就会执行此方法
        @Override
        public void onMessage(User user) {
            log.info("Con_Group_One tag1 监听到消息：user={}", JSON.toJSONString(user));
        }
    }

    @Service
    @RocketMQMessageListener(
            consumerGroup = "Con_Group_One",
            topic = Topic.RLT_TEST_TOPIC,
            selectorExpression = "tag2")
    public class ConsumerSendTag2 implements RocketMQListener<User> {
        @Override
        public void onMessage(User user) {
            log.info("Con_Group_One tag2 监听到消息：user={}", JSON.toJSONString(user));
        }
    }

    // 注意：这个ConsumerSend2和上面ConsumerSend在没有添加tag做区分时，不能共存，
    // 不然生产者发送一条消息，这两个都会去消费，如果类型不同会有一个报错，所以实际运用中最好加上tag，写这只是让你看知道就行
    @Service
    @RocketMQMessageListener(topic = Topic.RLT_TEST_TOPIC, consumerGroup = "Con_Group_Two")
    public class ConsumerSend2 implements RocketMQListener<String> {
        @Override
        public void onMessage(String str) {
            log.info("Con_Group_Two 监听到消息：str={}", str);
        }
    }

    // MessageExt：是一个消息接收通配符，不管发送的是String还是对象，都可接收，当然也可以像上面明确指定类型（我建议还是指定类型较方便）
    @Service
    @RocketMQMessageListener(topic = Topic.RLT_TEST_TOPIC, selectorExpression = "tag2", consumerGroup = "Con_Group_Three")
    public class Consumer implements RocketMQListener<MessageExt> {
        @Override
        public void onMessage(MessageExt messageExt) {
            byte[] body = messageExt.getBody();
            String msg = new String(body);
            log.info("Con_Group_Three 监听到消息：msg={}", msg);
        }
    }

    @Service
    @RocketMQMessageListener(topic = "TBW102", consumerGroup = "Con_Group_Three")
    public class Consumer1 implements RocketMQListener<MessageExt> {
        @Override
        public void onMessage(MessageExt messageExt) {
            byte[] body = messageExt.getBody();
            String msg = new String(body);
            log.info("Con_Group_Three 监听到消息：msg={}", msg);
        }
    }

}
