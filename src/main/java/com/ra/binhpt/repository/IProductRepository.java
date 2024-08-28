package com.ra.binhpt.repository;

import com.ra.binhpt.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product,Long>
{
}
