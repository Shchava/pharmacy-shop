package ua.training.hospital.service.shop.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.training.hospital.entity.User;
import ua.training.hospital.entity.shop.Cart;
import ua.training.hospital.entity.shop.ProductOrder;
import ua.training.hospital.repository.shop.CartRepository;
import ua.training.hospital.repository.shop.ProductOrderRepository;
import ua.training.hospital.service.shop.CartService;
import ua.training.hospital.service.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final ProductOrderRepository productOrderRepository;
    private final CartRepository cartRepository;
    private final UserService userService;

    @Override
    public Optional<Cart> getCart(long cartId) {
        return cartRepository.findById(cartId);
    }

    @Override
    public Optional<Cart> getCart(String userId) {
        return cartRepository.findFirstByOwnerEmail(userId);
    }

    @Override
    public Optional<Cart> createCart(String userEmail) {
        Optional<User> owner = userService.getUser(userEmail);
        return owner.map(user -> cartRepository
                .save(Cart.builder()
                        .owner(user)
                        .build()));
    }

    @Override
    public Optional<Cart> createCartOrAddItemToExistingCart(ProductOrder newProduct, String userEmail) {

        Optional<Cart> existingCart = cartRepository.findFirstByOwnerEmail(userEmail);
        if(existingCart.isPresent()) {
            Optional<ProductOrder> existingOrder  = existingCart.get()
                    .getProducts()
                    .stream()
                    .filter(product -> product.getProduct().getProductId() == newProduct.getProduct().getProductId()).findFirst();

            if(existingOrder.isPresent()) {
                existingOrder.get().setCount(existingOrder.get().getCount() + newProduct.getCount());
                productOrderRepository.save(existingOrder.get());

                return existingCart;
            }


        }

        Optional<ProductOrder> productOrder = getOrCreateProductOrder(newProduct);
        if(!productOrder.isPresent()) {
            return Optional.empty();
        }

        if(existingCart.isPresent()) {
            existingCart.get().getProducts().add(productOrder.get());
            return Optional.of(cartRepository.save(existingCart.get()));

        } else {
            Optional<User> owner = userService.getUser(userEmail);
            if(!owner.isPresent()) {
                return Optional.empty();
            }

            List<ProductOrder> products = new ArrayList<>(1);
            products.add(productOrder.get());

            final Cart cartEntry = Cart.builder()
                    .owner(owner.get())
                    .products(products)
                    .build();

            return Optional.of(cartRepository.save(cartEntry));
        }
    }

    @Override
    public void deleteOrderFromCart(long orderId, String userEmail) {
        Optional<Cart> existingCart = cartRepository.findFirstByOwnerEmail(userEmail);
        if(!existingCart.isPresent())
            return;

        for(int i =0; i < existingCart.get().getProducts().size(); i++) {
            if(existingCart.get().getProducts().get(i).getProductOrderId() == orderId) {
                existingCart.get().getProducts().remove(i);

                cartRepository.save(existingCart.get());
                break;
            }
        }

        productOrderRepository.deleteById(orderId);
    }

    private Optional<ProductOrder> getOrCreateProductOrder (ProductOrder newProductOrder) {
        Optional<ProductOrder> existing =  productOrderRepository.findById(newProductOrder.getProductOrderId());
        if(existing.isPresent()) {
            return existing;
        } else {
            return Optional.of(productOrderRepository.save(newProductOrder));
        }
    }
}
