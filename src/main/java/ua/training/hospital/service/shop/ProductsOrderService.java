package ua.training.hospital.service.shop;

import ua.training.hospital.entity.shop.ProductOrder;

import java.util.Optional;

public interface ProductsOrderService {
    Optional<ProductOrder> createProductOrder(ProductOrder productOrder);
    Optional<ProductOrder> getProductOrder(long productOrderId);
}
