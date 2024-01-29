package com.test.wordcounter.core;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.PriorityQueue;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.test.wordcounter.core.impl.WordFrequencyCounterCache;

public class WordFrequencyCounterCacheTest {

	@Test
	public void testCache_SingleEntry() throws NoSuchAlgorithmException
	{
		byte[] testdata = "Zapdos is cool".getBytes();
		WordFrequencyCounterCache cache = new WordFrequencyCounterCache();
		
		String key = cache.generateCheckSum(testdata);
		cache.storeData(key, new PriorityQueue<Map.Entry<String, Integer>>());
		Assert.isTrue(cache.containsData(key), "Should contain the key");
		Assert.isTrue(cache.getStoredData(key) != null, "Should not be null");
	}
	
	@Test
	public void testCache_MultipleSameEntry() throws NoSuchAlgorithmException
	{
		byte[] testdata = "Zapdos is cool".getBytes();
		WordFrequencyCounterCache cache = new WordFrequencyCounterCache();
		
		String key = cache.generateCheckSum(testdata);
		cache.storeData(key, new PriorityQueue<Map.Entry<String, Integer>>());
		
		String key2 = cache.generateCheckSum(testdata);
		Assert.state(key.equals(key2), "Should be equal");
		Assert.isTrue(cache.containsData(key2), "Should contain the key");
	}
	
	@Test
	public void testCache_NotExisting() throws NoSuchAlgorithmException
	{
		byte[] testdata = "Zapdos is cool".getBytes();
		WordFrequencyCounterCache cache = new WordFrequencyCounterCache();
		
		String key = cache.generateCheckSum(testdata);
		cache.storeData(key, new PriorityQueue<Map.Entry<String, Integer>>());
		
		byte[] testdata2 = "But Articuno is cooler".getBytes();
		String key2 = cache.generateCheckSum(testdata2);
		Assert.state(false == cache.containsData(key2), "Should NOT contain the key");
	}
}
