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
public class GetNearPlacesSearchRes {


    List<FoundItems> items;

    @Data
    public static class FoundItems {

        String title;
        Long distance;
    }
}
