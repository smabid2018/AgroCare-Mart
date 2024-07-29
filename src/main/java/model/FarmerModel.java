package model;

import java.io.File;

import javax.servlet.http.Part;

import util.StringUtil;

public class FarmerModel {
	private String fullname;
	private String phone;
	private String email;
	private String address;
	private String password;
	private String imageUrlFromPart;

	/**
	 * Default constructor for the FarmerModel class.
	 */
	public FarmerModel() {

	}

	/**
	 * Parameterized constructor for the FarmerModel class. Initializes the
	 * FarmerModel object with the provided fullname, phone, email, address,
	 * password, and image part.
	 * 
	 * @param fullname  The full name of the farmer.
	 * @param phone     The phone number of the farmer.
	 * @param email     The email address of the farmer.
	 * @param address   The address of the farmer.
	 * @param password  The password of the farmer.
	 * @param imagePart The Part object representing the profile image of the
	 *                  farmer.
	 */
	public FarmerModel(String fullname, String phone, String email, String address, String password, Part imagePart) {
		super();
		this.fullname = fullname;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.password = password;
		this.imageUrlFromPart = getImageUrl(imagePart);
	}

	/**
	 * Getter method to retrieve the full name of the farmer.
	 * 
	 * @return The full name of the farmer.
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * Setter method to set the full name of the farmer.
	 * 
	 * @param fullname The full name to be set for the farmer.
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * Getter method to retrieve the phone number of the farmer.
	 * 
	 * @return The phone number of the farmer.
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Setter method to set the phone number of the farmer.
	 * 
	 * @param phone The phone number to be set for the farmer.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Getter method to retrieve the email of the farmer.
	 * 
	 * @return The email of the farmer.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter method to set the email of the farmer.
	 * 
	 * @param email The email to be set for the farmer.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter method to retrieve the address of the farmer.
	 * 
	 * @return The address of the farmer.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Setter method to set the address of the farmer.
	 * 
	 * @param address The address to be set for the farmer.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Getter method to retrieve the password of the farmer.
	 * 
	 * @return The password of the farmer.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter method to set the password of the farmer.
	 * 
	 * @param password The password to be set for the farmer.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter method to retrieve the image URL of the farmer profile picture.
	 * 
	 * @return The image URL of the farmer profile picture.
	 */
	public String getImageUrlFromPart() {
		return imageUrlFromPart;
	}

	/**
	 * Setter method to set the image URL of the farmer profile picture using a Part
	 * object.
	 * 
	 * @param part The Part object representing the profile image of the farmer.
	 */
	public void setImageUrlFromPart(Part part) {
		this.imageUrlFromPart = getImageUrl(part);
	}

	/**
	 * Setter method to set the image URL of the farmer profile picture directly
	 * from the database.
	 * 
	 * @param imageUrl The image URL retrieved from the database.
	 */
	public void setImageUrlFromDB(String imageUrl) {
		this.imageUrlFromPart = imageUrl;

	}

	/**
	 * Private method to extract the image URL from a Part object representing the
	 * farmer's profile picture. This method also validates the file extension of
	 * the image. If the Part object is null or the file extension is invalid, it
	 * returns a default image URL.
	 * 
	 * @param part The Part object representing the farmer's profile picture.
	 * @return The image URL of the farmer profile picture.
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
