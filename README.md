# 💼 Sistema DAO com JDBC - Vendedores e Departamentos

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-Database%20Connection-orange?style=for-the-badge)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Design Pattern](https://img.shields.io/badge/Pattern-DAO-green?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

**Sistema completo de acesso a dados usando JDBC e padrão DAO (Data Access Object)**

[🇺🇸 English Version](README.en.md) | [📖 Sobre](#-sobre-o-projeto) | [🚀 Instalação](#-instalação) | [💡 Funcionalidades](#-funcionalidades)

---

</div>

## 📋 Sobre o Projeto

O **Sistema DAO com JDBC** é um projeto educacional desenvolvido em Java que demonstra a implementação completa do padrão **DAO (Data Access Object)** para acesso a banco de dados MySQL usando **JDBC (Java Database Connectivity)**. O sistema gerencia vendedores (Sellers) e departamentos (Departments) com operações CRUD completas.

### 🎯 Objetivos do Projeto

- Demonstrar **JDBC puro** sem frameworks
- Implementar **padrão DAO** completo
- Aplicar **princípios SOLID** (principalmente SRP e DIP)
- Criar **separação de responsabilidades** (camadas)
- Gerenciar **conexões de banco de dados** de forma eficiente
- Tratar **exceções personalizadas** para banco de dados
- Implementar **Factory Pattern** para criação de DAOs

### ✨ Características Principais

- 🗄️ **Conexão JDBC** - Gerenciamento eficiente de conexões
- 🏭 **Factory Pattern** - Criação centralizada de objetos DAO
- 📦 **DAO Pattern** - Separação da lógica de acesso a dados
- 🔗 **Relacionamento 1:N** - Seller possui Department
- ⚠️ **Exceções Personalizadas** - DbException e DbIntegrityException
- 🔄 **CRUD Completo** - Create, Read, Update, Delete
- 🎯 **Consultas Customizadas** - findByDepartment, findAll
- 📊 **Instanciação Otimizada** - Evita duplicação de objetos relacionados

---

## 🏗️ Arquitetura

### Estrutura do Projeto

```
jdbc-dao-project/
├── src/
│   ├── application/
│   │   ├── App.java              # Testes do SellerDao
│   │   └── App2.java             # Testes do DepartmentDao
│   ├── db/
│   │   ├── DB.java               # Gerenciador de conexões
│   │   ├── DbException.java      # Exceção genérica de DB
│   │   └── DbIntegrityException.java  # Exceção de integridade
│   └── model/
│       ├── entities/
│       │   ├── Seller.java       # Entidade Vendedor
│       │   └── Department.java   # Entidade Departamento
│       └── dao/
│           ├── SellerDao.java    # Interface SellerDao
│           ├── DepartmentDao.java # Interface DepartmentDao
│           ├── DaoFactory.java   # Factory para DAOs
│           └── impl/
│               ├── SellerDaoJDBC.java    # Implementação JDBC
│               └── DepartmentDaoJDBC.java # Implementação JDBC
├── db.properties                 # Configuração do banco
└── README.md
```

### 📊 Diagrama de Camadas

```
┌─────────────────────────────────────────┐
│         CAMADA DE APLICAÇÃO             │
│   (App.java, App2.java)                 │
│   - Testes e demonstrações              │
└────────────┬────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────┐
│         CAMADA DE ACESSO A DADOS        │
│   (DAO Interfaces + Factory)            │
│   - SellerDao, DepartmentDao            │
│   - DaoFactory                          │
└────────────┬────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────┐
│    IMPLEMENTAÇÕES DAO (impl/)           │
│   - SellerDaoJDBC                       │
│   - DepartmentDaoJDBC                   │
│   - SQL Queries                         │
└────────────┬────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────┐
│      GERENCIADOR DE CONEXÕES            │
│   (DB.java)                             │
│   - getConnection()                     │
│   - closeConnection()                   │
└────────────┬────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────┐
│         BANCO DE DADOS MySQL            │
│   - Tabela: seller                      │
│   - Tabela: department                  │
└─────────────────────────────────────────┘
```

### 🎨 Padrões de Projeto Aplicados

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

**Benefícios**:
- ✅ Separa lógica de negócio da lógica de persistência
- ✅ Facilita troca de banco de dados
- ✅ Facilita testes unitários (mock da interface)

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

**Benefícios**:
- ✅ Centraliza criação de objetos
- ✅ Esconde detalhes de implementação
- ✅ Facilita manutenção

---

## 💡 Funcionalidades

### 🔄 Fluxo de Operações

```
┌─────────────────────┐
│   Aplicação inicia  │
└──────────┬──────────┘
           │
           ▼
┌──────────────────────────────┐
│   DaoFactory.creatSellerDao()│
│   - Obtém conexão DB         │
│   - Cria SellerDaoJDBC       │
└──────────┬───────────────────┘
           │
           ▼
┌──────────────────────────────┐
│   Operações CRUD             │
│   - insert()                 │
│   - findById()               │
│   - findAll()                │
│   - findByDepartment()       │
│   - update()                 │
│   - delete()                 │
└──────────┬───────────────────┘
           │
           ▼
┌──────────────────────────────┐
│   Execução SQL               │
│   - PreparedStatement        │
│   - ResultSet                │
│   - Tratamento de exceções   │
└──────────────────────────────┘
```

### 📝 Operações CRUD - Seller

#### 1️⃣ **CREATE - Inserir Vendedor**

```java
Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", 
                               new Date(), 4000.00, department);
sellerDao.insert(newSeller);
System.out.println("Inserted! New id = " + newSeller.getId());
```

**Implementação**:
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
                int id = rs.getInt(1);
                seller.setId(id);  // Atualiza o ID do objeto
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

**Características**:
- ✅ Retorna ID gerado automaticamente
- ✅ Converte `java.util.Date` para `java.sql.Date`
- ✅ Tratamento de exceções
- ✅ Fechamento seguro de recursos

#### 2️⃣ **READ - Buscar por ID**

```java
Seller seller = sellerDao.findById(1);
System.out.println(seller);
```

**Implementação**:
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

**Métodos auxiliares**:
```java
private Department instantiateDepartment(ResultSet rs) throws SQLException {
    Department dep = new Department();
    dep.setId(rs.getInt("DepartmentId"));
    dep.setName(rs.getString("DepName"));
    return dep;
}

private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
    Seller seller = new Seller();
    seller.setId(rs.getInt("Id"));
    seller.setName(rs.getString("Name"));
    seller.setEmail(rs.getString("Email"));
    seller.setBirthDate(rs.getDate("BirthDate"));
    seller.setBaseSalary(rs.getDouble("BaseSalary"));
    seller.setDepartment(dep);
    return seller;
}
```

#### 3️⃣ **READ - Buscar por Departamento**

```java
Department department = new Department(2, null);
List<Seller> list = sellerDao.findByDepartment(department);
for (Seller s : list) {
    System.out.println(s);
}
```

**Implementação com Map para evitar duplicação**:
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
            "WHERE DepartmentId = ? " +
            "ORDER BY Name"
        );
        
        st.setInt(1, department.getId());
        rs = st.executeQuery();
        
        List<Seller> list = new ArrayList<>();
        Map<Integer, Department> map = new HashMap<>();
        
        while (rs.next()) {
            // Verifica se departamento já foi instanciado
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

**Técnica de Otimização**:
```
Sem Map (Ineficiente):
Seller 1 -> Department [id=2, name="Sales"]
Seller 2 -> Department [id=2, name="Sales"]  // Duplicado!
Seller 3 -> Department [id=2, name="Sales"]  // Duplicado!

Com Map (Eficiente):
Seller 1 ──┐
Seller 2 ──┼──> Department [id=2, name="Sales"]  // Mesma instância
Seller 3 ──┘
```

#### 4️⃣ **UPDATE - Atualizar Vendedor**

```java
seller = sellerDao.findById(1);
seller.setName("Martha Waine");
sellerDao.update(seller);
System.out.println("Update completed");
```

**Implementação**:
```java
@Override
public void update(Seller seller) {
    PreparedStatement st = null;
    try {
        st = conn.prepareStatement(
            "UPDATE seller " +
            "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " +
            "WHERE id = ?"
        );
        
        st.setString(1, seller.getName());
        st.setString(2, seller.getEmail());
        st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
        st.setDouble(4, seller.getBaseSalary());
        st.setInt(5, seller.getDepartment().getId());
        st.setInt(6, seller.getId());
        
        st.executeUpdate();
    } catch (SQLException e) {
        throw new DbException(e.getMessage());
    } finally {
        DB.closeStatement(st);
    }
}
```

#### 5️⃣ **DELETE - Excluir Vendedor**

```java
System.out.print("Enter id for delete test: ");
int id = sc.nextInt();
sellerDao.delete(id);
System.out.println("Delete completed");
```

**Implementação**:
```java
@Override
public void delete(Integer id) {
    PreparedStatement st = null;
    try {
        st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
        st.setInt(1, id);
        
        int rows = st.executeUpdate();
        
        if (rows == 0) {
            throw new DbException("The entered ID does not exist");
        }
    } catch (SQLException e) {
        throw new DbException(e.getMessage());
    } finally {
        DB.closeStatement(st);
    }
}
```

### 🏢 Operações CRUD - Department

#### Estrutura Similar

```java
// Interface
public interface DepartmentDao {
    void insert(Department dep);
    void update(Department dep);
    void delete(Department dep);
    Department findById(Integer id);
    List<Department> findAll();
}

// Uso
DepartmentDao departmentDao = DaoFactory.creatDepartmentDao();
Department dep = departmentDao.findById(1);
```

---

## 🔧 Gerenciamento de Conexões

### Classe DB - Singleton Pattern

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
    
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
    
    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
    
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}
```

### Arquivo de Configuração (db.properties)

```properties
user=root
password=sua_senha
dburl=jdbc:mysql://localhost:3306/coursejdbc
useSSL=false
```

**Características**:
- ✅ Conexão singleton (única instância)
- ✅ Carregamento de propriedades externas
- ✅ Métodos auxiliares para fechamento
- ✅ Tratamento centralizado de exceções

---

## 🗄️ Estrutura do Banco de Dados

### Script SQL

```sql
CREATE DATABASE IF NOT EXISTS coursejdbc;
USE coursejdbc;

-- Tabela Department
CREATE TABLE department (
    Id INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(60) DEFAULT NULL,
    PRIMARY KEY (Id)
);

-- Tabela Seller
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

-- Dados de exemplo
INSERT INTO department (Name) VALUES 
    ('Computers'),
    ('Electronics'),
    ('Fashion'),
    ('Books');

INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES 
    ('Bob Brown', 'bob@gmail.com', '1998-04-21', 1000, 1),
    ('Maria Green', 'maria@gmail.com', '1979-12-31', 3500, 2),
    ('Alex Grey', 'alex@gmail.com', '1988-01-15', 2200, 1),
    ('Martha Red', 'martha@gmail.com', '1993-11-30', 3000, 4),
    ('Donald Blue', 'donald@gmail.com', '2000-01-09', 4000, 3),
    ('Alex Pink', 'bob@gmail.com', '1997-03-04', 3000, 2);
```

### Diagrama ER

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

## 🚀 Instalação e Configuração

### Pré-requisitos

- **Java JDK 8+**
- **MySQL 5.7+** ou **MariaDB**
- **MySQL Connector/J** (JDBC Driver)
- **IDE**: Eclipse, IntelliJ IDEA ou VS Code

### Passo 1: Configurar Banco de Dados

```bash
# Conectar ao MySQL
mysql -u root -p

# Executar script SQL
source script.sql
```

### Passo 2: Adicionar JDBC Driver ao Projeto

#### Opção 1: Download Manual
1. Baixar MySQL Connector/J de: https://dev.mysql.com/downloads/connector/j/
2. Adicionar `.jar` ao buildpath do projeto

#### Opção 2: Maven (pom.xml)
```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```

### Passo 3: Configurar db.properties

Criar arquivo `db.properties` na raiz do projeto:

```properties
user=root
password=SUA_SENHA_AQUI
dburl=jdbc:mysql://localhost:3306/coursejdbc
useSSL=false
```

### Passo 4: Compilar e Executar

```bash
# Compilar
javac -cp .:mysql-connector-java-8.0.33.jar src/**/*.java

# Executar testes de Seller
java -cp .:mysql-connector-java-8.0.33.jar:src application.App

# Executar testes de Department
java -cp .:mysql-connector-java-8.0.33.jar:src application.App2
```

---

## 💻 Exemplos de Uso

### Exemplo Completo - Seller

```java
public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SellerDao sellerDao = DaoFactory.creatSellerDao();
        
        // TEST 1: Buscar por ID
        System.out.println("==== TEST 1: seller findById ====");
        Seller seller = sellerDao.findById(1);
        System.out.println(seller);
        
        // TEST 2: Buscar por Department
        System.out.println("\n==== TEST 2: findByDepartment ====");
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(department);
        for (Seller s : list) {
            System.out.println(s);
        }
        
        // TEST 3: Buscar todos
        System.out.println("\n==== TEST 3: findAll ====");
        list = sellerDao.findAll();
        for (Seller s : list) {
            System.out.println(s);
        }
        
        // TEST 4: Inserir
        System.out.println("\n==== TEST 4: insert ====");
        Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", 
                                      new Date(), 4000.00, department);
        sellerDao.insert(newSeller);
        System.out.println("Inserted! New id = " + newSeller.getId());
        
        // TEST 5: Atualizar
        System.out.println("\n==== TEST 5: update ====");
        seller = sellerDao.findById(1);
        seller.setName("Martha Waine");
        sellerDao.update(seller);
        System.out.println("Update completed");
        
        // TEST 6: Deletar
        System.out.println("\n==== TEST 6: delete ====");
        System.out.print("Enter id for delete test: ");
        int id = sc.nextInt();
        sellerDao.delete(id);
        System.out.println("Delete completed");
        
        sc.close();
    }
}
```

### Saída Esperada

```
==== TEST 1: seller findById ====
Seller [id=1, name=Bob Brown, email=bob@gmail.com, birthDate=1998-04-21, 
        baseSalary=1000.0, department=Department [id=1, name=Computers]]

