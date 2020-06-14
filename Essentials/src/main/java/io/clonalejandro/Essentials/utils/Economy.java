package io.clonalejandro.Essentials.utils;

import io.clonalejandro.DivecraftsCore.api.SBooster;
import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.Essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

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

    public static HashMap<Player, Economy> economyPlayers = new HashMap<>();

    public Economy(UUID uuid) throws SQLException {
        this.uuid = uuid;
        this.money = load();

        this.checkIfExists();
    }

    public Economy(String name) throws SQLException {
        this.name = name;
        this.money = load();

        this.checkIfExists();
    }

    public void withdraw(double amount){
        this.money -= amount;
        if (getPlayer() == null || getPlayer().getName() == null || getPlayer().getName().equals("")) {
            asyncSave();
        }
    }

    public void deposit(double amount){
        if (getPlayer() != null && getPlayer().getName() != null){
            final SUser user = SServer.getUser(getPlayer());
            final List<SBooster> boosters = user.getUserData().getBoosters()
                    .stream()
                    .filter(booster -> booster.getGameID() == SServer.GameID.SURVIVAL)
                    .collect(Collectors.toList());

            if (boosters.size() > 0){
                int multiplier = boosters.stream().mapToInt(SBooster::getMultiplier).sum();
                amount *= multiplier;
            }
        }

        depositWithOutBooster(amount);
    }

    public void depositWithOutBooster(double amount){
        this.money += amount;
        if (getPlayer() == null || getPlayer().getName() == null || getPlayer().getName().equals("")) {
            asyncSave();
        }
    }

    public double balance(){
        return this.money;
    }

    private double load() throws SQLException {
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

    public void save(){
        try {
            final String query = this.name != null ? "UPDATE economy SET amount=? WHERE name=?" : "UPDATE economy SET amount=? WHERE uuid=?";

            final PreparedStatement statement = MysqlManager.getConnection().prepareStatement(query);
            statement.setDouble(1, this.money);
            statement.setString(2, this.name != null ? this.name : this.uuid.toString());

            statement.executeUpdate();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void asyncSave(){
        Bukkit.getScheduler().runTaskAsynchronously(Main.instance, this::save);
    }

    public OfflinePlayer getPlayer(){
        return this.uuid == null ? null : Bukkit.getOfflinePlayer(this.uuid);
    }

    public void checkIfExists(){
        Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
            try {
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
            catch (Exception ex){
                ex.printStackTrace();
            }
        });
    }


    public static List<Economy> balanceTop(int limit) throws SQLException {
        final PreparedStatement statement = MysqlManager.getConnection().prepareStatement(String.format("SELECT * FROM economy order by amount desc limit %s", limit));
        final List<Economy> top = new ArrayList<>();

        try {
            final ResultSet rs = statement.executeQuery();

            while (rs.next()){
                final UUID uuid = UUID.fromString(rs.getString("uuid"));
                top.add(new Economy(uuid));
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return top.stream()
                .filter(eco -> eco.getPlayer() != null && eco.getPlayer().getName() != null)
                .collect(Collectors.toList());
    }

    private double round(double d){
        return Math.round(d * 10) / 10.0;
    }
}
