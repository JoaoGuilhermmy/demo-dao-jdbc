package application;

import java.util.Locale;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class App2 {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        DepartmentDao departmentDao = DaoFactory.creatDepartmentDao();

        System.out.println("==== TEST 1: department findbyid ====");
        System.out.print("Enter for Id department: ");
        int id = sc.nextInt();
        Department dep = departmentDao.findById(id);
        System.out.println(dep);

        sc.close();
    }
}
