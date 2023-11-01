package io.risf.sales.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the category that receipt items can have
 */
@Table(name = "category")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Category {
    @Id
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private int taxRatePercent;

}

