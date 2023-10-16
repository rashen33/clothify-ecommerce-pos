package edu.icet.dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemTm extends RecursiveTreeObject<ItemTm> {
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
