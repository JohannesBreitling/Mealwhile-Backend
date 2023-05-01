package de.johannesbreitling.mealwhile.business.model.user;

import de.johannesbreitling.mealwhile.business.model.generator.MealwhileIdGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "user_groups")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGroup {

    @Id
    @GenericGenerator(
            name = "group_id",
            strategy = "de.johannesbreitling.mealwhile.business.model.generator.MealwhileIdGenerator"
    )
    @GeneratedValue(generator = "group_id")
    private String id;

    @Column(unique = true)
    private String name;

    private String color;

}
