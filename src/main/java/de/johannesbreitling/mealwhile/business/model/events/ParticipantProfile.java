package de.johannesbreitling.mealwhile.business.model.events;

import de.johannesbreitling.mealwhile.business.model.grocery.GroceryFlag;
import de.johannesbreitling.mealwhile.business.model.responses.event.ParticipantProfileResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "participant_profiles")
@Data
public class ParticipantProfile {

    @Id
    @GenericGenerator(name = "profile_id", strategy = "de.johannesbreitling.mealwhile.business.model.generator.MealwhileIdGenerator")
    @GeneratedValue(generator = "profile_id")
    private String id;

    @ManyToOne
    private Event event;

    @ManyToMany
    private List<GroceryFlag> flags;

    private int number;

    private int factor;

    public ParticipantProfileResponse toResponse() {
        var flagResponses = flags.stream().map(flag -> flag.toResponse()).toList();

        return ParticipantProfileResponse
                .builder()
                .number(number)
                .flags(flagResponses)
                .build();
    }

}
