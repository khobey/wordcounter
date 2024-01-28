package com.test.wordcounter.core.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.stream.Stream;

import com.test.wordcounter.core.interfaces.WordFrequencyCounter;
import com.test.wordcounter.error.BadInputException;

@Component
public class WordFrequencyCounterImpl implements WordFrequencyCounter {

	@Autowired
	public WordFrequencyCounterImpl(WordFrequencyCounterCache cache)
	{
		this.cache = cache;
	}

	public Map<String, Integer> getKMostFrequentWord(MultipartFile inputFile, int k) throws Exception {
		
		validateInputs(inputFile, k);
		
		String key = cache.generateCheckSum(inputFile.getBytes());
		
		if(cache.containsData(key))
		{
			logger.debug("Data already cached.");
			orderedQueue  = cache.getStoredData(key);
		}
		else
		{
			logger.debug("Data not found in cache. Computing...");
			InputStream input;
			try {
				input = inputFile.getInputStream();
				Stream<String> stream = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))
						.lines();
				stream.forEach(line -> {
					countWords(wordCountHashMap, line);
				});
			} catch (Exception e) {
				logger.error("Error occurred duing processing", e);
				throw e;
			}

			orderWords(orderedQueue, wordCountHashMap);
			cacheData(key,orderedQueue );
		}

		
		return retrieveMostFrequentWords(orderedQueue, k);
	}

	private void validateInputs(MultipartFile inputFile, int k) throws BadInputException {
		if (inputFile == null || inputFile.isEmpty()) {
			logger.error("Input file is null");
			throw new BadInputException("Input file is null");
		}
		
		if(k < 0)
		{
			logger.error("Value of K is less than 0");
			throw new BadInputException("Value of K is less than 0");
		}
	}
	
	private void countWords(HashMap<String, Integer> wordCountHashMap, String line) {
		String[] words = line.split(" ");

		for (int i = 0; i < words.length; i++) {
			String currentString = words[i].replaceAll(NON_ASCII_FILTER, "");
			wordCountHashMap.compute(currentString, (k, v) -> v == null ? 1 : v + 1);
		}
	}
	
	private void orderWords(PriorityQueue<Entry<String, Integer>> orderedQueue, HashMap<String, Integer> wordCountHashMap)
	{
		for (Map.Entry<String, Integer> entry : wordCountHashMap.entrySet()) {
			orderedQueue.add(entry);
		}
	}

	private LinkedHashMap<String, Integer> retrieveMostFrequentWords(PriorityQueue<Entry<String, Integer>> orderedQueue, int k) {

		LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
		
		Iterator<Entry<String, Integer>> itr = orderedQueue.iterator();
		int i = 0;
		while (itr.hasNext() && i<k) {
			Entry<String, Integer> entry = itr.next();
			result.put(entry.getKey(), entry.getValue());
			i++;
		}
		return result;
	}
	
	private void cacheData(String key, PriorityQueue<Entry<String, Integer>> orderedQueue) throws NoSuchAlgorithmException
	{
		cache.storeData(key, orderedQueue);
	}


	private PriorityQueue<Map.Entry<String, Integer>> orderedQueue = new PriorityQueue<>((a, b) -> {
		return (b.getValue() - a.getValue());
	});

	private HashMap<String, Integer> wordCountHashMap = new HashMap<>();
	private String NON_ASCII_FILTER = "[^\\sa-zA-Z0-9]";
	private Logger logger = LogManager.getLogger(WordFrequencyCounterImpl.class);
	private WordFrequencyCounterCache cache;
}
