package de.johannesbreitling.mealwhile.business.services;

import de.johannesbreitling.mealwhile.business.model.events.ParticipantProfile;
import de.johannesbreitling.mealwhile.business.model.events.ScheduledMeal;
import de.johannesbreitling.mealwhile.business.model.events.eventdate.EventDate;
import de.johannesbreitling.mealwhile.business.model.events.eventdate.IllegalEventDateFormatException;
import de.johannesbreitling.mealwhile.business.model.exceptions.AccessNotAllowedException;
import de.johannesbreitling.mealwhile.business.model.exceptions.BadRequestException;
import de.johannesbreitling.mealwhile.business.model.exceptions.EntityNotFoundException;
import de.johannesbreitling.mealwhile.business.model.grocery.GroceryFlag;
import de.johannesbreitling.mealwhile.business.model.requests.event.EventRequest;
import de.johannesbreitling.mealwhile.business.model.requests.event.ParticipantProfileRequest;
import de.johannesbreitling.mealwhile.business.model.requests.event.ScheduledMealRequest;
import de.johannesbreitling.mealwhile.business.repositories.EventRepository;
import de.johannesbreitling.mealwhile.business.repositories.ParticipantProfileRepository;
import de.johannesbreitling.mealwhile.business.services.interfaces.IAdminService;
import de.johannesbreitling.mealwhile.business.services.interfaces.IEventService;
import de.johannesbreitling.mealwhile.business.model.events.Event;
import de.johannesbreitling.mealwhile.business.services.interfaces.IGroceryService;
import de.johannesbreitling.mealwhile.business.services.interfaces.IRecipeService;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService implements IEventService {

    private final IAdminService userService;
    private final IGroceryService groceryService;
    private final IRecipeService recipeService;
    private final EventRepository eventRepository;
    private final ParticipantProfileRepository participantProfileRepository;

    public EventService(
            AdminService userService,
            EventRepository eventRepository,
            GroceryService groceryService,
            RecipeService recipeService,
            ParticipantProfileRepository participantProfileRepository
    ) {
        this.userService = userService;
        this.eventRepository = eventRepository;
        this.groceryService = groceryService;
        this.recipeService = recipeService;
        this.participantProfileRepository = participantProfileRepository;
    }

    private Event validateEventAccess(String eventId) {
        var event = eventRepository.findEventById(eventId);

        if (event.isEmpty()) {
            throw new EntityNotFoundException("Event with id " + eventId);
        }

        var userGroup = userService.getUserGroupFromToken();
        if (!event.get().getAccessGroup().equals(userGroup)) {
            throw new AccessNotAllowedException("Event with id " + eventId + "not known for group of the user");
        }

        return event.get();
    }

    private ParticipantProfile convertRequestToParticipantProfile(ParticipantProfileRequest request) {
        List<GroceryFlag> flags = new ArrayList<>();

        for (String flagId : request.getFlags()) {
            var flag = groceryService.getGroceryFlagById(flagId);
            flags.add(flag);
        }

        return ParticipantProfile
                .builder()
                .flags(flags)
                .number(request.getNumber())
                .build();
    }

    @Override
    public Event createEvent(EventRequest request) {
        var group = userService.getUserGroupFromToken();

        try {
            var event = Event
                    .builder()
                    .name(request.getName())
                    .accessGroup(group)
                    .description(request.getDescription())
                    .startDate(new EventDate(request.getStartDate()).toString())
                    .endDate(new EventDate(request.getEndDate()).toString())
                    .meals(new ArrayList<>())
                    .profiles(new ArrayList<>())
                    .storage(new ArrayList<>())
                    .build();

            eventRepository.save(event);

            return event;
        } catch (IllegalEventDateFormatException e) {
            throw new BadRequestException("Illegal format for event date " + request.getEndDate() + " " + request.getEndDate());
        }
    }

    @Override
    public ParticipantProfile addParticipantProfile(String eventId, ParticipantProfileRequest request) {
        var event = validateEventAccess(eventId);
        var newProfile = convertRequestToParticipantProfile(request);
        participantProfileRepository.save(newProfile);
        event.addParticipantProfile(newProfile);
        eventRepository.save(event);

        return newProfile;
    }

    @Override
    public ParticipantProfile removeParticipantProfile(String eventId, String profileId) {
        var event = validateEventAccess(eventId);
        var foundProfile = participantProfileRepository.findParticipantProfileById(profileId);

        if (foundProfile.isEmpty()) {
            throw new EntityNotFoundException("ParticipantProfile with id " + profileId);
        }

        var profile = foundProfile.get();

        event.removeParticipantProfile(profile);
        participantProfileRepository.delete(profile);
        eventRepository.save(event);

        return profile;
    }

    @Override
    public ScheduledMeal addScheduledMeal(String eventId, ScheduledMealRequest request) {
        var event = validateEventAccess(eventId);
        var recipe = recipeService.validateRecipeAccess(request.getRecipeId());
        var date = new EventDate(request.getDate());

        var meal = ScheduledMeal
                .builder()
                .meal(recipe)
                .date(date)
                .build();

        event.addScheduledMeal(meal);
        eventRepository.save(event);

        return meal;
    }

    @Override
    public ScheduledMeal removeScheduledMeal(String eventId, ScheduledMealRequest request) {
        var event = validateEventAccess(eventId);

        throw new NotYetImplementedException();
    }

}
