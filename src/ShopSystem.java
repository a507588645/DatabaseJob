import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
/*
 * Created by JFormDesigner on Tue Jun 13 16:36:59 CST 2023
 */


/**
 * @author 23916
 */
public class ShopSystem extends JFrame {
    Mysql mysql;

    public void setMysql(Mysql mysql) {
        this.mysql = mysql;
    }

    BuyGoods buyGoods;

    public void BuyGoods_init(BuyGoods buyGoods) {
        this.buyGoods = buyGoods;
    }

    public ShopSystem() {
        initComponents();
    }

    public void AddBoombox() throws SQLException {
        ArrayList<String> data = mysql.return_goods();
        for (String item : data) {
            choose.addItem(item);
        }
    }

    private void ShowGoodsMouseClicked(MouseEvent e) throws SQLException {
        // TODO add your code here
        // 创建指定列名和数据的表格
        mysql.GET_GOODS();
    }

    public void exchange_shop_name(String info) throws SQLException {
        String temp = "店名：";
        String tmp = mysql.return_shop_name_new(info);
        temp = temp + tmp;
        shop_name.setText(temp);
    }

    private void exchange_shop_nameMouseClicked(MouseEvent e) {
        // TODO add your code here
        try {
            String info = JOptionPane.showInputDialog(null, "请输入店名", JOptionPane.WARNING_MESSAGE);
            if (info != null) {
                exchange_shop_name(info);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void sellMouseClicked(MouseEvent e) {
        // TODO add your code here
        if (!quantity.getText().isEmpty()) {
            int temp = Integer.parseInt(quantity.getText());
            String tmp = Objects.requireNonNull(choose.getSelectedItem()).toString();
            mysql.sell(tmp, temp);
        }
    }

    private void destroyMouseClicked(MouseEvent e) throws SQLException {
        // TODO add your code here
        if (choose.getItemCount() != 0) {
            String tmp = Objects.requireNonNull(choose.getSelectedItem()).toString();
            mysql.destroy(tmp);
        }
    }

    private void SaleConditionMouseClicked(MouseEvent e) throws SQLException {
        // TODO add your code here
        mysql.ReturnSale();
    }

    private void BuyGoodsMouseClicked(MouseEvent e) throws SQLException {
        // TODO add your code here
        buyGoods.setVisible(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        ShowGoods = new JButton();
        shop_name = new JLabel();
        exchange_shop_name = new JLabel();
        list = new JScrollPane();
        goods_particular = new JTable();
        choose = new JComboBox();
        sell = new JButton();
        destroy = new JButton();
        quantity = new JTextField();
        SaleCondition = new JButton();
        BuyGoods = new JButton();
        GoodsNumber = new JLabel();
        the_best_sale = new JLabel();

        //======== this ========
        setTitle("\u5546\u5e97\u7cfb\u7edf\u5e97\u957f\u7248");
        setVisible(true);
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- ShowGoods ----
        ShowGoods.setText("\u5e97\u5185\u5546\u54c1\u8be6\u60c5");
        ShowGoods.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
ShowGoodsMouseClicked(e);} catch (SQLException ex) {
    throw new RuntimeException(ex);
}
            }
        });
        contentPane.add(ShowGoods);
        ShowGoods.setBounds(625, 0, 138, 45);

        //---- shop_name ----
        shop_name.setText("\u5e97\u540d\uff1a");
        contentPane.add(shop_name);
        shop_name.setBounds(5, 0, 165, 35);

        //---- exchange_shop_name ----
        exchange_shop_name.setText("\u66f4\u6539\u5e97\u540d");
        exchange_shop_name.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                exchange_shop_nameMouseClicked(e);
            }
        });
        contentPane.add(exchange_shop_name);
        exchange_shop_name.setBounds(0, 125, 70, 25);

        //======== list ========
        {

            //---- goods_particular ----
            goods_particular.setBackground(new Color(0x00cc99));
            goods_particular.setForeground(new Color(0x333333));
            goods_particular.setModel(new DefaultTableModel(
                new Object[][] {
                    {null, null},
                },
                new String[] {
                    "good_name", "good_number"
                }
            ));
            list.setViewportView(goods_particular);
        }
        contentPane.add(list);
        list.setBounds(70, 210, 650, 240);
        contentPane.add(choose);
        choose.setBounds(5, 80, 135, 40);

        //---- sell ----
        sell.setText("\u51fa\u552e");
        sell.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                sellMouseClicked(e);
            }
        });
        contentPane.add(sell);
        sell.setBounds(290, 80, 135, 40);

        //---- destroy ----
        destroy.setText("\u6e05\u4ed3");
        destroy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
destroyMouseClicked(e);} catch (SQLException ex) {
    throw new RuntimeException(ex);
}
            }
        });
        contentPane.add(destroy);
        destroy.setBounds(440, 80, 135, 40);
        contentPane.add(quantity);
        quantity.setBounds(145, 80, 135, 40);

        //---- SaleCondition ----
        SaleCondition.setText("\u9500\u552e\u60c5\u51b5");
        SaleCondition.setFont(SaleCondition.getFont().deriveFont(SaleCondition.getFont().getSize() + 4f));
        SaleCondition.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
SaleConditionMouseClicked(e);} catch (SQLException ex) {
    throw new RuntimeException(ex);
}
            }
        });
        contentPane.add(SaleCondition);
        SaleCondition.setBounds(625, 55, 138, 45);

        //---- BuyGoods ----
        BuyGoods.setText("\u8fdb\u8d27");
        BuyGoods.setFont(BuyGoods.getFont().deriveFont(BuyGoods.getFont().getSize() + 4f));
        BuyGoods.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
BuyGoodsMouseClicked(e);} catch (SQLException ex) {
    throw new RuntimeException(ex);
}
            }
        });
        contentPane.add(BuyGoods);
        BuyGoods.setBounds(625, 110, 138, 45);

        //---- GoodsNumber ----
        GoodsNumber.setText("\u5546\u54c1\u6570\u91cf\uff1a");
        contentPane.add(GoodsNumber);
        GoodsNumber.setBounds(5, 45, 135, 35);

        //---- the_best_sale ----
        the_best_sale.setText("\u9500\u91cf(MAX):");
        contentPane.add(the_best_sale);
        the_best_sale.setBounds(140, 45, 135, 35);

        contentPane.setPreferredSize(new Dimension(770, 505));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }


    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JButton ShowGoods;
    private JLabel shop_name;
    JLabel exchange_shop_name;
    private JScrollPane list;
    JTable goods_particular;
    JComboBox choose;
    private JButton sell;
    JButton destroy;
    private JTextField quantity;
    JButton SaleCondition;
    JButton BuyGoods;
    JLabel GoodsNumber;
    JLabel the_best_sale;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
