package sbb;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found") //에러 상태, 이유를 명시
public class DataNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1;
    public DataNotFoundException(String message) {
        super(message); //메소드 발생 시 메세지 출력
    }
}
