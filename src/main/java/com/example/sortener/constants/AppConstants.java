package com.example.sortener.constants;

public final class AppConstants {

    private AppConstants() {
    }

    public static final String OPENED_ACCOUNT = "Your account is opened.";
    public static final String EXIST_ACCOUNT = "Your account already exist.";
    public static final String APP_LINK = "http://localhost:8080/";
    public static final String INCORRECT_REQUEST = "INCORRECT_REQUEST";
    public static final String BAD_REQUEST = "BAD_REQUEST";
    public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";

    public static final String HELP_PAGE = "INSTALL \n \n\n 1. Run mvc clean package to create executable JAR file \n\n " +
            "2. JAR File is contained in root/target/shortener-0.0.1-SNAPSHOT.jar \n\n " +
            "3. Find the file through any kind of cmd/terminal and run command java-jar-urlShortener-0.0.1-UrlShortener.jar \n\n " +
            "4. This will start up whole application \n\n " +
            "5. Add row (127.0.0.1) you your host file\n\n\n" +
            "USAGE\n\n\n " +
            "1. Swagger documentation can be found at localhost:8080/swagger/ui/index.html -> Here you can find all endpoints.\n\n " +
            "2. For using app you need an account. \n\n " +
            "3. Once account is create, endpoints are using basic auth (name/password)\n\n " +
            "4. For shortening of URL please use /register endpoint whitch will return short version of URL \n\n " +
            "";

}
