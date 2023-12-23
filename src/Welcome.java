import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Objects;
/*
 * Created by JFormDesigner on Mon Jun 12 17:05:42 CST 2023
 */


/**
 * @author 23916
 */
public class Welcome {
    Mysql mysql;
    ShopSystem shopSystem = null;

    public void mysql_init(Mysql mysql) {
        this.mysql = mysql;
    }

    public void setShopSystem_init(ShopSystem shopSystem) {
        this.shopSystem = shopSystem;
        shopSystem.setVisible(false);
    }

    String Administrator = "root";

    String Administrator_password = "123456789";

    public Welcome() throws SQLException {
        initComponents();
    }

    private void Register(MouseEvent e) {
        // TODO add your code here
        if (agree.isSelected() && !Objects.equals(user_in.getText(), "root") && !user_in.getText().isEmpty() && !password_in.getText().isEmpty()) {
            try {
                mysql.register(new String[]{user_in.getText(), password_in.getText()});
                Welcome.setVisible(false);

                int sign = JOptionPane.showConfirmDialog(null, "注册成功", "注册提示", JOptionPane.CLOSED_OPTION);
                if (sign == 0) {
                    Welcome.setVisible(true);
                }

            } catch (Exception f) {
                f.printStackTrace();
            }
        }
    }

    private void Sign(MouseEvent e) throws SQLException {
        // TODO add your code here
        if (Objects.equals(user_in.getText(), Administrator) && Objects.equals(password_in.getText(), Administrator_password) && agree.isSelected()) {
            Welcome.setVisible(false);
            int sign = JOptionPane.showConfirmDialog(null, "登陆成功，欢迎您店长", "登陆提示", JOptionPane.CLOSED_OPTION);
            if (sign == 0) {
                shopSystem.setVisible(true);
            }
        } else if (user_in.getText() != null && password_in.getText() != null && agree.isSelected()) {
            if (mysql.match(new String[]{user_in.getText(), password_in.getText()}) == 1) {
                Welcome.setVisible(false);
                int sign = JOptionPane.showConfirmDialog(null, "登陆成功", "登陆提示", JOptionPane.CLOSED_OPTION);
                if (sign == 0) {
                    shopSystem.setVisible(true);
                    shopSystem.setTitle("商店系统");
                    shopSystem.exchange_shop_name.setVisible(false);
                    shopSystem.SaleCondition.setVisible(false);
                    shopSystem.destroy.setVisible(false);
                    shopSystem.buyGoods.setVisible(false);
                    shopSystem.BuyGoods.setVisible(false);
                }
            } else {
                Welcome.setVisible(false);
                int sign = JOptionPane.showConfirmDialog(null, "用户名或密码错误，请重新输入", "登陆提示", JOptionPane.CLOSED_OPTION);
                if (sign == 0) {
                    Welcome.setVisible(true);
                }
            }
        } else {
            Welcome.setVisible(false);
            int sign = JOptionPane.showConfirmDialog(null, "请重新输入", "登陆提示", JOptionPane.CLOSED_OPTION);
            if (sign == 0) {
                Welcome.setVisible(true);
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        Welcome = new JFrame();
        user = new JLabel();
        password = new JLabel();
        user_in = new JTextField();
        sign = new JButton();
        register = new JButton();
        agree = new JCheckBox();
        password_in = new JPasswordField();

        //======== Welcome ========
        {
            Welcome.setAlwaysOnTop(true);
            Welcome.setTitle("Welcome");
            Welcome.setVisible(true);
            var WelcomeContentPane = Welcome.getContentPane();
            WelcomeContentPane.setLayout(null);

            //---- user ----
            user.setText("\u7528\u6237\u540d");
            WelcomeContentPane.add(user);
            user.setBounds(new Rectangle(new Point(105, 105), user.getPreferredSize()));

            //---- password ----
            password.setText("\u5bc6\u7801");
            WelcomeContentPane.add(password);
            password.setBounds(new Rectangle(new Point(105, 145), password.getPreferredSize()));
            WelcomeContentPane.add(user_in);
            user_in.setBounds(165, 100, 180, 40);

            //---- sign ----
            sign.setText("\u767b\u9646");
            sign.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
Sign(e);} catch (SQLException ex) {
    throw new RuntimeException(ex);
}
                }
            });
            WelcomeContentPane.add(sign);
            sign.setBounds(new Rectangle(new Point(290, 230), sign.getPreferredSize()));

            //---- register ----
            register.setText("\u6ce8\u518c");
            register.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Register(e);
                }
            });
            WelcomeContentPane.add(register);
            register.setBounds(new Rectangle(new Point(130, 230), register.getPreferredSize()));

            //---- agree ----
            agree.setText("\u540c\u610f\u534f\u8bae");
            WelcomeContentPane.add(agree);
            agree.setBounds(new Rectangle(new Point(105, 200), agree.getPreferredSize()));
            WelcomeContentPane.add(password_in);
            password_in.setBounds(165, 140, 180, 40);

            WelcomeContentPane.setPreferredSize(new Dimension(530, 350));
            Welcome.pack();
            Welcome.setLocationRelativeTo(Welcome.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JFrame Welcome;
    private JLabel user;
    private JLabel password;
    private JTextField user_in;
    private JButton sign;
    private JButton register;
    private JCheckBox agree;
    private JPasswordField password_in;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
