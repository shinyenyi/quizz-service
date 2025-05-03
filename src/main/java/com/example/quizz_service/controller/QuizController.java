package com.example.quizz_service.controller;


import com.example.quizz_service.model.QuestionWrapper;
import com.example.quizz_service.model.QuizzDto;
import com.example.quizz_service.model.ResponseAnswer;
import com.example.quizz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizzDto quizzDto) {
        return quizService.createQuiz(quizzDto.getCategory(), quizzDto.getNumQ(), quizzDto.getTitle());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable int id) {
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable int id,
                                              @RequestBody List<ResponseAnswer> responseAnswers) {
        return quizService.calculateScore(id, responseAnswers);
    }
}
