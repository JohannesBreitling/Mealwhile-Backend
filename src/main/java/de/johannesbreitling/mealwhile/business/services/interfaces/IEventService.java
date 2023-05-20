package de.johannesbreitling.mealwhile.business.services.interfaces;

import de.johannesbreitling.mealwhile.business.model.events.Event;
import de.johannesbreitling.mealwhile.business.model.events.ParticipantProfile;
import de.johannesbreitling.mealwhile.business.model.events.ScheduledMeal;
import de.johannesbreitling.mealwhile.business.model.requests.event.EventRequest;
import de.johannesbreitling.mealwhile.business.model.requests.event.ParticipantProfileRequest;
import de.johannesbreitling.mealwhile.business.model.requests.event.ScheduledMealRequest;

public interface IEventService {

    Event createEvent(EventRequest request);
    ParticipantProfile addParticipantProfile(String eventId, ParticipantProfileRequest request);
    ParticipantProfile removeParticipantProfile(String eventId, String profileId);

    ScheduledMeal addScheduledMeal(String eventId, ScheduledMealRequest request);
    ScheduledMeal removeScheduledMeal(String eventId, ScheduledMealRequest request);

}
