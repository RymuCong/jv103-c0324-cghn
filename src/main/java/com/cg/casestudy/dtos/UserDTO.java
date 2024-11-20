package com.cg.casestudy.dtos;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    private Long id;

    @NotNull(message = "*Tên đăng nhập là bắt buộc")
    @Size(min=5, message = "*Tên đăng nhập phải có ít nhất 5 ký tự")
    private String username;

    @NotNull(message = "*Email là bắt buộc")
    @Size(min = 5, message = "*Email phải có ít nhất 5 ký tự")
    private String email;

    @NotNull(message = "*Mật khẩu là bắt buộc")
    @Size(min = 5, message = "*Mật khẩu phải có ít nhất 5 ký tự")
    private String password;

    @NotNull(message = "*Xác nhận mật khẩu là bắt buộc")
    private String confirmPassword;
}
