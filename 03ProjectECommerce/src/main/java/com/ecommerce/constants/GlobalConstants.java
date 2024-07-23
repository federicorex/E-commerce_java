package com.ecommerce.constants;

public class GlobalConstants {
	
	// User constants

	public static final String LOGGER_GET_ALL_USERS = "Fetching all users";
	public static final String LOGGER_GET_USER_BY_ID = "Fetching the user with userId: {}";
	public static final String LOGGER_GET_USER_BY_ID_FAIL = "Fail, user not found";
	public static final String LOGGER_ADD_USER_START = "Adding {}...";
	public static final String LOGGER_ADD_USER_SUCCESS = "Success, user with userId: {} added";
	public static final String LOGGER_ADD_USER_FAIL = "Fail, user with userId: {} not added";
	public static final String LOGGER_UPDATE_USER_START = "Updating user with userId: {}...";
	public static final String LOGGER_UPDATE_USER_SUCCESS = "Success, user with userId: {} updated";
	public static final String LOGGER_DELETE_USER_START = "Deleting user with userId: {}...";
	public static final String LOGGER_DELETE_USER_SUCCESS = "Success, user with userId: {} deleted";
	public static final String INVALID_DATE_OF_BIRTH_YOUNGER_THAN_18 = "You must be at least 18 years old";
	
	// Product constants
	
	public static final String LOGGER_GET_ALL_PRODUCTS = "Fetching all products";
	public static final String LOGGER_GET_PRODUCT_BY_ID = "Fetching the product with productId: {}";
	public static final String LOGGER_GET_PRODUCT_BY_ID_FAIL = "Fail, product not found";
	public static final String LOGGER_ADD_PRODUCT_START = "Adding {}...";
	public static final String LOGGER_ADD_PRODUCT_SUCCESS = "Success, product with productId: {} added";
	public static final String LOGGER_UPDATE_PRODUCT_START = "Updating product with productId: {}...";
	public static final String LOGGER_UPDATE_PRODUCT_SUCCESS = "Success, product with productId: {} updated";
	public static final String LOGGER_DELETE_PRODUCT_START = "Deleting product with productId: {}...";
	public static final String LOGGER_DELETE_PRODUCT_SUCCESS = "Success, product with productId: {} deleted";
	
	// Order constants
	
	public static final String LOGGER_GET_ALL_ORDERS = "Fetching all orders";
	public static final String LOGGER_GET_ORDER_BY_ID = "Fetching the order with orderId: {}";
	public static final String LOGGER_GET_ORDER_BY_ID_FAIL = "Fail, order not found";
	public static final String LOGGER_ADD_ORDER_START = "Adding order with userId: {} and productId: {}...";
	public static final String LOGGER_ADD_ORDER_SUCCESS = "Success, order with orderId: {}, userId: {} and productId: {} added";
	public static final String LOGGER_ADD_ORDER_FAIL = "Fail, order not added: user or product not found";
	public static final String LOGGER_UPDATE_ORDER_START = "Updating order with orderId: {}...";
	public static final String LOGGER_UPDATE_ORDER_SUCCESS = "Success, order with orderId: {} updated";
	public static final String LOGGER_DELETE_ORDER_START = "Deleting order with orderId: {}...";
	public static final String LOGGER_DELETE_ORDER_SUCCESS = "Success, order with orderId: {} deleted";
}
