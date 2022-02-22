package com.chaperones.venue;

import com.chaperones.activity.Activity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class VenueController {

    private VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @PostMapping(path = "venues")
    public void addNewVenue(@RequestBody Venue venue){
        venueService.addNewVenue(venue);
    }

    @GetMapping(path = "venues")
    public List<Venue> getAllVenues() {
        return venueService.getAllVenues();
    }

    @GetMapping(path = "venues/{id}")
    public Venue getVenueById(@PathVariable("id") Integer id) {
        return venueService.getVenueById(id);
    }

    @GetMapping(path = "venues/{id}/activities")
    public List<Activity> getAllActivitiesAtVenue(@PathVariable("id") Integer id) {
        List<Activity> allActivities = new ArrayList<>();
        allActivities.addAll(venueService.getActivitiesAtVenue(id, false));
        allActivities.addAll(venueService.getActivitiesAtVenue(id, true));
        return allActivities;
    }

    @GetMapping(path = "venues/{id}/activities/open")
    public List<Activity> getOpenActivitiesAtVenue(@PathVariable("id") Integer id) {
        return venueService.getActivitiesAtVenue(id, false);
    }

    @GetMapping(path = "venues/{id}/activities/cancelled")
    public List<Activity> getCancelledActivitiesAtVenue(@PathVariable("id") Integer id) {
        return venueService.getActivitiesAtVenue(id, true);
    }

    @PutMapping(path = "venues/{id}")
    public void updateVenueById(@PathVariable("id") Integer id, @RequestBody Venue update) {
        venueService.updateVenueById(id, update);
    }

    @DeleteMapping(path = "venues/{id}")
    public void deleteVenueById(@PathVariable("id") Integer id) {
        venueService.deleteVenueById(id);
    }

}
