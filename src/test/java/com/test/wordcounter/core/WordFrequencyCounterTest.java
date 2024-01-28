package com.test.wordcounter.core;

import java.io.File;
import java.util.Map;
import com.test.wordcounter.core.interfaces.WordFrequencyCounter;
import com.test.wordcounter.core.impl.WordFrequencyCounterImpl;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

class WordFrequencyCounterImplTest {

	@Test
	void testWordFrequencyCounter_ShortText() {
		File file = new File("src/test/resources/BasicText.txt");

		WordFrequencyCounter counter = new WordFrequencyCounterImpl();
		Map<String, Integer> result = counter.getKMostFrequentWord(file, 10);

		Assert.state(10 == result.size(), "Should return 10");
		Assert.isTrue(result.entrySet().stream().findFirst().get().getKey().equals("you"), "Should return 'you'");
	}

	@Test
	void testWordFrequencyCounter_BlankFile() {
		File file = new File("src/test/resources/EmptyText.txt");

		WordFrequencyCounter counter = new WordFrequencyCounterImpl();
		Map<String, Integer> result = counter.getKMostFrequentWord(file, 10);

		Assert.state(0 == result.size(), "Should return 0");
	}

	@Test
	void testWordFrequencyCounter_ResultLargerThanK() {
		File file = new File("src/test/resources/ResultLargerThanK.txt");

		WordFrequencyCounter counter = new WordFrequencyCounterImpl();
		Map<String, Integer> result = counter.getKMostFrequentWord(file, 4);

		Assert.state(2 == result.size(), "Should return 0");
		Assert.isTrue(result.entrySet().stream().findFirst().get().getKey().equals("apple"), "Should return 'apple'");
	}

	@Test
	void testWordFrequencyCounter_NullFile() {
		WordFrequencyCounter counter = new WordFrequencyCounterImpl();
		Map<String, Integer> result = counter.getKMostFrequentWord(null, 10);
	}

	@Test
	void testWordFrequencyCounter_CountDifferentCases() {
		File file = new File("src/test/resources/DifferentCases.txt");

		WordFrequencyCounter counter = new WordFrequencyCounterImpl();
		Map<String, Integer> result = counter.getKMostFrequentWord(file, 4);

		Assert.state(4 == result.size(), "Should return 3");
		Assert.isTrue(result.entrySet().stream().findFirst().get().getKey().equals("apple"), "Should return 'apple'");
	}

	@Test
	void testWordFrequencyCounter_LargeFile() {

	}

}
