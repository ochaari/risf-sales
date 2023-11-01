package io.risf.sales.service.product;

import io.risf.sales.dto.ReceiptItem;
import io.risf.sales.model.Product;


/**
 * The contract for the product service.
 */
public interface ProductService {

    /**
     * should communicate with the persistence layer & extract the data
     *
     * @param receiptItem The receipt item in the input
     * @return the product having the same item name
     */
    Product getStoreProduct(ReceiptItem receiptItem);
}
