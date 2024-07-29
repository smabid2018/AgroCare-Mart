package util;

public class StringUtil {
	
	// Start: DB Connection
		public static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
		public static final String LOCALHOST_URL = "jdbc:mysql://localhost:3306/agrocare_mart";
		public static final String LOCALHOST_USERNAME = "root";
		public static final String LOCALHOST_PASSWORD = "";

		public static final String IMAGE_ROOT_PATH = "Users\\smabi\\eclipse-workspace\\AgroCareMart\\src\\main\\webapp\\resources\\images\\";
		public static final String IMAGE_DIR_PRODUCT = "C:\\" + IMAGE_ROOT_PATH + "product\\";
		public static final String IMAGE_DIR_USER = "C:\\" + IMAGE_ROOT_PATH + "user\\";
	
	// Start: Db columns names
		public static final String DB_FULL_NAME = "name";
		public static final String DB_ADDRESS = "address";
		public static final String DB_EMAIL = "email";
		public static final String DB_PHONE_NUMBER = "phone";
		public static final String DB_PHONE = "Phone";
		public static final String DB_PASSWORD = "password";
		
	// Start: Parameter names
		public static final String FULL_NAME = "fullName";
		public static final String ADDRESS = "address";
		public static final String EMAIL = "email";
		public static final String PHONE_NUMBER = "phoneNumber";
		public static final String IMAGE = "image";
		public static final String PASSWORD = "password";
		public static final String RETYPE_PASSWORD = "retypePassword";
		
		
		public static final String PRODUCT_CODE = "code";
		public static final String PRODUCT_NAME = "name";
		public static final String PRODUCT_CATEGORY = "category";
		public static final String PRODUCT_PRICE = "price";
		public static final String PRODUCT_STOCK = "stock";
		public static final String PRODUCT_DESCRIPTION = "description";
		public static final String PRODUCT_IMAGE = "image";
	// End: Parameter names
		
		
	// Start: Queries
	public static final String QUERY_REGISTER_FARMER = "INSERT INTO farmers ("
			+ "name,address,email,phone,profile_image,password) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";
	
	public static final String QUERY_REGISTER_ADMIN = "INSERT INTO sellers ("
			+ "Name,Addres,Email,Phone,Profile_image,password) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";
	
	public static final String QUERY_ADD_NEW_PRODUCT = "INSERT INTO products ("
			+ "Seller_id,Code,Name,Description,Category,Price,Stock,Sample_Image) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	
	public static final String QUERY_UPDATE_PRODUCT = "UPDATE products SET Seller_id = ?, Name = ?, Description = ?,"
			+ " Category = ?,  Price = ?, Stock = ?, Sample_Image = ? WHERE Code = ?";

	public static final String QUERY_FARMER_REGISTER_PHONE_CHECK = "SELECT COUNT(*) FROM farmers WHERE phone = ?";
	public static final String QUERY_SELLER_REGISTER_PHONE_CHECK = "SELECT COUNT(*) FROM sellers WHERE Phone = ?";
	public static final String QUERY_GET_FARMER_COUNT = "SELECT COUNT(*) FROM farmers";
	public static final String QUERY_FARMER_REGISTER_EMAIL_CHECK = "SELECT * FROM farmers WHERE email = ?";
	public static final String QUERY_SELLER_REGISTER_EMAIL_CHECK = "SELECT * FROM sellers WHERE Email = ?";
	public static final String QUERY_PRODUCT_CODE_CHECK = "SELECT * FROM products WHERE Code = ?";
	public static final String QUERY_FARMER_LOGIN_PHONE_CHECK = "SELECT * FROM farmers WHERE phone = ?";
	public static final String QUERY_SELLER_LOGIN_PHONE_CHECK = "SELECT * FROM sellers WHERE Phone = ?";
	
	public static final String QUERY_GET_FARMER = "SELECT * FROM farmers WHERE phone = ?";
	public static final String QUERY_GET_SELLER = "SELECT * FROM sellers WHERE Phone = ?";
	public static final String QUERY_GET_PRODUCT_INFO = "SELECT * FROM products WHERE Code = ?";
	
	public static final String QUERY_GET_ALL_FARMER = "SELECT * FROM farmers";
	
	public static final String QUERY_GET_ALL_PRODUCT = "SELECT * FROM products";
	public static final String QUERY_GET_ALL_SELLER_PRODUCT = "SELECT * FROM products WHERE Seller_id = ?";
	public static final String QUERY_GET_STOCK = "SELECT SUM(Stock) AS stocks FROM products WHERE Seller_id = ?";
	public static final String QUERY_GET_STOCK_VALUE = "SELECT SUM(Stock * Price) AS total_valuation FROM products WHERE Seller_id = ?";
	
	public static final String QUERY_GET_ALL_SELLER_SEARCHED_PRODUCT = "SELECT * FROM products WHERE Seller_id = ? AND Name LIKE ?";
	public static final String QUERY_GET_ALL_SEARCHED_PRODUCT = "SELECT * FROM products WHERE Name LIKE ? ";
	
	public static final String QUERY_UPDATE_FARMER_IMAGE = "UPDATE farmers SET profile_image = ? WHERE phone = ?";
	public static final String QUERY_UPDATE_FARMER_PASSWORD = "UPDATE farmers SET password = ? WHERE phone = ?";
	public static final String QUERY_UPDATE_FARMER_DETAILS = "UPDATE farmers SET name = ?, address = ? WHERE phone = ?";
	
	public static final String QUERY_DELETE_PRODUCT = "DELETE FROM products WHERE Code = ?";
	
