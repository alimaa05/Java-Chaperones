package com.chaperones.venue;

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
        int result = venueDAO.add(venue);

        if (result != 1) {
            throw new IllegalStateException("Could not add venue...");
        }
    }

    public List<Venue> getAllVenues() {
        return venueDAO.getAll();
    }

    public Venue getVenueById(Integer id) {
        Venue selected = venueDAO.getById(id);
        if (selected == null){
            throw new IllegalStateException("could not find venue..."); //todo VenueNotFoundException?
        } else return selected;
    }

    public void updateVenueById(Integer id, Venue venue) {
        if (venueDAO.getById(id) == null) {
            throw new IllegalStateException("could not find venue...");
        }

        int result = venueDAO.updateById(id, venue);

        if (result != 1) {
            throw new IllegalStateException("Could not update venue...");
        }
    }

    public void deleteVenueById(Integer id) {
        if (venueDAO.getById(id) == null) {
            throw new IllegalStateException("could not find venue...");
        }

        int result = venueDAO.deleteById(id);

        if (result != 1) {
            throw new IllegalStateException("Could not delete venue...");
        }
    }

}
