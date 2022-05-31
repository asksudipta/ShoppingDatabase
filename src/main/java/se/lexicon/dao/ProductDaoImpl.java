package se.lexicon.dao;

import com.google.protobuf.Internal;
import se.lexicon.dbConnection.MySqlConnection;
import se.lexicon.exception.DatabaseConnectionException;
import se.lexicon.exception.ObjectNotFoundException;
import se.lexicon.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDaoImpl implements ProductDao {

    // this method is an instance method- how to call an  instance method?
    // first: instantiate the class (ProductDaoImpl)
    // step2: through the reference name you can access the method and call it

    @Override
    public Product save(Product product) {


        // define db connection
        Connection connection = null;
        // create prepared statement
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        // save it on db
        String saveQuery = "insert into product(id,name,price) values (?,?,?)";


        try {
            connection = MySqlConnection.getMySQLConnection();
            preparedStatement = connection.prepareStatement(saveQuery, Statement.RETURN_GENERATED_KEYS);


            preparedStatement.setInt(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            System.out.println("insert operation is done successfully");
            if (resultSet.next()) {
                int generatedId = resultSet.getInt(1);
                product.setId(generatedId);
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        } catch (DatabaseConnectionException e) {
            System.out.println("Database URL " + e.getJdbcUrl() + " is not available.");
            System.out.println("Message: " + e.getMessage());
        }

        // return it

        finally {
            closeConnection(resultSet, preparedStatement, connection);
        }
        return product;
    }

    @Override
    public Optional<Product> findById(int id) {

        Product result = null;
        String selectQuery = "select * from product where id=?";
        ResultSet resultSet = null;
        try (
                Connection connection = MySqlConnection.getMySQLConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        ) {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price")
                );
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        } catch (DatabaseConnectionException e) {
            System.out.println("Database URL " + e.getJdbcUrl() + " is not available.");
            System.out.println("Message: " + e.getMessage());
        } finally {
            closeConnection(resultSet);
        }
        return Optional.ofNullable(result);
    }


    @Override
    public List<Product> findAll() {

        List<Product> productList = new ArrayList<>();
        String selectQuery = "select * from Product";
        ResultSet resultSet = null;
        try (
                Connection connection = MySqlConnection.getMySQLConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        ) {
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
               Product result = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price")
                );
               productList.add(result);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        } catch (DatabaseConnectionException e) {
            System.out.println("Database URL " + e.getJdbcUrl() + " is not available.");
            System.out.println("Message: " + e.getMessage());
        } finally {
            closeConnection(resultSet);
        }
        return productList;
    }

    @Override
    public List<Product> findByName(String name) {

        List<Product> productList = new ArrayList<>();
        String selectQuery = "select *from product where name=?";
        ResultSet resultSet = null;


        try (
                Connection connection = MySqlConnection.getMySQLConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        ) {
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product result = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price")
                );
                productList.add(result);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        } catch (DatabaseConnectionException e) {
            System.out.println("Database URL " + e.getJdbcUrl() + " is not available.");
            System.out.println("Message: " + e.getMessage());
        } finally {
            closeConnection(resultSet);
        }
        return productList;
    }


    @Override
    public List<Product> findByPriceBetween(double low, double high) {
        List<Product> list = new ArrayList<>();
        String selectQuery = "select price from product where price between ? and ? ";
        ResultSet resultSet = null;


        try (
                Connection connection = MySqlConnection.getMySQLConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);

        ) {
            preparedStatement.setDouble(1, low);
            preparedStatement.setDouble(1, high);
            //TOdo....


        } catch (DatabaseConnectionException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public int delete(int id) throws ObjectNotFoundException {
        String deleteQuery = "delete from product where id = ?";
        int result = 0;
        try (
                Connection connection = MySqlConnection.getMySQLConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
        ) {

            preparedStatement.setInt(1, id);
            result = preparedStatement.executeUpdate();
            if (result == 0) throw new ObjectNotFoundException
                    ("row for product id " + id + " dose not exist");

        } catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        } catch (DatabaseConnectionException e) {
            System.out.println("Database URL " + e.getJdbcUrl() + " is not available.");
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

}
