package sbb.question;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import sbb.answer.Answer;

import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@Entity
public class Question {
    @Id //기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //속성에 값이 자동으로 1씩 증가하여 저장
    private Integer id; //질문 데이터의 고유 번호

    @Column(length = 200) // length : 열의 길이를 설정
    private String subject; //질문 데이터의 제목

    @Column(columnDefinition = "TEXT") //columnDefinition : 열 데이터의 유형이나 성격을 정의
    private String content; //질문 데이터의 내용

    private LocalTime createDate; //질문 데이터의 작성한 일시

    //Answer과 1(질문):N(답변) 관계 속성 추가
    // cascade = CascadeType.REMOVE : 부모 테이블이 삭제되면 참조하던 자식 테이블도 같이 삭제 설정
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) // mappedBy : 참조 엔터티의 속성명을 정의
    private List<Answer> answerList;
}
