package com.here.heremap.pojo;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchPlacesForAllCatgRes {

    String place;
    List<String> parkingSpotList;
    List<String> restaurantList;
    List<String> chargingStationList;
}
