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

        System.out.println("\n==== TEST 3: department insert ====");
        Department newdep = new Department(null, "Construction");
        departmentDao.insert(newdep);
        System.out.println("Inserted! New id = " + newdep.getId());

        System.out.println("\n==== TEST 4: department update ====");
        Department dep2 = departmentDao.findById(1);
        dep2.setName("Maneger");
        departmentDao.update(dep2);
        System.out.println("Update completed!");

        System.out.println("\n==== TESTE 5: department delete ====");
        System.out.print("Enter the ID to delete the department: ");
        int id = sc.nextInt();
        departmentDao.delete(id);
        System.out.println("Delete completed");
        sc.close();
    }
}
