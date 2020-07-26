package com.here.heremap;

import com.here.heremap.api.SearchNearByPlaceForAllCategoriesApi;
import com.here.heremap.pojo.SearchPlacesForAllCatgRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api")
public class HereMapResource {

    private final SearchNearByPlaceForAllCategoriesApi searchNearByPlaceForAllCategoriesApi;


    /**
     * @param locName
     * @return
     */
    @GetMapping("/searchNearByPlacesForAllCatg")
    public SearchPlacesForAllCatgRes searchNearByPlacesForAllCatg(@RequestParam String locName) {
        return searchNearByPlaceForAllCategoriesApi.searchNearByPlaceForAllCategories(locName);
    }

}
