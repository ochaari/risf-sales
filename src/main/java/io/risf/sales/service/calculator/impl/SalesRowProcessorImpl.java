package io.risf.sales.service.calculator.impl;

import io.risf.sales.dto.ReceiptItem;
import io.risf.sales.dto.ReceiptItemOutput;
import io.risf.sales.dto.ReceiptOutput;
import io.risf.sales.model.Product;
import io.risf.sales.service.calculator.ItemNameCalculator;
import io.risf.sales.service.calculator.PriceCalculator;
import io.risf.sales.service.calculator.SalesRowProcessor;
import io.risf.sales.service.product.ProductService;
import io.risf.sales.service.tax.TaxCalculator;
import io.risf.sales.service.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class SalesRowProcessorImpl implements SalesRowProcessor {
    private static final Logger logger = LoggerFactory.getLogger(SalesRowProcessorImpl.class);

    private final ProductService productService;

    private final PriceCalculator priceCalculator;

    private final ItemNameCalculator itemNameCalculator;

    private final TaxCalculator taxCalculator;

    private final Validator<ReceiptItem> receiptItemValidator;

    public SalesRowProcessorImpl(ProductService productService, PriceCalculator priceCalculator,
                                 ItemNameCalculator itemNameCalculator, TaxCalculator taxCalculator, Validator<ReceiptItem> receiptItemValidator) {
        this.productService = productService;
        this.priceCalculator = priceCalculator;
        this.itemNameCalculator = itemNameCalculator;
        this.taxCalculator = taxCalculator;
        this.receiptItemValidator = receiptItemValidator;
    }


    @Override
    public ReceiptItemOutput processItem(ReceiptItem receiptItem) {
        logger.info("Processing {} ...", receiptItem);
        if (!receiptItemValidator.isValid(receiptItem)) {
            throw new IllegalArgumentException("Invalid receipt input");
        }
        Product storeProduct = productService.getStoreProduct(receiptItem);
        String itemName = itemNameCalculator.formatItemName(receiptItem);
        double totalBeforeTax = priceCalculator.calculateTotalWithoutTax(receiptItem);
        double totalTax = taxCalculator.calculateTax(receiptItem, storeProduct);
        return new ReceiptItemOutput(itemName, totalBeforeTax, totalTax, receiptItem.quantity());
    }

    @Override
    public ReceiptOutput processReceiptItems(Collection<ReceiptItem> receiptItemList) {
        logger.info("Generating the list for {} elements", receiptItemList.size());
        double totalTax = 0;
        double total = 0;
        var outputList = new ArrayList<ReceiptItemOutput>();
        for (ReceiptItem receiptItem : receiptItemList) {
            ReceiptItemOutput receiptItemOutput = processItem(receiptItem);
            totalTax += receiptItemOutput.totalTax();
            total += receiptItemOutput.totalTax() + receiptItemOutput.totalBeforeTax();
            outputList.add(receiptItemOutput);
        }
        return new ReceiptOutput(outputList, total, totalTax);
    }


}
