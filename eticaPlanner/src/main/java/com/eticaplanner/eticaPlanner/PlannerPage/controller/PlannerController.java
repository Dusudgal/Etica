package com.eticaplanner.eticaPlanner.PlannerPage.controller;

import com.eticaplanner.eticaPlanner.PlannerPage.dto.PlannerDTO;
import com.eticaplanner.eticaPlanner.PlannerPage.service.PlannerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/Planner")
public class PlannerController {

    private ModelAndView mav;
    private final PlannerService planService;
    private ApiComponent apikeys;
    Map<String , String> PlannerOk;

    @Autowired
    public PlannerController(PlannerService planService, ApiComponent apikeys ) {
        this.planService = planService;
        this.apikeys = apikeys;
    }

    @GetMapping("PlannerPage")
    public ModelAndView plannerPage(){
        System.out.println("[PlannerController] PlannerPage");
        mav = new ModelAndView("Planner/PlannerPage");
        String map_key = apikeys.map_apikey();
        mav.addObject("map_key" , map_key);
        return mav;
    }

    @PostMapping("PlannerSaveData")
    public Map<String , String> plannerSaveData(@RequestBody PlannerDTO planDto){
        System.out.println("[PlannerController] PlannerSaveData");
        PlannerOk = new HashMap<>();

        Boolean CreateResult = planService.planCreate(planDto);

        System.out.println(CreateResult ? "성공" : "실패");
        PlannerOk.put("Message" , "Ok");
        return PlannerOk ;
    }

    @PostMapping("TourApiSearch")
    public ResponseEntity<Map> tourApiSearch(@RequestBody Map<String, String> encodingdata){
        System.out.println("[PlannerController] tourApiSearch");

        RestTemplate restTemplate = new RestTemplate();

        // api 호출시 받아올때 빈맵일수 있으니 초기화를 Map.of()로 한것
        Map response = Map.of();

        String Tour_key = apikeys.tour_apikey();
        int PageNumber = 1;
        int numOfRows = 30;
        String keyword = encodingdata.get("keyword");
        String url = String.format("https://apis.data.go.kr/B551011/KorService1/searchKeyword1?numOfRows=%s&pageNo=%s&MobileOS=ETC&MobileApp=etica&_type=json&listYN=Y&arrange=A&keyword=%s&serviceKey=%s",
                numOfRows, PageNumber , keyword , Tour_key);
        try{
            URI uri = new URI(url);
            response = restTemplate.getForObject(uri , Map.class);
        }catch(URISyntaxException uriException){
            System.out.println(uriException.getMessage());
        }

        System.out.println(response);
        return ResponseEntity.ok(response);
    }
}
