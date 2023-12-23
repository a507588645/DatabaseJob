import java.sql.SQLException;

public class start {
    public static void main(String[] args) throws SQLException {
        Mysql mysql = new Mysql();
        Welcome welcome = new Welcome();
        ShopSystem shopSystem=new ShopSystem();
        sale_condition saleCondition=new sale_condition();

        //进货窗口
        BuyGoods buyGoods=new BuyGoods();
        buyGoods.mysql_init(mysql);

        saleCondition.mysql_init(mysql);
        saleCondition.shopsystem_init(shopSystem);
        welcome.mysql_init(mysql);
        welcome.setShopSystem_init(shopSystem);


        shopSystem.setMysql(mysql);
        shopSystem.BuyGoods_init(buyGoods);

        mysql.welcome_init(welcome);
        mysql.ShopSystem_init(shopSystem);
        mysql.sale_condition_init(saleCondition);
        shopSystem.AddBoombox();

        //开局设置店名
        String name= mysql.return_shop_name();
        shopSystem.exchange_shop_name(name);
        //商品列表
        mysql.GET_GOODS();
    }
}
