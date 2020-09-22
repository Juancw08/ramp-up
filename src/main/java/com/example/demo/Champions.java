package com.example.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

public class Champions {
    @Value("${API_KEY}")
    private String apikey;

    String champions = "http://ddragon.leagueoflegends.com/cdn/10.19.1/data/en_US/champion.json";

    public Object getChampions() {
        RestTemplate restTemplate = new RestTemplate();

        //setting headers
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("X-Riot-Token", apikey);

        HttpEntity<String> entity = new HttpEntity<String>(headers);
        System.out.println(entity);

        ResponseEntity<Object> response = restTemplate.exchange(champions, HttpMethod.GET, entity, Object.class);

        return response.getBody();
    }


}
