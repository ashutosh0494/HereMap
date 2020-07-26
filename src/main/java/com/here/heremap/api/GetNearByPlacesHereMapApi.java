package com.here.heremap.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.here.heremap.config.HereMapClientConfig;
import com.here.heremap.pojo.GetNearPlacesSearchRes;
import com.here.heremap.service.FindGeoCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetNearByPlacesHereMapApi {

    private final FindGeoCodeService findGeoCodeService;
    private final RestTemplate restTemplate;
    private final HereMapClientConfig hereMapClientConfig;
    final ObjectMapper objectMapper;


    /**
     * This API accept the location Name and category and returns the 3 nearest place.
     * It first calls GeoCode service to get the latitude and longitude of the given location.
     * Then it calls HereMap's API to get the nearest place with limit as 3(can be configured in properties file).
     * It is called in async thread and also stored the value in Cache to avoid hitting third PArty API for same loc.
     */
    @Cacheable("getPlaces")
    @Async
    public CompletableFuture<List<String>> getNearByPlacesHereMap(String locName, String catName) {
        log.info("inside GetNearByPlacesHereMapApi : GetNearPlacesSearchRes for location {} " +
                "and category {}", locName, catName);
        //calling GeoCode service to get the LatLong.
        String latLngGeoCode = findGeoCodeService.getGeoCode(locName);
        String searchPlacesUrl = hereMapClientConfig.getSearchPlaceUrl();
        String latLngQueryString = "at=" + latLngGeoCode;
        String categoryQueryString = "&q=" + catName;
        String limitQueryString = "&limit=" + hereMapClientConfig.getSearchLimit();
        String apiKeyQueryString = "&apiKey=" + hereMapClientConfig.getApiKey();
        searchPlacesUrl = searchPlacesUrl + latLngQueryString + categoryQueryString +
                limitQueryString + apiKeyQueryString;
        log.info("Url to search NearbyPlaces : {}", searchPlacesUrl);
        ParameterizedTypeReference<Map<String, Object>> responseTypeRef =
                new ParameterizedTypeReference<Map<String, Object>>() {
                };
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(searchPlacesUrl, HttpMethod.GET,
                null, responseTypeRef);
        //using object mapper to convert the response to the required class.
        GetNearPlacesSearchRes getNearPlacesSearchRes =
                objectMapper.convertValue(response.getBody(), GetNearPlacesSearchRes.class);
        List<String> placesTitleList = getNearPlacesSearchRes.getItems().stream()
                .map(GetNearPlacesSearchRes.FoundItems::getTitle).collect(Collectors.toList());
        return CompletableFuture.completedFuture(placesTitleList);
    }
}
