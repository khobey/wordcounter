package com.test.wordcounter.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;
import com.test.wordcounter.core.interfaces.WordFrequencyCounter;
import com.test.wordcounter.error.BadInputException;
import com.test.wordcounter.core.impl.WordFrequencyCounterImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import com.test.wordcounter.core.impl.WordFrequencyCounterCache;

class WordFrequencyCounterImplTest {

	@Test
	void testWordFrequencyCounter_ShortText() throws BadInputException, Exception {
		File file = new File("src/test/resources/BasicText.txt");

		WordFrequencyCounter counter = new WordFrequencyCounterImpl(new WordFrequencyCounterCache());
		Map<String, Integer> result = counter.getKMostFrequentWord(new SampleFile(file), 10);

		Assert.state(10 == result.size(), "Should return 10");
		Assert.isTrue(result.entrySet().stream().findFirst().get().getKey().equals("you"), "Should return 'you'");
	}
	
	
	@Test
	void testWordFrequencyCounter_Caching() throws BadInputException, Exception {
		File file = new File("src/test/resources/BasicText.txt");

		WordFrequencyCounter counter = new WordFrequencyCounterImpl(new WordFrequencyCounterCache());
		Map<String, Integer> result = counter.getKMostFrequentWord(new SampleFile(file), 10);

		Assert.state(10 == result.size(), "Should return 10");
		Assert.isTrue(result.entrySet().stream().findFirst().get().getKey().equals("you"), "Should return 'you'");
		result = counter.getKMostFrequentWord(new SampleFile(file), 10);
		result = counter.getKMostFrequentWord(new SampleFile(file), 10);
		Assert.isTrue(result.entrySet().stream().findFirst().get().getKey().equals("you"), "Should return 'you'");

	}

	@Test
	void testWordFrequencyCounter_BlankFile() throws BadInputException, Exception {
		File file = new File("src/test/resources/EmptyText.txt");

		WordFrequencyCounter counter = new WordFrequencyCounterImpl(new WordFrequencyCounterCache());
		Map<String, Integer> result = counter.getKMostFrequentWord(new SampleFile(file), 10);

		Assert.state(0 == result.size(), "Should return 0");
	}

	@Test
	void testWordFrequencyCounter_ResultLargerThanK() throws BadInputException, Exception {
		File file = new File("src/test/resources/ResultLargerThanK.txt");

		WordFrequencyCounter counter = new WordFrequencyCounterImpl(new WordFrequencyCounterCache());
		Map<String, Integer> result = counter.getKMostFrequentWord(new SampleFile(file), 4);

		Assert.state(2 == result.size(), "Should return 0");
		Assert.isTrue(result.entrySet().stream().findFirst().get().getKey().equals("apple"), "Should return 'apple'");
	}

	@Test
	void testWordFrequencyCounter_NullFile() throws BadInputException, Exception {
		WordFrequencyCounter counter = new WordFrequencyCounterImpl(new WordFrequencyCounterCache());
		Assertions.assertThrows(Exception.class, () ->  counter.getKMostFrequentWord(null, 10));
	}

	@Test
	void testWordFrequencyCounter_CountDifferentCases() throws BadInputException, Exception {
		File file = new File("src/test/resources/DifferentCases.txt");

		WordFrequencyCounter counter = new WordFrequencyCounterImpl(new WordFrequencyCounterCache());
		Map<String, Integer> result = counter.getKMostFrequentWord(new SampleFile(file), 4);

		Assert.state(4 == result.size(), "Should return 3");
		Assert.isTrue(result.entrySet().stream().findFirst().get().getKey().equals("apple"), "Should return 'apple'");
	}
	
	@Test
	void testWordFrequencyCounter_NegativeValueOfK() throws BadInputException, Exception {
		File file = new File("src/test/resources/EmptyText.txt");

		WordFrequencyCounter counter = new WordFrequencyCounterImpl(new WordFrequencyCounterCache());
		Assertions.assertThrows(BadInputException.class, () -> counter.getKMostFrequentWord(new SampleFile(file), -1));
	}
	
	@Test
	void testWordFrequencyCounter_WrongExtension() throws BadInputException, Exception {
		File file = new File("src/test/resources/empty.pdf");

		WordFrequencyCounter counter = new WordFrequencyCounterImpl(new WordFrequencyCounterCache());
		Assertions.assertThrows(BadInputException.class, () -> counter.getKMostFrequentWord(new WrongExtFile(file), -1));
	}
	
	
	class SampleFile implements MultipartFile
	{
		private File input;

		SampleFile(File input)
		{
			this.input = input;
		}
		
		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getOriginalFilename() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getContentType() {
			// TODO Auto-generated method stub
			return "text/plain";
		}

		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public long getSize() {
			// TODO Auto-generated method stub
			return input.length();
		}

		@Override
		public byte[] getBytes() throws IOException {
			// TODO Auto-generated method stub
			return Files.readAllBytes(input.toPath());
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return new FileInputStream(input);
		}

		@Override
		public void transferTo(File dest) throws IOException, IllegalStateException {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class SampleFile1 implements MultipartFile
	{

		private byte[] bytes;

		SampleFile1(byte[] bytes)
		{
			this.bytes = bytes;
		}
		
		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getOriginalFilename() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getContentType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public long getSize() {
			// TODO Auto-generated method stub
			return bytes.length;
		}

		@Override
		public byte[] getBytes() throws IOException {
			// TODO Auto-generated method stub
			return bytes;
		}

		@Override
		public InputStream getInputStream() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void transferTo(File dest) throws IOException, IllegalStateException {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class WrongExtFile implements MultipartFile
	{
		private File input;

		WrongExtFile(File input)
		{
			this.input = input;
		}
		
		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getOriginalFilename() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getContentType() {
			// TODO Auto-generated method stub
			return "application/pdf";
		}

		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public long getSize() {
			// TODO Auto-generated method stub
			return input.length();
		}

		@Override
		public byte[] getBytes() throws IOException {
			// TODO Auto-generated method stub
			return Files.readAllBytes(input.toPath());
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return new FileInputStream(input);
		}

		@Override
		public void transferTo(File dest) throws IOException, IllegalStateException {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
