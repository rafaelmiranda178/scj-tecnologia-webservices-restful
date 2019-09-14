package fiap.scj.modulo1.interfaces.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;
import fiap.scj.modulo1.domain.ProductDetails;

import java.util.List;

@Headers({"Content-Type: application/json", "Accept: application/json"})
public interface ProductDetailsResourceClient {

    @RequestLine("GET /products_details")
    List<ProductDetails> search();

    @RequestLine("GET /products_details?keyword={keyword}")
    List<ProductDetails> search(@Param("keyword") String keyword);

    @RequestLine("POST /products_details")
    Response create(ProductDetails productDetails);

    @RequestLine("GET /products_details/{id}")
    ProductDetails retrieve(@Param("id") Long id);

    @RequestLine("PUT /products_details/{id}")
    ProductDetails update(@Param("id") Long id, ProductDetails productDetails);

    @RequestLine("DELETE /products_details/{id}")
    Response delete(@Param("id") Long id);

}
