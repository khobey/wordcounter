package com.test.wordcounter.core.interfaces;

import java.io.File;
import java.util.Map;

public interface WordFrequencyCounter {

	public Map<String, Integer> getKMostFrequentWord(File file, int K);
}
