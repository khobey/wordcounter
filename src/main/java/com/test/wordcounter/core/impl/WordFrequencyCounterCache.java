package com.test.wordcounter.core.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import org.springframework.stereotype.Component;

@Component
public class WordFrequencyCounterCache {
	
	private HashMap<String, PriorityQueue<Map.Entry<String, Integer>>> cache = new HashMap<>();
	
	public void storeData(String key, PriorityQueue<Map.Entry<String, Integer>> data )
	{
		cache.put(key, data);
	}
	
	public String generateCheckSum(byte[] data) throws NoSuchAlgorithmException
	{
		byte[] hash = MessageDigest.getInstance("SHA-256").digest(data);
		return new BigInteger(1, hash).toString(16);
	}

	public PriorityQueue<Map.Entry<String, Integer>> getStoredData(String key )
	{
		return cache.get(key);
	}
	public boolean containsData(String key )
	{
		return cache.containsKey(key);
	}
}
