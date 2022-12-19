package com.example.demo.model.dao;

/**
 * Database info: table, column names.
 */
public final class DataBaseInfo {
    //Table: images
    public static final String IMAGES_TABLE = "images";

    public static final String IMAGES_TABLE_PK_COLUMN = "id";
    public static final String IMAGES_TABLE_FK_USER_ID_COLUMN = "user_id";
    public static final String IMAGES_TABLE_STATUS_COLUMN = "status";
    public static final String IMAGES_TABLE_SHORT_DESCRIPTION_COLUMN = "short_description";
    public static final String IMAGES_TABLE_lONG_DESCRIPTION_COLUMN = "long_description";
    public static final String IMAGES_TABLE_CREATED_AT_COLUMN = "created_at";
    public static final String IMAGES_TABLE_ORDERS_COLUMN = "orders";
    public static final String IMAGES_TABLE_RATING_COLUMN = "rating";
    public static final String IMAGES_TABLE_PRICE_COLUMN = "price";
    public static final String IMAGES_TABLE_URL_COLUMN = "url";

    //Table: orders
    public static final String ORDERS_TABLE = "orders";

    public static final String ORDERS_TABLE_PK_COLUMN = "id";
    public static final String ORDERS_TABLE_FK_USER_ID_COLUMN = "user_id";
    public static final String ORDERS_TABLE_FK_IMAGE_ID_COLUMN = "image_id";
    public static final String ORDERS_TABLE_STATUS_COLUMN = "status";
    public static final String ORDERS_TABLE_WITH_DISCOUNT_COLUMN = "with_discount";
    public static final String ORDERS_TABLE_AMOUNT_COLUMN = "amount";
    public static final String ORDERS_TABLE_CREATED_AT_COLUMN = "created_at";

    //Table: ratings
    public static final String RATINGS_TABLE = "ratings";

    public static final String RATINGS_TABLE_PK_COLUMN = "id";
    public static final String RATINGS_TABLE_FK_USER_ID_COLUMN = "user_id";
    public static final String RATINGS_TABLE_FK_IMAGE_ID_COLUMN = "image_id";
    public static final String RATINGS_TABLE_RATING_COLUMN = "rating";
    public static final String RATINGS_TABLE_REVIEW_COLUMN = "review";
    public static final String RATINGS_TABLE_STATUS_COLUMN = "status";
    public static final String RATINGS_TABLE_CREATED_AT_COLUMN = "created_at";


    //Table: users
    public static final String USERS_TABLE = "users";

    public static final String USERS_TABLE_PK_COLUMN = "id";
    public static final String USERS_TABLE_ROLE_COLUMN = "role";
    public static final String USERS_TABLE_STATUS_COLUMN = "status";
    public static final String USERS_TABLE_USER_NAME_COLUMN = "user_name";
    public static final String USERS_TABLE_PASSWORD_COLUMN = "password";
    public static final String USERS_TABLE_GENDER_COLUMN = "gender";
    public static final String USERS_TABLE_BIRTH_DATE_COLUMN = "birth_date";
    public static final String USERS_TABLE_FULL_NAME_COLUMN = "full_name";
    public static final String USERS_TABLE_EMAIL_ADDRESS_COLUMN = "email_address";
    public static final String USERS_TABLE_CONTACT_NUMBER_COLUMN = "contact_number";
}
