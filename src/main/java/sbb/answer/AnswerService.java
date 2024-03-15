package sbb.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sbb.question.Question;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private  final AnswerRepository answerRepository;

    public void create(Question question, String content) {
        //2개의 매개변수를 받아 Answer 객체를 생성 후 저장
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        this.answerRepository.save(answer);
    }
}
