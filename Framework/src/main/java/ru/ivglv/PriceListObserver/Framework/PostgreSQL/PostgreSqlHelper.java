package ru.ivglv.PriceListObserver.Framework.PostgreSQL;

import org.jetbrains.annotations.NotNull;
import ru.ivglv.PriceListObserver.Adapter.Port.RemoteDbFailException;
import ru.ivglv.PriceListObserver.Adapter.Port.RemoteDataBase;
import ru.ivglv.PriceListObserver.Configuration.Properties.DbConfig;
import ru.ivglv.PriceListObserver.Model.Entity.CarPart;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.*;

public class PostgreSqlHelper implements RemoteDataBase {
    private final String DB_DRIVER = "org.postgresql.Driver";
    private Connection dbConnection;
    private final DbConfig config;

    public PostgreSqlHelper(DbConfig config) throws ClassNotFoundException
    {
        this.config = config;
        checkClass();
    }

    public void checkClass() throws ClassNotFoundException
    {
        Class.forName(DB_DRIVER);
    }

    @Override
    public void insert(@NotNull CarPart carPart) throws RemoteDbFailException {
        try {
            Statement statement = dbConnection.createStatement();
            statement.executeUpdate(
                    "INSERT INTO \"" + config.getTableName() + "\""
                            + "(\"Vendor\", \"Number\", \"SearchVendor\", \"SearchNumber\", \"Description\", \"Price\", \"Count\")"
                            + "VALUES ('"
                                + carPart.getVendor() + "', '"
                                + carPart.getNumber() + "', '"
                                + carPart.getSearchVendor() + "', '"
                                + carPart.getSearchNumber() + "', '"
                                + carPart.getDescription() + "', "
                                + carPart.getPrice() + ", "
                                + carPart.getCount()
                            + ");"
            );
            statement.close();
        }
        catch (SQLException ex)
        {
            throw new RemoteDbFailException("Problem in VENDOR='" + carPart.getVendor() + "' NUMBER='" + carPart.getNumber() + "': " + ex.getMessage());
        }
    }

    @Override
    public void connect() throws RemoteDbFailException {
        try {
            System.out.println("Connecting to DB...");
            dbConnection = DriverManager.getConnection(config.getDbUrl(), config.getDbUser(), config.getDbPass());
            System.out.println("Connection to DB established");
        }
        catch (SQLException ex)
        {
            throw new RemoteDbFailException(ex.getMessage());
        }
    }

    public boolean connected() throws RemoteDbFailException
    {
        try
        {
            return dbConnection.isValid(1000);
        }
        catch (SQLException ex)
        {
            throw new RemoteDbFailException(ex.getMessage());
        }
    }

    @Override
    public void disconnect() {
        try {
            dbConnection.close();
        }
        catch (SQLException ex) {}
    }

    @Override
    public Optional<CarPart> selectByIndex(
            @NotNull String searchVendor
            , @NotNull String searchNumber) throws RemoteDbFailException
    {
        Optional<CarPart> result = Optional.empty();
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet queryResult = statement.executeQuery(
                    "SELECT \"Vendor\", \"Number\", \"SearchVendor\", \"SearchNumber\", \"Description\", \"Price\", \"Count\""
                    + " FROM \"" + config.getTableName() + "\""
                    + " WHERE \"SearchVendor\" = '" + searchVendor + "'"
                    + " AND \"SearchNumber\" = '" + searchNumber + "';"
            );
            while(queryResult.next())
            {
                CarPart carPart = new CarPart.Builder(
                            queryResult.getString("Vendor")
                            , queryResult.getString("Number"))
                        .searchVendor(queryResult.getString("SearchVendor"))
                        .searchNumber(queryResult.getString("SearchNumber"))
                        .description(queryResult.getString("Description"))
                        .price(queryResult.getFloat("Price"))
                        .count(queryResult.getInt("Count"))
                        .build();
                result = Optional.of(carPart);
            }
            statement.close();
        } catch (SQLException ex) {
            throw new RemoteDbFailException(ex.getMessage());
        }
        return result;
    }

    @Override
    public List<CarPart> selectAll() throws RemoteDbFailException {
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet queryResult = statement.executeQuery(
                    "SELECT \"Vendor\", \"Number\", \"SearchVendor\", \"SearchNumber\", \"Description\", \"Price\", \"Count\""
                            + " FROM \"" + config.getTableName() + "\";"
            );
            ArrayList<CarPart> result = new ArrayList<>();
            while(queryResult.next())
            {
                CarPart carPart = new CarPart.Builder(
                        queryResult.getString("Vendor")
                        , queryResult.getString("Number"))
                        .searchVendor(queryResult.getString("SearchVendor"))
                        .searchNumber(queryResult.getString("SearchNumber"))
                        .description(queryResult.getString("Description"))
                        .price(queryResult.getFloat("Price"))
                        .count(queryResult.getInt("Count"))
                        .build();
                result.add(carPart);
            }
            statement.close();
            return result;
        } catch (SQLException ex) {
            throw new RemoteDbFailException(ex.getMessage());
        }
    }

    public void deleteAll() throws RemoteDbFailException
    {
        try {
            Statement statement = dbConnection.createStatement();
            statement.executeUpdate(
                    "DELETE FROM \"" + config.getTableName() + "\";"
            );
            statement.close();
        } catch (SQLException ex) {
            throw new RemoteDbFailException(ex.getMessage());
        }
    }
}
