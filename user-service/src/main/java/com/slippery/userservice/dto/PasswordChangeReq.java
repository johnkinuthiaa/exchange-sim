package com.slippery.userservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = false)
public class PasswordChangeReq {
    @Min(6)
    @Max(20)
    private String oldPassword;
    @Min(6)
    @Max(20)
    private String newPassword;
}
