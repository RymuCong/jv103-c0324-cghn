package com.cg.casestudy.dtos;


import com.cg.casestudy.models.common.Image;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInfoDTO {

    @NotEmpty(message = "Trường có dấu * là bắt buộc")
    private String name;

    private Boolean gender;

    @NotEmpty(message = "Trường có dấu * là bắt buộc")
    private Date dob;

    @Max(value=200, message = "Độ dài tối đa là 200 ký tự")
    private String education;

    @Max(value=200, message = "Độ dài tối đa là 200 ký tự")
    private String location;

    private String description;

    private Image avatar;

    private Image background;

}