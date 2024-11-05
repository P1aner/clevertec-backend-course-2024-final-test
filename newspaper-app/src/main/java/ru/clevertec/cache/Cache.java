package ru.clevertec.cache;

import java.util.Arrays;
import java.util.Optional;

public interface Cache<K, V> {

    Optional<V> get(K key);

    void put(K key, V value);

    void delete(K key);

    default String generateKey(Object... args) {
        return Arrays.toString(args);
    }
}
