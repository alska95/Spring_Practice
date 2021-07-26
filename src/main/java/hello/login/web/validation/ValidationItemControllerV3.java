package hello.login.web.validation;


import hello.login.domain.item.Item;
import hello.login.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v3/items")
@RequiredArgsConstructor
public class ValidationItemControllerV3 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v3/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v3/addForm";
    }

    /***********************************************/
    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute Item item, BindingResult bindingResult , RedirectAttributes redirectAttributes, Model model) {

        log.info("objectName = {}" , bindingResult.getObjectName());
        log.info("target ={}" , bindingResult.getTarget());
        /**
         * 등록된 검증기 하나하나 뒤지고 support되는거 있으면 자동 사용*/
        //검증 오류 결과를 보관
//       @validated가 이거 대신 해준다. itemValidator.validate(item , bindingResult);

        //검증에 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()){
            log.info("error = {}" , bindingResult);
            return "validation/v3/addForm";
        }
        /**
         * 실행 개요
         * 1.rejectValue() 호출
         * 2.MessageCodesResolver을 사용해서 검증 오류 코드로 메시지 코드들을 생성
         * 3.new FieldError()를 생성하면서 메시지 코드들을 보관
         * "th:errors"에서 메시지 코드들로 메시지를 순서대로 메시지에서 찾고, 노출*/

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v3/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v3/items/{itemId}";
    }

}

