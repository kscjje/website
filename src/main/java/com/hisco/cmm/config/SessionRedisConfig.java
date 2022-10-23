package com.hisco.cmm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

import com.hisco.cmm.config.SessionRedisConfig.RedisSessionUseCondition;

import egovframework.com.cmm.service.EgovProperties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// TODO: 개발반영전 주석풀기
@Configuration
@Conditional(RedisSessionUseCondition.class)
@EnableRedisHttpSession /* (maxInactiveIntervalInSeconds = 60)//세션만료시간 */
public class SessionRedisConfig extends AbstractHttpSessionApplicationInitializer {
    // public class SessionRedisConfig {

    @Value("${session.redis.host}")
    String host;

    @Value("${session.redis.port}")
    String port;

    @Value("${session.redis.password}")
    String password;

    @Bean
    public JedisConnectionFactory connectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(Integer.parseInt(port));
        jedisConnectionFactory.setPassword(password);
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.afterPropertiesSet();
        return jedisConnectionFactory;
    }

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("JSESSIONID");
        serializer.setCookiePath("/");
        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
        return serializer;
    }

    static class RedisSessionUseCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            log.debug("session.redis.useyn = {}", EgovProperties.getProperty("session.redis.useyn"));
            return "Y".equalsIgnoreCase(EgovProperties.getProperty("session.redis.useyn"));
        }
    }
}