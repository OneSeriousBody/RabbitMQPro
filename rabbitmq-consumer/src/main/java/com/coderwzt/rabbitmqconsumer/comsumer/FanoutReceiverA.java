package com.coderwzt.rabbitmqconsumer.comsumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author CoderWZT
 * @Create on 2020/8/7.
 */
@Component
@RabbitListener(queues = "fanout.A")
public class FanoutReceiverA {

  @RabbitHandler
  public void process(Map testMessage) {
    System.out.println("FanoutReceiverA消费者收到消息  : " +testMessage.toString());
  }

}
