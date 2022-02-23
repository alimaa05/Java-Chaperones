package com.chaperones.guide;


import com.chaperones.activity.Activity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuideService {
    private GuideDAO guideDAO;

    public GuideService(@Qualifier("guidePostgres") GuideDAO guideDAO) {
        this.guideDAO = guideDAO;
    }

    public int addGuide(Guide guide) {
        // check the guide does not already exist
        //if the guide does not exist we need to add the guide
    // get all guides and loop through them
        List<Guide> guidesList = guideDAO.getAll();
        for (Guide exist : guidesList) {
            //if a guide with the same phone number or email is already in the list of all the guides then the guide already exists. It could be a phone and (&&) email.
            if (exist.getPhoneNumber().equals(guide.getPhoneNumber()) || exist.getEmail().equals(guide.getEmail())) {
                throw new IllegalStateException("Guide already exists");
            }
        }
        //if they do not already exist add the guide
        int adding = guideDAO.add(guide);
        //if the method in the dao does not give a 1 which should be the number of lines affected by adding a guide then user should be alerted that the guide has not been added
        if (adding != 1) {
            throw new IllegalStateException("Guide could not be added");
        }
        // if this is successful we return 1 to indicate 1 guide has been added
        return 1;
    }
    
    public List<Guide> allGuides(){
        //check list is not empty if it is alert user
        List<Guide> guides = guideDAO.getAll();
        if(guides == null){
            throw new IllegalStateException("There are no guides");
        }
        return guides;
    }
   public Guide guideById(Integer id){
       Guide selected = guideDAO.getById(id);
        if (selected == null) {
           throw new GuideDoesNotExistException("This guide does not exist");
       } else return selected;
   }
   public int updateGuide(Integer id, Guide guide){
       //check if the guide exists if they do not throw exception saying so
       Guide guideExist = guideById(id);
       //call on the method to update a guide by their id in the dao,
       // passing through the arguments this method accepts which is
       // the id and the guide information that is to be changed
       int updated = guideDAO.updateById(id, guide);
       // check the confirmation that guide has been updated if not
       // inform user that this has not happened
       if(updated != 1){
           throw new IllegalStateException("Unable to update this guide");
       }
        return updated;
   }
   public int deleteGuide(Integer id){
       //check if the guide exists
       Guide guideExist = guideById(id);
       int deleted = guideDAO.deleteById(id);
       if(deleted != 1){
           throw new IllegalStateException("Unable to delete this guide");
       }
        return deleted;
   }
   //get all activities assigned to a guide
    public List<Activity> guidesActivities(Integer id, boolean cancelled){
        //check if the guide exists
        Guide guideExist = guideById(id);
        return guideDAO.allActivities(id, cancelled);
    }
}
