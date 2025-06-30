package palindrome;



/**
 * Palindrome class contains methods to find the longest palindrome in a given a
 * string.
 * Source:
 * https://code-exercises.com/programming/hard/4/longest-palindrome-in-word
 */
public class Palindrome {

    /**
     * Find the longest palindrome in a given word.
     * 
     * @param word The word to find the longest palindrome in.
     * @return The longest palindrome in the word.
     */
    public String findLongestPalindrome(String word) throws MissingArgumentException {
        if (word == null) {
            throw new MissingArgumentException();
        }
        String longestPalindrome = "";
        for (int i = 0; i < word.length(); i++) {
            String current = findLongestPalindromeInternal(word.substring(i, word.length()));
            if (longestPalindrome.length() < current.length()) {
                longestPalindrome = current;
            }
        }
        return longestPalindrome;
    }

    /**
     * Find the longest palindrome in a given word.
     * 
     * @param word The word to find the longest palindrome in.
     * @return The longest palindrome in the word.
     */
    public String findLongestPalindromeInternal(String word) throws MissingArgumentException {
        if (word == null) {
            throw new MissingArgumentException();
        }
        String longestPalindrome;

        int index = word.length();
        while (index > 0 && !isPalindrome(word.substring(0, index))) {
            index--;
        }
        longestPalindrome = word.substring(0, index);

        return longestPalindrome;
    }

    /**
     * Check if a word is a palindrome.
     * 
     * @param word
     * @return
     */
    public boolean isPalindrome(String word) throws MissingArgumentException {
        if (word == null) {
            throw new MissingArgumentException();
        }
        int i1 = 0;
        int i2 = word.length() - 1;
        while (i2 > i1) {
            if (word.charAt(i1) != word.charAt(i2)) {
                return false;
            }
            ++i1;
            --i2;
        }
        return true;
    }
}