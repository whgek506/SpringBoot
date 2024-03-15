package sbb.answer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sbb.question.Question;
import sbb.question.QuestionService;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/create/{id}")
    public String createAnswer(Model model,
                               @PathVariable("id") Integer id,
                               //@RequestParam(value = "content") String content) => question_detail의 textarea 속성명과 동일한 변수명 사용
                               @Valid AnswerForm answerForm, BindingResult bindingResult) { //검증 진행
        Question question = this.questionService.getQuestion(id);
        if(bindingResult.hasErrors()) {
            model.addAttribute("question", question); //Question 객체가 필요하므로 model에 객체를 저장
            return "question_detail"; //오류가 발생하는 경우 다시 답변을 등록할 수 있게 question_detail으로 리턴
        }
        //TODO : 답변을 저장한다. => 주석을 작성하여 개발자들이 주로 코드 내에서 해결되지 않은 문제나 추가로 작업해야 하는 부분을 표시
        //answerService.create() 메서드를 호출하여 답변 저장
        this.answerService.create(question, answerForm.getContent());
        return  String.format("redirect:/question/detail/%s", id);
    }

}
