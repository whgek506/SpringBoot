package sbb.answer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sbb.question.Question;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne // N(답변):1(질문) 관계 설정 => 외래키
    private Question question; //질문 엔터티를 참조하기 위해 속성 추가
}
