 package isipv4valid;

import java.util.StringTokenizer;

public class IPAddressChecker {
    private static final String IPV4DELIM = ".";

    // Function to check whether the string passed is valid or not
    public boolean isValidIPV4Part(String s) {
        int n = s.length();

        // If the length of the string is more than 3, then it is not valid
        if (n > 3)
            return false;

        // Check if the string only contains digits
        // If not, then return false
        for (int i = 0; i < n; i++)
            if (!(s.charAt(i) >= '0' && s.charAt(i) <= '9'))
                return false;

        // If the string is "00" or "001" or "05" etc then it is not valid
        if (s.indexOf('0') == 0 && n > 1)
            return false;

        try {
            int x = Integer.parseInt(s);

            // The string is valid if the number generated is between 0 to 255
            return (x <= 255);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Return 1 if IP string is valid, else return 0
    public int isValidIPV4(String ipStr) {
        if (ipStr == null)
            return 0;

        int dots = 0;
        int len = ipStr.length();
        int count = 0;

        // The number dots in the original string should be 3
        // for it to be valid
        for (int i = 0; i < len; i++)
            if (ipStr.charAt(i) == '.')
                count++;
        if (count != 3)
            return 0;

        // Using StringTokenizer to split the IP string
        StringTokenizer st = new StringTokenizer(ipStr, IPV4DELIM);

        while (st.hasMoreTokens()) {
            String part = st.nextToken();

            if (isValidIPV4Part(part)) {
                // Parse remaining string
                if (st.hasMoreTokens())
                    dots++;
            } else
                return 0;
        }

        // Valid IP string must contain 3 dots
        // This is for cases such as 1...1 where
        // originally the number of dots is three but
        // after iteration of the string, we find it is not valid
        if (dots != 3)
            return 0;

        return 1;
    }
}