package sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sbb.DataNotFoundException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service //Service로 인식
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> getList() { //질문 목록을 조회하여 리턴하는 메서드
        return this.questionRepository.findAll();
    }
    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if(question.isPresent()){ //값이 존재하는지 확인
            return question.get(); //존재하면 데이터 값 리턴
        }else {
            throw new DataNotFoundException("question not found"); //존재하지 않을 때 생성한 DataNotFoundException 예외 클래스 실행
        }
    }

    public void  create(String subject, String content) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalTime.from(LocalDateTime.now()));
        this.questionRepository.save(q);
    }
    public Page<Question> getList(int page) { //정수 타입의 페이지 번호를 입력받아 Page 객체를 리턴
        //데이터 전체를 조회하지 않고 해당 페이지의 데이터만 조회
        Pageable pageable = PageRequest.of(page, 10); //PageRequest.of(조회할 페이지 번호, 한 페이지에 보여줄 게시물의 개수)
        return this.questionRepository.findAll(pageable);
    }
}
