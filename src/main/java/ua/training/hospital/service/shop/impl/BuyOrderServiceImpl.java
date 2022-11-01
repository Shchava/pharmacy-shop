package ua.training.hospital.service.shop.impl;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.training.hospital.entity.User;
import ua.training.hospital.entity.enums.Status;
import ua.training.hospital.entity.shop.BuyOrder;
import ua.training.hospital.entity.shop.ProductOrder;
import ua.training.hospital.repository.UserRepository;
import ua.training.hospital.repository.shop.BuyOrderRepository;
import ua.training.hospital.service.shop.BuyOrderService;
import ua.training.hospital.service.shop.ProductsOrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@AllArgsConstructor
public class BuyOrderServiceImpl implements BuyOrderService {
    private static final Logger logger = LogManager.getLogger(BuyOrderServiceImpl.class);
    private final BuyOrderRepository buyOrderRepository;
    private final ProductsOrderService productsOrderService;
    private final UserRepository userRepository;

    @Override
    public Optional<BuyOrder> createBuyOrder(BuyOrder productOrder, String userId) {
        final User owner = userRepository.findByEmail(userId);
        if (Objects.nonNull(owner)) {
            productOrder.setOwner(owner);
        }

        return createBuyOrder(productOrder);
    }

    @Override
    public Optional<BuyOrder> createBuyOrder(BuyOrder buyOrder) {

        buyOrder.setDateCreated(LocalDateTime.now());
        buyOrder.setCurrentStatus(Status.CREATED);

        List<ProductOrder> productOrders = buyOrder.getProducts();

        for (int i =0; i < productOrders.size(); i++) {
            ProductOrder productOrder = productOrders.get(i);
            Optional<ProductOrder> repProductOrder = productsOrderService.getProductOrder(productOrder.getProductOrderId());
            if (!repProductOrder.isPresent()) {
                productOrder.setProductOrderId(0);
                Optional<ProductOrder> createdProductOrder = productsOrderService.createProductOrder(productOrder);
                if (createdProductOrder.isPresent()) {
                    productOrders.set(i,(createdProductOrder.get()));
                } else {
                    return Optional.empty();
                }
            }
        }

        try {
            return Optional.of(buyOrderRepository.save(buyOrder));
        } catch (Exception exception) {
            logger.warn(exception.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Page<BuyOrder> getAllBuyOrders(int pageNumber, int requestsPerPage) {
        return buyOrderRepository.findAll( PageRequest.of(pageNumber, requestsPerPage, Sort.by(DESC, "dateCreated")));
    }

    @Override
    public Page<BuyOrder> findBuyOrders(int pageNumber, int requestsPerPage, String userEmail) {
        return buyOrderRepository.getBuyOrdersByOwnerEmail(userEmail, PageRequest.of(pageNumber,requestsPerPage));
    }

    @Override
    public Optional<BuyOrder> findBuyOrder(long orderId) {
        return buyOrderRepository.getBuyOrderByOrderId(orderId);
    }

    @Override
    public Optional<BuyOrder> updateStatus(long id, Status status) {
        Optional<BuyOrder> found = buyOrderRepository.findById(id);
        if(found.isPresent()) {
            BuyOrder update = found.get();
            update.setCurrentStatus(status);
            return Optional.of(buyOrderRepository.save(update));

        }

        return found;
    }
}
