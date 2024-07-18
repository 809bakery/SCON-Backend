package com.example.scon.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
@EnableRedisRepositories
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    // RedisProperties로 properties에 저장한 host, post를 연결
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    /* redis 실행방법
    1. Redis 설치 - https://github.com/microsoftarchive/redis/releases/tag/win-3.0.504
    2. 설치경로의 redis-cli.exe를 실행하여 아래와 같이 비밀번호를 설정한다.
        2-1. config get requirepass -> 초기 비밀번호 확인 "requurepass"와 ""이 존재하면 비밀번호 초기설정이 필요한 단계
        2-2. config set requirepass scon -> 초기 비밀번호를 'scon'으로 설정
        2-3. auth 비밀빈호 -> redis 연결
    3. shutdown, exit -> 순서대로 입력하여 redis-cli.exe 종료
    4. 관리자권한 cmd에 Redis 설치 경로에 들어가 'redis-server.exe redis.windows.conf'를 입력하여 Redis 실행

    5. 인증메일 전송 후, redis-cli.exe에서 "keys *"를 통해 인증코드 확인
    */
}
