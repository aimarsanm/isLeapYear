package isleapyear;

public class LeapYear {

	/**
 * Checks if the given string represents a valid leap year between 1 and 2100 (inclusive).
 * The year 0 is explicitly considered not a leap year.
 *
 * @param year The string representing the year to check.
 * @return {@code true} if the year is a leap year within the specified range, {@code false} otherwise.
 * @throws NullPointerException If the input string {@code year} is {@code null}.
 * @throws EmptyException If the input string {@code year} is empty.
 * @throws NumberFormatException If the input string {@code year} cannot be parsed as an integer.
 */
	public static boolean isLeapYear(String year) throws NullPointerException, EmptyException, NumberFormatException{
		try {
			if (year.isEmpty()) {
		        throw new EmptyException();
		    }else {
		    	try {
		    		int num= Integer.parseInt(year);
		    		return num>0 && num<=2100 && num % 4 == 0;		
				}catch (NumberFormatException ignored) {
					throw new NumberFormatException(); 
				}
		    }
 		}catch(NullPointerException e) {
			throw new NullPointerException();
			
		}
			
	}
	
	


	
	
	
}
