package com.capstone.client.user.domain.common;

public class Stringify {
    public static final String ORIGIN_FRONTEND_URL = "http://localhost:4200";
    public static final String SERVICE_DOMAIN = "http://localhost";
    public static final Integer SERVICE_PORT = 8888;
    public static final String SERVICE_URL = SERVICE_DOMAIN+":"+SERVICE_PORT;

    public static final String REGEX_USERNAME = "^(?=.{6,18}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
    public static final String REGEX_EMAIL = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";
}
