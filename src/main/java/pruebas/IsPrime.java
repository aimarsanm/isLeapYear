package pruebas;

public class IsPrime {
    public IsPrime() {
        // Default constructor
    }
    /**
     * @param args Set of strings consisting of integers.
     * @return Returns whether the numbers in args are all prime or not.
     * @throws MissingArgumentException When there are no numbers in args.
     * @throws Only1ArgumentException When there is more than 1 number in the args variable.
     * @throws NoPositiveNumberException When there are negative numbers in the args variable.
     */
    public static boolean isPrime(String[] args)
            throws MissingArgumentException, Only1ArgumentException, NoPositiveNumberException {
        if (args == null)
            throw new MissingArgumentException();
        else if (args.length > 1)
            throw new Only1ArgumentException();
        else {
            try {
                float numF = Float.parseFloat(args[0]);
                int num = (int) numF;
                if (num <= 0)
                    throw new NoPositiveNumberException();
                else {
                    for (int i = 2; i < num; i++)
                        if (num % i == 0)
                            return false;
                    return true;
                }
            } catch (NumberFormatException e) {
                throw new NoPositiveNumberException();
            }
        }
    }
}
