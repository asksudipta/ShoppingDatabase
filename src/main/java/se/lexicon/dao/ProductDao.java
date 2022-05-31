package se.lexicon.dao;

import se.lexicon.exception.DatabaseConnectionException;
import se.lexicon.exception.ObjectNotFoundException;
import se.lexicon.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    Product save(Product product) throws DatabaseConnectionException;

    Optional<Product> findById(int id);

    List<Product> findAll();

    List<Product> findByName(String name);

    List<Product> findByPriceBetween(double low,double high);

    int delete(int id) throws ObjectNotFoundException;

    default void closeConnection(AutoCloseable... closeables) {
        if (closeables != null) {
            System.out.println("Close connections and statements");
            for (AutoCloseable autoCloseable : closeables) {
                try {
                    autoCloseable.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
