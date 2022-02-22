package com.chaperones.guide;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class GuideController {

    private GuideService guideService;

    public GuideController(GuideService guideService) {
        this.guideService = guideService;
    }

    @PostMapping("guide")
    public void addGuide(@RequestBody Guide guide){
        guideService.addGuide(guide);
    }
    @RequestMapping("guides")
    public List<Guide> getAllGuide(){
       return guideService.allGuides();
    }
    


}
