package com.ra.binhpt.repository;

import com.ra.binhpt.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IShoppingCartRepository extends JpaRepository<ShoppingCart, Long>
{
	
	List<ShoppingCart> findAllByUsersId(Long userId);
	
	Optional<ShoppingCart> findByUsersIdAndProductId(Long userId, Long productId);
	
}