==== TEST 2: findByDepartment ====
Seller [id=2, name=Maria Green, email=maria@gmail.com, ...]
Seller [id=6, name=Alex Pink, email=bob@gmail.com, ...]

==== TEST 3: findAll ====
[Lista de todos os vendedores...]

==== TEST 4: insert ====
Inserted! New id = 7

==== TEST 5: update ====
Update completed

==== TEST 6: delete ====
Enter id for delete test: 7
Delete completed
```

---

## 📊 Princípios SOLID Aplicados

### 1. **Single Responsibility Principle (SRP)**

```
✅ DB.java          -> Gerencia apenas conexões
✅ SellerDao        -> Define apenas contrato de Seller
✅ SellerDaoJDBC    -> Implementa apenas acesso a dados de Seller
✅ Seller           -> Representa apenas entidade Seller
```

### 2. **Open/Closed Principle (OCP)**

```java
// Aberto para extensão (novas implementações)
public interface SellerDao { ... }

// Fechado para modificação (interface estável)
public class SellerDaoJDBC implements SellerDao { ... }

// Fácil adicionar nova implementação
public class SellerDaoMongoDB implements SellerDao { ... }
```

### 3. **Liskov Substitution Principle (LSP)**

```java
// Qualquer implementação de SellerDao pode substituir outra
SellerDao dao1 = new SellerDaoJDBC(conn);
SellerDao dao2 = new SellerDaoMongoDB(conn);  // Futuro

