package org.medmask.app.entity;

import lombok.Data;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Pattern;

@Data
public class ProductEntity {
    private int id;
    private String title;
    private String productType;
    private String desc;
    private String imagePath;
    private ImageIcon image;
    private double cost;
    private Date regDate;

    public ProductEntity(int id, String title, String productType, String desc, String imagePath, double cost, Date regDate){
        this.id = id;
        this.title = title;
        this.productType = productType;
        this.desc = desc;
        this.imagePath = imagePath.replaceAll(Pattern.quote("/products"), "products");
        try {
            this.image = new ImageIcon(
                    ImageIO.read(ProductEntity.class.getClassLoader().getResource(this.imagePath))
                            .getScaledInstance(40, 75, Image.SCALE_DEFAULT)
            );
        } catch (Exception e) {
            e.printStackTrace();
            try {
                this.image = new ImageIcon(
                        ImageIO.read(ProductEntity.class.getClassLoader().getResource("picture.png"))
                                .getScaledInstance(80, 50, Image.SCALE_DEFAULT)
                );
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        this.cost = cost;
        this.regDate = regDate;
    }


    public ProductEntity(String title, String productType, String desc, String imagePath, double cost, Date regDate){
        this(-1, title, productType, desc, imagePath, cost, regDate);
    }
}
