package controller.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.AdminModel;
import model.LoginModel;
import model.FarmerModel;
import model.PasswordEncryptionWithAes;
import model.ProductModel;
import util.StringUtil;

public class DBController {

	/**
	 * Establishes a connection to the database using pre-defined credentials and
	 * driver information.
	 * 
	 * @return A `Connection` object representing the established connection to the
	 *         database.
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public Connection getConnection() throws SQLException, ClassNotFoundException {

		// Load the JDBC driver class specified by the StringUtils.DRIVER_NAME constant
		Class.forName(StringUtil.DRIVER_NAME);

		// Create a connection to the database using the provided credentials
		return DriverManager.getConnection(StringUtil.LOCALHOST_URL, StringUtil.LOCALHOST_USERNAME,
				StringUtil.LOCALHOST_PASSWORD);
	}

	/**
	 * Registers a new farmer in the database.
	 * 
	 * @param farmer The FarmerModel object containing the information of the farmer to be registered.
	 * @return An integer indicating the registration status:
	 *         - 1: Registration successful
	 *         - 0: Registration failed (no rows affected)
	 *         - -1: Internal error
	 */
	public int registerFarmer(FarmerModel farmer) {
		try {
			// Prepare a statement using the predefined query for FARMER registration
			PreparedStatement stmt = getConnection().prepareStatement(StringUtil.QUERY_REGISTER_FARMER);

			// Set the student information in the prepared statement
			stmt.setString(1, farmer.getFullname());
			stmt.setString(2, farmer.getAddress());
			stmt.setString(3, farmer.getEmail());
			stmt.setString(4, farmer.getPhone());
			stmt.setString(5, farmer.getImageUrlFromPart());
			stmt.setString(6, PasswordEncryptionWithAes.encrypt(farmer.getPhone(), farmer.getPassword()));

			// Execute the update statement and store the number of affected rows
			int result = stmt.executeUpdate();

			// Check if the update was successful (i.e., at least one row affected)
			if (result > 0) {
				return 1; // Registration successful
			} else {
				return 0; // Registration failed (no rows affected)
			}

		} catch (ClassNotFoundException | SQLException ex) {
			// Print the stack trace for debugging purposes
			ex.printStackTrace();
			return -1; // Internal error
		}
	}

