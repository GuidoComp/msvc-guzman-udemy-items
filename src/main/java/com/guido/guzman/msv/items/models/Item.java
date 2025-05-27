package com.guido.guzman.msv.items.models;

import lombok.*;

/*@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter*/
public class Item {
    private ProductDTO product;
    private int quantity;

    /*public Item(ProductDTO product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }*/

    public Item() {
    }

    public Item(ProductDTO product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Double getTotal() {
        return product.getPrice() * quantity;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
