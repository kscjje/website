package com.hisco.cmm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

import com.hisco.cmm.config.SessionMapConfig.RedisSessionNotUseCondition;

import egovframework.com.cmm.service.EgovProperties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@Conditional(RedisSessionNotUseCondition.class)
// TODO: 필요시 주석제거
@EnableSpringHttpSession /* (maxInactiveIntervalInSeconds = 60)//세션만료시간 */
public class SessionMapConfig {

    @Bean
    public SessionRepository sessionRepository() {
        return new MapSessionRepository();
    }

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("MSESSIONID");
        serializer.setCookiePath("/");
        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
        return serializer;
    }

    static class RedisSessionNotUseCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            log.debug("session.redis.useyn = {}", EgovProperties.getProperty("session.redis.useyn"));
            return !"Y".equalsIgnoreCase(EgovProperties.getProperty("session.redis.useyn"));
        }

    }
}
