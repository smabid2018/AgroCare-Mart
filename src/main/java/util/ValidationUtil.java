package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {
	public static boolean isMatched(String password1, String password2) {
		return password1.equals(password2); 
	}

    
    public static boolean isRegex(String password) {
    	// Regex to validate the password
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    
    public static boolean isValidCode(String code) {
    	// Regular expression pattern
    	String regex = "^(?=.*[A-Z])(?=.*[0-9]).{4,10}$";
    	return code.matches(regex);
    }
    
    
	
    
    
	
	

}
