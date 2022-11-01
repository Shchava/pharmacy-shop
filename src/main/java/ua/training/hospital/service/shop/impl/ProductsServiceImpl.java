package ua.training.hospital.service.shop.impl;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.training.hospital.entity.shop.Product;
import ua.training.hospital.repository.shop.ProductsRepository;
import ua.training.hospital.service.shop.ProductsService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductsServiceImpl implements ProductsService {
    private static final Logger logger = LogManager.getLogger(ProductsServiceImpl.class);

    private final ProductsRepository repository;

    @Override
    public Optional<Product> getProduct(long productId) {
        logger.debug("searching for HelpRequest with id: " + productId);
        return repository.findById(productId);
    }

    @Override
    public Page<Product> getAllProducts(int pageNumber, int requestsPerPage) {
        logger.debug("searching for products from page " + pageNumber + " with " + requestsPerPage + "entries on page");
        return repository.findAll(PageRequest.of(pageNumber,requestsPerPage));
    }

    @Override
    public Page<Product> getAllProductsWithTitleContaining(int pageNumber, int requestsPerPage, String nameSearch) {
        return repository.findByNameContaining(nameSearch, PageRequest.of(pageNumber,requestsPerPage));
    }
}
