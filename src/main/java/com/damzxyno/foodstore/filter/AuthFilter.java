package com.damzxyno.foodstore.filter;

import com.damzxyno.foodstore.dto.request.identity.LoggedInContext;
import com.damzxyno.foodstore.enums.UserType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * This class act as a filter for authentication and authorization of request
 */
@Component
@RequiredArgsConstructor
public class AuthFilter {
    private final ObjectMapper mapper;
    private final String COOKIE_KEY = "details";

    /**
     * This class checkes the cookie to confirm if the user is logged in and who is logged in
     * @param request
     * @return LoggedInContext
     */

    public LoggedInContext parseLoginContextFromRequest(HttpServletRequest request){
        var cookies = request.getCookies();
        var res = new LoggedInContext();
        if (cookies == null) {
            return null;
        }
        for (var cookie : cookies) {
            if (cookie.getName().equals(COOKIE_KEY)) {
                String [] message = cookie.getValue().split(":");
                try {
                    res.setLoggedIn(true);
                    res.setId(Long.parseLong(message[0]));
                    res.setUserType(UserType.valueOf(message[1]));
                } catch (Exception ex){
                }
            }
        }
        return res;
    }
}
