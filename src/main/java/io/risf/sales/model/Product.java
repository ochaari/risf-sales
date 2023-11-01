package io.risf.sales.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contains the products for sale along with their category
 */
@Table(name = "product")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Product {
    @Id
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @ManyToOne
    @NotNull
    private Category category;

}
