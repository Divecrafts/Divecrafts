package io.clonalejandro.DivecraftsBungee.utils;

import java.sql.*;

public final class MySQL {
	
    private String host;
    private String basededatos;
    private String usuario;
    private String password;
    private Connection c;

    public MySQL(String host, String basededatos, String usuario, String password) {
        this.host = host;
        this.basededatos = basededatos;
        this.usuario = usuario;
        this.password = password;
        this.conectarDB();
    }

    public void conectarDB() {
        try {
            this.c = DriverManager.getConnection("jdbc:mysql://" + this.host + ":3306/" + this.basededatos + "?autoReconnect=true", this.usuario, this.password);
        }
        catch (SQLException localSQLException) {
            // empty catch block
        }
    }

    public void cerrarDB() {
        try {
            if (this.c != null) {
                this.c.close();
            }
        }
        catch (SQLException localSQLException) {
            // empty catch block
        }
    }

    public void actualizarQueryErr(String query) throws SQLException {
        Statement s = this.c.createStatement();
        s.executeUpdate(query);
    }

    public void actualizarQuery(String query) {
        try {
            Statement s = this.c.createStatement();
            s.executeUpdate(query);
        }
        catch (SQLException localSQLException) {
            // empty catch block
            localSQLException.printStackTrace();
        }
    }

    public boolean hasConexion() {
        return this.c == null;
    }

    public ResultSet query(String query) {
        ResultSet rs = null;
        try {
            Statement s = this.c.createStatement();
            rs = s.executeQuery(query);
        }
        catch (SQLException localSQLException) {
            // empty catch block
        }
        return rs;
    }
}

