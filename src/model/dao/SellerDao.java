package model.dao;

import java.util.List;
import model.entities.Seller;

public interface SellerDao {
    void insert(Seller dep);

    void update(Seller dep);

    void delete(Seller dep);

    Seller findById(Integer id);

    List<Seller> findAll();
}
