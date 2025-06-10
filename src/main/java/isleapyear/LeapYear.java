package isleapyear;

public class LeapYear {
	
	public LeapYear() {}
	public static void main(String[] args) {
		String prueba = "2020";
   
		try {
			System.out.println(isLeapYear(prueba));
		} catch (NullPointerException e) {
			System.out.println("El string es nulo");
		} catch (EmptyException e) {
			System.out.println("El string esta vacío");
		} catch (NumberFormatException e) {
			System.out.println("El string no es un número entero válido");
		}
	}
	
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
		    	}catch (NumberFormatException e) {
		    		throw new NumberFormatException(); 
		    	}
		    }
 		}catch(NullPointerException e) {
			throw new NullPointerException();
			
		}
			
	}
	
	


	
	
	
}
