package com.cg.casestudy.dtos;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInfoDTO {

    private Long id;

    @Pattern(regexp = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$",
            message = "*Tên không chứa chữ số và các kí tự đặc biệt")
    @Size(max= 100, message = "*Độ dài tối đa 100 kí tự")
    @Size(min= 5, message = "*Độ dài tối thiểu 5 kí tự")
    private String name;

    private Boolean gender;

    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dob;

    @Size(max=100, message = "*Độ dài tối đa 100 kí tự")
    private String education;

    @Size(max=100, message = "*Độ dài tối đa 100 kí tự")
    private String location;

    @Size(max= 500, message = "*Độ dài tối đa 500 kí tự")
    private String description;


}