	/**
	 * Checks if a phone number already exists in the database.
	 * 
	 * @param phone The phone number to be checked for existence.
	 * @return true if the phone number exists in either farmer or seller table, false otherwise.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public boolean checkNumberIfExist(String phone) throws ClassNotFoundException {
		boolean farmerPhoneExists = false;
		boolean sellerPhoneExists = false;
		try {
			// checks in farmer table
			PreparedStatement stFarmer = getConnection().prepareStatement(StringUtil.QUERY_FARMER_REGISTER_PHONE_CHECK);

			stFarmer.setString(1, phone);

			try (ResultSet farmerResultSet = stFarmer.executeQuery()) {
				farmerPhoneExists = farmerResultSet.next() && farmerResultSet.getInt(1) > 0;
			}

			// checks in seller table
			PreparedStatement stSeller = getConnection().prepareStatement(StringUtil.QUERY_SELLER_REGISTER_PHONE_CHECK);

			stSeller.setString(1, phone);

			try (ResultSet sellerResultSet = stSeller.executeQuery()) {
				sellerPhoneExists = sellerResultSet.next() && sellerResultSet.getInt(1) > 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return (sellerPhoneExists || farmerPhoneExists);

	}

	/**
	 * Checks if an email already exists in the database.
	 * 
	 * @param email The email address to be checked for existence.
	 * @return true if the email exists in either farmer or seller table, false otherwise.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public boolean checkEmailIfExist(String email) throws ClassNotFoundException {
		boolean farmerEmailExists = false;
		boolean sellerEmailExists = false;

		try {
			// checks in farmer table
			PreparedStatement stFarmer = getConnection().prepareStatement(StringUtil.QUERY_FARMER_REGISTER_EMAIL_CHECK);

			stFarmer.setString(1, email);

			try (ResultSet resultSetFarmer = stFarmer.executeQuery()) {
				farmerEmailExists = resultSetFarmer.next() && resultSetFarmer.getInt(1) > 0;
			}

			// checks in seller table
			PreparedStatement stSeller = getConnection().prepareStatement(StringUtil.QUERY_SELLER_REGISTER_EMAIL_CHECK);

			stSeller.setString(1, email);

			try (ResultSet resultSetSeller = stSeller.executeQuery()) {
				sellerEmailExists = resultSetSeller.next() && resultSetSeller.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		System.out.println("at database controller email exists : " + (farmerEmailExists || sellerEmailExists));
		return (farmerEmailExists || sellerEmailExists);
	}

	

	/**
	 * Validates the login information of a farmer.
	 * 
	 * @param loginModel The LoginModel object containing the login credentials.
	 * @return An integer value indicating the login status:
	 *         - 1: Login successful
	 *         - 0: Phone or password mismatch
	 *         - -1: Phone not found in the database
	 *         - -2: Internal error (e.g., SQL or ClassNotFound exceptions)
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public int isFarmerLoginInfo(LoginModel loginModel) {
		try {
			// Prepare a statement using the predefined query for login check
			PreparedStatement st = getConnection().prepareStatement(StringUtil.QUERY_FARMER_LOGIN_PHONE_CHECK);

			// Set the username in the first parameter of the prepared statement
			st.setString(1, loginModel.getPhone());

			// Execute the query and store the result set
			ResultSet result = st.executeQuery();

			// Check if there's a record returned from the query
			if (result.next()) {
				// Get the username from the database
				String userDb = result.getString(StringUtil.DB_PHONE_NUMBER);

				// Get the password from the database
				String encryptedPwd = result.getString(StringUtil.DB_PASSWORD);

				String decryptedPwd = PasswordEncryptionWithAes.decrypt(encryptedPwd, userDb);
				// Check if the username and password match the credentials from the database
				if (userDb.equals(loginModel.getPhone()) && decryptedPwd.equals(loginModel.getPassword())) {
					// Login successful, return 1
					return 1;
				} else {
					// Username or password mismatch, return 0
					return -1;
				}
			} else {
				// Username not found in the database, return -1
				return 0;
			}
		} catch (SQLException | ClassNotFoundException ex) {
			// Print the stack trace for debugging purposes
			ex.printStackTrace();
			// Return -2 to indicate an internal error
			return -2;
		}
	}

	/**
	 * Validates the login information of a seller.
	 * 
	 * @param loginModel The LoginModel object containing the login credentials.
	 * @return An integer value indicating the login status:
	 *         - 1: Login successful
	 *         - 0: Phone or password mismatch
	 *         - -1: Phone not found in the database
	 *         - -2: Internal error (e.g., SQL or ClassNotFound exceptions)
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public int isSellerLoginInfo(LoginModel loginModel) {
		try {
			// Prepare a statement using the predefined query for login check
			PreparedStatement st = getConnection().prepareStatement(StringUtil.QUERY_SELLER_LOGIN_PHONE_CHECK);

			// Set the username in the first parameter of the prepared statement
			st.setString(1, loginModel.getPhone());

			// Execute the query and store the result set
			ResultSet result = st.executeQuery();

			// Check if there's a record returned from the query
			if (result.next()) {
				// Get the username from the database
				String userDb = result.getString(StringUtil.DB_PHONE);

				// Get the password from the database
				String encryptedPwd = result.getString(StringUtil.DB_PASSWORD);

				String decryptedPwd = PasswordEncryptionWithAes.decrypt(encryptedPwd, userDb);
				// Check if the username and password match the credentials from the database
				if (userDb.equals(loginModel.getPhone()) && decryptedPwd.equals(loginModel.getPassword())) {
					// Login successful, return 1
					return 1;
				} else {
					// Username or password mismatch, return 0
					return -1;
				}
			} else {
				// Username not found in the database, return -1
				return 0;
			}
		} catch (SQLException | ClassNotFoundException ex) {
			// Print the stack trace for debugging purposes
			ex.printStackTrace();
			// Return -2 to indicate an internal error
			return -2;
		}
	}
	// check for seller login end

	/**
	 * Retrieves information about all products from the database.
	 * 
	 * @return An ArrayList containing ProductModel objects representing all products in the database.
	 */
	public ArrayList<ProductModel> getAllProduct() {
		try {
			PreparedStatement stmt = getConnection().prepareStatement(StringUtil.QUERY_GET_ALL_PRODUCT);
			ResultSet result = stmt.executeQuery();

			ArrayList<ProductModel> products = new ArrayList<ProductModel>();

			while (result.next()) {

				ProductModel product = new ProductModel();
				product.setSellerId(result.getInt("Seller_id"));
				product.setProductCode(result.getString("Code"));
				product.setName(result.getString("Name"));
				product.setDescription(result.getString("Description"));
				product.setCategory(result.getString("Category"));
				product.setStock(result.getInt("Stock"));
				product.setPrice(result.getInt("Price"));
				product.setImageUrlFromDB(result.getString("Sample_Image"));
				products.add(product);
			}
			return products;
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Retrieves information about all products of a specific seller from the database.
	 * 
	 * @param sellerId The ID of the seller whose products are to be retrieved.
	 * @return An ArrayList containing ProductModel objects representing all products of the specified seller.
	 */
	public ArrayList<ProductModel> getAllSellerProduct(int Seller_id) {
		try {
			PreparedStatement stmt = getConnection().prepareStatement(StringUtil.QUERY_GET_ALL_SELLER_PRODUCT);
			// Set the Seller_id in the first parameter of the prepared statement
			stmt.setInt(1, Seller_id);

			ResultSet result = stmt.executeQuery();

			ArrayList<ProductModel> products = new ArrayList<ProductModel>();

			while (result.next()) {
				ProductModel product = new ProductModel();
				product.setSellerId(result.getInt("Seller_id"));
				product.setProductCode(result.getString("Code"));
				product.setName(result.getString("Name"));
				product.setDescription(result.getString("Description"));
				product.setCategory(result.getString("Category"));
				product.setStock(result.getInt("Price"));
				product.setPrice(result.getInt("Stock"));
				product.setImageUrlFromDB(result.getString("Sample_Image"));
				products.add(product);
			}
			return products;
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Retrieves information about a farmer from the database based on their phone number.
	 * 
	 * @param phone The phone number of the farmer.
	 * @return A FarmerModel object containing the information of the farmer.
	 */
	public FarmerModel getFarmerInfo(String phone) {
		FarmerModel farmer = new FarmerModel();
		try {
			PreparedStatement stmt = getConnection().prepareStatement(StringUtil.QUERY_GET_FARMER);
			stmt.setString(1, phone);
			ResultSet result = stmt.executeQuery();

			while (result.next()) {
				farmer.setFullname(result.getString("name"));
				farmer.setEmail(result.getString("email"));
				farmer.setAddress(result.getString("address"));
				farmer.setPhone(result.getString("phone"));
				farmer.setPassword(result.getString("password"));
				farmer.setImageUrlFromDB(result.getString("profile_image"));
			}

		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}
		return farmer;
	}
	
	/**
	 * Retrieves the password of a farmer from the database based on their phone number.
	 * 
	 * @param phone The phone number of the farmer.
	 * @return The password of the farmer.
	 */
	public String getFarmerPass(String phone) {
		String password = null;
		try {
			PreparedStatement stmt = getConnection().prepareStatement(StringUtil.QUERY_GET_FARMER);
			stmt.setString(1, phone);
			ResultSet result = stmt.executeQuery();

			while (result.next()) {
				password = result.getString("password");
			}
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}
		return password;
	}

	/**
	 * Retrieves information about a seller from the database based on their phone number.
	 * 
	 * @param phone The phone number of the seller.
	 * @return An AdminModel object containing the information of the seller.
	 */
	public AdminModel getSellerInfo(String phone) {
		AdminModel seller = new AdminModel();
		try {
			PreparedStatement stmt = getConnection().prepareStatement(StringUtil.QUERY_GET_SELLER);
			stmt.setString(1, phone);
			ResultSet result = stmt.executeQuery();

			while (result.next()) {
				seller.setName(result.getString("Name"));
				seller.setEmail(result.getString("Email"));
				seller.setAddress(result.getString("Addres"));
				seller.setPhone(result.getString("Phone"));
				seller.setImageUrlFromDB(result.getString("Profile_image"));
			}
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return seller;
	}

	/**
	 * Retrieves the ID of a seller from the database based on their phone number.
	 * 
	 * @param phone The phone number of the seller.
	 * @return The ID of the seller.
	 */
	public int getSellerId(String phone) {
		int id = 0;
		try {
			PreparedStatement stmt = getConnection().prepareStatement(StringUtil.QUERY_GET_SELLER);

			stmt.setString(1, phone);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				id = Integer.parseInt(result.getString("ID"));
			}
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return id;
	}

	/**
	 * Updates the profile image of a farmer in the database.
	 * 
	 * @param farmer The FarmerModel object containing the updated information.
	 * @return An integer indicating the status of the update operation:
	 *         - 1: Update successful
	 *         - 0: Update failed
	 *         - -1: Internal error
	 */
	public int updateFarmerImage(FarmerModel farmer) {
		try {
			PreparedStatement stmt = getConnection().prepareStatement(StringUtil.QUERY_UPDATE_FARMER_IMAGE);
			// Set the parameters for the prepared statement
			stmt.setString(1, farmer.getImageUrlFromPart());
			stmt.setString(2, farmer.getPhone());
			// Execute the update
			int result = stmt.executeUpdate();
			return result;
		} catch (SQLException | ClassNotFoundException ex) {
			// Print the stack trace for debugging purposes
			ex.printStackTrace();
			// Return -2 to indicate an internal error
			return -2;
		}

	}

	/**
	 * Updates the password of a farmer in the database.
	 * 
	 * @param password The new password.
	 * @param phone    The phone number of the farmer.
	 * @return An integer indicating the status of the update operation:
	 *         - 1: Update successful
	 *         - 0: Update failed
	 *         - -1: Internal error
	 */
	public int updateFarmerPassword(String password, String phone) {
		try {
			PreparedStatement stmt = getConnection().prepareStatement(StringUtil.QUERY_UPDATE_FARMER_PASSWORD);
			// Set the parameters for the prepared statement
			stmt.setString(1, password);
			stmt.setString(2, phone);
			// Execute the update
			int result = stmt.executeUpdate();
			return result;
		} catch (SQLException | ClassNotFoundException ex) {
			// Print the stack trace for debugging purposes
			ex.printStackTrace();
			// Return -2 to indicate an internal error
			return -2;
		}

	}

	/**
	 * Registers a new admin in the database.
	 * 
	 * @param admin The AdminModel object containing the information of the admin to be registered.
	 * @return An integer indicating the registration status:
	 *         - 1: Registration successful
	 *         - 0: Registration failed (no rows affected)
	 *         - -1: Internal error
	 */
	public int registerAdmin(AdminModel admin) {
		try {
			// Prepare a statement using the predefined query for FARMER registration
			PreparedStatement stmt = getConnection().prepareStatement(StringUtil.QUERY_REGISTER_ADMIN);

			// Set the student information in the prepared statement
			stmt.setString(1, admin.getName());
			stmt.setString(2, admin.getAddress());
			stmt.setString(3, admin.getEmail());
			stmt.setString(4, admin.getPhone());
			stmt.setString(5, admin.getImageUrlFromPart());
			stmt.setString(6, PasswordEncryptionWithAes.encrypt(admin.getPhone(), admin.getPassword()));

			// Execute the update statement and store the number of affected rows
			int result = stmt.executeUpdate();

			// Check if the update was successful (i.e., at least one row affected)
			if (result > 0) {
				return 1; // Registration successful
			} else {
				return 0; // Registration failed (no rows affected)
			}

		} catch (ClassNotFoundException | SQLException ex) {
			// Print the stack trace for debugging purposes
			ex.printStackTrace();
			return -1; // Internal error
		}
	}

	/**
	 * Checks if a product code already exists in the database.
	 * 
	 * @param code The product code to be checked for existence.
	 * @return true if the product code exists in the database, false otherwise.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public boolean isCodeExists(String code) throws ClassNotFoundException {
		boolean codeExists = false;
		try {
			// checks in farmer table
			PreparedStatement stPCode = getConnection().prepareStatement(StringUtil.QUERY_PRODUCT_CODE_CHECK);

			stPCode.setString(1, code);

			try (ResultSet productResultSet = stPCode.executeQuery()) {
				codeExists = productResultSet.next() && productResultSet.getInt(1) > 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return codeExists;
	}

	/**
	 * Adds a new product to the database.
	 * 
	 * @param product The ProductModel object containing the information of the product to be added.
	 * @return An integer indicating the status of the insertion operation:
	 *         - 1: Insertion successful
	 *         - 0: Insertion failed
	 *         - -1: Internal error
	 */
	public int addNewProduct(ProductModel product) {
		try {
			// Prepare a statement using the predefined query for FARMER registration
			PreparedStatement stmt = getConnection().prepareStatement(StringUtil.QUERY_ADD_NEW_PRODUCT);

			// Set the student information in the prepared statement
			stmt.setLong(1, product.getSellerId());
			stmt.setString(2, product.getProductCode());
			stmt.setString(3, product.getName());
			stmt.setString(4, product.getDescription());
			stmt.setString(5, product.getCategory());
			stmt.setLong(6, product.getPrice());
			stmt.setLong(7, product.getStock());
			stmt.setString(8, product.getImageUrlFromPart());

			// Execute the update statement and store the number of affected rows
			int result = stmt.executeUpdate();

			// Check if the update was successful (i.e., at least one row affected)
			if (result > 0) {
				return 1; // Registration successful
			} else {
				return 0; // Registration failed (no rows affected)
			}

		} catch (ClassNotFoundException | SQLException ex) {
			// Print the stack trace for debugging purposes
			ex.printStackTrace();
			return -1; // Internal error
		}
	}

	/**
	 * Updates an existing product in the database.
	 * 
	 * @param product The ProductModel object containing the updated information.
	 * @return An integer indicating the status of the update operation:
	 *         - 1: Update successful
	 *         - 0: Update failed
	 *         - -1: Internal error
	 */
	public int updateProduct(ProductModel product) {
		try {
			// Prepare a statement using the predefined query for FARMER registration
			PreparedStatement stmt = getConnection().prepareStatement(StringUtil.QUERY_UPDATE_PRODUCT);

			// Set the student information in the prepared statement
			stmt.setLong(1, product.getSellerId());
			stmt.setString(2, product.getName());
			stmt.setString(3, product.getDescription());
			stmt.setString(4, product.getCategory());
			stmt.setLong(5, product.getPrice());
			stmt.setLong(6, product.getStock());
			stmt.setString(7, product.getImageUrlFromPart());
			stmt.setString(8, product.getProductCode());

			// Execute the update statement and store the number of affected rows
			int result = stmt.executeUpdate();

			// Check if the update was successful (i.e., at least one row affected)
			if (result > 0) {
				return 1; // Registration successful
			} else {
				return 0; // Registration failed (no rows affected)
			}

		} catch (ClassNotFoundException | SQLException ex) {
			// Print the stack trace for debugging purposes
			ex.printStackTrace();
			return -1; // Internal error
		}
	}

	/**
	 * Retrieves information about a product from the database based on its product code.
	 * 
	 * @param pCode The product code.
	 * @return A ProductModel object containing the information of the product.
	 */
	public ProductModel getProductInfo(String pCode) {
		ProductModel product = new ProductModel();
		try {
			PreparedStatement stmt = getConnection().prepareStatement(StringUtil.QUERY_GET_PRODUCT_INFO);
			stmt.setString(1, pCode);
			ResultSet result = stmt.executeQuery();

			while (result.next()) {
				product.setSellerId(result.getInt("Seller_id"));
				product.setProductCode(result.getString("Code"));
				product.setName(result.getString("Name"));
				product.setDescription(result.getString("Description"));
				product.setCategory(result.getString("Category"));
				product.setPrice(result.getInt("Price"));
				product.setStock(result.getInt("Stock"));
				product.setImageUrlFromDB(result.getString("Sample_Image"));
			}
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}
		return product;
	}

	/**
	 * Deletes a product from the database based on its product code.
	 * 
	 * @param code The product code of the product to be deleted.
	 * @return An integer indicating the status of the deletion operation:
	 *         - 1: Deletion successful
	 *         - 0: Deletion failed
	 *         - -1: Internal error
	 */
	public int deleteProduct(String code) {
		try (Connection con = getConnection()) {
			PreparedStatement st = con.prepareStatement(StringUtil.QUERY_DELETE_PRODUCT);
			st.setString(1, code);
			return st.executeUpdate();
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace(); // Log the exception for debugging
			return -1;
		}
	}

	/**
	 * Retrieves information about all products matching a search key from the database.
	 * 
	 * @param searchKey The search key used to filter products.
	 * @return An ArrayList containing ProductModel objects representing the searched products.
	 */
	public ArrayList<ProductModel> getAllSearchedProduct(String searchKey) {
		try {
			PreparedStatement stmt = getConnection().prepareStatement(StringUtil.QUERY_GET_ALL_SEARCHED_PRODUCT);
			stmt.setString(1, "%" + searchKey + "%");
			ResultSet result = stmt.executeQuery();

			ArrayList<ProductModel> searchedProducts = new ArrayList<ProductModel>();

			while (result.next()) {

				ProductModel product = new ProductModel();
				product.setSellerId(result.getInt("Seller_id"));
				product.setProductCode(result.getString("Code"));
				product.setName(result.getString("Name"));
				product.setDescription(result.getString("Description"));
				product.setCategory(result.getString("Category"));
				product.setStock(result.getInt("Price"));
				product.setPrice(result.getInt("Stock"));
				product.setImageUrlFromDB(result.getString("Sample_Image"));
				searchedProducts.add(product);
			}
			return searchedProducts;
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}

	}
	
	/**
	 * Retrieves information about all products of a specific seller matching a search key from the database.
	 * 
	 * @param searchKey The search key used to filter products.
	 * @param sellerId  The ID of the seller.
	 * @return An ArrayList containing ProductModel objects representing the searched products.
	 */
	public ArrayList<ProductModel> getAllSellerSearchedProduct(String searchKey, int sellerId) {
		try {
			PreparedStatement stmt = getConnection().prepareStatement(StringUtil.QUERY_GET_ALL_SELLER_SEARCHED_PRODUCT);
			stmt.setInt(1,sellerId);
			stmt.setString(2, "%" + searchKey + "%");
			ResultSet result = stmt.executeQuery();
			ArrayList<ProductModel> searchedProducts = new ArrayList<ProductModel>();

			while (result.next()) {
				System.out.println(" : this is name form db controller");
				ProductModel product = new ProductModel();
				product.setSellerId(result.getInt("Seller_id"));
				product.setProductCode(result.getString("Code"));
				product.setName(result.getString("Name"));
				product.setDescription(result.getString("Description"));
				product.setCategory(result.getString("Category"));
				product.setStock(result.getInt("Price"));
				product.setPrice(result.getInt("Stock"));
				product.setImageUrlFromDB(result.getString("Sample_Image"));
				searchedProducts.add(product);
			}
			System.out.println(searchKey + " : theis is searach key");
			System.out.println(searchedProducts.size() + ": this is size of search list from db controller");
			return searchedProducts;
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}

	}

	/**
	 * Updates the details of a farmer in the database.
	 * 
	 * @param newFarmer The FarmerModel object containing the updated information.
	 * @param phone     The phone number of the farmer.
	 * @return An integer indicating the status of the update operation:
	 *         - 1: Update successful
	 *         - 0: Update failed
	 *         - -1: Internal error
	 */
	public int updateFarmerDetails(FarmerModel newFarmer, String phone) {
		try {
			PreparedStatement stmt = getConnection().prepareStatement(StringUtil.QUERY_UPDATE_FARMER_DETAILS);
			// Set the parameters for the prepared statement
			stmt.setString(1, newFarmer.getFullname());
			stmt.setString(2, newFarmer.getAddress());
			stmt.setString(3, phone);
			// Execute the update
			int result = stmt.executeUpdate();
			return result;
		} catch (SQLException | ClassNotFoundException ex) {
			// Print the stack trace for debugging purposes
			ex.printStackTrace();
			// Return -2 to indicate an internal error
			return -2;
		}
	}

	/**
	 * Deletes the profile of a farmer from the database.
	 * 
	 * @param user The FarmerModel object representing the farmer to be deleted.
	 * @return An integer indicating the status of the deletion operation:
	 *         - 1: Deletion successful
	 *         - 0: Deletion failed
	 *         - -1: Internal error
	 */
	public int deleteFarmerProfile(FarmerModel user) {
		try (Connection con = getConnection()) {
			PreparedStatement st = con.prepareStatement(StringUtil.QUERY_DELETE_FARMER);
			st.setString(1, user.getPhone());
			return st.executeUpdate();
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace(); // Log the exception for debugging
			return -1;
		}
	}

	/**
	 * Retrieves the count of farmers registered in the database.
	 * 
	 * @return The count of farmers.
	 */
	public int getFarmerCount() {
		try {
			PreparedStatement stmt = getConnection().prepareStatement(StringUtil.QUERY_GET_FARMER_COUNT);
			
			ResultSet result = stmt.executeQuery();
			
			int farmerCount = 0;

			while (result.next()) {
				farmerCount = result.getInt(1);
			}
			
			return farmerCount;
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	
	/**
	 * Retrieves the total value of stock for a specific seller from the database.
	 * 
	 * @param sellerId The ID of the seller.
	 * @return The total value of stock.
	 */
	public double getStockValue(int sellerId) {
		try {
			PreparedStatement stmt = getConnection().prepareStatement(StringUtil.QUERY_GET_STOCK_VALUE);
			stmt.setInt(1, sellerId);
			ResultSet result = stmt.executeQuery();
			
			double stockValue = 0.0;

			while (result.next()) {
				stockValue = result.getDouble("total_valuation");
			}
			return stockValue;
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	
	
	/**
	 * Retrieves the total stock count for a specific seller from the database.
	 * 
	 * @param sellerId The ID of the seller.
	 * @return The total stock count.
	 */
	public int getStock(int sellerId) {
		try {
			PreparedStatement stmt = getConnection().prepareStatement(StringUtil.QUERY_GET_STOCK);
			stmt.setInt(1, sellerId);
			ResultSet result = stmt.executeQuery();
			
			int stockCount = 0;

			while (result.next()) {
				stockCount = result.getInt("stocks");
			}
			return stockCount;
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	

	

}
