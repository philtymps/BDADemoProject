/*******************************************************************************
 * IBM Confidential
 * OCO Source Materials
 * IBM Call Center for Commerce (5725-P82)
 * (C) Copyright IBM Corp. 2013 
 * The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
 ******************************************************************************/
package com.ibm.mobile.dataprovider;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.yantra.yfc.util.YFCCommon;

public class BDASimpleCache<K, V> {
	
	public long flushTime = 7200000;
	private final Map<K, V> map;
	private long timestamp;
	
	public BDASimpleCache(final int maxsize) {
		map = Collections.synchronizedMap(new LinkedHashMap<K, V>(16, 0.75f, true) {
			private static final long serialVersionUID = 1L;
			@Override
			protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
				return size() > maxsize;
			}
		});
		timestamp = System.currentTimeMillis();
	}
	
	public BDASimpleCache(final int maxsize, final int flushTimeMillis) {
		map = Collections.synchronizedMap(new LinkedHashMap<K, V>(16, 0.75f, true) {
			private static final long serialVersionUID = 1L;
			@Override
			protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
				return size() > maxsize;
			}
		});
		timestamp = System.currentTimeMillis();
		
		this.flushTime = flushTimeMillis;
	}
	
	public void autoFlush(){
		if (System.currentTimeMillis() - timestamp > this.flushTime){
			clear();
			timestamp = System.currentTimeMillis();
		}
	}
	public V get(K key) {
		return map.get(key);
	}
	public void put(K key, V value) {
		map.put(key, value);
	}
	public boolean containsKey(K key) {
		autoFlush();
		return map.containsKey(key) && !YFCCommon.isVoid(map.get(key));
	}
	public void clear(){
		map.clear();
	}
}
