package io.clonalejandro.Essentials.objects;

import io.clonalejandro.Essentials.utils.MysqlManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alex
 * On 01/06/2020
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

public class Permission {

    private final UUID uuid;

    public Permission(UUID uuid){
        this.uuid = uuid;
    }

    public List<String> get(){
        final List<String> permissions = new ArrayList<>();

        try {
            final PreparedStatement statement = MysqlManager.getConnection().prepareStatement("SELECT * FROM Permissions WHERE uuid=?");

            statement.setString(1, this.uuid.toString());
            final ResultSet rs = statement.executeQuery();

            while (rs.next())
                permissions.add(rs.getString("node"));
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return permissions;
    }

    public void add(String node){
        try {
            final PreparedStatement statement = MysqlManager.getConnection().prepareStatement("INSERT INTO Permissions VALUES (NULL, ?, ?)");

            statement.setString(1, uuid.toString());
            statement.setString(2, node);

            statement.executeUpdate();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void remove(String node){
        try {
            final PreparedStatement statement = MysqlManager.getConnection().prepareStatement("DELETE FROM Permissions WHERE uuid=? AND node=?");

            statement.setString(1, uuid.toString());
            statement.setString(2, node);

            statement.executeUpdate();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
