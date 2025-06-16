package com.guido.guzman.msv.items.models;

import com.guidio.guzman.libs.msv.commons.entities.Product;
import lombok.*;

/*@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter*/
public class Item {
    private Product product;
    private int quantity;

    /*public Item(ProductDTO product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }*/

    public Item() {
    }

    public Item(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Double getTotal() {
        return product.getPrice() * quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
