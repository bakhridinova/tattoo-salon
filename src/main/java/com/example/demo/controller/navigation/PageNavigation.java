package com.example.demo.controller.navigation;

public final class PageNavigation {
    // common pages
    public static final String HOME = "/index.jsp";
    public static final String DEFAULT = "/index.jsp";
    public static final String CATALOG = "/view/pages/catalog.jsp";
    public static final String NEW_IMAGE = "/view/pages/new_image.jsp";
    public static final String IMAGE_DETAILS = "/view/pages/image_details.jsp";

    // sign pages
    public static final String SIGN_UP = "/view/pages/signup.jsp";
    public static final String SIGN_IN = "/view/pages/signin.jsp";

    // user pages
    public static final String USER_ACCOUNT = "/view/pages/user_account.jsp";
    public static final String USER_ORDERS = "/view/pages/user_orders.jsp";
    public static final String USER_IMAGES = "/view/pages/user_images.jsp";
    public static final String USER_RATINGS = "/view/pages/user_ratings.jsp";
    public static final String EDIT_ACCOUNT = "/view/pages/edit_account.jsp";
    public static final String DELETE_ACCOUNT = "/view/pages/delete_account.jsp";

    // error pages
    public static final String ERROR_404 = "/error_404";
    public static final String ERROR_500 = "/error_500";

    // admin pages
    public static final String STATISTICS = "/view/pages/admin/stats.jsp";
    public static final String ALL_TASKS = "/view/pages/admin/all_tasks.jsp";
    public static final String ALL_USERS = "/view/pages/admin/all_users.jsp";
    public static final String ALL_IMAGES = "/view/pages/admin/all_images.jsp";
    public static final String ALL_ORDERS = "/view/pages/admin/all_orders.jsp";
    public static final String ALL_RATINGS = "/view/pages/admin/all_ratings.jsp";

    // url patterns
    public static final String URL_REDIRECT_BASE_PATTERN = "/home?command=%s";
}
