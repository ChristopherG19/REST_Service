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
import com.practicaA.apis.dto.ResultsDto;
import com.practicaA.apis.dto.ResponseBody;

@CrossOrigin
//@RestController
@RequestMapping("/rest")
public class RestService {

	RestTemplate restTemplate = new RestTemplate();

	@GetMapping(path = "/find/{name}")
	public ResultsDto RequestInfo(@PathVariable("name") String param)
			throws JSONException, JsonMappingException, JsonProcessingException {

		// Se instancia el objeto donde se almacenan los resultados
		ResultsDto searchResults = new ResultsDto();

		// Se crean variables de las URL
		String sourceUrl = String.format("https://api.tvmaze.com/search/people?q=%s", param);
		String sourceUrl2 = String.format("https://itunes.apple.com/search?term=%s", param.replaceAll(" ", "+"));

		// Se obtiene la información mediante REST
		String responseTVmaze = restTemplate.getForObject(sourceUrl, String.class);
		String responseItunes = restTemplate.getForObject(sourceUrl2, String.class);

		// Se crean objetos Json para manejar los datos

		JSONArray jsonObjTVm = new JSONArray(responseTVmaze);
		JSONObject jsonObjApple = new JSONObject(responseItunes);

		// Se obtienen las secciones objetivo de cada API
		JSONArray DataResultsA = jsonObjApple.getJSONArray("results");

		// Se obtiene la cantidad de resultados
		int lengthA = DataResultsA.length();
		int lengthT = jsonObjTVm.length();

		// Se crean listas temporales para almacenar los resultados de cada API
		ArrayList<ResponseBody> r = new ArrayList<ResponseBody>();
		ArrayList<ResponseBody> r2 = new ArrayList<ResponseBody>();

		// Ciclo para recorrer API de iTunes y obtener los campos requeridos
		for (int i = 0; i < lengthA; i++) {
			JSONObject jObj = DataResultsA.getJSONObject(i);

			String artistN;
			String trackN;
			String wType;

			// Progra defensiva contra excepciones por falta de datos
			try {
				artistN = jObj.getString("artistName");
				trackN = jObj.getString("trackName");
				wType = jObj.getString("kind");
			} catch (JSONException e) {
				artistN = "Sin datos";
				trackN = "Sin datos";
				wType = "Sin datos";
			}

			ResponseBody objTemp = new ResponseBody();
			objTemp.setName(artistN);
			objTemp.setTrackName(trackN);
			objTemp.setType(wType);
			objTemp.setService("API iTunes");
			objTemp.setServiceUrl(sourceUrl2);

			// Se agrega el objeto temporal a la lista temporal anteriormente mencionada
			r.add(objTemp);
		}

		// Se agrega la lista de objetos al objeto resultado
		searchResults.setResults(r);

		// Ciclo para recorrer API de TVmaze y obtener los campos requeridos
		if (!responseTVmaze.equals("[]")) {
			for (int j = 0; j < lengthT; j++) {
				JSONObject DataResults = jsonObjTVm.getJSONObject(j);
				ResponseBody objTemp = new ResponseBody();

				objTemp.setName(DataResults.getJSONObject("person").get("name").toString());
				objTemp.setTrackName("N/A");
				objTemp.setType("person");
				objTemp.setService("API TVmaze");
				objTemp.setServiceUrl(DataResults.getJSONObject("person").get("url").toString());

				// Se agrega el objeto temporal a la segunda lista temporal anteriormente
				// mencionada
				r2.add(objTemp);
			}
		} else {
			ResponseBody objTemp = new ResponseBody();

			objTemp.setName("Sin datos");
			objTemp.setTrackName("Sin datos");
			objTemp.setType("Sin datos");
			objTemp.setService("API TVmaze");
			objTemp.setServiceUrl(sourceUrl);

			// Se agrega el objeto temporal a la segunda lista temporal anteriormente
			// mencionada
			r2.add(objTemp);
		}

		// Se juntan todos los resultados en el objeto resultante
		searchResults.combineResults(r2);

		return searchResults;
	}

}