	public static final String QUERY_DELETE_FARMER = "DELETE FROM farmers WHERE phone = ?";
	
	
	// End: Queries
	
	
	// Start: Validation Messages
		// Register Page Messages
		public static final String MESSAGE_SUCCESS_REGISTER = "Successfully Registered!";
		public static final String MESSAGE_ERROR_USERNAME = "Username is already registered.";
		public static final String MESSAGE_ERROR_EMAIL = "Email is already registered.";
		public static final String MESSAGE_ERROR_PHONE_NUMBER = "Phone number is already registered.";
		public static final String MESSAGE_ERROR_PASSWORD_UNMATCHED = "Password is not matched.";
		public static final String MESSAGE_ERROR_PASSWORD_FORMAT = "Password must contains Uppercase,Lowercase,number, and symbol";
		public static final String MESSAGE_ERROR_IMAGE_FORMAT = 
				"Invalid file type. Only JPG, JPEG, and PNG files are allowed.";
		public static final String MESSAGE_ERROR_INCORRECT_DATA = "Please correct all the fields.";
		public static final String MESSAGE_ERROR_WRONG_PASS = "Password is incorrect";
	
	// Other Messages
		public static final String MESSAGE_SUCCESS_DELETE = "Successfully Deleted!";
		public static final String MESSAGE_ERROR_DELETE = "Cannot delete the user!";
		public static final String MESSAGE_SUCCESS = "successMessage";
		public static final String MESSAGE_ERROR = "errorMessage";
		
		// Login Page Messages
		public static final String MESSAGE_SUCCESS_LOGIN = "Successfully LoggedIn!";
		public static final String MESSAGE_ERROR_LOGIN = "Either username or password is not correct!";
		public static final String MESSAGE_ERROR_CREATE_ACCOUNT = "Account for this username is not registered! Please create a new account.";
		
		
		// Add new product page messages
		public static final Object MESSAGE_ERROR_INVALID_CODE = "The Product code is Invalid";
		public static final Object MESSAGE_ERROR_CODE_EXISTS = "The Product code is already added";
		public static final Object MESSAGE_SUCCESS_ADD_PRODUCT = "Product added Successfully";
		public static final Object MESSAGE_ERROR_ADD_PRODUCT = "Product add Failed";
		public static final Object MESSAGE_SUCCESS_UPDATE_PRODUCT = "Product Updated Successfully";
		public static final Object MESSAGE_ERROR_UPDATE_PRODUCT = "Product Update Failed";
		
		public static final Object MESSAGE_ERROR_DELETE_PRODUCT = "Product Delete Failed";
		
	// Error Messages
		public static final Object MESSAGE_ERROR_REGISTER = "Please Correct the form data";
		public static final Object MESSAGE_ERROR_SERVER = "An unexpected server error Occured";
		
	// Page URL
		public static final String URL_INDEX = "/index.jsp";
		public static final String URL_FARMER_PROFILE = "/pages/farmerProfile.jsp";
		public static final String URL_ADMIN_HOME = "/pages/admin.jsp";
		public static final String URL_ADMIN_REGISTER = "/pages/adminRegistration.jsp";
		public static final String URL_ADMIN_PRODUCT = "/pages/adminProducts.jsp";
		public static final String URL_ADD_PRODUCT = "/pages/addNewProduct.jsp";
		public static final String URL_EDIT_PRODUCT = "/pages/editProduct.jsp";
		public static final String URL_FARMER_SEARCH_PAGE = "/pages/farmerSearchPage.jsp";
		public static final String URL_SEARCHED_PRODUCT_SELLER = "/pages/adminSearchedItems.jsp";

		public static final String URL_HOME_CATEGORY = "/pages/homeCategory.jsp";
		public static final String SERVLET_URL_FARMER_HOME = "/FarmerHomeServlet";
		public static final String SERVLET_URL_SELLER_HOME = "/AdminHome";
		public static final String URL_SERVLET_ADMIN_PRODUCT = "/AdminProducts";
		public static final String URL_SERVLET_PRODUCT_USER = "/ProductServlet";
		public static final String URL_SERVLET_ALL_PRODUCT = "/AllProductFarmer";
		
		
		
		
		
		// Start: Normal Text
		public static final String USER = "user";
		public static final String SUCCESS = "success";
		public static final String TRUE = "true";
		public static final String JSESSIONID = "JSESSIONID";
		public static final String LOGIN = "Login";
		public static final String LOGOUT = "Logout";
		public static final String FARMER_MODEL = "studentModel";
		public static final String FARMER_LISTS = "studentLists";
		public static final String SLASH= "/";
		// End: Normal Text
		public static final String FARMER_INFO = "farmerInfo";
		public static final String ADMIN_INFO = "adminInfo";
		public static final String UPDATE_ID = "uploadImage";
		public static final String DELETE_ID = "deleteImage";
		
		
		public static final String OLD_PASS = "oldPass";
		public static final String NEW_PASS = "newPass";
		public static final String RETYPED_PASS = "retypedPass";
		
		public static final String ADMIN_PHONE = "adminPhone";
		public static final String ADMIN_ID = "adminPhone";
		public static final String PRODUCTS_LIST = "productsList";
		public static final String PRODUCTS_INFO = "productInfo";
		public static final String PRODUCT_CODE_DELETE = "deleteCode";
		public static final String CURRENT_IMAGE = "currentImage";
		
		
		public static final String SEARCH_KEY = "searchKey";
		public static final String UPDATE_FARMER_DETAILS = "updateFarmerDetails";
		public static final String UPDATE_FARMER_PASS = "updateFarmerPass";
		public static final String DELETE_USER = "deleteUser";
		public static final String PRODUCT_COUNT = "productCount";
		public static final String FARMER_COUNT = "farmerCount";
		public static final String STOCK_VALUE = "stockValue";
		public static final String STOCK_COUNT = "stockCount";
		
		
		
		
		
				
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
				
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}
