package com.test.wordcounter.result;

import java.util.LinkedHashMap;

public class WordResult {
	
	public LinkedHashMap<String, Integer> getResult() {
		return result;
	}

	public void setResult(LinkedHashMap<String, Integer> result) {
		this.result = result;
	}

	private LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
}
