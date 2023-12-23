import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class JListExample {
    public static void main(String[] args) {
        // 创建一个ArrayList<String>对象  
        ArrayList<String> list = new ArrayList<>();
        list.add("Apple");
        list.add("Banana");
        list.add("Orange");

        // 创建一个JFrame对象  
        JFrame frame = new JFrame("JList Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        // 创建一个JList对象，并将ArrayList<String>对象传递给它  
        JList<String> listView = new JList<>(list.toArray(new String[list.size()]));

        // 将JList添加到JScrollPane中，并将JScrollPane添加到JFrame中  
        JScrollPane scrollPane = new JScrollPane(listView);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // 显示JFrame  
        frame.setVisible(true);
    }
}