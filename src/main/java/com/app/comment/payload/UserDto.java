package com.app.comment.payload;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Name should be min 2 characters")
    private String name;
}
