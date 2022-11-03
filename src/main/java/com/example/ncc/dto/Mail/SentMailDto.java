package com.example.ncc.dto.Mail;

import com.example.ncc.dto.user.UserMailDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class SentMailDto {
    @JsonProperty
    private int id;

    @JsonProperty
    private String title;

    @JsonProperty
    private String content;

    @JsonProperty
    private LocalDateTime date;

    @JsonProperty
    private Set<UserMailDto> receivers;
}
