package sbb.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    //question를 인티티로 리포지터리를 생성하고 여기에 기본키가 Integer임을 추가로 지정
    //question 테이블에 데이터를 저장, 조회, 수정, 삭제 가능
    Question findBySubject(String subject); // => findBySubject() 메소드가 없기 때문에 만들어서 속성 값으로 데이터를 조회 가능
    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject); //결과 발생 값이 여러개일 경우 List<> 사용
    Page<Question> findAll(Pageable pageable); //Pageable 객체를 입력받아 Page<Question> 타입 객체를 리턴하는 메소드 생성 => 페이징 처리
}
