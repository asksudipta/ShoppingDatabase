package se.lexicon;

import se.lexicon.dao.ProductDao;
import se.lexicon.dao.ProductDaoImpl;
import se.lexicon.exception.ObjectNotFoundException;
import se.lexicon.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class App {

    public static void main(String[] args) {
       // saveDataToDb();

        //fetchDataById();

        //findDataByName();

        findAllFromDb();

        //clearDataFromDb();
    }
    public static void saveDataToDb(){
        ProductDaoImpl productDao=new ProductDaoImpl();

        Product product1 = new Product(1, "mobile",1000.00);
        Product product2 = new Product(2, "Headphone",500.00);
        Product product3 = new Product(3, "Tv",7000.00);
        Product product4 = new Product(4, "Speaker",2000.00);
        Product product5 = new Product(5, "Washing Machine",5000.00);

        productDao.save(product1);
        productDao.save(product2);
        productDao.save(product3);
        productDao.save(product4);
        productDao.save(product5);
    }

    public static void fetchDataById(){
        ProductDaoImpl productDao=new ProductDaoImpl();

        Optional<Product> findId=productDao.findById(5);
        if (findId.isPresent()){
            System.out.println(findId.get());
        }else{
            System.out.println(">>>> Data not found!");
        }
    }
    public static void findDataByName(){
        ProductDaoImpl productDao=new ProductDaoImpl();
        List<Product> list=productDao.findByName("mobile");
        list.forEach(System.out::println);
    }

    public static void findAllFromDb(){
        ProductDaoImpl productDao=new ProductDaoImpl();
        List<Product> list=productDao.findAll();
        list.forEach(System.out::println);

    }

    public static void clearDataFromDb(){
        ProductDaoImpl productDao=new ProductDaoImpl();

        try {
            productDao.delete(5);
        } catch (ObjectNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

}
