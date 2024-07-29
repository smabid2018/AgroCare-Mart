package model;

import java.io.File;

import javax.servlet.http.Part;

import util.StringUtil;

public class AdminModel {

	private String name;
	private String phone;
	private String email;
	private String address;
	private String password;
	private String imageUrlFromPart;

	/**
	 * Default constructor for the AdminModel class.
	 */
	public AdminModel() {

	}

	/**
	 * Parameterized constructor for the AdminModel class. Initializes the
	 * AdminModel object with the provided name, phone, email, address, password,
	 * and image part.
	 * 
	 * @param name      The name of the administrator.
	 * @param phone     The phone number of the administrator.
	 * @param email     The email address of the administrator.
	 * @param address   The address of the administrator.
	 * @param password  The password of the administrator.
	 * @param imagePart The Part object representing the profile image of the
	 *                  administrator.
	 */
	public AdminModel(String name, String phone, String email, String address, String password, Part imagePart) {
		super();
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.password = password;
		this.imageUrlFromPart = getImageUrl(imagePart);
	}

	/**
	 * Getter method to retrieve the name of the admin.
	 * 
	 * @return The name of the admin.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter method to set the name of the admin.
	 * 
	 * @param name The name to be set for the admin.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter method to retrieve the phone number of the admin.
	 * 
	 * @return The phone number of the admin.
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Setter method to set the phone number of the admin.
	 * 
	 * @param phone The phone number to be set for the admin.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Getter method to retrieve the email of the admin.
	 * 
	 * @return The email of the admin.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter method to set the email of the admin.
	 * 
	 * @param email The email to be set for the admin.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter method to retrieve the address of the admin.
	 * 
	 * @return The address of the admin.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Setter method to set the address of the admin.
	 * 
	 * @param address The address to be set for the admin.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Getter method to retrieve the password of the admin.
	 * 
	 * @return The password of the admin.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter method to set the password of the admin.
	 * 
	 * @param password The password to be set for the admin.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter method to retrieve the image URL of the admin profile picture.
	 * 
	 * @return The image URL of the admin profile picture.
	 */
	public String getImageUrlFromPart() {
		return imageUrlFromPart;
	}

	/**
	 * Setter method to set the image URL of the admin profile picture using a Part
	 * object.
	 * 
	 * @param part The Part object representing the profile image of the admin.
	 */
	public void setImageUrlFromPart(Part part) {
		this.imageUrlFromPart = getImageUrl(part);
	}

	/**
	 * Setter method to set the image URL of the admin profile picture directly from
	 * the database.
	 * 
	 * @param imageUrl The image URL retrieved from the database.
	 */
	public void setImageUrlFromDB(String imageUrl) {
		this.imageUrlFromPart = imageUrl;

	}

	/**
	 * Private method to extract the image URL from a Part object representing the
	 * admin's profile picture. This method also validates the file extension of the
	 * image. If the Part object is null or the file extension is invalid, it
	 * returns a default image URL.
	 * 
	 * @param part The Part object representing the admin's profile picture.
	 * @return The image URL of the admin profile picture.
	 */
	private String getImageUrl(Part part) {
		String savePath = StringUtil.IMAGE_DIR_USER;
		File fileSaveDir = new File(savePath);
		String imageUrlFromPart = "profile_pic_unknown.png"; // Default image

		if (part != null && !(part.getSubmittedFileName().isEmpty())) {
			if (!fileSaveDir.exists()) {
				fileSaveDir.mkdir();
			}
			String contentDisp = part.getHeader("content-disposition");
			String[] items = contentDisp.split(";");
			for (String s : items) {
				if (s.trim().startsWith("filename")) {
					imageUrlFromPart = s.substring(s.indexOf("=") + 2, s.length() - 1);
				}
			}

			// Validate the file extension
			if (imageUrlFromPart != null && !imageUrlFromPart.isEmpty()) {
				String lowerCaseImageUrl = imageUrlFromPart.toLowerCase();
				if (lowerCaseImageUrl.endsWith(".png") || lowerCaseImageUrl.endsWith(".jpg")
						|| lowerCaseImageUrl.endsWith(".jpeg")) {
					return imageUrlFromPart; // Valid extension
				} else {
					return null; // Invalid extension, return null or handle accordingly
				}
			}
		}
		return imageUrlFromPart; // Return default image if part is null or invalid
	}

}
