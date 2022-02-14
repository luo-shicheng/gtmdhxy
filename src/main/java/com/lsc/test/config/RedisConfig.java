package com.lsc.test.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.ResourceUtils;

//@Configuration
public class RedisConfig {
    @Bean
    @ConditionalOnResource(resources = "classpath:singleServerConfig.yml")
    public RedissonClient redisson() throws Exception{
        Config config = Config.fromYAML(ResourceUtils.getFile("classpath:singleServerConfig.yml"));
        return Redisson.create(config);
    }

    @Bean
    public RBloomFilter<String> rBloomFilter(RedissonClient client){
        RBloomFilter<String> lsc = client.getBloomFilter("bloomFilter:lsc");
        lsc.tryInit(1000000L,0.03);
        lsc.add("1");
        lsc.add("2");
        lsc.add("3");
        lsc.add("4");
        lsc.add("5");
        return lsc;
    }

    @Bean
    @ConditionalOnMissingBean(RedisTemplate.class)
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory, RedisSerializer<Object> redisSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(redisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RedisSerializer<Object> redisSerializer() {
        //创建JSON序列化器
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setFilterProvider(new DefaultFilterProvider());//定制一个默认的全序列化
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //必须设置，否则无法将JSON转化为对象，会转化成Map类型
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);
        return serializer;
    }


    /*
     *  定制一个默认的全序列化
     */
    public static class DefaultFilterProvider extends FilterProvider {

        @Override
        public BeanPropertyFilter findFilter(Object filterId) {
            throw new UnsupportedOperationException("Access to deprecated filters not supported");
        }

        @Override
        public PropertyFilter findPropertyFilter(Object filterId, Object valueToFilter) {
            return SimpleBeanPropertyFilter.serializeAll();
        }
    }
}
