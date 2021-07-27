package hello.login.web.validation;


import hello.login.web.item.form.ItemSaveForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/validation/api/items")
public class ValidationItemApiController {


    /**
     * Api의 경우 3가지로 나누어 생각함
     * 1.성공
     * 2.실패 요청 : JSON을 객체로 생성하는 것 자체가 실패함
     * 3.검증 오류 요청 : JSON을 객체로 생성하는 것은 성공했고, 검증에서 실해함
     *
     * 객체로 바꾸는 것조차 실패하면 bindingResult를 호출하는대 까지도 못감.
     *      --> 컨트롤러가 호출 안되고 예외처리 (실패 요청)
     *      */
    @PostMapping("/add")
    public Object addItem(@RequestBody @Validated ItemSaveForm form , BindingResult bindingResult){
        log.info("API 컨트롤러 호출");
        if(bindingResult.hasErrors()){
            log.info("검층 오류 발생 errors = {}" , bindingResult);
            return bindingResult.getAllErrors();
        }

        log.info("성공 로직 실행");
        return form;
    }
}
