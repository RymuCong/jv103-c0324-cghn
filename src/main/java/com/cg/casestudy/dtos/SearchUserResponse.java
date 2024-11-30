package com.cg.casestudy.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchUserResponse {

    private Long id;
    private String name;
    private Boolean gender;
    private String location;
    private String avatar;
    private String background;
    private String description;
}
