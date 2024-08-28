package com.ra.binhpt.controller;

import com.ra.binhpt.dto.req.ShoppingCartRequest;
import com.ra.binhpt.exception.CustomException;
import com.ra.binhpt.model.ShoppingCart;
import com.ra.binhpt.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/user/cart")
@RequiredArgsConstructor
public class CartController
{
	
	private final ICartService cartService;
	
	@GetMapping
	public ResponseEntity<?> getCart()
	{
		return ResponseEntity.ok().body(cartService.findAllByUserId());
	}
	
	@PostMapping
	public ResponseEntity<?> addShoppingCart(@RequestBody ShoppingCartRequest shoppingCartRequest) throws CustomException
	{
		return ResponseEntity
				  .created(URI.create("api/v1/user/cart"))
				  .body(cartService.addNewCart(shoppingCartRequest));
	}
	
	
}
