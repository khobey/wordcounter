package com.test.wordcounter.core.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.PriorityQueue;

import com.test.wordcounter.core.interfaces.WordFrequencyCounter;

public class WordFrequencyCounterImpl implements WordFrequencyCounter{

	private PriorityQueue<Map.Entry<String, Integer>> orderedQueue = new PriorityQueue<>((a, b) -> {
		return (b.getValue() - a.getValue());
	});

	private HashMap<String, Integer> wordCountHashMap = new HashMap<>();
	private String NON_ASCII_FILTER = "[^\\sa-zA-Z0-9]"; 

	public Map<String, Integer> getKMostFrequentWord(File inputFile, int k) {
		if (inputFile == null) {
			return new LinkedHashMap<>();
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line;
			while ((line = reader.readLine()) != null) {
				countWords(wordCountHashMap, line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retrieveMostFrequentWords(orderedQueue, wordCountHashMap, k);
	}

	private void countWords(HashMap<String, Integer> wordCountHashMap, String line) {
		String[] words = line.split(" ");

		for (int i = 0; i < words.length; i++) {
			String currentString = words[i].replaceAll(NON_ASCII_FILTER, "");
			wordCountHashMap.compute(currentString, (k, v) -> v == null ? 1 : v + 1);
		}
	}

	private LinkedHashMap<String, Integer> retrieveMostFrequentWords(PriorityQueue<Entry<String, Integer>> orderedQueue,
			HashMap<String, Integer> wordCountHashMap, int k) {

		LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
		
		for (Map.Entry<String, Integer> entry : wordCountHashMap.entrySet()) {
			orderedQueue.add(entry);
		}

		for (int i = 0; i < k; ++i) {
			Entry<String, Integer> entry = orderedQueue.poll();
			result.put(entry.getKey(), entry.getValue());
		}
		
		return result;
	}
}
