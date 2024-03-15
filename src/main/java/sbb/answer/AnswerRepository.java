package sbb.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    //answer를 인티티로 리포지터리를 생성하고 여기에 기본키가 Integer임을 추가로 지정
    //answer 테이블에 데이터를 저장, 조회, 수정, 삭제 가능
}
