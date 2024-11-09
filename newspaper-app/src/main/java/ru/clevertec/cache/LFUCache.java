package ru.clevertec.cache;

import jakarta.validation.constraints.Min;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LFUCache<K, V> implements Cache<K, V> {

    private final @Min(0) int capacity;
    private final Map<K, Pair<V>> map;
    private final LinkedHashSet<K> keySet;

    public LFUCache(int capacity) {
        this.map = HashMap.newHashMap(capacity);
        this.keySet = LinkedHashSet.newLinkedHashSet(capacity);
        this.capacity = capacity;
    }

    @Override
    public Optional<V> get(K key) {
        if (!map.containsKey(key)) {
            return Optional.empty();
        }
        Pair<V> pair = map.get(key);
        pair.hitsCount++;

        keySet.remove(key);
        keySet.add(key);

        return Optional.of(pair.value);
    }

    @Override
    public void put(K key, V value) {
        if (capacity == 0) return;

        if (map.containsKey(key)) {
            Pair<V> pair = map.get(key);
            pair.value = value;
            pair.hitsCount++;
            return;
        }

        if (map.size() == capacity) {
            K keyToDelete = determineKeyToDelete();
            delete(keyToDelete);
        }

        map.put(key, new Pair<>(value));
        keySet.add(key);
    }

    @Override
    public void delete(K key) {
        map.remove(key);
        keySet.remove(key);
    }

    private K determineKeyToDelete() {
        List<K> keys = keySet.stream().toList();
        return map.entrySet().stream()
            .min(Comparator.comparingInt((Map.Entry<K, Pair<V>> o) -> o.getValue().hitsCount)
                .thenComparingInt(o -> keys.indexOf(o.getKey()))).orElseThrow(RuntimeException::new)
            .getKey();
    }

    private class Pair<E> {
        private int hitsCount;
        private E value;

        public Pair(E value) {
            this.value = value;
        }
    }
}
