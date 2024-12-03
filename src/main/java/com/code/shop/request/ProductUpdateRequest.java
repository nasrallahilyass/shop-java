package com.code.shop.request;

import com.code.shop.model.Category;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class ProductUpdateRequest {
    /*
        The @Data annotation is provided by Lombok and is a convenient shortcut
        to generate common methods for the class automatically.

        When you annotate a class with @Data, Lombok will generate the following methods:
        1. **Getter and Setter methods**: For each field (e.g., getId(), setId(), getName(), setName(), etc.).
        2. **toString()**: A string representation of the object that includes all field values (e.g., `AddProductRequest{id=1, name='Product A', ...}`).
        3. **equals() and hashCode()**: Implementations of these methods based on the fields of the class, which are useful for comparing objects and storing them in collections like HashMaps or HashSets.
        4. **RequiredArgsConstructor**: A constructor for all fields that are final or marked as `@NonNull`, though not relevant in this case as no fields are marked `final` or `@NonNull`.

        By using @Data, you save time by not having to manually implement these common methods. Lombok takes care of it behind the scenes, making your code more concise and maintainable.
      */
    private String id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
