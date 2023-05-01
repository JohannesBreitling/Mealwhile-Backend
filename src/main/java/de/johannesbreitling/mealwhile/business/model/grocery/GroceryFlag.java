package de.johannesbreitling.mealwhile.business.model.grocery;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "grocery_flags")
public class GroceryFlag {

    @Id
    @GenericGenerator(name = "id_generator", strategy = "de.johannesbreitling.mealwhile.business.model.generator.MealwhileIdGenerator")
    @GeneratedValue(generator = "id_generator")
    private String id;

    @Column(unique = true)
    private String name;

    private String color;
}
