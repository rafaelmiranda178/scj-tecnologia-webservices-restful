package fiap.scj.modulo1.application;

import fiap.scj.modulo1.domain.ProductDetails;
import fiap.scj.modulo1.infrastructure.ProductServiceException;

import java.io.Serializable;
import java.util.List;

public interface ProductDetailsService extends Serializable {

    List<ProductDetails> search(String keyword) throws ProductServiceException;

    ProductDetails create(ProductDetails product) throws ProductServiceException;

    ProductDetails retrieve(Long id) throws ProductServiceException;

    ProductDetails update(Long id, ProductDetails productDetails) throws ProductServiceException;

    void delete(Long id) throws ProductServiceException;
}
