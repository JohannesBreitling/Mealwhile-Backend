package de.johannesbreitling.mealwhile.business.services;

import de.johannesbreitling.mealwhile.business.model.events.LinkedRecipe;
import de.johannesbreitling.mealwhile.business.model.events.ParticipantProfile;
import de.johannesbreitling.mealwhile.business.model.events.ScheduledMeal;
import de.johannesbreitling.mealwhile.business.model.events.eventdate.EventDate;
import de.johannesbreitling.mealwhile.business.model.events.eventdate.IllegalEventDateFormatException;
import de.johannesbreitling.mealwhile.business.model.exceptions.AccessNotAllowedException;
import de.johannesbreitling.mealwhile.business.model.exceptions.BadRequestException;
import de.johannesbreitling.mealwhile.business.model.exceptions.EntityNotFoundException;
import de.johannesbreitling.mealwhile.business.model.grocery.GroceryFlag;
import de.johannesbreitling.mealwhile.business.model.requests.event.EventRequest;
import de.johannesbreitling.mealwhile.business.model.requests.event.LinkedRecipeRequest;
import de.johannesbreitling.mealwhile.business.model.requests.event.ParticipantProfileRequest;
import de.johannesbreitling.mealwhile.business.model.requests.event.ScheduledMealRequest;
import de.johannesbreitling.mealwhile.business.repositories.EventRepository;
import de.johannesbreitling.mealwhile.business.repositories.ParticipantProfileRepository;
import de.johannesbreitling.mealwhile.business.repositories.ScheduledMealRepository;
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
    private final ScheduledMealRepository scheduledMealRepository;

    public EventService(
            AdminService userService,
            EventRepository eventRepository,
            GroceryService groceryService,
            RecipeService recipeService,
            ParticipantProfileRepository participantProfileRepository,
            ScheduledMealRepository scheduledMealRepository) {
        this.userService = userService;
        this.eventRepository = eventRepository;
        this.groceryService = groceryService;
        this.recipeService = recipeService;
        this.participantProfileRepository = participantProfileRepository;
        this.scheduledMealRepository = scheduledMealRepository;
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

    private ParticipantProfile validateUserProfileAccess(String profileId, Event event) {
        var foundProfile = participantProfileRepository.findParticipantProfileById(profileId);

        if (foundProfile.isEmpty()) {
            throw new EntityNotFoundException("ParticipantProfile with id " + profileId);
        }

        var profile = foundProfile.get();

        if (!event.getId().equals(profile.getEvent().getId())) {
            throw new AccessNotAllowedException("ParticipantProfile with id " + profileId + " is not known for event with id " + event.getId());
        }

        return profile;
    }

    private ParticipantProfile convertRequestToParticipantProfile(Event event, ParticipantProfileRequest request) {
        List<GroceryFlag> flags = new ArrayList<>();

        for (String flagId : request.getFlags()) {
            var flag = groceryService.getGroceryFlagById(flagId);
            flags.add(flag);
        }

        return ParticipantProfile
                .builder()
                .event(event)
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
        var newProfile = convertRequestToParticipantProfile(event, request);
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
        var date = new EventDate(request.getDate());

        List<LinkedRecipe> linkedRecipes = new ArrayList<>();

        for (LinkedRecipeRequest recipeRequest : request.getRecipes()) {
            // VALIDATE USER PROFILE
            var userProfile = validateUserProfileAccess(recipeRequest.getProfileId(), event);
            var recipe = recipeService.validateRecipeAccess(recipeRequest.getRecipeId());

            var linkedRecipe = new LinkedRecipe(recipe, userProfile);
            linkedRecipes.add(linkedRecipe);
        }

        var meal = ScheduledMeal
                .builder()
                .meals(linkedRecipes)
                .date(date)
                .build();

        scheduledMealRepository.save(meal);
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
