package de.johannesbreitling.mealwhile.business.model.grocery;


import de.johannesbreitling.mealwhile.business.model.responses.grocery.GroceryFlagResponse;
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

    public GroceryFlagResponse toResponse() {
        return GroceryFlagResponse
                .builder()
                .id(id)
                .name(name)
                .color(color)
                .build();
    }

}
