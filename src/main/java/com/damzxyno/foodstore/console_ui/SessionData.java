package com.damzxyno.foodstore.console_ui;

import com.damzxyno.foodstore.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionData {
    private String Username;
    private long userId;
    private UserType userType;
}
