package com.example.officebureauapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
//@Value
//@Jacksonized
//@JsonInclude(JsonInclude.Include.ALWAYS)
//@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    String userName;
    String email;

}
