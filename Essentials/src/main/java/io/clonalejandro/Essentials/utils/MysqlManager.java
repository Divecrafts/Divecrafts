package io.clonalejandro.Essentials.utils;

import io.clonalejandro.Essentials.Main;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Alex
 * On 02/05/2020
 *
 * -- SOCIAL NETWORKS --
 *
 * GitHub: https://github.com/clonalejandro or @clonalejandro
 * Website: https://clonalejandro.me/
 * Twitter: https://twitter.com/clonalejandro11/ or @clonalejandro11
 * Keybase: https://keybase.io/clonalejandro/
 *
 * -- LICENSE --
 *
 * All rights reserved for clonalejandro ©Essentials 2017/2020
 */

public class MysqlManager {

    private Connection connection;
    private static Statement statement;

    public MysqlManager(String host, int port, String database, String username, String password){
        Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
            try {
                open(host, port, database, username, password);
                statement = connection.createStatement();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void open(String host, int port, String database, String username, String password) throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) return;

        synchronized (Main.instance){
            if (connection != null && !connection.isClosed()) return;

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
        }
    }

    public static Statement getStatement(){
        return statement;
    }

    public static String secureQuery(String str){
        while (str.contains("\""))
            str = str.replace("\"", "");
        return str;
    }
}
