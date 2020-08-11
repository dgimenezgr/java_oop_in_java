package document;

/** 
 * A class that represents a text document
 * @author UC San Diego Intermediate Programming MOOC team
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Document {

	private String text;
	
	/** Create a new document from the given text.
	 * Because this class is abstract, this is used only from subclasses.
	 * @param text The text of the document.
	 */
	protected Document(String text)
	{
		this.text = text;
	}
	
	/** Returns the tokens that match the regex pattern from the document 
	 * text string.
	 * @param pattern A regular expression string specifying the 
	 *   token pattern desired
	 * @return A List of tokens from the document text that match the regex 
	 *   pattern
	 */
	protected List<String> getTokens(String pattern)
	{
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile(pattern);
		Matcher m = tokSplitter.matcher(text);
		
		while (m.find()) {
			tokens.add(m.group());
		}
		
		return tokens;
	}
	
	/** This is a helper function that returns the number of syllables
	 * in a word.  You should write this and use it in your 
	 * BasicDocument class.
	 * 
	 * You will probably NOT need to add a countWords or a countSentences 
	 * method here.  The reason we put countSyllables here because we'll 
	 * use it again next week when we implement the EfficientDocument class.
	 * 
	 * For reasons of efficiency you should not create Matcher or Pattern 
	 * objects inside this method. Just use a loop to loop through the 
	 * characters in the string and write your own logic for counting 
	 * syllables.
	 * 
	 * @param word  The word to count the syllables in
	 * @return The number of syllables in the given word, according to 
	 * this rule: Each contiguous sequence of one or more vowels is a syllable, 
	 *       with the following exception: a lone "e" at the end of a word 
	 *       is not considered a syllable unless the word has no other syllables. 
	 *       You should consider y a vowel.
	 */
	protected int countSyllables(String word)
	{
		// TODO: Implement this method so that you can call it from the 
	    // getNumSyllables method in BasicDocument (module 2) and 
	    // EfficientDocument (module 3).
		// The pattern to recognize what a syllable is is stated as such for this assignment:
		// - Each set of adjacent vowels is the nucleus for a syllable.
		// - Unless it's an E at the end of a word and it's not preceded by another vowel.
		// - All words have at least one syllable, as stated in the assignment hints/instructions.
		// A helper method to determine if a letter is a vowel will be needed, and implemented as isVowel().
		
		int syllablesCounter = 0;
		
		word = word.toLowerCase();
		
		char[] wordArray = word.toCharArray();
		
		Pattern p = Pattern.compile("[aeiouy]+");
		Matcher m = p.matcher(word);
		
		while (m.find()) {
			syllablesCounter++;
		}
			
		if (word.charAt(wordArray.length - 1) == 'e' && !isVowel(word.charAt(wordArray.length - 2))) {
			syllablesCounter--;
		}
		
		if (syllablesCounter == 0) {
			syllablesCounter = 1;
		}
		
	    return syllablesCounter;
	}
	
	private boolean isVowel(char c) {
		boolean isThisVowel = false;
		
		String vowelDict = "aeiouy";
		
		String matcher = Character.toString(c).toLowerCase();
		
		if (vowelDict.indexOf(matcher) != -1) {
			isThisVowel = true;
		}
		
		return isThisVowel;		
	}
	
	/** A method for testing
	 * 
	 * @param doc The Document object to test
	 * @param syllables The expected number of syllables
	 * @param words The expected number of words
	 * @param sentences The expected number of sentences
	 * @return true if the test case passed.  False otherwise.
	 */
	public static boolean testCase(Document doc, int syllables, int words, int sentences)
	{
		System.out.println("Testing text: ");
		System.out.print(doc.getText() + "\n....");
		boolean passed = true;
		int syllFound = doc.getNumSyllables();
		int wordsFound = doc.getNumWords();
		int sentFound = doc.getNumSentences();
		if (syllFound != syllables) {
			System.out.println("\nIncorrect number of syllables.  Found " + syllFound 
					+ ", expected " + syllables);
			passed = false;
		}
		if (wordsFound != words) {
			System.out.println("\nIncorrect number of words.  Found " + wordsFound 
					+ ", expected " + words);
			passed = false;
		}
		if (sentFound != sentences) {
			System.out.println("\nIncorrect number of sentences.  Found " + sentFound 
					+ ", expected " + sentences);
			passed = false;
		}
		

		if (passed) {
			System.out.println("passed.\n");
		}
		else {
			System.out.println("FAILED.\n");
		}
		return passed;
	}
	
	
	/** Return the number of words in this document */
	public abstract int getNumWords();
	
	/** Return the number of sentences in this document */
	public abstract int getNumSentences();
	
	/** Return the number of syllables in this document */
	public abstract int getNumSyllables();
	
	/** Return the entire text of this document */
	public String getText()
	{
		return this.text;
	}

	/** return the Flesch readability score of this document */
	public double getFleschScore() {
		double fleschScore = 0.0;
		
		// Sentence values
		List<String> sentenceTokens = getTokens("[^.!?]+");
		int numSentences = sentenceTokens.size();
		
		// Word values
		List<String> wordTokens = getTokens("[a-zA-Z]+");
		int numWords = wordTokens.size();
		
		// Syllable values
		List<String> syllableTokens = getTokens("[a-zA-Z]+");
		int syllableCounter = 0;
		for (String word : syllableTokens) {
			int currSyllables = countSyllables(word);
			syllableCounter += currSyllables;
		}
		
		double firstTerm = 1.015 * numWords/numSentences;
		
		double secondTerm = 84.6 * syllableCounter/numWords;
		
		fleschScore = 206.835 - firstTerm - secondTerm;
		
		return fleschScore;

	};
	

}
