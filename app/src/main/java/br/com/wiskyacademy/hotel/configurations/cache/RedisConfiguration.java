package br.com.wiskyacademy.hotel.configurations.cache;

import java.time.Duration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

@EnableCaching
@Configuration
public class RedisConfiguration extends CachingConfigurerSupport {

  @Bean
  public RedisCacheConfiguration redisCacheConfiguration() {
    return RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofMinutes(60))
        .computePrefixWith(cacheName -> "hotel::" + cacheName + "::")
        .disableCachingNullValues();
  }

  @Bean("customKeyGenerator")
  public KeyGenerator keyGenerator() {
    return new CustomKeyGenerator();
  }
}