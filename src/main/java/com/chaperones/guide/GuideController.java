package com.chaperones.guide;

import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("guide/{id}")
    public Guide getGuideById(@PathVariable("id") Integer guideId){
       return guideService.guideById(guideId);
    }

}
