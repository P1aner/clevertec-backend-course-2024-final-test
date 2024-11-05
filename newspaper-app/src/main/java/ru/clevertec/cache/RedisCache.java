package ru.clevertec.cache;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

public class RedisCache<K, V> implements Cache<K, V> {

    private final RedisTemplate<String, V> redisTemplate;

    public RedisCache(RedisTemplate<String, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Optional<V> get(K key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key.toString()));
    }

    @Override
    public void put(K key, V value) {
        redisTemplate.opsForValue().set(key.toString(), value);
    }

    @Override
    public void delete(K key) {
        redisTemplate.delete(key.toString());
    }
}
