package model;

public class LoginModel {
	private String phone;
	private String password;

	/**
	 * Parameterized constructor for the LoginModel class. Initializes the
	 * LoginModel object with the provided phone number and password.
	 * 
	 * @param phone    The phone number associated with the login.
	 * @param password The password associated with the login.
	 */
	public LoginModel(String phone, String password) {
		super();
		this.phone = phone;
		this.password = password;
	}

	/**
	 * Getter method to retrieve the phone number associated with the login.
	 * 
	 * @return The phone number associated with the login.
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Setter method to set the phone number associated with the login.
	 * 
	 * @param phone The phone number to be set for the login.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Getter method to retrieve the password associated with the login.
	 * 
	 * @return The password associated with the login.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter method to set the password associated with the login.
	 * 
	 * @param password The password to be set for the login.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
