package be.ordina.jworks.gaming.config;

import be.hubau.spring.social.redis.EnableSocialRedis;
import be.ordina.jworks.gaming.controller.CustomConnectController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import redis.clients.jedis.JedisShardInfo;

@Configuration
@EnableSocialRedis
public class SocialConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new JedisConnectionFactory(new JedisShardInfo("192.168.99.100"));
    }

    @Bean
    public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
        return new CustomConnectController(connectionFactoryLocator, connectionRepository);
    }
}
