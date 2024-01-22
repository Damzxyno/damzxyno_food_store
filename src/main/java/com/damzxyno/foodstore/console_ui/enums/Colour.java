package com.damzxyno.foodstore.console_ui.enums;

/**
 * This class contains all colour used in the console app
 */
public enum Colour {
    RESET("\u001B[0m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    ORANGE("\u001B[38;2;255;165;0m"),
    BLACK("\u001B[30m"),
    CYAN("\u001B[36m"),
    PURPLE("\u001B[35m");


    private final String colorCode;
    Colour (String colorCode){
        this.colorCode = colorCode;
    }

    public String getCode(){
        return this.colorCode;
    }
}

