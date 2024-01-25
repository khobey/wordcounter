package com.test.wordcounter.core;


import java.io.File;
import java.util.Map;
import com.test.wordcounter.core.interfaces.WordFrequencyCounter;
import com.test.wordcounter.core.impl.WordFrequencyCounterImpl;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;


class WordFrequencyCounterTest {

	@Test
	void testWordFrequencyCounter_ShortText_Successful() {
		File file = new File("src/test/resources/BasicText.txt");
		
		WordFrequencyCounter counter = new WordFrequencyCounterImpl();
		Map<String, Integer> result = counter.getKMostFrequentWord(file, 10);
		
		Assert.state(10 == result.size(), "Should return 10");
		Assert.isTrue(result.entrySet().stream().findFirst().get().getKey().equals("you"), "Should return true");
	}

}
