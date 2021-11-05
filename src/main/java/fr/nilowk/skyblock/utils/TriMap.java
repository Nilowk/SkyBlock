package fr.nilowk.skyblock.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class TriMap<K, V, X> {

    private Map<K, HashMap<V, X>> triMap;

    public TriMap() {
        this.triMap = new HashMap<>();
    }

    public void put(K key, V value, X secondValue) {
        if (getKeys().contains(key)) {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        HashMap<V, X> hashMap = new HashMap<>();
        hashMap.put(value, secondValue);
        triMap.put(key, hashMap);
    }

    public void replace(K key, V newValue) {
        if (getKeys().contains(key)) {
            X secondValue = null;
            for (Map.Entry<V, X> map : triMap.get(key).entrySet()) {
                secondValue = map.getValue();
                triMap.get(key).remove(map.getKey());
            }
            triMap.get(key).put(newValue, secondValue);
        }
    }

    public void replaceS(K key, X newSecondValue) {
        if (getKeys().contains(key)) {
            V value = null;
            for (Map.Entry<V, X> map : triMap.get(key).entrySet()) {
                value = map.getKey();
                triMap.get(key).remove(map.getKey());
            }
            triMap.get(key).put(value, newSecondValue);
        }
    }

    public V getValue(K key) {
        if (getKeys().contains(key)) {
            for (Map.Entry<V, X> map : triMap.get(key).entrySet()) {
                return map.getKey();
            }
        }
        return null;
    }

    public X getSecondValue(K key) {
        if (getKeys().contains(key)) {
            for (Map.Entry<V, X> map : triMap.get(key).entrySet()) {
                return map.getValue();
            }
        }
        return null;
    }

    public void remove(K key) {
        if (getKeys().contains(key)) {
            triMap.remove(key);
        }
    }

    public void clear() {
        triMap.clear();
    }

    public int getSize() {
        return getKeys().size();
    }

    public void forEachKeys(Consumer<? super K> action) {
        getKeys().forEach(action);
    }

    private List<K> getKeys() {
        List<K> keys = new ArrayList<>();
        for (Map.Entry<K, HashMap<V, X>> key : triMap.entrySet()) {
            keys.add(key.getKey());
        }
        return keys;
    }

}
