package com.ra.binhpt.service;

import com.ra.binhpt.dto.req.ShoppingCartRequest;
import com.ra.binhpt.exception.CustomException;
import com.ra.binhpt.model.ShoppingCart;

import java.util.List;

public interface ICartService
{
	
	List<ShoppingCart> findAllByUserId();
	
	ShoppingCart addNewCart(ShoppingCartRequest shoppingCartRequest) throws CustomException;
	
}
