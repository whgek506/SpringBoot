package sbb.question;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sbb.answer.AnswerForm;

import java.util.List;

//@ResponseBody
@RequestMapping("/question")
@RequiredArgsConstructor // final이 붙은 속성을 포함하는 생성자를 자동으로 만들어줌(Lombok 제공)
@Controller
public class QuestionController {
    private final QuestionRepository questionRepository;
    private final QuestionService questionService; //questionRepository를 직접 사용하지 않고 컨트롤러->서비스->리포지터리 순서로 접근
    @GetMapping("/list")
    //Model : 따로 객체를 생성할 필요 없이 컨트롤러의 메서드에 매개변수로 지정하기만 하면 스프링 부트가 자동으로 생성
    public String list(Model model, //매개변수로 model을 지정하여 객체가 자동으로 생성
                       @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging); //Model 객체에 값을 담아둠
        return "question_list"; // 파일명이 question_list인 html파일을 자동으로 매핑해서 리턴
    }
    @GetMapping(value = "/detail/{id}")
    public String detail(Model model,
                         //@PathVariable() : URL(id)값을 얻을 때 사용(URL과 PathVariable의 매개변수명이 동일)
                         @PathVariable("id") Integer id,
                         AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) { //매개변수로 바인딩한 객체는 Model 객체로 전달하지 않아도 템플릿에서 사용 가능
        return "question_form";
    }
    @PostMapping("/create") //오버로딩 : 매개변수가 다르기 때문에 위의 메서드명과 동일하게 사용 가능
                            //@Valid : @NotEmpty, @Size 등으로 설정한 검증 기능이 동작 / BindingResult : @Valid으로 검증이 수행된 결과를 의미하는 객체
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
            //question_form의 입력 항목 이름(subject,content) == RequestParam-value 값 => 바인딩 사용 안할 때
//                                @RequestParam(value = "subject") String subject,
//                                @RequestParam(value = "content") String content)
        if (bindingResult.hasErrors()) { //오류가 있는 경우 question_form으로 돌아가고, 없는 경우에만 등록
            return  "question_form";
        }
        //TODO 질문을 저장
        //subject, content 항목을 지닌 폼이 전송되면 QuestionForm의 속성이 자동으로 바인딩 됨
        this.questionService.create(questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/list"; //질문 저장 후 목록으로 이동
    }
}
