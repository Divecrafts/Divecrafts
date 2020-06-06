package io.clonalejandro.Essentials.utils;

import io.clonalejandro.Essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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

    private Main plugin = Main.instance;
    private UUID uuid;
    private String name = null;
    private double money;

    public Economy(UUID uuid) throws SQLException {
        this.uuid = uuid;
        this.money = balance();

        this.checkIfExists();
    }

    public Economy(String name) throws SQLException {
        this.name = name;
        this.money = balance();

        this.checkIfExists();
    }

    public double withdraw(double amount) throws SQLException {
        if (name == null){
            final String query = MysqlManager.secureQuery("UPDATE economy SET amount=? WHERE uuid=?");
            final PreparedStatement statement = MysqlManager.getConnection().prepareStatement(query);

            this.money -= amount;

            statement.setDouble(1, this.money);
            statement.setString(2, this.uuid.toString());

            statement.executeUpdate();
        }
        else {
            final String query = MysqlManager.secureQuery("UPDATE economy SET amount=? WHERE name=?");
            final PreparedStatement statement = MysqlManager.getConnection().prepareStatement(query);

            this.money -= amount;

            statement.setDouble(1, this.money);
            statement.setString(2, this.name);

            statement.executeUpdate();
        }

        return this.money;
    }

    public double deposit(double amount) throws SQLException {
        if (name == null){
            final String query = MysqlManager.secureQuery("UPDATE economy SET amount=? WHERE uuid=?");
            final PreparedStatement statement = MysqlManager.getConnection().prepareStatement(query);

            this.money += amount;

            statement.setDouble(1, this.money);
            statement.setString(2, this.uuid.toString());

            statement.executeUpdate();
        }
        else {
            final String query = MysqlManager.secureQuery("UPDATE economy SET amount=? WHERE name=?");
            final PreparedStatement statement = MysqlManager.getConnection().prepareStatement(query);

            this.money += amount;

            statement.setDouble(1, this.money);
            statement.setString(2, this.name);

            statement.executeUpdate();
        }
        return this.money;
    }

    public double balance() throws SQLException {
        if (name == null){
            final String query = MysqlManager.secureQuery("SELECT * FROM economy WHERE uuid=?");
            final PreparedStatement statement = MysqlManager.getConnection().prepareStatement(query);

            statement.setString(1, this.uuid.toString());

            final ResultSet rs = statement.executeQuery();

            return round(rs.next() ? rs.getDouble("amount") : 0.0D);
        }
        else {
            final String query = MysqlManager.secureQuery("SELECT * FROM economy WHERE name=?");
            final PreparedStatement statement = MysqlManager.getConnection().prepareStatement(query);

            statement.setString(1, this.name);

            final ResultSet rs = statement.executeQuery();

            return round(rs.next() ? rs.getDouble("amount") : 0.0D);
        }
    }

    public OfflinePlayer getPlayer(){
        return Bukkit.getOfflinePlayer(this.uuid);
    }

    public void checkIfExists() throws SQLException {
        if (this.name != null){
            final PreparedStatement statement1 = MysqlManager.getConnection().prepareStatement("SELECT * FROM economy WHERE name=?");
            final PreparedStatement statement2 = MysqlManager.getConnection().prepareStatement("INSERT INTO economy VALUES(?,?,?)");
            statement1.setString(1, this.name);

            if (!statement1.executeQuery().next()){
                statement2.setString(1, UUID.randomUUID().toString());
                statement2.setDouble(2, 1300D);
                statement2.setString(3, this.name);

                statement2.executeUpdate();
            }
        }
        else {
            this.name = getPlayer().getName();

            final PreparedStatement statement1 = MysqlManager.getConnection().prepareStatement("SELECT * FROM economy WHERE name=?");
            final PreparedStatement statement2 = MysqlManager.getConnection().prepareStatement("UPDATE economy SET name=? WHERE uuid=?");
            statement1.setString(1, getPlayer().getName());

            if (!statement1.executeQuery().next()) {
                statement2.setString(1, getPlayer().getName() == null ? "" : getPlayer().getName());
                statement2.setString(2, this.uuid.toString());

                statement2.executeUpdate();
            }
        }
    }


    public static List<Economy> balanceTop(int limit) throws SQLException {
        final PreparedStatement statement = MysqlManager.getConnection().prepareStatement(String.format("SELECT * FROM economy order by amount desc limit %s", limit));
        final ResultSet rs = statement.executeQuery();
        final List<Economy> top = new ArrayList<>();

        while (rs.next()){
            final UUID uuid = UUID.fromString(rs.getString("uuid"));
            top.add(new Economy(uuid));
        }

        return top;
    }

    private double round(double d){
        return Math.round(d * 10) / 10.0;
    }
}
