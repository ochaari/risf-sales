package io.risf.sales.commands;

import io.risf.sales.dto.ReceiptItem;
import io.risf.sales.dto.ReceiptOutput;
import io.risf.sales.service.calculator.SalesRowProcessor;
import io.risf.sales.service.outputter.OutputType;
import io.risf.sales.service.outputter.Outputter;
import io.risf.sales.service.outputter.OutputterFactory;
import io.risf.sales.service.outputter.impl.JSONOutputterFactory;
import io.risf.sales.service.outputter.impl.OutputterFactoryProvider;
import io.risf.sales.service.outputter.impl.TextOutputterFactory;
import io.risf.sales.service.parser.InputParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.command.annotation.ExceptionResolver;
import org.springframework.shell.command.annotation.ExitCode;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

/**
 * This class represents the top layer of the app.
 * It defines the commands for the sales app
 */
@ShellComponent
public class SalesCommand {

    private static final Logger logger = LoggerFactory.getLogger(SalesCommand.class);

    private static final String INPUT_1 = """
            1 book at 12.49
            1 music CD at 14.99
            1 chocolate bar at 0.85
            """;
    private static final String INPUT_2 = """
            1 imported box of chocolates at 10.00
            1 imported bottle of perfume at 47.50
            """;
    private static final String INPUT_3 = """
            1 imported bottle of perfume at 27.99
            1 bottle of perfume at 18.99
            1 packet of headache pills at 9.75
            1 box of imported chocolates at 11.25
            """;
    private final InputParser inputParser;
    private final SalesRowProcessor salesRowProcessor;

    public SalesCommand(InputParser inputParser, SalesRowProcessor salesRowProcessor) {
        this.inputParser = inputParser;
        this.salesRowProcessor = salesRowProcessor;
    }

    @ShellMethod(key = "preview", value = "Preview The sales results with the input in the test," +
            "you can choose either '1', '2' or '3'. ex. preview -example 2." +
            "You can also choose the output type as either 'text' or 'json', ex. preview -output json.")
    public String generateOutputForInput1(@ShellOption(defaultValue = "all") String example, @ShellOption(defaultValue = "text") String output) {
        logger.info("Preview called with the example : {} & the output type: {}", example, output);
        Outputter outputter = resolveOutputter(output);
        ReceiptOutput receiptOutput = resolveExample(example);

        if (receiptOutput == null) {
            return "Unknown option, available options are: '1', '2', '3'";
        }
        return outputter.output(receiptOutput);
    }

    /**
     * @param example the number of input example, it can be either 1, 2 or 3
     * @return The string input defined in the test's pdf
     */
    private ReceiptOutput resolveExample(String example) {
        logger.debug("Looking for the example {}", example);
        return switch (example) {
            case "1" -> process(INPUT_1);
            case "2" -> process(INPUT_2);
            case "3" -> process(INPUT_3);
            default -> null;
        };
    }

    /**
     * @param outputType The desired format of output. It can be either JSON or text (default)
     * @return the outputter responsible for printing on the console.
     */
    private Outputter resolveOutputter(String outputType) {
        logger.debug("resolved the outputter for the type {}", outputType);
        outputType = outputType.toUpperCase();
        OutputterFactoryProvider.registerFactory(OutputType.TEXT.name(), new TextOutputterFactory());
        OutputterFactoryProvider.registerFactory(OutputType.JSON.name(), new JSONOutputterFactory());

        OutputterFactory outputterFactory = OutputterFactoryProvider.getFactory(outputType);

        logger.debug("Outputter resolved successfully");
        return outputterFactory.createOutputter();
    }

    /**
     * @param input the sentence of the input which contains all the needed data to be parsed
     * @return A Receipt item containing all the data to be printed
     */
    private ReceiptOutput process(String input) {
        List<ReceiptItem> receiptItems = inputParser.parseRows(input);
        return salesRowProcessor.processReceiptItems(receiptItems);
    }

    @ExceptionResolver
    @ExitCode(code = 5)
    String errorHandler(Exception e) {
        return e.getMessage();
    }

}
