package edu.icet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private String itemId;
    private String desc;
    private int qty;
    private double buyingPrice;
    private double sellingPrice;
    private String type;
    private String size;
    private double profit;
    private String supplierId;
}
