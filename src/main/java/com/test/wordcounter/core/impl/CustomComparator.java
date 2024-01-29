package com.test.wordcounter.core.impl;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

public class CustomComparator implements Comparator<Map.Entry<String, Integer>>{

	@Override
	public int compare(Entry<String, Integer> a, Entry<String, Integer> b) {
		int result = b.getValue()-a.getValue();
        if(result == 0)
            return a.getKey().compareTo(b.getKey());
        return result;
	}

}
