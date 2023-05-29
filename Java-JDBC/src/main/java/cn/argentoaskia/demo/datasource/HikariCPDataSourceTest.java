package cn.argentoaskia.demo.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HikariCPDataSourceTest {
    public static void main(String[] args) throws Exception {
        // TODO: 2022/8/12 Hikari的使用方法

        // TODO: 2022/8/12
        //  1.导入GAV坐标，注意高版本的HikariCP可能需要高版本的JDK
        /*
            <!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
            <dependency>
                <groupId>com.zaxxer</groupId>
                <artifactId>HikariCP</artifactId>
                <version>3.3.1</version>
            </dependency>
         */

         // TODO: 2022/8/12
        //  2.定义配置文件：
        //      名称：hikari.properties（不限制）
        //      Maven工程放在resource里面的任意位置都可以
        //      配置文件可以写的内容参考：文档


        // TODO: 2022/8/12
        //  3.创建核心对象 数据库连接池对象HikariDataSource、数据库连接配置对象HikariConfig

        // 1.可以使用类似于Druid的Properties配置方法，也可以使用下面的这种直接指定配置文件的方法
        // 2.注意在使用下面的创建方法中，不能使用相对路径，如：../hikari.properties，要使用绝对路径/hikari.properties
        HikariConfig hikariConfig = new HikariConfig("/hikari.properties");
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        Connection connection = hikariDataSource.getConnection();


        // TODO: 2022/8/11
        //  4.获取statement，也叫SQL执行器，常用的：
        //     - PreparedStatement： 预处理SQL执行器
        //     - Statement：         基本SQL执行器
        //     - CallableStatement： 存储过程执行器
        //    使用SQL执行器之前需要编写SQL语句。
        String sql = "SELECT * FROM user";
        String preparedSql = "SELECT * FROM user where Host = ?";

        //  获取SQL执行器的方法需要用到connection对象，对应的方法如下：
        //  connection.createStatement()            --> Statement
        //  connection.prepareStatement(String sql) --> PreparedStatement
        //  connection.prepareCall(String sql)      --> CallableStatement
        Statement statement = connection.createStatement();

        // 如果是preparedStatement的话后期则需要通过setXXX()方法填充参数，如：
        // setXXX(index，XXX)：index从1开始，代表第一个?，XXX代表用于填充的参数。
        // PreparedStatement preparedStatement = connection.prepareStatement(preparedSql);
        // preparedStatement.setString(1, "localhost");

        // TODO: 2022/8/11
        //  5.执行SQL语句，根据SQL语句的类型，执行不同的方法：
        //  execute(String sql)：DDL，create、drop、alter...
        //  executeUpdate(String sql)：insert、delete、update
        //  executeQuery(String sql)：select
        ResultSet resultSet = statement.executeQuery(sql);
        // ResultSet resultSet1 = preparedStatement.executeQuery();

        // 这是三个方法也有所不同，
        // 其中execute(String sql)返回boolean类型，代表执行的返回结果，true代表执行成功，false代表执行失败
        // executeUpdate(String sql)返回一个整数，代表收影响的行数，例如插入一行数据，插入成功则返回1，一般判断是否>0
        // 来判断是否插入成功
        // executeQuery(String sql)返回一个ResultSet，代表查询出来的二维表。

        // TODO: 2022/8/11
        //   6.结果处理，对于execute(String sql)、executeUpdate(String sql)结果没啥特别处理的方式，
        //      但是对于executeQuery(String sql)则需要自行解析ResultSet，解析的步骤如下：
        //      1.调用next()，让ResultSet指向下一行数据，如果没有数据了则返回false，退出循环。
        //      2.通过调用getXXX()的方法来获取数据。
        while (resultSet.next()){
            String host = resultSet.getString(1);
            String user = resultSet.getString("User");
            System.out.println(host);
            System.out.println(user);
        }

        /*
        while(resultSet1.next()){
            String host = resultSet1.getString(1);
            String user = resultSet1.getString(2);
            String Select_priv = resultSet1.getString(3);
            String Insert_priv = resultSet1.getString(4);
            System.out.println(host);
            System.out.println(user);
            System.out.println(Select_priv);
            System.out.println(Insert_priv);
        }

         */

        // TODO: 2022/8/11
        //   7. 关闭资源，resultSet要关，statement要关、connection要关
        //   8. 特别注意在使用hikariDataSource时，也要关闭hikari
        resultSet.close();
        statement.close();
        connection.close();
        hikariDataSource.close();

    }
}
