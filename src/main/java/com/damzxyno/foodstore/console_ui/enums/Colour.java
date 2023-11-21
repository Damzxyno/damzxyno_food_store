package com.damzxyno.foodstore.console_ui.enums;
public enum Colour {


    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    ORANGE("\u001B[38;2;255;165;0m"),
    BLACK("\u001B[30m"),
    CYAN("\u001B[36m"),
    PURPLE("\u001B[35m"),
    WHITE("\u001B[37m"),
    BLUE("\u001B[34m");

    private final String colorCode;
    Colour (String colorCode){
        this.colorCode = colorCode;
    }

    public String getCode(){
        return this.colorCode;
    }
}

