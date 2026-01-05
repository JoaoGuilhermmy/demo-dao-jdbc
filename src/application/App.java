package application;

import java.util.Date;
import java.util.Locale;

import model.entities.Sellere;
import model.entities.Seller;

public class App {
    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.US);
        Sellere department = new Sellere(2, "Books");
        Seller seller = new Seller(1, "Luan", "luan@gmail.com", new Date(), 3.000, department);

        IO.println(seller);

    }
}
