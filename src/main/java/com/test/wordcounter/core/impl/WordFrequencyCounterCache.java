package com.test.wordcounter.core.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

@Component
public class WordFrequencyCounterCache {
	
	private static ConcurrentHashMap<String, PriorityQueue<Map.Entry<String, Integer>>> cache = new ConcurrentHashMap<>();
	
	public void storeData(String key, PriorityQueue<Map.Entry<String, Integer>> data )
	{
		PriorityQueue<Entry<String, Integer>> storedQueue = new PriorityQueue<Entry<String, Integer>>(new CustomComparator());
		storedQueue.addAll(data);
		cache.put(key, storedQueue);
	}
	
	public String generateCheckSum(byte[] data) throws NoSuchAlgorithmException
	{
		byte[] hash = MessageDigest.getInstance("SHA-256").digest(data);
		return new BigInteger(1, hash).toString(16);
	}

	public PriorityQueue<Map.Entry<String, Integer>> getStoredData(String key )
	{

		PriorityQueue<Entry<String, Integer>> result = new PriorityQueue<Entry<String, Integer>>(new CustomComparator());
		result.addAll(cache.get(key));
		return result;
	}
	
	public boolean containsData(String key )
	{
		return cache.containsKey(key);
	}
}
