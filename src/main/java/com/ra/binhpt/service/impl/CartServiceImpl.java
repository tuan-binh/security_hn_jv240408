package com.ra.binhpt.service.impl;

import com.ra.binhpt.dto.req.ShoppingCartRequest;
import com.ra.binhpt.exception.CustomException;
import com.ra.binhpt.model.Product;
import com.ra.binhpt.model.ShoppingCart;
import com.ra.binhpt.repository.IProductRepository;
import com.ra.binhpt.repository.IShoppingCartRepository;
import com.ra.binhpt.security.principle.MyUserDetails;
import com.ra.binhpt.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService
{
	private final IShoppingCartRepository shoppingCartRepository;
	private final IProductRepository productRepository;
	
	@Override
	public List<ShoppingCart> findAllByUserId()
	{
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return shoppingCartRepository.findAllByUsersId(userDetails.getUsers().getId());
	}
	
	@Override
	public ShoppingCart addNewCart(ShoppingCartRequest shoppingCartRequest) throws CustomException
	{
		/**
		 * Kiểm tra trong database xem người đang đăng nhập đã
		 * mua sản phẩm gửi lên chưa dựa vào userId và productId
		 * */
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Optional<ShoppingCart> optionalShoppingCart =
				  shoppingCartRepository.findByUsersIdAndProductId(
							 userDetails.getUsers().getId(),
							 shoppingCartRequest.getProductId()
				  );
		if (optionalShoppingCart.isPresent())
		{
			// đã tồn tại và thay đổi số lượng
			ShoppingCart shoppingCart = optionalShoppingCart.get();
			if (shoppingCart.getProduct().getStock() > shoppingCart.getQuantity() + shoppingCartRequest.getQuantity())
			{
				shoppingCart.setQuantity(shoppingCart.getQuantity() + shoppingCartRequest.getQuantity());
				return shoppingCartRepository.save(shoppingCart);
			}
			else
			{
				throw new CustomException("product not enough quantity", HttpStatus.BAD_REQUEST);
			}
			
		}
		else
		{
			// chưa tồn tại thì tạo đối tượng ShoppingCart và thêm mới vào database
			Product product = productRepository.findById(shoppingCartRequest.getProductId())
					  .orElseThrow(() -> new CustomException("product not found", HttpStatus.NOT_FOUND));
			if (product.getStock() > shoppingCartRequest.getQuantity())
			{
				ShoppingCart shoppingCart = ShoppingCart.builder()
						  .users(userDetails.getUsers())
						  .product(product)
						  .quantity(shoppingCartRequest.getQuantity())
						  .build();
				return shoppingCartRepository.save(shoppingCart);
			}
			else
			{
				throw new CustomException("product not enough quantity", HttpStatus.BAD_REQUEST);
			}
		}
	}
}
