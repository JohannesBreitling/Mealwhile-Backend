package de.johannesbreitling.mealwhile.business.controller;


import de.johannesbreitling.mealwhile.business.model.exceptions.BadRequestException;
import de.johannesbreitling.mealwhile.business.model.requests.event.EventRequest;
import de.johannesbreitling.mealwhile.business.model.requests.event.ParticipantProfileRequest;
import de.johannesbreitling.mealwhile.business.model.responses.query.QueryMode;
import de.johannesbreitling.mealwhile.business.model.responses.query.SuccessfulQueryResponse;
import de.johannesbreitling.mealwhile.business.services.EventService;
import de.johannesbreitling.mealwhile.business.services.interfaces.IEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    private final IEventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("")
    public ResponseEntity<SuccessfulQueryResponse> createRecipe(@RequestBody EventRequest request) {

        if (request == null
                || request.getName() == null
                || request.getDescription() == null
                || request.getStartDate() == null
                || request.getEndDate() == null
        ) {
            throw new BadRequestException("Provide valid arguments to create a event");
        }

        var event = eventService.createEvent(request);
        return ResponseEntity.ok(new SuccessfulQueryResponse("Event", event.getId(), QueryMode.CREATE));
    }

    @PostMapping("/{eventId}/profiles")
    public ResponseEntity<SuccessfulQueryResponse> addParticipantProfile(
            @PathVariable String eventId,
            @RequestBody ParticipantProfileRequest request
    ) {
        if (eventId == null) {
            throw new BadRequestException("Provide a valid id for the event you want to add a profile");
        }

        if (request == null
                || request.getFlags() == null
                || request.getFlags().size() == 0
                || request.getNumber() == 0
        ) {
            throw new BadRequestException("Provide valid arguments to add a profile");
        }

        var profile = eventService.addParticipantProfile(eventId, request);
        return ResponseEntity.ok(new SuccessfulQueryResponse("ParticipantProfile", "Event", profile.getId(), eventId, QueryMode.ADD));
    }

    @DeleteMapping("/{eventId}/profiles/{profileId}")
    public ResponseEntity<SuccessfulQueryResponse> removeParticipantProfile(
            @PathVariable String eventId,
            @PathVariable String profileId
    ) {
        if (eventId == null || profileId == null) {
            throw new BadRequestException("Provide a id for the event you want to update and the profile you want to remove");
        }

        var profile = eventService.removeParticipantProfile(eventId, profileId);
        return ResponseEntity.ok(new SuccessfulQueryResponse("ParticipantProfile", "Event", profileId, eventId, QueryMode.REMOVE));
    }


}
