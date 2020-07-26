package com.here.heremap.api;

import com.here.heremap.pojo.SearchPlacesForAllCatgRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchNearByPlaceForAllCategoriesApi {

    private final GetNearByPlacesHereMapApi getNearByPlacesHereMapApi;

    /**
     * This API accepts the locationName and returns the nearest 3 places for each categories.
     * Categories like, parkingSpot, Restaurants, chargingStation.
     * This API calls getNearByPlacesHereMap every time for each category.
     *It is making parallel call to getNearByPlacesHereMap for every category.
     */
    public SearchPlacesForAllCatgRes searchNearByPlaceForAllCategories(String locName) {
        log.error("inside  SearchNearByPlaceForAllCategoriesApi : " +
                "searchNearByPlaceForAllCategories for location {} ", locName);
        CompletableFuture<List<String>> parkingSpots =
                getNearByPlacesHereMapApi.getNearByPlacesHereMap(locName, "parkingspot");
        CompletableFuture<List<String>> restaurants =
                getNearByPlacesHereMapApi.getNearByPlacesHereMap(locName, "restaurant");
        CompletableFuture<List<String>> chargings =
                getNearByPlacesHereMapApi.getNearByPlacesHereMap(locName, "chargingstation");
        CompletableFuture.allOf(parkingSpots, restaurants, chargings).join();
        List<String> parkingSpotList = null;
        List<String> restaurantList = null;
        List<String> chargingStationList = null;
        try {
            chargingStationList = chargings.get();
            parkingSpotList = parkingSpots.get();
            restaurantList = restaurants.get();
        } catch (InterruptedException e) {
            log.error("exception raised while getting the category list {}", e.getMessage());
        } catch (ExecutionException e) {

            log.error("exception raised while getting the category list {}", e.getMessage());
        }
        return SearchPlacesForAllCatgRes.builder()
                .place(locName)
                .parkingSpotList(parkingSpotList)
                .restaurantList(restaurantList)
                .chargingStationList(chargingStationList)
                .build();
    }
}
