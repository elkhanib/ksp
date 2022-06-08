package com.iodigital.ksp.domain;

import java.time.LocalDate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTedTalkRequest {
    @NotEmpty(message = "value cannot be a null or an empty")
    @Size(max = 512, message = "value's length should not be greater than 512")
    private String title;

    @NotEmpty(message = "value cannot be a null or an empty")
    @Size(max = 100, message = "value's length should not be greater than 512")
    private String author;

    @NotNull(message = "'value cannot be a null")
    private LocalDate talkDate;

    @PositiveOrZero(message = "value cannot be a null or less than zero")
    private Long viewCount;

    @PositiveOrZero(message = "value cannot be a null or less than zero")
    private Long likeCount;

    @NotEmpty(message = "value cannot be a null or an empty")
    private String link;
}
