package com.coderwzt.rabbitmqprovider.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author CoderWZT
 * @Create on 2020/8/7.
 */
@RestController
@Api(tags = "RabbitMQ的Demo测试接口集")
public class SendMessageController {

  @Autowired
  private RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法

  @ApiOperation(value = "直连交换机")
  @GetMapping("/sendDirectMessage")
  public String sendDirectMessage() {
    String messageId = String.valueOf(UUID.randomUUID());
    String messageData = "test message, hello!";
    String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    Map<String,Object> map=new HashMap<>();
    map.put("messageId",messageId);
    map.put("messageData",messageData);
    map.put("createTime",createTime);
    //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
    rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
    return "ok";
  }

  @ApiOperation(value = "主题交换机1")
  @GetMapping("/sendTopicMessage1")
  public String sendTopicMessage1() {
    String messageId = String.valueOf(UUID.randomUUID());
    String messageData = "message: M A N ";
    String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    Map<String, Object> manMap = new HashMap<>();
    manMap.put("messageId", messageId);
    manMap.put("messageData", messageData);
    manMap.put("createTime", createTime);
    rabbitTemplate.convertAndSend("topicExchange", "topic.man", manMap);
    return "ok";
  }

  @ApiOperation(value = "主题交换机2")
  @GetMapping("/sendTopicMessage2")
  public String sendTopicMessage2() {
    String messageId = String.valueOf(UUID.randomUUID());
    String messageData = "message: woman is all ";
    String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    Map<String, Object> womanMap = new HashMap<>();
    womanMap.put("messageId", messageId);
    womanMap.put("messageData", messageData);
    womanMap.put("createTime", createTime);
    rabbitTemplate.convertAndSend("topicExchange", "topic.woman", womanMap);
    return "ok";
  }

  //消息推送成功,触发的是 ConfirmCallback 回调函数。
  @GetMapping("/sendFanoutMessage")
  @ApiOperation(value = "扇形交换机")
  public String sendFanoutMessage() {
    String messageId = String.valueOf(UUID.randomUUID());
    String messageData = "message: testFanoutMessage ";
    String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    Map<String, Object> map = new HashMap<>();
    map.put("messageId", messageId);
    map.put("messageData", messageData);
    map.put("createTime", createTime);
    rabbitTemplate.convertAndSend("fanoutExchange", null, map);
    return "ok";
  }

  //消息推送到server，但是在server里找不到交换机,触发的是 ConfirmCallback 回调函数。
  @GetMapping("/TestMessageAck")
  @ApiOperation(value = "测试生产者的回调函数1")
  public String TestMessageAck() {
    String messageId = String.valueOf(UUID.randomUUID());
    String messageData = "message: non-existent-exchange test message ";
    String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    Map<String, Object> map = new HashMap<>();
    map.put("messageId", messageId);
    map.put("messageData", messageData);
    map.put("createTime", createTime);
    rabbitTemplate.convertAndSend("non-existent-exchange", "TestDirectRouting", map);
    return "ok";
  }

  //消息推送到server，找到交换机了，但是没找到队列,触发的是 ConfirmCallback和RetrunCallback两个回调函数。
  @GetMapping("/TestMessageAck2")
  @ApiOperation(value = "测试生产者的回调函数2")
  public String TestMessageAck2() {
    String messageId = String.valueOf(UUID.randomUUID());
    String messageData = "message: lonelyDirectExchange test message ";
    String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    Map<String, Object> map = new HashMap<>();
    map.put("messageId", messageId);
    map.put("messageData", messageData);
    map.put("createTime", createTime);
    rabbitTemplate.convertAndSend("lonelyDirectExchange", "TestDirectRouting", map);
    return "ok";
  }

}
