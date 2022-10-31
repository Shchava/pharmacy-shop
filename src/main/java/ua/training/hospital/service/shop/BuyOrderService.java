package ua.training.hospital.service.shop;

import org.springframework.data.domain.Page;
import ua.training.hospital.entity.shop.BuyOrder;

import java.util.List;
import java.util.Optional;

public interface BuyOrderService {
    Optional<BuyOrder> createBuyOrder(BuyOrder productOrder, String userId);
    Optional<BuyOrder> createBuyOrder(BuyOrder productOrder);
    Page<BuyOrder> getAllBuyOrders(int pageNumber, int requestsPerPage);

    Page<BuyOrder> findBuyOrders(int pageNumber, int requestsPerPage, String userEmail);
    Optional<BuyOrder> findBuyOrder(long orderId);
}
