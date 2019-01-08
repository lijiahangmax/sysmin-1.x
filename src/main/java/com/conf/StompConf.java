package com.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * @author:Li
 * @time: 2019/1/2 9:59
 * @version: 1.0.0
 * 注解开启使用STOMP协议来传输基于代理(message broker)的消息,这时控制器支持使用@MessageMapping,就像使用@RequestMapping一样
 */
@Configuration
@EnableWebSocketMessageBroker
public class StompConf extends AbstractWebSocketMessageBrokerConfigurer {

    /**
     * 配置Stomp的节点
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册一个Stomp的节点（endpoint）,并指定使用SockJS协议, 映射指定的url
        registry.addEndpoint("/sock").withSockJS();
        // 跨域消息推送
        /// registry.addEndpoint("/sock").setAllowedOrigins("*").withSockJS();
    }

    /**
     * 配置消息代理(Message Broker)
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 注册多个（参数是可变参数）服务端接收消息前缀
        registry.setApplicationDestinationPrefixes("/app");
        // 注册多个（参数是可变参数）服务端发送消息前缀
        registry.enableSimpleBroker("/queue", "/topic", "/user");
        // 注册 服务端接收1对1消息前缀
        registry.setUserDestinationPrefix("/user/");
    }

}
