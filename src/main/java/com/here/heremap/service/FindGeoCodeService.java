package com.here.heremap.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.here.heremap.config.HereMapClientConfig;
import com.here.heremap.pojo.GeoSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindGeoCodeService {

    private final RestTemplate restTemplate;
    private final HereMapClientConfig hereMapClientConfig;
    final ObjectMapper objectMapper;

    /**
     * It accepts the locationNAme as input and return the latitude and longitude in concatenated form.
     * It uses caching to store the value , so that repeated call to third party can be avoided.
     */
    @Cacheable("geoCode")
    public String getGeoCode(String locName) {
        log.info("inside getGeoCode, finding geoCode for locName {}", locName);
        String urlToGetGeoCode = hereMapClientConfig.getGeoCodeUrl();
        String locQueryString = "q=" + locName;
        String apiKeyQueryString = "&apiKey=" + hereMapClientConfig.getApiKey();
        urlToGetGeoCode = urlToGetGeoCode + locQueryString + apiKeyQueryString;
        log.info("GeoCodeSearch URI {}", urlToGetGeoCode);
        ParameterizedTypeReference<Map<String, Object>> responseTypeRef =
                new ParameterizedTypeReference<Map<String, Object>>() {
                };
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(urlToGetGeoCode, HttpMethod.GET,
                null, responseTypeRef);
        GeoSearchResponse geoSearchResponse = objectMapper.convertValue(response.getBody(),
                GeoSearchResponse.class);
        if(geoSearchResponse.getItems()==null || geoSearchResponse.getItems().isEmpty() ){
            throw new RuntimeException("Invalid place name");
        }
        GeoSearchResponse.FoundItems.LatLongPos position = geoSearchResponse.getItems().get(0).getPosition();
        return position.getLat().toString() + "," + position.getLng().toString();
    }

}
