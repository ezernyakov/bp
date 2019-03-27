package ru.bp.sql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import ru.bp.file.ResourceFile;

/**
 * Класс для работы с  PostgreSQL
 */
public class SQLConnection {
    protected String username;
    protected String password;
    protected String address;
    protected String port;
    protected String base;
    protected Connection sql;
    private String connectionString = "jdbc:postgresql://%s:%s/%s?user=%s&password=%s";


    public SQLConnection(String address, String base, String port, String username, String password) {
        this.address = address;
        this.port = port;
        this.username = username;
        this.password = password;
        this.base = base;
    }

    protected void initConnection() throws SQLException{
        sql = DriverManager.getConnection(String.format(connectionString, address, port, base, username, password));
    }

    public void closeConnection() throws SQLException {
        sql.close();
    }

    /**
     * Executes SQL query and returns the ResultSet.
     * Should be used if we need to get some data FROM database
     * @param query
     * @return
     * @throws SQLException
     */
    public ResultSet executeQuery(String query) throws SQLException {
        initConnection();
        try {
            Statement statement = sql.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            throw new SQLException("Выполнение SQL-запроса закончилось неудачей", e);
        } finally {
            closeConnection();
        }
    }

    /**
     * Executes SQL query and returns status of execution
     * Should be used if we need to write some data TO database
     * @param query
     * @return
     * @throws SQLException
     */
    public int execute(String query) throws SQLException {
        initConnection();
        try {
            Statement statement = sql.createStatement();
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new SQLException("Выполнение SQL-запроса закончилось неудачей", e);
        } finally {
            closeConnection();
        }
    }

    /**
     * Executes SQL from resource file
     * @param filename relative path to resource file
     * @throws SQLException
     * @throws IOException
     */
    public void executeSQLFromFile(String filename) throws SQLException, IOException {
        ResourceFile sqlScript = new ResourceFile(filename);
        execute(sqlScript.read());
    }

    /**
     * Executes SQL from resource file with text substitutions
     * @param filename relative path to resource file
     * @param substitutions map with substitutions pairs
     * @throws SQLException
     * @throws IOException
     */
    public void executeSQLFromFile(String filename, Map<String, String> substitutions) throws SQLException, IOException {
        ResourceFile sqlFile = new ResourceFile(filename);
        String file = sqlFile.read();
        for (Map.Entry<String, String> entry : substitutions.entrySet()) {
            file = file.replaceAll(entry.getKey(), entry.getValue());
        }

        execute(file);
    }
}
