package com.hide.amqp;

import com.hide.entity.Order;
import com.hide.entity.User;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Zhiwei Jian
 * @create 1/10/20 10:17 AM
 */
@RestController
public class MqController {

//    @Autowired
//    RabbitAdmin rabbitAdmin;
//
//    @Autowired
//    RabbitTemplate rabbitTemplate;

    @Autowired
    RabbitSender rabbitSender;

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @RequestMapping("/api/testMQ3")
    public String testMq3(@RequestParam("routingKey") String routingKey) throws Exception {
        send(routingKey);
        return "789";
    }

    @PostMapping("/api/user")
    public boolean sendUserMsg(@RequestParam("routingKey") String routingKey,
            @RequestParam("exchangeName") String exchangeName, @RequestBody List<User> users)
            throws Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put("data_type", "user");
        rabbitSender.directSend(users.get(0), properties, routingKey, exchangeName);
        return true;
    }

    @PostMapping("/api/order")
    public boolean sendOrderMsg(@RequestParam("routingKey") String routingKey,
            @RequestParam("exchangeName") String exchangeName, @RequestBody List<Order> orders)
            throws Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put("data_type", "order");
        rabbitSender.directSend(orders.get(0), properties, routingKey, exchangeName);
        return true;
    }

    public void send(String routingKey) throws Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put("number", "12345");
        properties.put("send_time", simpleDateFormat.format(new Date()));
        rabbitSender.send("Hello RabbitMQ For Spring Boot!", properties, "user.abcd");
        rabbitSender.send("Hello XXXX For Spring Boot!", properties, routingKey);
    }

//    @RequestMapping("/api/testMQ1")
//    public String testMq1(){
//        register();
//        return "123";
//    }
//
//    @RequestMapping("/api/testMQ2")
//    public String testMq2(){
//        sendMessage();
//        return "456";
//    }
//
//    public void sendMessage() {
//        //1 创建消息
//        MessageProperties messageProperties = new MessageProperties();
//        //设置消息属性
//        messageProperties.getHeaders().put("desc", "This is desc..");
//        messageProperties.getHeaders().put("type", "This is type..");
//        Message message = new Message("Hello RabbitMQ".getBytes(), messageProperties);
//
//        //发送消息
//        rabbitTemplate.convertAndSend("test.topic", "user.amqp", message, new MessagePostProcessor() {
//            @Override
//            public Message postProcessMessage(Message message) throws AmqpException {
//                System.err.println("------A B C D---------");
//                message.getMessageProperties().getHeaders().put("desc", "xiao xiao dao");
//                message.getMessageProperties().getHeaders().put("attr", "da da jian");
//                return message;
//            }
//        });
//    }
//
//    public void register() {
//        rabbitAdmin.declareExchange(new DirectExchange("test.direct", false, false));
//        rabbitAdmin.declareExchange(new TopicExchange("test.topic", false, false));
//        rabbitAdmin.declareExchange(new FanoutExchange("test.fanout", false, false));
//
//        rabbitAdmin.declareQueue(new Queue("test.direct.queue", false));
//        rabbitAdmin.declareQueue(new Queue("test.topic.queue", false));
//        rabbitAdmin.declareQueue(new Queue("test.fanout.queue", false));
//
//        rabbitAdmin.declareBinding(new Binding("test.direct.queue",
//                Binding.DestinationType.QUEUE,
//                "test.direct", "direct", new HashMap<>()));
//
//        //一步到位
//        rabbitAdmin.declareBinding(
//                BindingBuilder
//                        .bind(new Queue("test.topic.queue", false)) //直接创建队列
//                        .to(new TopicExchange("test.topic", false, false)) //直接创建交换机 建立关联关系
//                        .with("user.#") //指定路由Key
//        );
//
//        //fanout交换机不需要routingKey
//        rabbitAdmin.declareBinding(
//                BindingBuilder
//                        .bind(new Queue("test.fanout.queue", false))
//                        .to(new FanoutExchange("test.fanout", false, false)));
//    }
}
