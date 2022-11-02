package ua.training.hospital.controller.shop;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.training.hospital.entity.shop.Product;
import ua.training.hospital.service.shop.ProductsService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
@AllArgsConstructor
public class ShopPageController {
    private static final Logger logger = LogManager.getLogger(ShopPageController.class);

    private ProductsService productsService;

    @RequestMapping(value = "/shop/{pageNumber}", method = RequestMethod.GET)
    public String defaultShowPatient(@PathVariable(required = false) int pageNumber,
                                     @RequestParam(defaultValue = "10", required = false) int recordsPerPage,
                                     @RequestParam(defaultValue = "", required = false) String nameSearch,
                                     Model model) {

        logger.debug("requested /shop");

        Page<Product> page = productsService.getAllProductsWithTitleContaining(pageNumber, recordsPerPage, nameSearch);
        model.addAttribute("searchVal", nameSearch);
        model.addAttribute("page", page);

        logger.debug("returning shop/shopPage.jsp page");
        return "shop/shopPage";
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @ResponseBody
    @RequestMapping(value = "/shop/medicinefillhelper", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> defaultShowPatient(@RequestParam(defaultValue = "", required = true) String query,
                                     Model model) {

        logger.debug("requested /shop/medicinefillhelper");

        List<Product> found = productsService.getAllProductsWithTitleContaining(query);

        return found.stream().map(Product::getName).collect(Collectors.toList());
    }
}
