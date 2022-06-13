package org.medmask.app.ui;

import org.medmask.app.Main;
import org.medmask.app.entity.ProductEntity;
import org.medmask.app.manager.ProductEntityManager;
import org.medmask.app.util.BaseForm;
import org.medmask.app.util.CustomTableModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class TableForm extends BaseForm {
    private JPanel mainPanel;
    private JTable table;
    private JButton addButton;
    private CustomTableModel<ProductEntity> model;

    public TableForm() {
        super(1200, 800);
        setContentPane(mainPanel);

        initTable();
        initButtons();

        setVisible(true);
    }

    private void initButtons(){
        if(Main.IS_ADMIN){
            addButton.addActionListener(e -> {
                dispose();
                new CreateForm();
            });
        } else {
            addButton.setEnabled(false);
        }

    }

    private void initTable(){
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(80);

        try {
            model = new CustomTableModel<>(
                    ProductEntity.class,
                    new String[]{"ID", "Название", "Тип продукта", "Описание", "Путь до картинки", "Изображение", "Цена", "Дата регистрации"},
                    ProductEntityManager.selectAll()
            );

            table.setModel(model);

            TableColumn column = table.getColumn("Путь до картинки");
            column.setMinWidth(0);
            column.setPreferredWidth(0);
            column.setMaxWidth(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(Main.IS_ADMIN){
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getClickCount() == 2){
                        int row = table.rowAtPoint(e.getPoint());
                        if(row != -1){
                            dispose();
                            new EditForm(model.getRows().get(row));
                        }
                    }
                }
            });
        }

    }
}
