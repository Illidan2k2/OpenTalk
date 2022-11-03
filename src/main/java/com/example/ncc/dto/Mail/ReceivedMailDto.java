package com.example.ncc.dto.Mail;

import com.example.ncc.dto.user.UserMailDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReceivedMailDto {
    @JsonProperty
    private int id;

    @JsonProperty
    private String title;

    @JsonProperty
    private String content;

    @JsonProperty
    private LocalDateTime date;

    @JsonProperty
    private UserMailDto sender;
}