// Código cliente não muda
Seller s = dao1.findById(1);  // Funciona
Seller s = dao2.findById(1);  // Também funciona
```

### 4. **Dependency Inversion Principle (DIP)**

```java
// Aplicação depende de abstração (interface)
SellerDao sellerDao = DaoFactory.creatSellerDao();
                   ↑
              (interface)

// Não depende diretamente de implementação concreta
// ❌ SellerDaoJDBC sellerDao = new SellerDaoJDBC(conn);
```

---

## 🎓 Conceitos JDBC Aplicados

### 1. **PreparedStatement vs Statement**

```java
// ✅ PreparedStatement (Seguro contra SQL Injection)
PreparedStatement st = conn.prepareStatement("SELECT * FROM seller WHERE Id = ?");
st.setInt(1, id);

// ❌ Statement (Vulnerável)
Statement st = conn.createStatement();
ResultSet rs = st.executeQuery("SELECT * FROM seller WHERE Id = " + id);
```

### 2. **Gerenciamento de Recursos**

```java
// Pattern try-finally para garantir fechamento
PreparedStatement st = null;
ResultSet rs = null;
try {
    // Operações
} catch (SQLException e) {
    throw new DbException(e.getMessage());
} finally {
    DB.closeStatement(st);   // Sempre executa
    DB.closeResultSet(rs);
}
```

### 3. **Retorno de Chaves Geradas**

```java
st = conn.prepareStatement(
    "INSERT INTO seller (...) VALUES (...)", 
    Statement.RETURN_GENERATED_KEYS  // ← Importante!
);

