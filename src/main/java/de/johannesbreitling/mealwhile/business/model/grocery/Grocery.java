package de.johannesbreitling.mealwhile.business.model.grocery;

import de.johannesbreitling.mealwhile.business.model.responses.grocery.GroceryResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "groceries")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grocery {

    @Id
    @GenericGenerator(name = "grocery_id", strategy = "de.johannesbreitling.mealwhile.business.model.generator.MealwhileIdGenerator")
    @GeneratedValue(generator = "grocery_id")
    private String id;

    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private GroceryUnit unit;

    @ManyToMany
    private List<GroceryFlag> flags;

    public GroceryResponse toResponse() {

        var convertedFlags = flags
                .stream()
                .map(
                        flag -> flag.toResponse()
                )
                .toList();

        return GroceryResponse
                .builder()
                .id(id)
                .name(name)
                .defaultUnit(unit.toString())
                .flags(convertedFlags)
                .build();
    }

}
