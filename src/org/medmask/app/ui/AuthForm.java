package org.medmask.app.ui;

import org.medmask.app.Main;
import org.medmask.app.manager.UserEntityManager;
import org.medmask.app.util.BaseForm;
import org.medmask.app.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;

public class AuthForm extends BaseForm {
    private JPanel mainPanel;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton authButton;
    private JButton guestButton;


    public AuthForm() {
        super(800, 400);
        setContentPane(mainPanel);

        initButtons();

        setVisible(true);
    }

    private void initButtons(){
        authButton.addActionListener(e -> {
            String login = loginField.getText();
            String password = new String(passwordField.getPassword());

            try {
                String role = UserEntityManager.getUserRole(login, password);
                if(role == null){
                    DialogUtil.showError(this, "Неверный логин или пароль");
                    return;
                }

                if("Администратор".equalsIgnoreCase(role)){
                    Main.IS_ADMIN = true;
                }
                DialogUtil.showInfo(this, "Successful");

                dispose();
                new TableForm();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        guestButton.addActionListener(e -> {
            dispose();
            new TableForm();
        });
    }
}
