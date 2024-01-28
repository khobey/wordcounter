package com.test.wordcounter.core.interfaces;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.test.wordcounter.error.BadInputException;

@Component
public interface WordFrequencyCounter {

	public Map<String, Integer> getKMostFrequentWord(MultipartFile file, int K) throws BadInputException, Exception;
}
