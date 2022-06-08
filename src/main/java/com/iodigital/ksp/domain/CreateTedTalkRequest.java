package com.iodigital.ksp.domain;

import java.time.LocalDate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTedTalkRequest {
    @NotEmpty(message = "value cannot be a null or an empty")
    private String title;

    @NotEmpty(message = "value cannot be a null or an empty")
    private String author;

    @NotNull(message = "'value cannot be a null")
    private LocalDate talkDate;

    @Min(value = 0, message = "value cannot be a null or less than zero")
    private Long viewCount;

    @Min(value = 0, message = "value cannot be a null or less than zero")
    private Long likeCount;

    @NotEmpty(message = "value cannot be a null or an empty")
    private String link;
}
