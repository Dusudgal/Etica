//package com.eticaplanner.eticaPlanner.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericToStringSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//@Configuration
//public class RedisConfig {
//
//    @Bean  // 스프링 컨테이너에 RedisTemplate 빈을 등록
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        // RedisTemplate 객체 생성
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//
//        // Redis 연결을 위한 ConnectionFactory 설정
//        template.setConnectionFactory(redisConnectionFactory);
//
//        // Redis에서 사용할 key를 문자열로 직렬화하는 방식 설정
//        template.setKeySerializer(new StringRedisSerializer());
//
//        // Redis에서 value를 직렬화하는 방식 설정
//        template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
//
//        // 완성된 RedisTemplate 객체 반환
//        return template;
//    }
//}
