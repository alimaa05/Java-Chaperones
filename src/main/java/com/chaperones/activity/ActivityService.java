package com.chaperones.activity;

import com.chaperones.guide.Guide;
import com.chaperones.user.User;
import com.chaperones.venue.Venue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ActivityService {

    private ActivityDAO activityDAO;

    public ActivityService(@Qualifier("activitiesPostgres") ActivityDAO activityDAO) {
        this.activityDAO = activityDAO;
    }

    // Private method to check whether an activity already exists
    private Activity getExistingActivityOrThrowException(Integer id) {
        Activity activity = activityDAO.getById(id);
        // if it doesn't exist then throw exception
        if (activity == null) {
            throw new ActivityDoesNotExistException("Sorry, activity with id " + id + " does not exist");

        }
        return activity;
    }


    // ----------------------------------------------------------

    // Method to get all the activities

    public List<Activity> getAllActivities() {
        return activityDAO.getAll();
    }

    // ----------------------------------------------------------

    // Method to add a new activity
    public void addNewActivity(Activity activity) {
        // check if this activity already exists

        // Creating a list of activities called checkActivities
        // = to getAllActivities that's equal to the method above and will return list of all activities
        // Can’t call a method and then equal another method - need to create a placeholder i.e. 'checkAvailability'
        List<Activity> checkActivity = getAllActivities();

        // for loop --> loop through the list checkedActivities and storing it in the placeholder 'currentActivity'
        for (Activity currentActivity : checkActivity) {
            // throw exception if 'currentActivity' is equal to the activity passed
            if (currentActivity.equals(activity)) {
                throw new IllegalStateException("Sorry, this activity already exists. Please try again!");
            }
        }

        int newActivity = activityDAO.add(activity);

        if (newActivity != 1) {
            throw new IllegalStateException("Sorry, this activity could not be added");
        }
    }

    // ----------------------------------------------------------

    // Method to get an activity by id

    public Activity getActivityById(Integer id) {

        // check if the activity exists
        Activity activity = activityDAO.getById(id);
        // if it doesn't exist then throw exception
        if (activity == null) {
            throw new ActivityDoesNotExistException("Sorry, activity with id " + id + " does not exist");

        }


        return activity;

    }

    // ----------------------------------------------------------

    // Method to update an activity by id

    public void updateActivityById(Integer id, Activity activity) {

        // if it doesn't exist then throw exception
        if (activityDAO.getById(id) == null) {
            throw new ActivityDoesNotExistException("Sorry, activity with id " + id + " does not exist");
        }

        int updateId = activityDAO.updateById(id, activity);

        if (updateId != 1) {
            throw new IllegalStateException("Sorry, activity with id " + id + " could not be updated");
        }

    }

    // ----------------------------------------------------------


    // Method to delete an activity by id

    public void deleteActivityById(Integer deleteId) {

        if (activityDAO.getById(deleteId) == null) {
            throw new ActivityDoesNotExistException("Sorry, activity with id " + deleteId + " does not exist");
        }

        int deleteActivity = activityDAO.deleteById(deleteId);

        if (deleteActivity != 1) {
            throw new IllegalStateException("Sorry, activity with id " + deleteId + " could not be deleted");
        }

    }

    // ----------------------------------------------------------

    // Method to get all the users booked on a given activity

    // want to return a list of all the users
    public List<User> getAllUsersFromGivenActivity(Integer id){

        // check if the activity exists
        if (activityDAO.getById(id) == null) {
            throw new ActivityDoesNotExistException("Sorry, activity with id " + id + " does not exist");
        }

        // return a list of users called 'allUsersFromActivity' which is equal to the activityDAO method 'getAllUsersFromGivenActivity' taking in the id we pass
        List<User> allUsersFromActivity = activityDAO.getAllUsersFromGivenActivity(id);
        return allUsersFromActivity;

    }


    // ----------------------------------------------------------
    //  Method to check if activity has free spaces

    public int getFreeSpaces(Integer id){


        Activity activity = activityDAO.getById(id);

        if (activity == null) {
            throw new ActivityDoesNotExistException("Sorry, activity with id " + id + " does not exist");

        }

        return activity.getCapacity() - activityDAO.getNumberOfBookings(id);

    }

}
