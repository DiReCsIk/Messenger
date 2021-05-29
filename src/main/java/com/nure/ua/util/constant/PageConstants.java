package com.nure.ua.util.constant;

import java.net.URL;
import java.util.Objects;

public final class PageConstants {
    public static final URL SIGN_UP_PAGE_PATH = Objects.requireNonNull(PageConstants.class.getResource("/fxml/signUpPage.fxml"));
    public static final URL SIGN_IN_PAGE_PATH = Objects.requireNonNull(PageConstants.class.getResource("/fxml/signInPage.fxml"));
    public static final URL MAIN_PAGE_PATH = Objects.requireNonNull(PageConstants.class.getResource("/fxml/mainPage.fxml"));

    private PageConstants() {
    }
}
