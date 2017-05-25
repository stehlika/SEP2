package Server.Persistence;

import java.sql.*;

import java.util.ArrayList;

/**
 * Created by Karolina on 15/05/2017.
 */
public class Database {

    private String url;
    private String user;
    private String password;
    private Connection connection;

    /**
     * Constructor.
     *
     * @param driver
     *          the name of database driver
     *
     *@param url
     *          the full url for the database
     * @param user
     *          the username for database
     * @param password
     *          the password for database    @throws ClassNotFoundException
     *           if driver cannot be loaded
     */

    public Database(String driver, String url, String user, String password) throws ClassNotFoundException {
        this.url = url;
        this.user = user;
        this.password = password;
        this.connection = null;
        Class.forName(driver);
    }

    private void openDatabase() throws SQLException //set up connection
    {
        connection = DriverManager.getConnection(url, user, password);
    }

    private void closeDatabase() throws SQLException
    {
        connection.close();
    }

    /**
     * Returning the result from an SQL query in the form of an Object array for
     * each row. All Object arrays are returned in an ArrayList.
     *
     * @param sql
     *          the SQL statement to execute. Starting with "SELECT".
     * @param statementElements
     *          a number of statement elements each representing an element for a
     *          placeholder in the SQL string.
     * @return an ArrayList with an Object[] for each row in the query result
     * @throws SQLException
     *           if something went wrong in the connection or query
     */

    public ResultSet queryRS (String sql, Object... statementElements) throws SQLException
    {
        openDatabase();

        PreparedStatement statement = null;
        ArrayList<Object[]> list = null;
        ResultSet resultSet = null;
        if(sql != null && statement == null)
        {
            statement = connection.prepareStatement(sql);
            if(statementElements != null)
            {
                for (int i=0; i<statementElements.length; i++)
                {
                    statement.setObject(i+1, statementElements[i]);
                }
            }

        }
        resultSet = statement.executeQuery();
        list = new ArrayList<Object[]>();
        while(resultSet.next())
        {
            Object [] row = new Object [resultSet.getMetaData().getColumnCount()];
            for(int i=0; i<row.length; i++)
            {
                row[i] = resultSet.getObject(i+1);
            }
            list.add(row);
        }
        if(resultSet !=null)
            resultSet.close();
        if(statement != null)
            statement.close();
        closeDatabase();
        return resultSet;
    }

    public ArrayList<Object[]> query (String sql, Object... statementElements) throws SQLException
    {
        openDatabase();

        PreparedStatement statement = null;
        ArrayList<Object[]> list = null;
        ResultSet resultSet = null;
        if(sql != null && statement == null)
        {
            statement = connection.prepareStatement(sql);
            if(statementElements != null)
            {
                for (int i=0; i<statementElements.length; i++)
                {
                    statement.setObject(i+1, statementElements[i]);
                }
            }

        }
        resultSet = statement.executeQuery();
        list = new ArrayList<Object[]>();
        while(resultSet.next())
        {
            Object [] row = new Object [resultSet.getMetaData().getColumnCount()];
            for(int i=0; i<row.length; i++)
            {
                row[i] = resultSet.getObject(i+1);
            }
            list.add(row);
        }
        if(resultSet !=null)
            resultSet.close();
        if(statement != null)
            statement.close();
        closeDatabase();
        return list;
    }

    /**
     * This will update the database given an sql statement and multiple or no arguments
     *
     * @param sql
     *          the sql updates to execute. Could start with "UPDATE", "INSERT", "CREATE", ...
     * @param statementElements
     *          a number of statement elements each representing an element for a
     *          placeholder in the SQL string.
     * @return an integer representing the number of updates given by the database
     * @throws SQLException
     *           if something went wrong in the connection or update
     */

    public int update(String sql, Object... statementElements) throws SQLException
    {
        openDatabase();
        PreparedStatement statement = connection.prepareStatement(sql);
        if(statementElements != null)
        {
            for(int i = 0; i<statementElements.length; i++)
                statement.setObject(i+1, statementElements[i]);
        }
        int result = statement.executeUpdate();
        closeDatabase();
        return result;
    }
}