package ua.training.hospital.controller.shop;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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


    @RequestMapping(value = "/shop/orders/{pageNumber}", method = RequestMethod.GET)
    public String getOrders(@PathVariable(required = false) int pageNumber,
                            @RequestParam(defaultValue = "10", required = false) int recordsPerPage,
                            Principal principal,
                            Model model) {

        Page<BuyOrder> orderList = orderService.findBuyOrders(pageNumber, recordsPerPage, principal.getName());
        setModelparams(model, orderList);

        model.addAttribute("pageName", "orders");

        return "shop/shopOrders";
    }

    @PreAuthorize("hasAnyRole('SHOP_WORKER')")
    @RequestMapping(value = "/shop/allOrders/{pageNumber}", method = RequestMethod.GET)
    public String getAllOrders(@PathVariable(required = false) int pageNumber,
                               @RequestParam(defaultValue = "10", required = false) int recordsPerPage,
                               Model model) {


        Page<BuyOrder> orderList = orderService.getAllBuyOrders(pageNumber, recordsPerPage);
        setModelparams(model, orderList);

        model.addAttribute("pageName", "allOrders");

        return "shop/shopOrders";
    }

    private static void setModelparams(Model model, Page<BuyOrder> orderList) {
        Page<Object> DTOs = orderList
                .map(order -> {
                    String shortList = order.getProducts()
                            .stream()
                            .map(productOrder -> {
                                return productOrder.getProduct().getName();
                            }).collect(Collectors.joining(", "));

                    if (shortList.length() > 100) {
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
                });


        model.addAttribute("page", DTOs);
    }
}