st.executeUpdate();

ResultSet rs = st.getGeneratedKeys();
if (rs.next()) {
    int id = rs.getInt(1);
    seller.setId(id);  // Atualiza objeto com ID do banco
}
```

---

## 🐛 Tratamento de Exceções

### Hierarquia de Exceções

```
RuntimeException
    │
    ├── DbException (Genérica)
    │   └── "Error connecting to database"
    │   └── "Error executing query"
    │
    └── DbIntegrityException (Integridade)
        └── "Foreign key constraint violation"
        └── "Duplicate entry"
```

### Uso Prático

```java
try {
    sellerDao.delete(1);
} catch (DbIntegrityException e) {
    System.out.println("Erro: Vendedor possui dependências");
} catch (DbException e) {
    System.out.println("Erro genérico: " + e.getMessage());
}
```

---

## 📈 Possíveis Melhorias

### Curto Prazo
- [ ] Adicionar transações (commit/rollback)
- [ ] Implementar connection pool
- [ ] Adicionar paginação em findAll
- [ ] Criar testes unitários (JUnit)

### Médio Prazo
- [ ] Migrar para JPA/Hibernate
- [ ] Adicionar cache de segundo nível
- [ ] Implementar auditoria (created_at, updated_at)
- [ ] Criar API REST com Spring Boot

### Longo Prazo
- [ ] Adicionar Docker para banco
- [ ] Implementar CI/CD
- [ ] Migrar para microserviços
- [ ] Adicionar métricas e monitoring

---

## 🎯 Lições Aprendidas

### ✅ Boas Práticas Implementadas

1. **Separação de Responsabilidades**: Cada classe tem papel bem definido
2. **Uso de Interfaces**: Facilita manutenção e testes
3. **Factory Pattern**: Centraliza criação de objetos
4. **PreparedStatement**: Previne SQL Injection
5. **Gerenciamento de Recursos**: Sempre fecha conexões/statements
6. **Exceções Personalizadas**: Tratamento específico de erros de BD

### 🚫 Desafios Encontrados

- Gerenciamento manual de conexões (solução: connection pool)
- Código SQL misturado com Java (solução: JPA)
- Muito código boilerplate (solução: frameworks)

---

## 📄 Licença

Este projeto está sob a licença MIT. Consulte o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👨‍💻 Autor

**João Guilhermmy**

- 🔗 GitHub: [https://github.com/JoaoGuilhermmy](https://github.com/JoaoGuilhermmy)
- 💼 LinkedIn: [www.linkedin.com/in/joão-guilhermmy-93661b29b](https://www.linkedin.com/in/joão-guilhermmy-93661b29b)
- 📧 Email: joaoguilhermmy2@gmail.com

---

## 🙏 Agradecimentos

- Comunidade Java pelos recursos educacionais
- Curso de Java que proporcionou este aprendizado
- Professores e colegas que contribuíram com feedback

---

<div align="center">

### ⭐ Se este projeto foi útil, considere dar uma estrela!

**Desenvolvido com ❤️ e muito ☕**

### 💡 Projeto educacional demonstrando JDBC e padrão DAO em Java

</div>
