package io.risf.sales.service.outputter.impl;

import io.risf.sales.dto.ReceiptItemOutput;
import io.risf.sales.dto.ReceiptOutput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TextOutputterTest {

    @Autowired
    private TextOutputter textOutputter;

    @Test
    void testOutputOk() {
        System.setProperty("line.separator", "\n");
        String expectedOutput = """
                1 imported Bottle of perfume: 22.95
                1 music CD: 30.00
                Sales Taxes: 5.30	Total: 79.88
                """;
        var receiptItemList = new ArrayList<ReceiptItemOutput>();
        receiptItemList.add(new ReceiptItemOutput("imported Bottle of perfume", 20.9999, 1.95, 1));
        receiptItemList.add(new ReceiptItemOutput("music CD", 25d, 5d, 1));
        double total = 79.882;
        double totalTax = 5.3;
        ReceiptOutput receiptOutput = new ReceiptOutput(receiptItemList, total, totalTax);
        String output = textOutputter.output(receiptOutput);
        // Had to use assertJ since JUnit can't handle Line separators  (CRLF & LF)
        assertThat(output).isEqualToNormalizingNewlines(expectedOutput);
    }
}