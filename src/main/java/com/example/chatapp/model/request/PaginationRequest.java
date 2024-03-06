package com.example.chatapp.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationRequest {
    @NotBlank
    @Min(message = "Page is not negative number", value = 0)
    private int page;
    @NotBlank
    private int size;
}
