package edu.cmu.cs.cs214.hw4.core;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;

public class Dictionary {
	private String[] dictionary;
	private static final int NO_OF_WORDS = 64001;

	public Dictionary() {
		dictionary = new String[NO_OF_WORDS];
		init();
	}

	/**
	 * Checks if a string is an actual word
	 * 
	 * @param word
	 *            The string of the word to be checked for legality
	 * @return True if an actual word, false otherwise
	 */
	public boolean isWord(String word) {
		return searchDictionary(word, 0, NO_OF_WORDS);
	}

	private boolean searchDictionary(String word, int start, int end) {

		if (start == end - 1)
			return false;
		int mid = (start + end) / 2;
		if (dictionary[mid].compareTo(word) == 0)
			return true;
		else if (dictionary[mid].compareTo(word) > 0)
			return searchDictionary(word, start, mid);
		else
			return searchDictionary(word, mid, end);
	}

	/**
	 * Creating the dictionary from a text file (words.txt) containing all the
	 * words
	 */
	private void init() {

		try (BufferedReader br = new BufferedReader(new FileReader(
				"assets/words.txt"))) {
			int count = 0;
			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				dictionary[count] = sCurrentLine;
				count++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
