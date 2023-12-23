import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
/*
 * Created by JFormDesigner on Wed Jun 14 14:24:30 CST 2023
 */


/**
 * @author 23916
 */
public class sale_condition extends JFrame {
    public static JTable salecondition;
    Mysql mysql;
    ShopSystem shopsystem;

    public void mysql_init(Mysql mysql) {
        this.mysql = mysql;
    }

    public void shopsystem_init(ShopSystem shopsystem) {
        this.shopsystem = shopsystem;
    }

    public sale_condition() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        scrollPane1 = new JScrollPane();
        salecondition = new JTable();

        //======== this ========
        var contentPane = getContentPane();

        contentPane.setLayout(null);

        //======== scrollPane1 ========
        {

            //---- salecondition ----
            salecondition.setModel(new DefaultTableModel(
                new Object[][] {
                    {null, null, null},
                },
                new String[] {
                    "customer_number", "good_name", "number"
                }
            ));
            scrollPane1.setViewportView(salecondition);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(0, -10, 395, 275);

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
    private JScrollPane scrollPane1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
