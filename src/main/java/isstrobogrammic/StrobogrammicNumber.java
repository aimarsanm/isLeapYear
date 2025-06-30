package isstrobogrammic;

import java.util.HashMap;
import java.util.Map;

public class StrobogrammicNumber {
    /**
     * @param number The number to check if it is strobogrammic.
     *               A strobogrammatic number is a number whose numeral is
     *               rotationally symmetric, so that it appears the same when
     *               rotated 180 degrees. In other words, the numeral looks the same
     *               right-side up and upside down (e.g., 69, 96, 1001). A
     *               strobogrammatic prime is a strobogrammatic number that is also
     *               a prime number, i.e., a number that is only divisible by one
     *               and itself (e.g., 11). It is a type of ambigram, words and
     *               numbers that retain their meaning when viewed from a different
     *               perspective, such as palindromes.
     *               https://www.w3resource.com/java-exercises/basic/java-basic-exercise-186.php
     * @return True if the number is strobogrammic.
     */
    public static boolean isStrobogrammic(String number) {
        // Check for null or empty string
        if (number == null || number.length() == 0) {
            return true;
        }
        // Create a HashMap to store Strobogrammatic pairs
        Map<Character, Character> map = new HashMap<>();
        map.put('0', '0');
        map.put('1', '1');
        map.put('8', '8');
        map.put('6', '9');
        map.put('9', '6');
        // Use two pointers to traverse the string from both ends
        int left = 0;
        int right = number.length() - 1;
        // Continue until the left pointer is less than or equal to the right pointer
        while (left <= right) {
            // Check if the characters at the current positions are valid Strobogrammatic
            // pairs
            if (!map.containsKey(number.charAt(right)) || number.charAt(left) != map.get(number.charAt(right))) {
                return false;
            }
            // Move the pointers towards the center
            left++;
            right--;
        }
        // If the loop completes, the string is Strobogrammatic
        return true;
    }
}
