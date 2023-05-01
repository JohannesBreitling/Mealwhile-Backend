package de.johannesbreitling.mealwhile.business.model.grocery;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;


//@Entity
//@Table(name = "groceries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grocery {

    @Id
    @GenericGenerator(name = "grocery_id", strategy = "de.johannesbreitling.mealwhile.business.model.generator.MealwhileIdGenerator")
    @GeneratedValue(generator = "grocery_id")
    private String id;

    private String name;

    //private List<GroceryFlag> flags;


}
