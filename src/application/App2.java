package application;

import java.util.List;
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
        Department dep = departmentDao.findById(1);
        System.out.println(dep);

        System.out.println("\n==== TEST 2: department findall ====");
        List<Department> list = departmentDao.findAll();
        for (Department d : list) {
            System.out.println(d);
        }

        sc.close();
    }
}
