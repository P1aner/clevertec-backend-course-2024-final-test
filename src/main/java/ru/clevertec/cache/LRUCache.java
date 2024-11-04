package ru.clevertec.cache;

import jakarta.validation.constraints.Min;

import java.util.LinkedHashMap;
import java.util.Optional;

public class LRUCache<K, V> implements Cache<K, V> {

    private final @Min(0) int capacity;
    private final LinkedHashMap<K, V> map;

    public LRUCache(int capacity) {
        this.map = new LinkedHashMap<>(capacity);
        this.capacity = capacity;
    }

    @Override
    public Optional<V> get(K key) {
        Optional<V> result = Optional.ofNullable(map.get(key));
        if (result.isPresent()) {
            moveToFirst(key);
        }
        return result;
    }

    @Override
    public void put(K key, V value) {
        if (capacity == 0) return;

        map.remove(key);
        if (map.size() == capacity) {
            map.pollLastEntry();
        }
        map.putFirst(key, value);
    }

    @Override
    public void delete(K key) {
        map.remove(key);
    }

    private void moveToFirst(K key) {
        V value = map.get(key);
        map.remove(key);
        map.putFirst(key, value);
    }
}
