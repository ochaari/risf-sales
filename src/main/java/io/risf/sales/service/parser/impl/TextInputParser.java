package io.risf.sales.service.parser.impl;

import io.risf.sales.service.parser.InputParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TextInputParser implements InputParser {

    private static final Logger logger = LoggerFactory.getLogger(TextInputParser.class);

    /**
     * @param inputRow the input row in the console
     * @return the itemName, it's between the quantity & the "at" keyword
     */
    @Override
    public String extractItemName(String inputRow) {
        logger.debug("Extracting item name for the row {}", inputRow);
        String itemNameRegex = "^\\d+\\s(.*)at";
        Pattern pattern = Pattern.compile(itemNameRegex);
        Matcher matcher = pattern.matcher(inputRow);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }

    /**
     * @param inputRow the input row in the console
     * @return the price of the item. It comes after the "at" keyword
     */
    @Override
    public Double extractPrice(String inputRow) {
        logger.debug("Extracting price for the row {}", inputRow);
        String priceRegex = "\\bat\\s(.*)";
        Pattern pattern = Pattern.compile(priceRegex);
        Matcher matcher = pattern.matcher(inputRow);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1).trim());
        }
        return null;
    }

    /**
     * @param inputRow the input row in the console
     * @return the quantity of items in a single row. It's the first number in the row
     */
    @Override
    public Integer extractQuantity(String inputRow) {
        logger.debug("Extracting quantity for the row {}", inputRow);
        String firstDigitsRegex = "^\\d+";
        Pattern pattern = Pattern.compile(firstDigitsRegex);
        Matcher matcher = pattern.matcher(inputRow);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
        return null;
    }

    /**
     * @param inputRow the input row in the console
     * @return whether the item is imported. Simply check if the "imported" keyword exists (case-insensitive)
     */
    @Override
    public Boolean isImported(String inputRow) {
        logger.debug("Extracting the imported value for the row {}", inputRow);
        return inputRow.toLowerCase().contains("imported");
    }

    /**
     * @param input the collection of rows separated by \n
     * @return a list containing the rows in the input
     */
    @Override
    public List<String> splitRows(String input) {
        logger.debug("Splitting...: {}", input);
        final String newLine = "\n";
        return Arrays.asList(input.trim().split(newLine));
    }
}
