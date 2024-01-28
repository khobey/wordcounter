package com.test.wordcounter.restservices;

import java.util.LinkedHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.wordcounter.core.interfaces.WordFrequencyCounter;
import com.test.wordcounter.error.BadInputException;
import com.test.wordcounter.result.WordResult;

@RestController
public class WordFrequencyCounterAPI 
{
	
	private Logger logger = LogManager.getLogger(WordFrequencyCounterAPI.class);
	private WordFrequencyCounter counterApp;

	@Autowired
	public WordFrequencyCounterAPI(WordFrequencyCounter counterApp)
	{
		this.counterApp = counterApp;
	}
	WordFrequencyCounter counter;
	
	@PostMapping("/api/getKMostFrequentWords")
	public ResponseEntity<?> getMostKMostFrequentWord(@RequestParam("file") MultipartFile file, @RequestParam("K") int k) throws BadInputException, Exception
	{
		logger.debug(" getMostKMostFrequentWord method called");
		WordResult result = new WordResult((LinkedHashMap<String, Integer>) counterApp.getKMostFrequentWord(file, k));
		
		return ResponseEntity.ok().body(result);
	}
}  
