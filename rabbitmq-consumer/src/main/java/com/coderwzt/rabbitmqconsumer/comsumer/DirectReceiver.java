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
@RabbitListener(queues = "TestDirectQueue")   //监听的队列名称 TestDirectQueue
public class DirectReceiver {

  @RabbitHandler
  public void process(Map msg){
    System.out.println("接收到的消息：" + msg.toString());
  }

}
