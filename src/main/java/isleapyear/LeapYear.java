package isleapyear;

public class LeapYear {

	/**
	 * The function consists in if the string is any leap year between 0 and 2100,
	 * but the year 0 will be false.
     * @param a string.
     * @return Returns a boolean.
     * @throws NullPointerException When the string is null.
     * @throws EmptyException When the string is empty.
     * @throws NumberFormatException When the value of the string is not a integer.
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
