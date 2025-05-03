package com.example.quizz_service.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ResponseAnswer {
    private Integer questionId;
    private String responseAnswer;
}
