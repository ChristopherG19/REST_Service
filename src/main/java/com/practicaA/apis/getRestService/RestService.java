package com.practicaA.apis.getRestService;

import java.util.ArrayList;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practicaA.apis.dto.InfoDto;
import com.practicaA.apis.dto.ResultsBody;

@CrossOrigin
@RestController
@RequestMapping("/rest")
public class RestService {
	
	RestTemplate restTemplate = new RestTemplate();
	ObjectMapper mapper = new ObjectMapper();

	@GetMapping(path="/find/{name}")
	public InfoDto RequestInfo(@PathVariable("name") String param) throws JSONException, JsonMappingException, JsonProcessingException {

		//String sourceUrl = String.format("https://api.tvmaze.com/search/people?q=%s", param.replaceAll(" ", "%20"));
		String sourceUrl2 = String.format("https://itunes.apple.com/search?term=%s", param.replaceAll(" ", "+"));

		// Trim debido a espacios en blanco iniciales
		String response = restTemplate.getForObject(sourceUrl2, String.class).trim();	
		
		JSONObject jsonObj = new JSONObject(response);
		
		JSONArray DataResults = jsonObj.getJSONArray("results");
		int length = DataResults.length();
		
		InfoDto ResultsItunes = new InfoDto();
		ArrayList<ResultsBody> r = new ArrayList<ResultsBody>();
		
		for(int i = 0; i < length; i++) {
			JSONObject jObj = DataResults.getJSONObject(i);
			
			ResultsBody objTemp = new ResultsBody();
			objTemp.setName(jObj.getString("artistName"));
			objTemp.setTrackName(jObj.getString("trackName"));
			objTemp.setType(jObj.getString("wrapperType"));
			objTemp.setService("API iTunes");
			objTemp.setServiceUrl(sourceUrl2);

			r.add(objTemp);
			ResultsItunes.setResults(r);
			
			//System.out.println(i+") "+objTemp);
		}

		//ResponseEntity<String> response2 = restTemplate.getForEntity(sourceUrl2, String.class);
		
		//InfoDto obj2 = mapper.readValue(jsonObj.getJSONArray("results").toString(), InfoDto.class);
		
		return ResultsItunes;
	}
	
	
	
}
