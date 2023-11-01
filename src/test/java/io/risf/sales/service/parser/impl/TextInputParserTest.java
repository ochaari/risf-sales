package io.risf.sales.service.parser.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SpringBootTest
class TextInputParserTest {

    @Autowired
    private TextInputParser textInputParser;

    private static Stream<Arguments> validTestInputRowsWithItemNames() {
        return Stream.of(
                arguments("1 chocolate bar at 0.85", "chocolate bar"),
                arguments("1  imported  bottle of   perfume at   47.50", "imported  bottle of   perfume"),
                arguments("1 box of imported chocolates at 11.25", "box of imported chocolates")
        );
    }

    private static Stream<Arguments> validTestInputRowsWithPrice() {
        return Stream.of(
                arguments("1 chocolate bar at 0.85111", 0.85111),
                arguments("1  imported  bottle of   perfume at   1147.50 ", 1147.50),
                arguments("1 box of imported chocolates at 011.025  ", 11.025)
        );
    }

    private static Stream<Arguments> validTestInputRowsWithQuantity() {
        return Stream.of(
                arguments("1 chocolate bar at 0.85", 1),
                arguments("23  imported  bottle of   perfume at   47.50", 23),
                arguments("999 box of imported chocolates at 11.25", 999)
        );
    }

    private static Stream<Arguments> validTestInputRowsWithImported() {
        return Stream.of(
                arguments("1 chocolate bar Import at 0.85", false),
                arguments("23  imported  bottle of perfume at   47.50", true),
                arguments("23  bottle of impor ted  perfume at   47.50", false),
                arguments("999 box of imported  IMPORTED  chocolates at 11.25", true)
        );
    }

    @ParameterizedTest
    @MethodSource("validTestInputRowsWithItemNames")
    void testExtractItemNameOK(String inputRow, String expected) {
        String output = textInputParser.extractItemName(inputRow);
        assertEquals(expected, output, "Should slice the item name correctly");
    }

    @ParameterizedTest
    @MethodSource("validTestInputRowsWithPrice")
    void testExtractPriceOk(String inputRow, double expected) {
        double output = textInputParser.extractPrice(inputRow);
        assertEquals(expected, output, "Should extract the item's price correctly");
    }

    @ParameterizedTest
    @MethodSource("validTestInputRowsWithQuantity")
    void testExtractQuantityOk(String inputRow, int expected) {
        int output = textInputParser.extractQuantity(inputRow);
        assertEquals(expected, output, "Should extract the item's quantity correctly");
    }

    @ParameterizedTest
    @MethodSource("validTestInputRowsWithImported")
    void testIsImportedOk(String inputRow, boolean expected) {
        boolean output = textInputParser.isImported(inputRow);
        assertEquals(expected, output, "Should determine whether the item is imported or not correctly");
    }

    @Test
    void testSplitRowsOk() {
        String input = """
                     1 chocolate bar at 0.85111
                1  imported  bottle of   perfume at   1147.50\s
                """;
        input += "1 box of imported chocolates at 011.025  ";
        String row1 = "1 chocolate bar at 0.85111";
        String row2 = "1  imported  bottle of   perfume at   1147.50 ";
        String row3 = "1 box of imported chocolates at 011.025";

        List<String> splitRows = textInputParser.splitRows(input);

        assertEquals(3, splitRows.size(), "The length of split rows should be 3");
        assertEquals(row1, splitRows.get(0), "The 1st row should have the correct value");
        assertEquals(row2, splitRows.get(1), "The 2nd row should have the correct value");
        assertEquals(row3, splitRows.get(2), "The 3rd row should have the correct value");
    }
}