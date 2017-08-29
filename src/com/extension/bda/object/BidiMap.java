package com.extension.bda.object;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BidiMap<K, V> implements Map<K, V> {

	private Map<K, V> keyToValueMap = new ConcurrentHashMap<K, V>();
	private Map<V, K> valueToKeyMap = new ConcurrentHashMap<V, K>();
	
	@Override
	public int size() {
		return keyToValueMap.size();
	}

	@Override
	public boolean isEmpty() {
		return keyToValueMap.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return keyToValueMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return valueToKeyMap.containsKey(value);
	}

	@Override
	public V get(Object key) {
		return keyToValueMap.get(key);
	}

	public K getKey(Object value){
		return valueToKeyMap.get(value);
	}
	
	@Override
	public V put(K key, V value) {
		valueToKeyMap.put(value, key);
		return keyToValueMap.put(key, value);
	}

	@Override
	public V remove(Object key) {
		valueToKeyMap.remove(keyToValueMap.get(key));
		return keyToValueMap.remove(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		keyToValueMap.putAll(m);
		for(K key : m.keySet()){
			valueToKeyMap.put(m.get(key), key);
		}
	}

	@Override
	public void clear() {
		keyToValueMap.clear();
		valueToKeyMap.clear();
	}

	@Override
	public Set<K> keySet() {
		return keyToValueMap.keySet();
	}

	@Override
	public Collection<V> values() {
		return keyToValueMap.values();
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return keyToValueMap.entrySet();
	}

}
