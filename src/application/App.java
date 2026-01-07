package application;

import java.util.List;
import java.util.Locale;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class App {
    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.US);
        SellerDao sellerDao = DaoFactory.creatSellerDao();

        IO.println("==== TEST 1: seller findbyid ====");
        Seller seller = sellerDao.findById(1);
        IO.println(seller);

        IO.println("\n==== TEST 2: seller findByDepartment ====");
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(department);
        for (Seller s : list) {
            IO.println(s);
        }

        IO.println("\n==== TEST 3: seller findAll ====");
        list = sellerDao.findAll();
        for (Seller s : list) {
            IO.println(s);
        }

    }
}
