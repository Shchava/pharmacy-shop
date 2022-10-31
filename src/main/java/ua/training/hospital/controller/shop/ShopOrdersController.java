package ua.training.hospital.controller.shop;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.training.hospital.controller.dto.ShowOrderDto;
import ua.training.hospital.entity.shop.BuyOrder;
import ua.training.hospital.service.shop.BuyOrderService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
@AllArgsConstructor
public class ShopOrdersController {
    BuyOrderService orderService;
//    Order cartService;

    @RequestMapping(value = "/shop/orders", method = RequestMethod.GET)
    public String getOrders(Principal principal,
                            Model model) {

        List<BuyOrder> orderList =  orderService.findBuyOrders(principal.getName());
        List<ShowOrderDto> DTOs = orderList.stream()
                .map(order -> {
                    String shortList = order.getProducts()
                            .stream()
                            .map(productOrder -> {
                                return productOrder.getProduct().getName();
                            }).collect( Collectors.joining( ", " ) );

                    if(shortList.length() > 100) {
                        shortList = shortList.substring(0, 100) + "...";
                    }

                    int totalPrice = order.getProducts().stream()
                            .mapToInt(request -> request.getCount() * request.getProduct().getPrice())
                            .sum();

                    return ShowOrderDto
                            .builder()
                            .orderId(order.getOrderId())
                            .name(order.getName())
                            .address(order.getAddress())
                            .currentStatus(order.getCurrentStatus().name())
                            .totalPrice(totalPrice)
                            .shortListOfProducts(shortList)
                            .build();
                })
                .collect(Collectors.toList());


        model.addAttribute("orders", DTOs);

        return "shop/shopOrders";
    }
}
