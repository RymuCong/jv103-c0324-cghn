package com.cg.casestudy.dtos;


import com.cg.casestudy.models.common.Image;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInfoDTO {

    private String name;

    private Boolean gender;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    private String education;

    private String location;

    private String description;

    private Image avatar;

    private Image background;

}