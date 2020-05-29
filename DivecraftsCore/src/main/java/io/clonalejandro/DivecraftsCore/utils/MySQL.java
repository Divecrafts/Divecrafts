package io.clonalejandro.DivecraftsCore.utils;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import com.yapzhenyie.GadgetsMenu.api.GadgetsMenuAPI;
import com.zaxxer.hikari.HikariDataSource;
import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.api.SBooster;
import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class MySQL {

    private final Main plugin = Main.getInstance();

    private final String user, database, password, port, hostname;
    protected Connection connection;

    /**
     * Default constructor
     *
     * @param hostname The host
     * @param database The db name
     * @param username The username
     * @param password The password
     */
    public MySQL(String hostname, String database, String username, String password) {
        this.hostname = hostname;
        this.port = "3306";
        this.database = database;
        this.user = username;
        this.password = password;
    }

    public boolean checkConnection() throws SQLException {
        return connection != null && !connection.isClosed();
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean closeConnection() throws SQLException {
        if (connection == null) return false;
        connection.close();
        return true;
    }

    public Connection openConnection() throws SQLException {
        if (checkConnection()) return connection;

        final HikariDataSource ds = new HikariDataSource();
        ds.setMaximumPoolSize(20);
        ds.setDriverClassName("org.mariadb.jdbc.Driver");
        ds.setConnectionTimeout(86400);
        ds.setJdbcUrl(String.format("jdbc:mariadb://%s:%s/%s?autoReconnect=true", hostname, port, database));
        ds.addDataSourceProperty("user", user);
        ds.addDataSourceProperty("password", password);

        connection = ds.getConnection();
        return connection;
    }

    // -----------------

    /**
     * Creates a row for the user in the tables
     *
     * @param p The player
     */
    public void setupTable(Player p) {
        //Datos
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                PreparedStatement statement = openConnection().prepareStatement("SELECT * FROM `data` WHERE `uuid` = ?");
                statement.setString(1, p.getUniqueId().toString());
                ResultSet rs = statement.executeQuery();


                if (rs.next()) return;
                //if (!rs.getString("name").equalsIgnoreCase("")) return;
                System.out.println("Creando tabla para " + p.getName());
                PreparedStatement inserDatos = openConnection().prepareStatement("INSERT INTO `data` (`uuid`, `name`, `grupo`) VALUES (?, ?, ?)");
                inserDatos.setString(1, p.getUniqueId().toString());
                inserDatos.setString(2, p.getName());
                inserDatos.setInt(3, 0);
                inserDatos.executeUpdate();

                PreparedStatement inserStats = openConnection().prepareStatement("INSERT INTO `stats` (`uuid`) VALUES (?)");
                inserStats.setString(1, p.getUniqueId().toString());
                inserStats.executeUpdate();

                PreparedStatement inserSettings = openConnection().prepareStatement("INSERT INTO `settings` (`uuid`, `visible`) VALUES (?, ?)");
                inserSettings.setString(1, p.getUniqueId().toString());
                inserSettings.setInt(2, 0);
                inserSettings.executeUpdate();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Saves a user in the MySQL
     * @param u The user
     */
    public void saveUser(SUser u) {
        SUser.UserData data = u.getUserData();

        try {
            PreparedStatement statementDatos = openConnection().prepareStatement("UPDATE `data` SET `grupo`=?,`god`=?,`coins`=?,`lastConnect`=?,`ip`=?,`nick`=?,`nickcolor`=?, `boosters`=? WHERE `uuid`=?");
            statementDatos.setInt(1, data.getRank() != null ? data.getRank().getRank() : 0);
            statementDatos.setBoolean(2, data.getGod() == null ? false : data.getGod());
            statementDatos.setInt(3, data.getCoins() == null ? 0 : data.getCoins());
            statementDatos.setTimestamp(4, new Timestamp(new java.util.Date().getTime()));
            statementDatos.setString(5, data.getIp() == null ? "" : data.getIp().getAddress().getHostAddress());
            statementDatos.setString(6, data.getNickname() == null ? "" : data.getNickname());
            statementDatos.setString(7, data.getNickcolor() == null ? "7" : data.getNickcolor());
            statementDatos.setInt(8, data.getBoosters().size());
            statementDatos.setString(9, u.getUuid().toString());
            statementDatos.executeUpdate();

            //Stats
            PreparedStatement statementStats = openConnection().prepareStatement("UPDATE `stats` SET `kills_ffa`=?, `deaths_ffa`=?, `plays_ffa`=?, `kills_mb`=?, `deaths_mb`=?, `plays_mb`=?, `wins_mb`=?, `plays_fc`=?, `kills_fc`=?, `deaths_fc`=?, `wins_fc`=?, `plays_mum`=?, `kills_mum`=?, `deaths_mum`=?, `wins_mum`=?, `elo_mum`=?, `reroll_mum`=? WHERE `uuid`=?");
            statementStats.setInt(1, data.getKills(SServer.GameID.FFA));
            statementStats.setInt(2, data.getDeaths(SServer.GameID.FFA));
            statementStats.setInt(3, data.getPlays(SServer.GameID.FFA));
            statementStats.setInt(4, data.getKills(SServer.GameID.MICROBATTLES));
            statementStats.setInt(5, data.getDeaths(SServer.GameID.MICROBATTLES));
            statementStats.setInt(6, data.getPlays(SServer.GameID.MICROBATTLES));
            statementStats.setInt(7, data.getWins(SServer.GameID.MICROBATTLES));
            statementStats.setInt(8, data.getPlays(SServer.GameID.FIGTHCLUB));
            statementStats.setInt(9, data.getKills(SServer.GameID.FIGTHCLUB));
            statementStats.setInt(10, data.getDeaths(SServer.GameID.FIGTHCLUB));
            statementStats.setInt(11, data.getWins(SServer.GameID.FIGTHCLUB));
            statementStats.setInt(12, data.getPlays(SServer.GameID.MUM));
            statementStats.setInt(13, data.getKills(SServer.GameID.MUM));
            statementStats.setInt(14, data.getDeaths(SServer.GameID.MUM));
            statementStats.setInt(15, data.getWins(SServer.GameID.MUM));
            statementStats.setInt(16, data.getMum_elo());
            statementStats.setInt(17, data.getMum_reroll());
            statementStats.setString(18, u.getUuid().toString());
            statementStats.executeUpdate();

            //Settings
            PreparedStatement statementSett = openConnection().prepareStatement("UPDATE `settings` SET `fly`=?,`visible`=?,`chat`=?,`party`=?,`lang`=?,`disguise`=? WHERE `uuid`=?");
            statementSett.setBoolean(1, data.getFly());
            statementSett.setInt(2, data.getVisible());
            statementSett.setBoolean(3, data.getChat());
            statementSett.setBoolean(4, data.getPartys());
            statementSett.setInt(5, data.getLang());
            statementSett.setString(6, data.getDisguise());
            statementSett.setString(7, u.getUuid().toString());
            statementSett.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Ha ocurrido un error guardando los datos de " + u.getName());
            ex.printStackTrace();
        }

    }

    /**
     * Loads all the Data for the User
     *
     * @param id The User UUID
     * @return The userData
     * @see SUser.UserData
     */
    public SUser.UserData loadUserData(UUID id) {
        SUser.UserData data = new SUser.UserData();

        try {
            final PreparedStatement statementDatos = openConnection().prepareStatement("SELECT `timeJoin`,`grupo`,`god`,`coins`,`lastConnect`,`clan`,`nickcolor`,`boosters` FROM `data` WHERE `uuid` = ?");
            final PreparedStatement statementBoosters = openConnection().prepareStatement("SELECT * FROM `booster` where `uuid`=?");

            statementDatos.setString(1, id.toString());
            statementBoosters.setString(1, id.toString());

            ResultSet rsDatos = statementDatos.executeQuery();

            if (rsDatos.next()) {
                int rank = rsDatos.getInt("grupo");
                data.setRank(SCmd.Rank.values()[rank] == null ? SCmd.Rank.USUARIO : SCmd.Rank.values()[rank]);
                data.setTimeJoin(Timestamp.valueOf(rsDatos.getString("timeJoin")).getTime());
                data.setGod(rsDatos.getBoolean("god"));
                data.setCoins(rsDatos.getInt("coins"));
                data.setKeys(loadKeys(id));
                data.setBoosters(loadBoosters(statementBoosters));
                data.setLastConnect(Timestamp.valueOf(rsDatos.getString("lastConnect")).getTime());
                data.setClanName(rsDatos.getString("clan"));
                data.setNickcolor(rsDatos.getString("nickcolor"));
            }

            PreparedStatement statementStats = openConnection().prepareStatement("SELECT * FROM `stats` WHERE `uuid` = ?");
            statementStats.setString(1, id.toString());
            ResultSet rsStats = statementStats.executeQuery();

            if (rsStats.next()) {
                data.getKills().replace(SServer.GameID.FFA.getId(), rsStats.getInt("kills_ffa"));
                data.getDeaths().replace(SServer.GameID.FFA.getId(), rsStats.getInt("deaths_ffa"));
                data.getPlays().replace(SServer.GameID.FFA.getId(), rsStats.getInt("plays_ffa"));

                data.getKills().replace(SServer.GameID.MICROBATTLES.getId(), rsStats.getInt("kills_mb"));
                data.getDeaths().replace(SServer.GameID.MICROBATTLES.getId(), rsStats.getInt("deaths_mb"));
                data.getWins().replace(SServer.GameID.MICROBATTLES.getId(), rsStats.getInt("wins_mb"));
                data.getPlays().replace(SServer.GameID.MICROBATTLES.getId(), rsStats.getInt("plays_mb"));

                data.getKills().replace(SServer.GameID.FIGTHCLUB.getId(), rsStats.getInt("kills_fc"));
                data.getDeaths().replace(SServer.GameID.FIGTHCLUB.getId(), rsStats.getInt("deaths_fc"));
                data.getWins().replace(SServer.GameID.FIGTHCLUB.getId(), rsStats.getInt("wins_fc"));
                data.getPlays().replace(SServer.GameID.FIGTHCLUB.getId(), rsStats.getInt("plays_fc"));

                data.getKills().replace(SServer.GameID.MUM.getId(), rsStats.getInt("kills_mum"));
                data.getDeaths().replace(SServer.GameID.MUM.getId(), rsStats.getInt("deaths_mum"));
                data.getWins().replace(SServer.GameID.MUM.getId(), rsStats.getInt("wins_mum"));
                data.getPlays().replace(SServer.GameID.MUM.getId(), rsStats.getInt("plays_mum"));
                data.setMum_elo(rsStats.getInt("elo_mum"));
                data.setMum_reroll(rsStats.getInt("reroll_mum"));
            }

            //Settings
            PreparedStatement statementSett = openConnection().prepareStatement("SELECT * FROM `settings` WHERE `uuid` = ?");
            statementSett.setString(1, id.toString());
            ResultSet rsSett = statementSett.executeQuery();

            if (rsSett.next()) {
                data.setVisible(rsSett.getInt("visible"));
                data.setClanes(rsSett.getBoolean("clanes"));
                data.setChat(rsSett.getBoolean("chat"));
                data.setFly(rsSett.getBoolean("fly"));
                data.setLang(rsSett.getInt("lang"));
                data.setPartys(rsSett.getBoolean("party"));
                data.setDisguise(rsSett.getString("disguise"));
            }
        } catch (CommunicationsException ex) {
            ex.printStackTrace();
            try {
                if (closeConnection())
                    openConnection();
                return loadUserData(id);
            } catch (Exception ex1) {
                ex1.printStackTrace();
            }

            return data;
        } catch (Exception ex) {
            System.out.println("Ha ocurrido un error cargando los datos de " + id);
            ex.printStackTrace();
        }
        return data;
    }


    // Antium
    public void register(SUser u, String pass, String email) {
        Main.getInstance().getServer().getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            Player p = u.getPlayer();
            try {
                PreparedStatement statement = openConnection().prepareStatement("SELECT `id` FROM `antium` WHERE `name` = ?");
                statement.setString(1, p.getUniqueId().toString());
                ResultSet rs = statement.executeQuery();
                if (!rs.next()) { //No hay filas encontradas, insertar nuevos datos
                    PreparedStatement inserDatos = openConnection().prepareStatement("INSERT INTO `antium` (`uuid`, `name`, `pass`, `email`) VALUES (?, ?, ?, ?)");
                    inserDatos.setString(1, u.getUuid().toString());
                    inserDatos.setString(2, p.getName());
                    inserDatos.setString(3, pass);
                    inserDatos.setString(4, email);
                    inserDatos.executeUpdate();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    public boolean login(SUser u, String inPass) {
        try {
            PreparedStatement statementDatos = openConnection().prepareStatement("SELECT `pass` FROM `antium` WHERE `uuid` = ?");
            statementDatos.setString(1, u.getUuid().toString());
            ResultSet rsDatos = statementDatos.executeQuery();

            if (rsDatos.next()) {
                String pass = PassUtils.decodePass(rsDatos.getString("pass"));
                return pass.equalsIgnoreCase(inPass);
            }
        } catch (CommunicationsException ex) {
            Log.debugLog(ex.toString());
            try {
                closeConnection();
                openConnection();
                return login(u, inPass);
            } catch (Exception ex1) {
                ex1.printStackTrace();
            }
        } catch (Exception ex) {
            System.out.println("Ha ocurrido un error cargando los datos de " + u.toString());
            ex.printStackTrace();
        }
        return false;
    }

    public boolean isRegistered(String uuid) {
        try {
            PreparedStatement statement = openConnection().prepareStatement("SELECT * FROM `antium` WHERE `uuid` =?");
            statement.setString(1, uuid);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            Log.log(Log.ERROR, e.toString());
        }
        return false;
    }

    public boolean deleteUserAntium(String uuid) {
        try {
            PreparedStatement statement = openConnection().prepareStatement("DELETE * FROM `antium` WHERE `uuid` =?");
            statement.setString(1, uuid);
            statement.executeQuery();
            return true;
        } catch (SQLException e) {
            Log.log(Log.ERROR, e.toString());
        }
        return false;
    }

    public boolean changePassword(String uuid, String newPassword) {
        try {
            PreparedStatement statement = openConnection().prepareStatement("UPDATE `antium` SET `pass`=? WHERE `uuid` =?");
            statement.setString(1, PassUtils.encodePass(newPassword));
            statement.setString(2, uuid);
            statement.executeQuery();
            return true;
        } catch (SQLException e) {
            Log.log(Log.ERROR, e.toString());
        }
        return false;
    }

    public boolean setEmail(String uuid, String email) {
        try {
            PreparedStatement statement = openConnection().prepareStatement("UPDATE `antium` SET `email`=? WHERE `uuid` =?");
            statement.setString(1, email);
            statement.setString(2, uuid);
            statement.executeQuery();
            return true;
        } catch (SQLException e) {
            Log.log(Log.ERROR, e.toString());
        }
        return false;
    }

    public void addBooster(SUser user, SBooster booster) throws SQLException {
        PreparedStatement statement = openConnection().prepareStatement("INSERT INTO `booster` (`multiplier`, `gameId`, `expires`, `uuid`) VALUES (?, ?, ?, ?)");
        statement.setInt(1, booster.getMultiplier());
        statement.setInt(2, booster.getGameID().getId());
        statement.setString(3, booster.getExpires().toString());
        statement.setString(4, booster.getUuid().toString());

        statement.executeUpdate();

        user.save();//Refresh the user data
    }

    private List<SBooster> loadBoosters(PreparedStatement statement) throws SQLException {
        ResultSet rs = statement.executeQuery();

        final List<SBooster> boosters = new ArrayList<>();

        while (rs.next()) {
            boosters.add(new SBooster(
                    rs.getInt("id"),
                    rs.getInt("multiplier"),
                    SServer.GameID.values()[rs.getInt("gameId")],
                    Timestamp.valueOf(rs.getString("expires")),
                    UUID.fromString(rs.getString("uuid"))
            ));
        }

        return boosters;
    }

    private int loadKeys(UUID id){
        if (Main.getInstance().getServer().getPluginManager().isPluginEnabled("GadgetsMenu")){
            try {
                Class<?> apiClazz = Class.forName("com.yapzhenyie.GadgetsMenu.api.GadgetsMenuAPI");
                Object factory = apiClazz.getMethod("getOfflinePlayerManager", UUID.class).invoke(null, id);
                return (int) factory.getClass().getMethod("getMysteryBoxes").invoke(factory);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
        else {
            try {
                final PreparedStatement statement = openConnection().prepareStatement("SELECT * FROM GadgetsMenu_Mystery_Boxes WHERE UUID=?");
                statement.setString(1, id.toString());
                final ResultSet rs = statement.executeQuery();

                int keys = 0;
                while (rs.next()) keys++;
                return keys;
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return 0;
    }
}
