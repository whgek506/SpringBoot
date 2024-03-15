package com.example.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sbb.*;
import sbb.answer.Answer;
import sbb.answer.AnswerRepository;
import sbb.question.Question;
import sbb.question.QuestionRepository;
import sbb.question.QuestionService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//스프링 부트의 테스트 클래스임을 의미
@SpringBootTest(classes = Application.class) //실제 실행되는 main class명을 명시해줘야 함
 class SbbApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionService questionService;

    @Transactional // 메서드가 종료될 때까지 DB 세션이 유지(테스트 코드에서만 사용)
    @Test
    void testJpa() {

        //******** 질문 데이터 저장하기 ********
        Question q1 = new Question(); //=> Question 생성
        q1.setSubject("sbb가 무엇인가요?"); //=> 제목 넣기
        q1.setContent("sbb에 대해서 알고 싶습니다."); //=> 내용 넣기
        q1.setCreateDate(LocalTime.from(LocalDateTime.now())); //=> 현재 시간 넣기
        this.questionRepository.save(q1); //=> 첫 번째 질문 저장

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalTime.from(LocalDateTime.now()));
        this.questionRepository.save(q2); // 두 번째 질문 저장

       //******** 질문 데이터 조회하기 ********
       List<Question> all = this.questionRepository.findAll(); // findAll() : 테이블에 저장 된 모든 데이터 조회
       //assertEquals(기대한 값, 실제 값) : 테스트에서 예상한 결과와 실제 결과가 동일한지 확인하는 목적
       assertEquals(2, all.size()); //(기대한 전체 사이즈, 실제 전체 사이즈)
       Question q = all.get(0); //0번째 부터 시작
       assertEquals("sbb가 무엇인가요?", q.getSubject()); //(기대한 내용, 실제 내용)

       //******** id값으로 질문 데이터 조회하기 ********
       //Optional<> : 리턴 값을 처리하기 위한 클래스로, isPresent() 메서드를 통해 값이 존재하는지 확인 가능
       Optional<Question> oq = this.questionRepository.findById(1); //id가 1인 질문을 조회
       if(oq.isPresent()) {
          q = oq.get(); //존재 한다면 get() 메서드를 통해 실제 객체 값을 얻음
          assertEquals("sbb가 무엇인가요?", q.getSubject()); //기대한 내용, 실제 얻은 값과 비교
       }

       //******** subject값으로 질문 데이터 조회하기 ********
       q = this.questionRepository.findBySubject("sbb가 무엇인가요?"); //리포지터리의 메서드명은 데이터를 조회하는 쿼리문의 where 조건을 결정하는 역할
       assertEquals(1, q.getId());

       //******** subject와 content 값으로 질문 데이터 조회하기 ********
       //응답 결과가 여러 건인 경우는 리포지터리의 메소드의 리턴 타입을 List<Question>으로 작성!
       q = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
       assertEquals(1, q.getId());

       //******** 특정 문자열을 포함하는(like) 질문 데이터 조회하기 ********
       List<Question> qList = this.questionRepository.findBySubjectLike("sbb%"); //'sbb'로 시작하는 문자열
       q = qList.get(0);
       assertEquals("sbb가 무엇인가요?", q.getSubject());

       //******** 질문 데이터 수정하기 ********
       Optional<Question> oqUp = this.questionRepository.findById(1); //where id = findById(1)인 값 찾아서 넣기
       assertTrue(oqUp.isPresent()); //assertTrue() : 괄호 안의 값이 true 인지를 테스트
       q.setSubject("수정된 제목"); // 저장할 값 넣기(update question set subject = q.setSubject() )
       this.questionRepository.save(q); //데이터에 저장

       //******** 질문 데이터 삭제하기 ********
       assertEquals(2, this.questionRepository.count()); //(기대한 값, questionRepository의 총 개수)
       Optional<Question> opDel = this.questionRepository.findById(1); //where id = findById(1)인 값 찾아서 넣기
       assertTrue(opDel.isPresent()); //실제 값이 존재하는지 확인
       q = opDel.get(); // 존재한다면 q에 값 넣기
       this.questionRepository.delete(q); //delete from question where id = q
       assertEquals(1, this.questionRepository.count()); //(기대한 값, 삭제 후 questionRepository의 총 개수)

      //-------------------------------------------------------------------------
       //******** 답변 데이터 저장하기 ********
       //답변을 생성하려면 질문이 필요하므로 질문을 조회
       Optional<Question> op = this.questionRepository.findById(2);
       assertTrue(oq.isPresent());
       q = op.get();

       Answer a = new Answer(); //생성자 호출
       a.setContent("네 자동으로 생성됩니다.");
       a.setQuestion(q); //조회한 id 값
       a.setCreateDate(LocalDateTime.now());
       this.answerRepository.save(a); //데이터에 저장

       //******** 답변 데이터 조회하기 ********
       Optional<Answer> oa = this.answerRepository.findById(1);
       assertTrue(oa.isPresent());
       a = oa.get();
       assertEquals(2, a.getQuestion().getId()); //질문의 id(question_id)값이 2인지 확인

       op = this.questionRepository.findById(2);
       assertTrue(op.isPresent());
       q = op.get();

       List<Answer> answerList = q.getAnswerList(); //List 형식으로 답변 전체를 가져옴

       assertEquals(1, answerList.size());
       assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());

       //-------------------------------------------------------------------------
       //******** 페이징 기능 ********
       for(int i=1; i <= 300; i++) {
          String subject = String.format("테스트 데이터입니다:[%03d]" ,i);
          String content = "내용 무";
          this.questionService.create(subject, content);
       }
    }
}
