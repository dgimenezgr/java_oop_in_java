/**
 * 
 */
package spelling;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


/**
 * @author UC San Diego Intermediate MOOC team
 *
 */
public class NearbyWords implements SpellingSuggest {
	// THRESHOLD to determine how many words to look through when looking
	// for spelling suggestions (stops prohibitively long searching)
	// For use in the Optional Optimization in Part 2.
	private static final int THRESHOLD = 1000; 

	Dictionary dict;

	public NearbyWords (Dictionary dict) 
	{
		this.dict = dict;
	}

	/** Return the list of Strings that are one modification away
	 * from the input string.  
	 * @param s The original String
	 * @param wordsOnly controls whether to return only words or any String
	 * @return list of Strings which are nearby the original string
	 */
	public List<String> distanceOne(String s, boolean wordsOnly )  {
		   List<String> retList = new ArrayList<String>();
		   insertions(s, retList, wordsOnly);
		   substitution(s, retList, wordsOnly);
		   deletions(s, retList, wordsOnly);
		   return retList;
	}


	// substitution(), insertions() and deletions() are only permutations of the same logic. Every one of these methods only takes the input string, and iterates through it
	// and, after applying the modification, checks if that new string is in the dictionary (is a word, another method) and not already in the potential words list. If both are true,
	// it is then added.
	// The logic for substitution() is to set the current index to another character and check.
	// The logic for insertions() is to insert another character in the current index position and check.
	// The logic for deletions is to delete the character at the current index position and check.
	// In the example substitution method, the class used to hold the modified Strings is StringBuffer.
	// The StringBuffer class documentation and methods are at https://docs.oracle.com/javase/7/docs/api/java/lang/StringBuffer.html
	// There are clear candidate methods to apply the given logic for insertions() and deletions() in insert(int offset, char c) (using charCode)
	// and deleteCharAt(int index), so it should only be a question of copying both methods from substitution() and changing that specific line.
	
	/** Add to the currentList Strings that are one character mutation away
	 * from the input string.  
	 * @param s The original String
	 * @param currentList is the list of words to append modified words 
	 * @param wordsOnly controls whether to return only words or any String
	 * @return
	 */
	public void substitution(String s, List<String> currentList, boolean wordsOnly) {
		// for each letter in the s and for all possible replacement characters
		for(int index = 0; index < s.length(); index++){
			for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
				// use StringBuffer for an easy interface to permuting the 
				// letters in the String. The StringBuffer class documentation and methods are at https://docs.oracle.com/javase/7/docs/api/java/lang/StringBuffer.html
				StringBuffer sb = new StringBuffer(s);
				sb.setCharAt(index, (char)charCode);

				// if the item isn't in the list, isn't the original string, and
				// (if wordsOnly is true) is a real word, add to the list
				if(!currentList.contains(sb.toString()) && 
						(!wordsOnly||dict.isWord(sb.toString())) &&
						!s.equals(sb.toString())) {
					currentList.add(sb.toString());
				}
			}
		}
	}
	
	/** Add to the currentList Strings that are one character insertion away
	 * from the input string.  
	 * @param s The original String
	 * @param currentList is the list of words to append modified words 
	 * @param wordsOnly controls whether to return only words or any String
	 * @return
	 */
	public void insertions(String s, List<String> currentList, boolean wordsOnly ) {
		// TODO: Implement this method
		// For every character in the given string, do:
	   for(int index = 0; index <= s.length(); index++){
		   // For every character code between A and Z lower case
		   for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
			   // Create a StringBuffer sb to hold changes
			   StringBuffer sb = new StringBuffer(s);
			   // In sb, insert, in the position for that index, this step's charCode
			   sb.insert(index, (char)charCode);
			   // If the resulting StringBuffer toString is NOT contained in the list of words to append, and the resulting sb toString is a word or the wordsOnly restriction is not applied, then: 
			   if(!currentList.contains(sb.toString()) && (!wordsOnly||dict.isWord(sb.toString()))) {
				  // Add the resulting string from sb to the list of words to append.
				   currentList.add(sb.toString());
			   }
		   }
	   }
   }

	/** Add to the currentList Strings that are one character deletion away
	 * from the input string.  
	 * @param s The original String
	 * @param currentList is the list of words to append modified words 
	 * @param wordsOnly controls whether to return only words or any String
	 * @return
	 */
	public void deletions(String s, List<String> currentList, boolean wordsOnly ) {
		// TODO: Implement this method
	   // For every character in the given string, do:
	   for(int index = 0; index < s.length(); index++){
		   // Create a new StringBuffer sb to hold changes:
		   StringBuffer sb = new StringBuffer(s);
		   // Delete the char that sits at that position.
		   sb.deleteCharAt(index);
		   // If the resulting StringBuffer toString is NOT contained in the list of words to append, and the resulting sb toString is a word or the wordsOnly restriction is not applied,
		   // and additionally the new toString is NOT equal to the original string, and also the new toString is not empty, then:
		   if(!currentList.contains(sb.toString()) && (!wordsOnly||dict.isWord(sb.toString())) && !s.equals(sb.toString()) && s.length() != 0) {
			   // Add the resulting string from sb to the list of words to append.
			   currentList.add(sb.toString());
		   }
	   }
	}

	/** Add to the currentList Strings that are one character deletion away
	 * from the input string.  
	 * @param word The misspelled word
	 * @param numSuggestions is the maximum number of suggestions to return 
	 * @return the list of spelling suggestions
	 */
	@Override
	public List<String> suggestions(String word, int numSuggestions) {

		// initial variables
		List<String> queue = new LinkedList<String>();     // String to explore
		HashSet<String> visited = new HashSet<String>();   // to avoid exploring the same  
														   // string multiple times
		List<String> retList = new LinkedList<String>();   // words to return
		 
		
		// insert first node
		queue.add(word);
		
		// This list seems to help avoid excessive run times
		visited.add(word);
					
		// TODO: Implement the remainder of this method, see assignment for algorithm
		// While there's a word of size bigger than zero and the number of returned words is less than the number of max candidates		
		while(!queue.isEmpty() && retList.size() < numSuggestions) {
			// Start BFS algorithm by removing first node from the front. There's no removeFirst method so we'll just hardcode the first index.
			String thisWord = queue.remove(0);
			// Find all possible strings with a distance of one. For this step it doesn't need to be a word, but it has to be checked in future steps.
			List<String> suggestions = distanceOne(thisWord, false);
			
			// For each suggestion string:
			for(String suggestion : suggestions) {
				// Check if it has been explored. If not, do:
				if(!visited.contains(suggestion)) {
					// Put it in the back of the queue
					queue.add(suggestion);
					// Add to the visited list.
					visited.add(suggestion);
					// If it is a word, and
					if(dict.isWord(suggestion)) {
						// We haven't reached the max number of needed suggestions
						if (retList.size() < numSuggestions) {
							retList.add(suggestion);
						}
					}
				}
			}
		}
		return retList;

	}	

   public static void main(String[] args) {
	   /* basic testing code to get started*/
	   String word = "i";
	   // Pass NearbyWords any Dictionary implementation you prefer
	   Dictionary d = new DictionaryHashSet();
	   DictionaryLoader.loadDictionary(d, "data/dict.txt");
	   NearbyWords w = new NearbyWords(d);
	   List<String> l = w.distanceOne(word, true);
	   System.out.println("One away word Strings for for \""+word+"\" are:");
	   System.out.println(l+"\n");

	   word = "tailo";
	   List<String> suggest = w.suggestions(word, 10);
	   System.out.println("Spelling Suggestions for \""+word+"\" are:");
	   System.out.println(suggest);
   }

}
