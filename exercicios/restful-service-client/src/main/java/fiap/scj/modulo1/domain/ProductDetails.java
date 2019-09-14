package fiap.scj.modulo1.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetails implements Serializable {

    private Long id;
    private String key;
    private String description;

    private Product product;

}
