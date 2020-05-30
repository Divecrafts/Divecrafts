package io.clonalejandro.Essentials.utils;

import io.clonalejandro.Essentials.Main;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Alex
 * On 28/05/2020
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

public class Economy {

    private final Main plugin = Main.instance;
    private final UUID uuid;
    private double money;

    public Economy(UUID uuid) throws SQLException {
        this.uuid = uuid;
        this.money = balance();
    }

    public double withdraw(double amount) throws SQLException {
        final String query = MysqlManager.secureQuery("UPDATE economy SET amount=? WHERE uuid=?");
        final PreparedStatement statement = MysqlManager.getConnection().prepareStatement(query);

        this.money -= amount;

        statement.setDouble(1, this.money);
        statement.setString(2, uuid.toString());

        statement.executeUpdate();
        return this.money;
    }

    public double deposit(double amount) throws SQLException {
        final String query = MysqlManager.secureQuery("UPDATE economy SET amount=? WHERE uuid=?");
        final PreparedStatement statement = MysqlManager.getConnection().prepareStatement(query);

        this.money += amount;

        statement.setDouble(1, this.money);
        statement.setString(2, uuid.toString());

        statement.executeUpdate();
        return this.money;
    }

    public double balance() throws SQLException {
        final String query = MysqlManager.secureQuery("SELECT * FROM economy WHERE uuid=?");
        final PreparedStatement statement = MysqlManager.getConnection().prepareStatement(query);

        statement.setString(1, uuid.toString());

        final ResultSet rs = statement.executeQuery();

        return rs.next() ? rs.getDouble("amount") : 0.0D;
    }

    public static HashMap<String, Economy> balanceTop(int limit) throws SQLException {
        final String query = MysqlManager.secureQuery(String.format("SELECT * FROM economy order by 'amount' asc limit %s", limit));
        final PreparedStatement statement = MysqlManager.getConnection().prepareStatement(query);
        final ResultSet rs = statement.executeQuery();
        final HashMap<String, Economy> top = new HashMap<>();

        while (rs.next()){
            final UUID uuid = UUID.fromString(rs.getString("uuid"));
            top.put(Bukkit.getOfflinePlayer(uuid).getName(), new Economy(uuid));
        }

        return top;
    }
}
