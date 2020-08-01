package com.agmtopy.testcontainers;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.Assert.assertEquals;

/**
 * redis 容器测试
 */
@Testcontainers
public class RedisTestContainersTest {

    private RedisCommands<String,String> redisCommands;

    /**
     * 初始化redis 容器
     */
    @Container
    public GenericContainer redis = new GenericContainer("redis:5.0.3-alpine")
            .withExposedPorts(6379);

    @BeforeEach
    public void setUp() {
        String address = redis.getHost();
        Integer port = redis.getFirstMappedPort();
        //初始化redis客户端
        redisCommands = RedisClient.create(String.format("redis://%s:%d/0", address, port)).connect().sync();
    }

    @Test
    public void testSimplePutAndGet() {
        redisCommands.set("test", "example");
        String retrieved = redisCommands.get("test");
        System.out.println("retrieved : " + retrieved);
        assertEquals("example", retrieved);
    }
}
