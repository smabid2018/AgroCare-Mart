package model;

import java.io.File;

import javax.servlet.http.Part;

import util.StringUtil;

public class ProductModel {
	private int sellerId;
	private String productCode;
	private String name;
	private String description;
	private String category;
	private int price;
	private int stock;
	private String imageUrlFromPart;

	/**
	 * Constructs a new ProductModel instance with default values.
	 */
	public ProductModel() {

	}

	/**
	 * Constructs a new ProductModel instance with the provided attributes.
	 * 
	 * @param sellerId    The ID of the seller associated with the product.
	 * @param productCode The unique code of the product.
	 * @param name        The name of the product.
	 * @param description The description of the product.
	 * @param category    The category of the product.
	 * @param price       The price of the product.
	 * @param stock       The stock quantity of the product.
	 * @param imagePart   The image part representing the product image.
	 */
	public ProductModel(int sellerId, String productCode, String name, String description, String category, int price,
			int stock, Part imagePart) {
		super();
		this.sellerId = sellerId;
		this.productCode = productCode;
		this.name = name;
		this.description = description;
		this.category = category;
		this.price = price;
		this.stock = stock;
		this.imageUrlFromPart = getImageUrl(imagePart);
	}

	/**
	 * Retrieves the ID of the seller associated with the product.
	 * 
	 * @return The seller ID.
	 */
	public int getSellerId() {
		return sellerId;
	}

	/**
	 * Sets the ID of the seller associated with the product.
	 * 
	 * @param sellerId The seller ID to set.
	 */
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	/**
	 * Retrieves the unique code of the product.
	 * 
	 * @return The product code.
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * Sets the unique code of the product.
	 * 
	 * @param productCode The product code to set.
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * Retrieves the name of the product.
	 * 
	 * @return The product name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the product.
	 * 
	 * @param name The product name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retrieves the description of the product.
	 * 
	 * @return The product description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the product.
	 * 
	 * @param description The product description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Retrieves the category of the product.
	 * 
	 * @return The product category.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Sets the category of the product.
	 * 
	 * @param category The product category to set.
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Retrieves the price of the product.
	 * 
	 * @return The product price.
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * Sets the price of the product.
	 * 
	 * @param price The product price to set.
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * Retrieves the stock quantity of the product.
	 * 
	 * @return The product stock quantity.
	 */
	public int getStock() {
		return stock;
	}

	/**
	 * Sets the stock quantity of the product.
	 * 
	 * @param stock The product stock quantity to set.
	 */
	public void setStock(int stock) {
		this.stock = stock;
	}

	/**
	 * Retrieves the URL of the product image obtained from the image part.
	 * 
	 * @return The URL of the product image.
	 */
	public String getImageUrlFromPart() {
		return imageUrlFromPart;
	}

	/**
	 * Sets the URL of the product image obtained from the provided image part.
	 * 
	 * @param image The image part representing the product image.
	 */
	public void setImageUrlFromPart(Part image) {
		this.imageUrlFromPart = getImageUrl(image);
	}

	/**
	 * Sets the URL of the product image obtained from the database.
	 * 
	 * @param imageUrl The URL of the product image.
	 */
	public void setImageUrlFromDB(String imageUrl) {
		this.imageUrlFromPart = imageUrl;

	}

	/**
	 * Retrieves the URL of the product image parsed from the provided image part.
	 * 
	 * @param part The image part representing the product image.
	 * @return The URL of the product image.
	 */
	private String getImageUrl(Part part) {
		String savePath = StringUtil.IMAGE_DIR_PRODUCT;
		File fileSaveDir = new File(savePath);
		String imageUrlFromPart = "sample_product.png"; // Default image

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
