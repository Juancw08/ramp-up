package com.example.demo.resources;
import com.example.demo.Champions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
//@RequestMapping("/team")
public class Team {
    @Value("${API_KEY}")
    private String apikey;

    String riot_url = "https://na1.api.riotgames.com";
    String champion_rot = "/lol/platform/v3/champion-rotations";
    String summ_name_url = "/lol/summoner/v4/summoners/by-name/";
    String champ_mastery = "/lol/champion-mastery/v4/champion-masteries/by-summoner/";

    @RequestMapping("/{teamId}")
    public Object getStats(@PathVariable("teamId") String teamid) {
        RestTemplate restTemplate = new RestTemplate();
        Champions champs = new Champions();

        Object champions = champs.getChampions();

        //setting headers
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("X-Riot-Token", apikey);

        HttpEntity<String> entity = new HttpEntity<String>(headers);
        System.out.println(entity);

        ResponseEntity<String> response = restTemplate.exchange(riot_url+summ_name_url+teamid, HttpMethod.GET, entity, String.class);

        String summoner_id = "";
        String repsonse = response.getBody();
        for (int i = 7; i < repsonse.length(); i++ ){
            char curr = repsonse.charAt(i);
            if (curr == '"')  {
                break;
            }
            summoner_id += repsonse.charAt(i);
        }

        ResponseEntity<String> response1 = restTemplate.exchange(riot_url+champ_mastery+summoner_id, HttpMethod.GET, entity, String.class);
        return response1.getBody() + "\n \n" + champions;
    }

}
