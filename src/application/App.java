package application;

import java.util.Date;
import java.util.Locale;

import model.entities.Department;
import model.entities.Seller;

public class App {
    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.US);
        Department department = new Department(2, "Books");
        Seller seller = new Seller(1, "Luan", "luan@gmail.com", new Date(), 3.000, department);

        IO.println(seller);

    }
}
