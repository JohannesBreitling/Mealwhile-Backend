package de.johannesbreitling.mealwhile.business.model.events;


import de.johannesbreitling.mealwhile.business.model.recipe.Ingredient;
import de.johannesbreitling.mealwhile.business.model.user.UserGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "events")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GenericGenerator(name = "event_id", strategy = "de.johannesbreitling.mealwhile.business.model.generator.MealwhileIdGenerator")
    @GeneratedValue(generator = "event_id")
    private String id;

    private String name;

    @ManyToOne
    private UserGroup accessGroup;

    private String description;

    private String startDate;

    private String endDate;

    @ElementCollection
    private List<ScheduledMeal> meals;

    @OneToMany
    private List<ParticipantProfile> profiles;

    @ElementCollection
    private List<Ingredient> storage;

    public void addParticipantProfile(ParticipantProfile profile) {
        profiles.add(profile);
    }

    public void removeParticipantProfile(ParticipantProfile profile) {
        profiles.remove(profile);
    }

}
