package com.example.quizz_service.service;

import com.example.quizz_service.dao.QuizDao;
import com.example.quizz_service.feign.QuizzInterface;
import com.example.quizz_service.model.QuestionWrapper;
import com.example.quizz_service.model.Quiz;
import com.example.quizz_service.model.ResponseAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizzInterface quizzInterface;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Integer> questionIds = quizzInterface.getQuestionsForQuiz(category, numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questionIds);

        try {
            quizDao.save(quiz);
            return new ResponseEntity<>("Quiz created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error creating quiz");
        }

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
        Optional<Quiz> quizOptional = quizDao.findById(id);
        List<Integer> questionIdsFromDB = quizOptional.isPresent() ?
                quizOptional.get().getQuestionIds() : new ArrayList<>();
        return quizzInterface.getQuestions(questionIdsFromDB);
    }

    public ResponseEntity<Integer> calculateScore(int id, List<ResponseAnswer> responseAnswers) {

        return quizzInterface.getScore(responseAnswers);
    }

}
