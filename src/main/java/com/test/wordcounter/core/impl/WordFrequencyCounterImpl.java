package com.test.wordcounter.core.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.stream.Stream;

import com.test.wordcounter.core.interfaces.WordFrequencyCounter;
import com.test.wordcounter.error.BadInputException;

@Component
public class WordFrequencyCounterImpl implements WordFrequencyCounter {

	private PriorityQueue<Map.Entry<String, Integer>> orderedQueue = new PriorityQueue<>((a, b) -> {
		return (b.getValue() - a.getValue());
	});

	private HashMap<String, Integer> wordCountHashMap = new HashMap<>();
	private String NON_ASCII_FILTER = "[^\\sa-zA-Z0-9]";
	private Logger logger = LogManager.getLogger(WordFrequencyCounterImpl.class);

	public Map<String, Integer> getKMostFrequentWord(MultipartFile inputFile, int k) throws Exception {
		if (inputFile == null) {
			logger.error("Input file is null");
			throw new BadInputException("Input file is null");
		}

		InputStream input;
		try {
			input = inputFile.getInputStream();
			Stream<String> stream = new BufferedReader(
					new InputStreamReader(input, StandardCharsets.UTF_8))
					.lines();
			stream.forEach(line -> {
				countWords(wordCountHashMap, line);
			});
		} catch (Exception e) {
			logger.error("Error occurred duing processing", e);
			throw e;
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

		for (int i = 0; i < k && i < wordCountHashMap.size(); ++i) {
			Entry<String, Integer> entry = orderedQueue.poll();
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}
}
