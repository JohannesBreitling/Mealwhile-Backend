package de.johannesbreitling.mealwhile.business.model.user;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "user_groups")
@Data
public class UserGroup {

    @Id
    @GenericGenerator(name = "group_id", strategy = "de.johannesbreitling.mealwhile.business.model.generator.MealwhileIdGenerator")
    @GeneratedValue(generator = "group_id")
    private String groupId;

    @Column(unique = true)
    private String groupName;

    private String color;

}
