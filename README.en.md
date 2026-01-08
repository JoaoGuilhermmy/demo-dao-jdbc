# 💼 DAO System with JDBC - Sellers and Departments

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-Database%20Connection-orange?style=for-the-badge)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Design Pattern](https://img.shields.io/badge/Pattern-DAO-green?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

**Complete data access system using JDBC and DAO (Data Access Object) pattern**

[🇧🇷 Versão em Português](README.md) | [📖 About](#-about-the-project) | [🚀 Installation](#-installation) | [💡 Features](#-features)

---

</div>

## 📋 About the Project

The **DAO System with JDBC** is an educational project developed in Java that demonstrates complete implementation of the **DAO (Data Access Object) pattern** for MySQL database access using **JDBC (Java Database Connectivity)**. The system manages sellers and departments with complete CRUD operations.

### 🎯 Project Objectives

- Demonstrate **pure JDBC** without frameworks
- Implement complete **DAO pattern**
- Apply **SOLID principles** (mainly SRP and DIP)
- Create **separation of concerns** (layers)
- Manage **database connections** efficiently
- Handle **custom exceptions** for database
- Implement **Factory Pattern** for DAO creation

### ✨ Key Features

- 🗄️ **JDBC Connection** - Efficient connection management
- 🏭 **Factory Pattern** - Centralized DAO object creation
- 📦 **DAO Pattern** - Separation of data access logic
- 🔗 **1:N Relationship** - Seller has Department
- ⚠️ **Custom Exceptions** - DbException and DbIntegrityException
- 🔄 **Complete CRUD** - Create, Read, Update, Delete
- 🎯 **Custom Queries** - findByDepartment, findAll
- 📊 **Optimized Instantiation** - Avoids duplicate related objects

---

## 🏗️ Architecture

### Project Structure

```
jdbc-dao-project/
├── src/
│   ├── application/
│   │   ├── App.java              # SellerDao tests
│   │   └── App2.java             # DepartmentDao tests
│   ├── db/
│   │   ├── DB.java               # Connection manager
│   │   ├── DbException.java      # Generic DB exception
│   │   └── DbIntegrityException.java  # Integrity exception
│   └── model/
│       ├── entities/
│       │   ├── Seller.java       # Seller entity
│       │   └── Department.java   # Department entity
│       └── dao/
│           ├── SellerDao.java    # SellerDao interface
│           ├── DepartmentDao.java # DepartmentDao interface
│           ├── DaoFactory.java   # DAO factory
│           └── impl/
│               ├── SellerDaoJDBC.java    # JDBC implementation
│               └── DepartmentDaoJDBC.java # JDBC implementation
├── db.properties                 # Database configuration
└── README.md
```

### 📊 Layer Diagram

```
┌─────────────────────────────────────────┐
│         APPLICATION LAYER               │
│   (App.java, App2.java)                 │
│   - Tests and demonstrations            │
└────────────┬────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────┐
│         DATA ACCESS LAYER               │
│   (DAO Interfaces + Factory)            │
│   - SellerDao, DepartmentDao            │
│   - DaoFactory                          │
└────────────┬────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────┐
│    DAO IMPLEMENTATIONS (impl/)          │
│   - SellerDaoJDBC                       │
│   - DepartmentDaoJDBC                   │
│   - SQL Queries                         │
└────────────┬────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────┐
│      CONNECTION MANAGER                 │
│   (DB.java)                             │
│   - getConnection()                     │
│   - closeConnection()                   │
└────────────┬────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────┐
│         MySQL DATABASE                  │
│   - Table: seller                       │
│   - Table: department                   │
└─────────────────────────────────────────┘
```

### 🎨 Applied Design Patterns

#### 1. **DAO Pattern (Data Access Object)**

```
┌──────────────┐          ┌──────────────┐
│ Application  │ -------> │ SellerDao    │ (Interface)
│              │          │              │
└──────────────┘          └──────┬───────┘
                                 │
                                 │ implements
                                 ▼
                          ┌──────────────┐
                          │SellerDaoJDBC │
                          │              │
                          └──────┬───────┘
                                 │
                                 ▼
                          ┌──────────────┐
                          │   Database   │
                          └──────────────┘
```

**Benefits**:
- ✅ Separates business logic from persistence logic
- ✅ Facilitates database switching
- ✅ Facilitates unit testing (interface mocking)

#### 2. **Factory Pattern**

```java
public class DaoFactory {
    public static SellerDao creatSellerDao() {
        return new SellerDaoJDBC(DB.getConnection());
    }
    
    public static DepartmentDao creatDepartmentDao() {
        return new DepartmentDaoJDBC(DB.getConnection());
    }
}
```

**Benefits**:
- ✅ Centralizes object creation
- ✅ Hides implementation details
- ✅ Facilitates maintenance

---

## 💡 Features

### 📝 CRUD Operations - Seller

#### 1️⃣ **CREATE - Insert Seller**

```java
Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", 
                               new Date(), 4000.00, department);
sellerDao.insert(newSeller);
System.out.println("Inserted! New id = " + newSeller.getId());
```

**Implementation**:
```java
@Override
public void insert(Seller seller) {
    PreparedStatement st = null;
    try {
        st = conn.prepareStatement(
            "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) " +
            "VALUES (?, ?, ?, ?, ?)", 
            Statement.RETURN_GENERATED_KEYS
        );
        
        st.setString(1, seller.getName());
        st.setString(2, seller.getEmail());
        st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
        st.setDouble(4, seller.getBaseSalary());
        st.setInt(5, seller.getDepartment().getId());
        
        int rowsAffected = st.executeUpdate();
        
        if (rowsAffected > 0) {
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                seller.setId(rs.getInt(1));
            }
            DB.closeResultSet(rs);
        }
    } catch (SQLException e) {
        throw new DbException(e.getMessage());
    } finally {
        DB.closeStatement(st);
    }
}
```

**Features**:
- ✅ Returns auto-generated ID
- ✅ Converts `java.util.Date` to `java.sql.Date`
- ✅ Exception handling
- ✅ Safe resource closing

#### 2️⃣ **READ - Find by ID**

```java
Seller seller = sellerDao.findById(1);
System.out.println(seller);
```

**Implementation with JOIN**:
```java
@Override
public Seller findById(Integer id) {
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
        st = conn.prepareStatement(
            "SELECT seller.*, department.Name as DepName " +
            "FROM seller INNER JOIN department " +
            "ON seller.DepartmentId = department.Id " +
            "WHERE seller.Id = ?"
        );
        
        st.setInt(1, id);
        rs = st.executeQuery();
        
        if (rs.next()) {
            Department dep = instantiateDepartment(rs);
            Seller seller = instantiateSeller(rs, dep);
            return seller;
        }
        return null;
    } catch (SQLException e) {
        throw new DbException(e.getMessage());
    } finally {
        DB.closeStatement(st);
        DB.closeResultSet(rs);
    }
}
```

#### 3️⃣ **READ - Find by Department**

**Optimization with Map**:
```java
@Override
public List<Seller> findByDepartment(Department department) {
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
        st = conn.prepareStatement(
            "SELECT seller.*, department.Name as DepName " +
            "FROM seller INNER JOIN department " +
            "ON seller.DepartmentId = department.Id " +
            "WHERE DepartmentId = ? ORDER BY Name"
        );
        
        st.setInt(1, department.getId());
        rs = st.executeQuery();
        
        List<Seller> list = new ArrayList<>();
        Map<Integer, Department> map = new HashMap<>();
        
        while (rs.next()) {
            Department dep = map.get(rs.getInt("DepartmentId"));
            
            if (dep == null) {
                dep = instantiateDepartment(rs);
                map.put(rs.getInt("DepartmentId"), dep);
            }
            
            Seller seller = instantiateSeller(rs, dep);
            list.add(seller);
        }
        return list;
    } catch (SQLException e) {
        throw new DbException(e.getMessage());
    } finally {
        DB.closeStatement(st);
        DB.closeResultSet(rs);
    }
}
```

**Optimization Technique**:
```
Without Map (Inefficient):
Seller 1 -> Department [id=2, name="Sales"]
Seller 2 -> Department [id=2, name="Sales"]  // Duplicate!
Seller 3 -> Department [id=2, name="Sales"]  // Duplicate!

With Map (Efficient):
Seller 1 ──┐
Seller 2 ──┼──> Department [id=2, name="Sales"]  // Same instance
Seller 3 ──┘
```

#### 4️⃣ **UPDATE - Update Seller**

```java
seller = sellerDao.findById(1);
seller.setName("Martha Waine");
sellerDao.update(seller);
System.out.println("Update completed");
```

#### 5️⃣ **DELETE - Delete Seller**

```java
System.out.print("Enter id for delete test: ");
int id = sc.nextInt();
sellerDao.delete(id);
System.out.println("Delete completed");
```

---

## 🔧 Connection Management

### DB Class - Singleton Pattern

```java
public class DB {
    private static Connection conn = null;
    
    public static Connection getConnection() {
        if (conn == null) {
            try {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }
    
    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }
}
```

### Configuration File (db.properties)

```properties
user=root
password=your_password
dburl=jdbc:mysql://localhost:3306/coursejdbc
useSSL=false
```

---

## 🗄️ Database Structure

### SQL Script

```sql
CREATE DATABASE IF NOT EXISTS coursejdbc;
USE coursejdbc;

CREATE TABLE department (
    Id INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(60) DEFAULT NULL,
    PRIMARY KEY (Id)
);

CREATE TABLE seller (
    Id INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(60) NOT NULL,
    Email VARCHAR(100) NOT NULL,
    BirthDate DATE NOT NULL,
    BaseSalary DOUBLE NOT NULL,
    DepartmentId INT NOT NULL,
    PRIMARY KEY (Id),
    FOREIGN KEY (DepartmentId) REFERENCES department(Id)
);

INSERT INTO department (Name) VALUES 
    ('Computers'),
    ('Electronics'),
    ('Fashion'),
    ('Books');

INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES 
    ('Bob Brown', 'bob@gmail.com', '1998-04-21', 1000, 1),
    ('Maria Green', 'maria@gmail.com', '1979-12-31', 3500, 2);
```

### ER Diagram

```
┌─────────────────┐            ┌─────────────────┐
│   Department    │            │     Seller      │
├─────────────────┤            ├─────────────────┤
│ Id (PK)         │<───────────│ Id (PK)         │
│ Name            │      1:N   │ Name            │
└─────────────────┘            │ Email           │
                               │ BirthDate       │
                               │ BaseSalary      │
                               │ DepartmentId(FK)│
                               └─────────────────┘
```

---

## 🚀 Installation and Setup

### Prerequisites

- **Java JDK 8+**
- **MySQL 5.7+** or **MariaDB**
- **MySQL Connector/J** (JDBC Driver)
- **IDE**: Eclipse, IntelliJ IDEA or VS Code

### Step 1: Setup Database

```bash
mysql -u root -p
source script.sql
```

### Step 2: Add JDBC Driver

#### Option 1: Manual Download
1. Download MySQL Connector/J from: https://dev.mysql.com/downloads/connector/j/
2. Add `.jar` to project buildpath

#### Option 2: Maven (pom.xml)
```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```

### Step 3: Configure db.properties

```properties
user=root
password=YOUR_PASSWORD_HERE
dburl=jdbc:mysql://localhost:3306/coursejdbc
useSSL=false
```

### Step 4: Compile and Run

```bash
javac -cp .:mysql-connector-java-8.0.33.jar src/**/*.java
java -cp .:mysql-connector-java-8.0.33.jar:src application.App
```

---

## 💻 Usage Examples

### Complete Example

```java
public class App {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.creatSellerDao();
        
        // Find by ID
        Seller seller = sellerDao.findById(1);
        System.out.println(seller);
        
        // Find by Department
        Department dept = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(dept);
        for (Seller s : list) {
            System.out.println(s);
        }
        
        // Insert
        Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", 
                                      new Date(), 4000.00, dept);
        sellerDao.insert(newSeller);
        System.out.println("Inserted! New id = " + newSeller.getId());
        
        // Update
        seller.setName("Martha Waine");
        sellerDao.update(seller);
        
        // Delete
        sellerDao.delete(7);
    }
}
```

---

## 📊 SOLID Principles Applied

### 1. **Single Responsibility Principle**
- `DB.java` → Manages only connections
- `SellerDao` → Defines only Seller contract
- `SellerDaoJDBC` → Implements only Seller data access

### 2. **Dependency Inversion Principle**
```java
// Application depends on abstraction
SellerDao sellerDao = DaoFactory.creatSellerDao();
            ↑
       (interface)
```

---

## 🎓 JDBC Concepts Applied

### PreparedStatement Benefits

```java
// ✅ Safe (prevents SQL Injection)
PreparedStatement st = conn.prepareStatement("SELECT * FROM seller WHERE Id = ?");
st.setInt(1, id);

// ❌ Unsafe
Statement st = conn.createStatement();
ResultSet rs = st.executeQuery("SELECT * FROM seller WHERE Id = " + id);
```

### Resource Management

```java
try {
    // Operations
} finally {
    DB.closeStatement(st);   // Always executes
    DB.closeResultSet(rs);
}
```

---

## 🐛 Exception Handling

### Exception Hierarchy

```
RuntimeException
    │
    ├── DbException (Generic)
    └── DbIntegrityException (Integrity)
```

### Practical Use

```java
try {
    sellerDao.delete(1);
} catch (DbIntegrityException e) {
    System.out.println("Error: Seller has dependencies");
} catch (DbException e) {
    System.out.println("Generic error: " + e.getMessage());
}
```

---

## 📈 Possible Improvements

### Short Term
- [ ] Add transactions (commit/rollback)
- [ ] Implement connection pool
- [ ] Add pagination in findAll
- [ ] Create unit tests (JUnit)

### Medium Term
- [ ] Migrate to JPA/Hibernate
- [ ] Add second-level cache
- [ ] Implement auditing
- [ ] Create REST API with Spring Boot

### Long Term
- [ ] Add Docker for database
- [ ] Implement CI/CD
- [ ] Migrate to microservices
- [ ] Add metrics and monitoring

---

## 🎯 Lessons Learned

### ✅ Best Practices Implemented

1. **Separation of Concerns**: Each class has well-defined role
2. **Interface Usage**: Facilitates maintenance and testing
3. **Factory Pattern**: Centralizes object creation
4. **PreparedStatement**: Prevents SQL Injection
5. **Resource Management**: Always closes connections/statements
6. **Custom Exceptions**: Specific DB error handling

### 🚫 Challenges Encountered

- Manual connection management (solution: connection pool)
- SQL code mixed with Java (solution: JPA)
- Much boilerplate code (solution: frameworks)

---

## 📄 License

This project is under the MIT license. See the [LICENSE](LICENSE) file for more details.

---

## 👨‍💻 Author

**João Guilhermmy**

- 🔗 GitHub: [https://github.com/JoaoGuilhermmy](https://github.com/JoaoGuilhermmy)
- 💼 LinkedIn: [www.linkedin.com/in/joão-guilhermmy-93661b29b](https://www.linkedin.com/in/joão-guilhermmy-93661b29b)
- 📧 Email: joaoguilhermmy2@gmail.com

---

## 🙏 Acknowledgments

- Java community for educational resources
- Java course that provided this learning
- Professors and colleagues who contributed feedback

---

<div align="center">

### ⭐ If this project was useful, consider giving it a star!

**Developed with ❤️ and lots of ☕**

### 💡 Educational project demonstrating JDBC and DAO pattern in Java

</div>
