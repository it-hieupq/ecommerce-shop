package com.capstone.client.user;

import com.capstone.client.user.domain.common.Stringify;

public class MAIN {
    public static void main(String[] args) {
        String email = "arabiapps9999@gmail.com";
        String username = "hihuhihi";
        System.out.println(email.matches(Stringify.REGEX_EMAIL));
        System.out.println(username.matches(Stringify.REGEX_USERNAME));
    }
}
