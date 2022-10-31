package ua.training.hospital.repository.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.hospital.entity.shop.BuyOrder;

import java.util.List;
import java.util.Optional;

public interface BuyOrderRepository extends JpaRepository<BuyOrder, Long> {
    Page<BuyOrder> getBuyOrdersByOwnerEmail (String ownerEmail, Pageable pageable);

    Optional<BuyOrder> getBuyOrderByOrderId (long orderId);
}
