import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class Mysql {
    //    final String url = "jdbc:mysql://localhost:3306/shop?useUnicode=true&characterEncoding=UTF-8&userSSL=false&serverTimezone=GMT%2B8";
//    final String user = "root";
//    final String password = "123456789";
    final String url = "jdbc:mysql://rm-cn-lbj3es94l000h7jo.rwlb.rds.aliyuncs.com:3306/picture?useUnicode=true&characterEncoding=UTF-8&userSSL=false&serverTimezone=GMT%2B8";
    final String user = "lly3323";
    final String password = "Aa123456789";
    Connection connection = null;
    Statement statement = null;
    Welcome welcome;
    ShopSystem shopSystem;
    sale_condition saleCondition;

    public Mysql() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("连接成功");
            sign_init();
            table_init();
        } catch (ClassNotFoundException e) {
            System.out.println("找不到驱动类");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void table_init() throws SQLException {
        String table = "shop";
        String sql;
        int data = this.table_exists(table);
        if (data == 0) {
            sql = "create table " + table + "(shop_name varchar(50) primary key ,shop_number varchar(50));";
            boolean result = statement.execute(sql);
        }
        this.shop_init(table);
        table = "goods";
        data = this.table_exists(table);
        if (data == 0) {
            sql = "create table " + table + "(good_name varchar(50) primary key ,good_number integer,shop_name varchar(50) ,foreign key (shop_name) references shop (shop_name) on update cascade );";
            boolean result = statement.execute(sql);
        }
        table = "customer";
        data = this.table_exists(table);
        if (data == 0) {
            sql = "create table " + table + "(customer_number integer primary key ,shop_name varchar(50) ,foreign key (shop_name) references shop (shop_name) on update cascade );";
            boolean result = statement.execute(sql);
        }
        table = "sale";
        data = this.table_exists(table);
        if (data == 0) {
            sql = "create table %s(customer_number integer,good_name varchar(50) ,number integer,foreign key (good_name) references goods(good_name) on delete cascade,foreign key (customer_number) references customer (customer_number)) ;".formatted(table);
            boolean result = statement.execute(sql);
            if (result) {
                System.out.println("创建成功");
            }
        }

        PreparedStatement preparedStatement;
        //构建触发器，用来保存销售情况
        String table_1 = "sale_back";

        data = this.table_exists(table_1);
        if (data == 0) {
            sql = "create table %s(customer_number integer,good_name varchar(50) ,number integer) ;".formatted(table_1);
            boolean result = statement.execute(sql);
        }

        sql = "SELECT count(*) FROM information_schema.TRIGGERS WHERE TRIGGER_NAME='sale_test';";

        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            if (resultSet.getInt(1) == 0) {
                sql = "CREATE TRIGGER " + "sale_test" + " AFTER INSERT ON " + table + " FOR EACH ROW INSERT INTO " + table_1 + " (customer_number, good_name, number) VALUES (new.customer_number, new.good_name, new.number)";
                statement.execute(sql);
            }
            ;
        }


    }

    public void sign_init() throws SQLException {
        String table_name = "sign_user";
        int data = this.table_exists(table_name);
        if (data == 0) {
            String sql = "create table " + table_name + "(user_name varchar(50),user_password varchar(50));";
            boolean result = statement.execute(sql);
        }
        this.register(new String[]{"root", "123456789"});
    }

    public void shop_init(String table) throws SQLException {
        statement = connection.createStatement();
        String sql = "select exists (select * from %s where shop_name = '%s' limit 1);".formatted(table, "dream");
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            if (resultSet.getInt(1) == 0) {
                sql = "insert into " + table + "(shop_name,shop_number)" + "values(?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, "dream");
                preparedStatement.setString(2, "001");
                preparedStatement.executeUpdate();
            }
        }
    }

    public void register(String[] user) throws SQLException {
        String table_name = "sign_user";
        int data = this.data_exists(user[0], table_name);
        if (data == 0) {
            String sql = "insert into " + table_name + "(user_name,user_password)" + "values(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user[0]);
            preparedStatement.setString(2, user[1]);
            preparedStatement.executeUpdate();
        }
    }

    public int match(String[] user) throws SQLException {
        String table = "sign_user";
        String sql = "select exists (select * from %s where user_name = '%s' and user_password= '%s' limit 1);".formatted(table, user[0], user[1]);
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            int temp = resultSet.getInt(1);
            if (temp == 1) return 1;
            else return 0;
        }
        return 0;
    }

    public int data_exists(String data, String table) throws SQLException {
        statement = connection.createStatement();
        String sql = "select exists (select * from %s where user_name = '%s' limit 1);".formatted(table, data);
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            int temp = resultSet.getInt(1);
            if (temp == 1) return 1;
            else return 0;
        }
        return 0;
    }

    public int table_exists(String table) throws SQLException {
        statement = connection.createStatement();
        String sql = "select count(*) from information_schema.tables where TABLE_NAME = " + "'" + table + "';";
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            int data = resultSet.getInt(1);
            if (data == 1) return 1;
        }
        return 0;
    }

    public void welcome_init(Welcome welcome) {
        this.welcome = welcome;
    }

    public void ShopSystem_init(ShopSystem shopSystem) {
        this.shopSystem = shopSystem;
    }

    public ArrayList<String> return_goods() throws SQLException {
        String table;
        String sql;
        String temp = return_shop_name();
        //下面操作是先改变商品所属的商店
        table = "goods";
        String sql_1 = "update " + table + " set shop_name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql_1);
        preparedStatement.setString(1, temp);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        table = "shop";
        this.exchange_temp(table);

        //获取最新的商品详情
        table = "goods";
        sql = "select good_name from " + table;
        ResultSet rs = statement.executeQuery(sql); // 执行查询语句
        ArrayList<String> data = new ArrayList<String>(); // 创建一个ArrayList对象来存储数据
        while (rs.next()) { // 遍历ResultSet对象，获取每一行数据
            String row = rs.getString(1);
            data.add(row); // 将当前行数据添加到ArrayList中
        }
        rs.close();
        return data;
    }//返回商品详情数组

    public void exchange_temp(String table) throws SQLException {
        String temp = return_shop_name();
        String sql = "update " + table + " set shop_name = ?,shop_number=? where shop_name=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, temp);//可以更改点名
        preparedStatement.setString(2, "001");
        preparedStatement.setString(3, temp);
        preparedStatement.executeUpdate();
    }

    public String exchange(String info, String temp) throws SQLException {
        String table = "shop";
        String sql = "update " + table + " set shop_name = ?,shop_number=? where shop_name=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, info);//可以更改点名
        preparedStatement.setString(2, "001");
        preparedStatement.setString(3, temp);
        preparedStatement.executeUpdate();
        return info;
    }//改变店名

    public String return_shop_name() throws SQLException {
        String table = "shop";
        String temp = null;
        String sql = "select shop_name from  " + table + ";";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            temp = resultSet.getString("shop_name");
        }
        return temp;
    }//获取最新店名

    public String return_shop_name_new(String info) throws SQLException {
        String table = "shop";
        String temp = null;
        String sql = "select shop_name from  " + table + ";";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            temp = resultSet.getString("shop_name");
        }
        this.exchange(info, temp);
        return info;
    }//更改新的店名

    public void GET_GOODS() throws SQLException {
        String table = "goods";

        String sql = "select count(*) from " + table;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            shopSystem.GoodsNumber.setText("商品数量：" + resultSet.getString(1));
        } else {
            shopSystem.GoodsNumber.setText("商品数量：0");
        }


        sql = "select good_name,good_number from %s order by good_number desc".formatted(table);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();

        DefaultTableModel defaultTableModel = (DefaultTableModel) shopSystem.goods_particular.getModel();
        defaultTableModel.setRowCount(0);
        try {
            while (resultSet.next()) {
                Vector<Object> vector = new Vector<>();
                vector.add(resultSet.getString("good_name"));
                vector.add(resultSet.getString("good_number"));
                defaultTableModel.addRow(vector);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        table = "sale_back";
        sql = "select good_name from %s order by number desc".formatted(table);
        Statement statement1 = connection.createStatement();
        ResultSet resultSet1 = statement1.executeQuery(sql);
        if (resultSet1.next()) {
            shopSystem.the_best_sale.setText("销量(MAX):" + resultSet1.getString("good_name"));
        }
    }//点击店内商品详情，重新覆盖表格

    public void sale_condition_init(sale_condition saleCondition) {
        this.saleCondition = saleCondition;
    }

    public void ReturnSale() throws SQLException {
        String table = "sale_back";
        String sql = "select customer_number,good_name,number from %s order by number desc".formatted(table);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        DefaultTableModel defaultTableModel = (DefaultTableModel) this.saleCondition.salecondition.getModel();
        defaultTableModel.setRowCount(0);
        try {
            while (resultSet.next()) {
                Vector<Object> vector = new Vector<>();
                vector.add(resultSet.getString("customer_number"));
                vector.add(resultSet.getString("good_name"));
                vector.add(resultSet.getString("number"));
                defaultTableModel.addRow(vector);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        saleCondition.setVisible(true);
    }//销售详情获取

    int count = 0;

    public void GetCount() throws SQLException {
        String table = "customer";
        String sql = "select customer_number from " + table;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            count = rs.getInt(1);
        }
    }//获取顾客最新编号

    public void sell(String good_name, int good_number) {
        try {
            this.GetCount();
            String table = "goods";
            String sql = "select good_number from " + table + " where good_name like '" + good_name + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            int data = 0;
            while (rs.next()) {
                data = rs.getInt(1);
            }
            if (data >= good_number) {
                sql = "update " + table + " set good_number = ? where good_name = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, String.valueOf(data - good_number));
                preparedStatement.setString(2, good_name);
                preparedStatement.executeUpdate();
            }
            if (data - good_number == 0) {
                this.destroy(good_name);
            }
            count += 1;
            table = "customer";
            sql = "insert into " + table + " (customer_number,shop_name)values (?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, count);
            String temp = return_shop_name();
            preparedStatement.setString(2, temp);
            preparedStatement.executeUpdate();

            table = "sale";

            sql = "insert into " + table + " (customer_number,good_name,number)values (?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, count);
            preparedStatement.setString(2, good_name);
            preparedStatement.setInt(3, good_number);
            preparedStatement.executeUpdate();

            this.GET_GOODS();
        } catch (SQLException e) {
            return;
        }

    }//出售货物

    public void destroy(String good_name) {
        try {
            String table = "goods";
            String sql = "delete from " + table + " where good_name like '" + good_name + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

            this.GET_GOODS();
            shopSystem.choose.removeAllItems();
            shopSystem.AddBoombox();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "货物已完全出售或完成清仓");
        }

    }//删除货

    public void add_goods(String temp, int tmp) throws SQLException {
        String table = "goods";
        String name = this.return_shop_name();
        PreparedStatement preparedStatement;
        String sql = "select count(*) from " + table + " where good_name like ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, temp);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            if (resultSet.getInt(1) == 0) {
                String sq2 = "insert into " + table + " (good_name, good_number, shop_name) values (?, ?, ? )";
                preparedStatement = connection.prepareStatement(sq2);
                preparedStatement.setString(1, temp);
                preparedStatement.setInt(2, tmp);
                preparedStatement.setString(3, name);
                preparedStatement.executeUpdate();
                shopSystem.choose.removeAllItems();
                shopSystem.AddBoombox();
                this.GET_GOODS();
            } else {
                String sq1 = "update " + table + " set good_number = good_number + ? where good_name = ?";
                preparedStatement = connection.prepareStatement(sq1);
                preparedStatement.setInt(1, tmp);
                preparedStatement.setString(2, temp);
                preparedStatement.executeUpdate();
                shopSystem.choose.removeAllItems();
                shopSystem.AddBoombox();
                this.GET_GOODS();
            }
        }
    }//进货
}
