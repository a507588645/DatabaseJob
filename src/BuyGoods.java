import com.mysql.cj.util.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
/*
 * Created by JFormDesigner on Wed Jun 14 16:50:16 CST 2023
 */



/**
 * @author 23916
 */
public class BuyGoods extends JFrame {
    Mysql mysql;
    public void mysql_init(Mysql mysql){
        this.mysql=mysql;
    }
    public BuyGoods() {
        initComponents();
    }

    private void SureMouseClicked(MouseEvent e) throws SQLException {
        // TODO add your code here
        if(!GoodsName.getText().isEmpty()&&!GoodsNumber.getText().isEmpty()&&sure.isSelected()){
            if(StringUtils.isStrictlyNumeric(GoodsNumber.getText())){
                int number= Integer.parseInt(GoodsNumber.getText());
                String name= GoodsName.getText();
                if(number>0){
                    mysql.add_goods(name,number);
                    this.setVisible(false);
                }
            }
        }
    }
    

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        sure = new JCheckBox();
        scrollPane1 = new JScrollPane();
        GoodsNumber = new JTextArea();
        Sure = new JButton();
        GoodsName = new JTextField();
        label1 = new JLabel();
        label2 = new JLabel();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- sure ----
        sure.setText("\u8bf7\u52fe\u9009\u786e\u5b9a");
        contentPane.add(sure);
        sure.setBounds(140, 175, 100, 35);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(GoodsNumber);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(205, 90, 90, 50);

        //---- Sure ----
        Sure.setText("\u786e\u5b9a");
        Sure.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
SureMouseClicked(e);} catch (SQLException ex) {
    throw new RuntimeException(ex);
}
            }
        });
        contentPane.add(Sure);
        Sure.setBounds(new Rectangle(new Point(285, 180), Sure.getPreferredSize()));
        contentPane.add(GoodsName);
        GoodsName.setBounds(85, 90, 95, 50);

        //---- label1 ----
        label1.setText("\u5546\u54c1\u540d\u79f0");
        contentPane.add(label1);
        label1.setBounds(85, 65, 95, 25);

        //---- label2 ----
        label2.setText("\u5546\u54c1\u6570\u91cf");
        contentPane.add(label2);
        label2.setBounds(210, 65, 70, 20);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JCheckBox sure;
    private JScrollPane scrollPane1;
    private JTextArea GoodsNumber;
    private JButton Sure;
    private JTextField GoodsName;
    private JLabel label1;
    private JLabel label2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
