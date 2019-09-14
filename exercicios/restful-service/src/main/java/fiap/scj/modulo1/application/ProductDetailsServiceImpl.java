package fiap.scj.modulo1.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import fiap.scj.modulo1.domain.ProductDetails;
import fiap.scj.modulo1.domain.repository.ProductDetailsRepository;
import fiap.scj.modulo1.domain.repository.ProductRepository;
import fiap.scj.modulo1.infrastructure.ProductServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static fiap.scj.modulo1.infrastructure.ProductServiceException.*;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@Slf4j
public class ProductDetailsServiceImpl implements ProductDetailsService {

    private final ProductDetailsRepository repository;

    private final ObjectMapper objectMapper;

    @Autowired
    public ProductDetailsServiceImpl(ProductDetailsRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<ProductDetails> search(String keyword) throws ProductServiceException {
        log.info("Searching products for keyword={}", keyword);
        try {
            List<ProductDetails> result = new ArrayList<>();

            if (keyword == null || keyword.isEmpty()) {
                log.debug("No keyword specified, listing all products");
                result.addAll(repository.findAll());
            } else {
                log.debug("Finding products by name or description");
                result.addAll(repository.findByDescriptionAllIgnoreCase(keyword));
            }

            return result;
        } catch (Exception e) {
            log.error("Error searching product", e);
            throw new ProductServiceException(SEARCH_OPERATION_ERROR, e);
        }
    }

    @Override
    public ProductDetails create(ProductDetails product) throws ProductServiceException {
        log.info("Creating product ({})", product);
        try {
            if (product == null) {
                log.error("Invalid product");
                throw new ProductServiceException(INVALID_PARAMETER_ERROR, null);
            }
            ProductDetails result = repository.save(product);
            return result;
        } catch (Exception e) {
            log.error("Error creating product", e);
            throw new ProductServiceException(CREATE_OPERATION_ERROR, e);
        }
    }

    @Override
    public ProductDetails retrieve(Long id) throws ProductServiceException {
        log.info("Retrieving product for id={}", id);
        try {
            if (id == null) {
                log.error("Invalid id");
                throw new ProductServiceException(INVALID_PARAMETER_ERROR, null);
            }
            ProductDetails result = repository.findById(id).get();
            return result;
        } catch (Exception e) {
            log.error("Error creating product", e);
            throw new ProductServiceException(RETRIEVE_OPERATION_ERROR, e);
        }
    }

    @Override
    public ProductDetails update(Long id, ProductDetails product) throws ProductServiceException {
        log.info("Updating product ({}) for id={}", product, id);
        try {
            if (id == null || product == null) {
                log.error("Invalid id or product");
                throw new ProductServiceException(INVALID_PARAMETER_ERROR, null);
            }
            if (!repository.existsById(id)) {
                log.debug("Product not found for id={}", id);
                throw new ProductServiceException(PRODUCT_NOT_FOUND_ERROR, null);
            }
            ProductDetails result = repository.save(product);
            return result;
        } catch (Exception e) {
            log.error("Error creating product", e);
            throw new ProductServiceException(RETRIEVE_OPERATION_ERROR, e);
        }
    }

    @Override
    public void delete(Long id) throws ProductServiceException {
        log.info("Deleting product for id={}", id);
        try {
            if (id == null) {
                log.error("Invalid id or product");
                throw new ProductServiceException(INVALID_PARAMETER_ERROR, null);
            }
            if (!repository.existsById(id)) {
                log.debug("Product not found for id={}", id);
                throw new ProductServiceException(PRODUCT_NOT_FOUND_ERROR, null);
            }
            repository.deleteById(id);
        } catch (Exception e) {
            log.error("Error creating product", e);
            throw new ProductServiceException(DELETE_OPERATION_ERROR, e);
        }
    }
}
