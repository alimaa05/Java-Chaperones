package com.chaperones.guide;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class GuideService {
    private GuideDAO guideDAO;

    public GuideService(@Qualifier("guidePostgres") GuideDAO guideDAO) {
        this.guideDAO = guideDAO;
    }

    public int addGuide(Guide guide){
    // check the guide does not already exist

        return 0;
    }

    private Guide guideExist(Integer id) {
        Guide guide = guideDAO.getById(id);
        if(guide == null){
            throw new GuideDoesNotExistException("This guide does not exist");
        }
        return guide;
    }
}
