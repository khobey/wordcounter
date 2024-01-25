package com.test.wordcounter.restservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.wordcounter.core.interfaces.WordFrequencyCounter;
import com.test.wordcounter.result.WordResult;

@RestController
public class WordFrequencyCounterAPI 
{

	@Autowired
	WordFrequencyCounter counter;
	
	@PostMapping("/api/getKMostFrequentWords")
	public ResponseEntity<?> getMostKMostFrequentWord(@PathVariable("file") MultipartFile file, @PathVariable("K") int k)
	{
		return ResponseEntity.ok().body(new WordResult());
	}
}  
