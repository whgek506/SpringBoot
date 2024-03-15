package sbb.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {
    @NotEmpty(message = "제목은 필수항목입니다.") //해당 값이 Null 또는 빈 문자열을 허용X => message = "검증이 실패할 경우 화면에 표시할 요류 메세지"
    @Size(max=200) //길이 설정 => 200바이트를 넘으면 오류 발생
    private String subject;

    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;
}
