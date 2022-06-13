package org.medmask.app.ui;

import org.medmask.app.Main;
import org.medmask.app.entity.ProductEntity;
import org.medmask.app.manager.ProductEntityManager;
import org.medmask.app.ui.TableForm;
import org.medmask.app.util.BaseForm;
import org.medmask.app.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Date;

public class EditForm extends BaseForm {
    private JPanel mainPanel;
    private JTextField titleField;
    private JButton saveButton;
    private JButton backButton;
    private JTextField typeField;
    private JTextField descField;
    private JTextField imageField;
    private JTextField costField;
    private JTextField idField;
    private JTextField dateField;
    private JButton deleteButton;
    private ProductEntity product;

    public EditForm(ProductEntity product) {
        super(400, 700);
        this.product = product;
        setContentPane(mainPanel);

        initFields();
        initButtons();

        setVisible(true);
    }

    private void initFields(){
        idField.setText(String.valueOf(product.getId()));
        idField.setEditable(false);
        titleField.setText(product.getTitle());
        typeField.setText(product.getProductType());
        descField.setText(product.getDesc());
        imageField.setText(product.getImagePath());
        costField.setText(String.valueOf(product.getCost()));
        dateField.setText(String.valueOf(product.getRegDate()));
        dateField.setEditable(false);
    }

    private void initButtons(){
        deleteButton.addActionListener(e -> {
            try {
                ProductEntityManager.delete(product);
                dispose();
                new TableForm();
            } catch (SQLException ex) {
                ex.printStackTrace();
                DialogUtil.showError(this, "-");
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            new TableForm();
        });

        saveButton.addActionListener(e -> {
            String title = titleField.getText();
            String type = typeField.getText();
            String desc = descField.getText();
            String image = imageField.getText();
            double cost = -1;
            try{
                cost = Double.parseDouble(costField.getText());
            } catch (Exception ex) {
                DialogUtil.showError(this, "Неверно введена цена");
                return;
            }

            product.setTitle(title);
            product.setProductType(type);
            product.setDesc(desc);
            product.setImagePath(image);
            product.setCost(cost);

            try {
                ProductEntityManager.update(product);
                DialogUtil.showInfo(this, "+");
                dispose();
                new TableForm();
            } catch (SQLException ex) {
                ex.printStackTrace();
                DialogUtil.showError(this, "-");
            }
        });
    }
}
