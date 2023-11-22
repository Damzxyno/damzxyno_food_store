package com.damzxyno.foodstore.console_ui.helpers;

import com.damzxyno.foodstore.console_ui.enums.Colour;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.damzxyno.foodstore.console_ui.statics.Message.*;

/**
 * This class help manage I/O on the app. 
 * The functionalities include recognition of exit prompts, customized printing as well as input checks.
 */
@Service
@RequiredArgsConstructor
public class ConsoleIOManager {
    private final Scanner scanner;
    private static final Set<String> exitCharacters = new HashSet<>(List.of("e", "0", "exit"));

    /**
     * This method prints to the screen with a default colour of GREEN.
     * @param word The string/sentence to print to screen.
     */
    public void write(String word){
        write(Colour.GREEN, word);
    }

    /**
     * This method prints a sentence/word to the screen with the specified colour
     * @param color The colour the sentence/ word should assume upon printing to the screen.
     * @param word The sentence/ word to print to the screen.
     */
    public void write(Colour color, String word){
        System.out.println(color.getCode() + word + Colour.RESET);
    }

    /**
     * This method prints to a sentence/ word to the screen with the specified colour as well
     * as formatting the sentence with the provided variadic arguments.
     * @param color The colour the sentence/ word should assume upon printing to the screen.
     * @param word The sentence/ word to print to the screen.
     * @param arr The parameters to format the word.
     */
    public void write(Colour color, String word, Object ...arr){
        write( color,  String.format(word, arr));
    }

    /**
     * This method prints to a sentence/ word to the screen with the default colour as well
     * as formatting the sentence with the provided variadic arguments.
     * @param word The sentence/ word to print to the screen.
     * @param arr The parameters to format the word.
     */
    public void write(String word, Object ...arr){
        write( String.format(word, arr));
    }

    /**
     * This method prints warning messages to the screen with a default colour of PURPLE.
     * @param warning The warning statement to print to screen.
     */
    public void writeWarning(String warning){
        write(Colour.PURPLE, warning);
    }

    /**
     * This method prints success messages to the screen with a default colour of ORANGE.
     * @param success The success statement to print to screen.
     */
    public void writeSuccess(String success){
        write(Colour.ORANGE, success);
    }

    /**
     * This method prints success messages to the screen with a default colour of ORANGE as well
     * as formatting the sentence with the provided variadic arguments.
     * @param success The success statement to print to screen.
     * @param arr The parameters to format the word.
     */
    public void writeSuccess(String success, Object ...arr){
        writeSuccess(String.format(success, arr));
    }

    /**
     * This method prints warning messages to the screen with a default colour of PURPLE as well
     * as formatting the sentence with the provided variadic arguments.
     * @param warning The warning statement to print to screen.
     * @param arr The parameters to format the word.
     */
    public void writeWarning(String warning, Object ...arr){
        writeWarning(String.format(warning, arr));
    }

    /**
     * This method prints error messages to the screen with a default colour of RED.
     * @param error The error statement to print to screen.
     */
    public void writeError(String error){
        write(Colour.PURPLE, error);
    }


    /**
     * This method help manage input of valid integer values.
     * @param noOfRemainingAttempts This keeps tract of how many login attempts the user has.
     * @param promptRemainingAttempts This check if the user should be prompted how many login attempts remains.
     * @param startRange This is the start inclusion of valid input value.
     * @param endRange This is the start inclusion of valid input value.
     * @return an integer value of valid number
     */
    public int readNumber(int noOfRemainingAttempts, int startRange, int endRange, boolean promptRemainingAttempts){
        if (noOfRemainingAttempts == 0){
            writeError(INPUT_ATTEMPT_EXHAUSTION);
            System.exit(0);
        }
        if (promptRemainingAttempts){
            writeWarning(INPUT_ATTEMPT_REMAINING_WARNING, noOfRemainingAttempts);
        }
        int val = 0;
        try{
            val = Integer.parseInt(scanner.next());
        } catch (Exception e){
            writeWarning(INVALID_OPTION);
            writeWarning(NUMERIC_VALUE_REQUIRED_WARNING);
            return readNumber(noOfRemainingAttempts -1, startRange, endRange, true);
        }
        if (val == 0){
            writeWarning(EXIT_SYSTEM_WARNING);
            System.exit(0);
        }
        if (val < startRange || val > endRange){
            writeWarning(INVALID_OPTION);
            writeWarning(NUMBER_OUT_OF_RANGE_WARNING, startRange, endRange);
            return readNumber(noOfRemainingAttempts -1, startRange, endRange, true);
        }
        return val;
    }

    /**
     * This method help manage input of valid string values.
     * @param noOfRemainingAttempts This keeps tract of how many login attempts the user has.
     * @param promptRemainingAttempts This check if the user should be prompted how many login attempts remains.
     * @return an integer value of valid number
     */
    public String readString(int noOfRemainingAttempts, boolean promptRemainingAttempts){
        if (noOfRemainingAttempts == 0){
            writeError(INPUT_ATTEMPT_EXHAUSTION);
            System.exit(0);
        }
        if (promptRemainingAttempts){
            writeWarning(INPUT_ATTEMPT_REMAINING_WARNING, noOfRemainingAttempts);
        }
        var val = scanner.nextLine();
        if (val.isBlank() && promptRemainingAttempts){
            writeWarning(INVALID_OPTION);
            writeWarning(EMPTY_STRING_WARNING);
            return readString(noOfRemainingAttempts -1, promptRemainingAttempts);
        } else if (val.isBlank()){
            return readString(noOfRemainingAttempts, true);
        }

        if (exitCharacters.contains(val.trim().toLowerCase())){
            writeWarning(EXIT_SYSTEM_WARNING);
            System.exit(0);
        }


        return val;
    }
}
