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
public class GeoSearchResponse {

    List<FoundItems> items;

    @Data
    public static class FoundItems {
        LatLongPos position;

        @Data
        public static class LatLongPos {
            Double lat;
            Double lng;
        }

    }
}
