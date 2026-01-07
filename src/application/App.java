package application;

import java.util.Locale;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class App {
    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.US);

        SellerDao sellerDao = DaoFactory.creatSellerDao();

        Seller seller = sellerDao.findById(1);

        IO.println(seller);

    }
}
