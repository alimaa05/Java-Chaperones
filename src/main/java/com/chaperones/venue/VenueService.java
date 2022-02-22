package com.chaperones.venue;

import com.chaperones.activity.Activity;
import com.chaperones.user.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {

    private VenueDAO venueDAO;

    public VenueService(@Qualifier("venuePostgres") VenueDAO venueDAO) {
        this.venueDAO = venueDAO;
    }

    public void addNewVenue(Venue venue) {
        List<Venue> getAllVenues = venueDAO.getAll();
        for (Venue getVenueById : getAllVenues) {
            if (getVenueById.getName().equals(venue.getName())) {
                throw new IllegalStateException("Venue already exists");
            }

            int result = venueDAO.add(venue);

            if (result != 1) {
                throw new IllegalStateException("Venue could not be added");
            }
        }
    }

    public List<Venue> getAllVenues() {
        return venueDAO.getAll();
    }

    public List<Activity> getAllActivitiesAtVenue(Integer id) {
        if (venueDAO.getById(id) == null) {
            throw new VenueNotFoundException("Venue with id " + id + " could not be found");
        }

        return venueDAO.getAllActivities(id);
    }

    public Venue getVenueById(Integer id) {
        Venue selected = venueDAO.getById(id);
        if (selected == null){
            throw new VenueNotFoundException("Venues could not be found");
        } else return selected;
    }

    public void updateVenueById(Integer id, Venue venue) {
        if (venueDAO.getById(id) == null) {
            throw new VenueNotFoundException("Venue with id " + id + " could not be found");
        }

        int result = venueDAO.updateById(id, venue);

        if (result != 1) {
            throw new IllegalStateException("Venue with id " + id + " could not be updated");
        }
    }

    public void deleteVenueById(Integer id) {
        if (venueDAO.getById(id) == null) {
            throw new VenueNotFoundException("Venue with id " + id + " could not be found");
        }

        int result = venueDAO.deleteById(id);

        if (result != 1) {
            throw new IllegalStateException("Venue with id " + id + " could not be deleted");
        }
    }
}
