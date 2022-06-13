package org.medmask.app.ui;

import org.medmask.app.Main;
import org.medmask.app.entity.ProductEntity;
import org.medmask.app.manager.ProductEntityManager;
import org.medmask.app.util.BaseForm;
import org.medmask.app.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Date;

public class CreateForm extends BaseForm {
    private JPanel mainPanel;
    private JTextField titleField;
    private JButton saveButton;
    private JButton backButton;
    private JTextField typeField;
    private JTextField descField;
    private JTextField imageField;
    private JTextField costField;

    public CreateForm() {
        super(400, 700);
        setContentPane(mainPanel);

        initButtons();

        setVisible(true);
    }

    private void initButtons(){
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

            ProductEntity product = new ProductEntity(
                    title,
                    type,
                    desc,
                    image,
                    cost,
                    new Date()
            );

            try {
                ProductEntityManager.insert(product);
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
