package ua.training.hospital.controller.shop;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.training.hospital.entity.UserAuthentication;
import ua.training.hospital.entity.shop.BuyOrder;
import ua.training.hospital.service.shop.BuyOrderService;

import java.security.Principal;
import java.util.Optional;

@Controller
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
@AllArgsConstructor
public class ShopOrderPageController {
    private static final Logger logger = LogManager.getLogger(ShopOrderPageController.class);

    private BuyOrderService buyOrderService;

    @RequestMapping(value = "/shop/order/{orderId}", method = RequestMethod.GET)
    public String showHelpRequest(@PathVariable long orderId,
                                  Model model, Authentication authentication) {

        Optional<BuyOrder> buyOrder = buyOrderService.findBuyOrder(orderId);


        if (!buyOrder.isPresent()) {
            return "pageNotFound";
        }

        if (!(buyOrder.get().getOwner().getIdUser() == ((UserAuthentication)  authentication.getPrincipal()).getId()) &&
                authentication.getAuthorities().stream().map(
                        f-> f.getAuthority()).noneMatch(f -> f.equals("ROLE_SHOP_WORKER"))) {
            return "pageNotFound";
        }

        logger.debug("requested /shop/product/");


        logger.debug("returning shop/showProduct.jsp page");

        int totalPrice = buyOrder.get().getProducts().stream()
                .mapToInt(request -> request.getCount() * request.getProduct().getPrice())
                .sum();

        model.addAttribute("totalPrice", totalPrice);

        buyOrder.ifPresent(order -> model.addAttribute("order", order));

        return "shop/showOrder";
    }
}
