package hello.login.web.validation;


import hello.login.domain.item.Item;
import hello.login.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder){
        dataBinder.addValidators(itemValidator);
    }
    /**
     * dataBinder에 itemValidator 추가하면 검증기 자동 사용 가능*/

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

//    @PostMapping("/add")
//    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult , RedirectAttributes redirectAttributes, Model model) {
//
//        //검증 오류 결과를 보관
//
//        //검증 로직
//        if(!StringUtils.hasText(item.getItemName())){
//            bindingResult.addError(new FieldError("item" , "itemName" , "상품 이름은 필수 입니다."));
//        }
//        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
//            bindingResult.addError(new FieldError("item" , "price" , "가격은 필수 입니다."));
//        }
//        if(item.getQuantity() == null || item.getQuantity() >= 9999){
//            bindingResult.addError(new FieldError("item" , "quantity" , "수량은 필수 입니다."));
//        }
//        //특정 필드가 아닌 복합 룰 검증
//        if( item.getPrice()!= null && item.getQuantity() != null && item.getQuantity()*item.getPrice() < 10000){
//            bindingResult.addError(new ObjectError("item" , "가격 * 수량의 합은 10,000원 이상이어야 합니다."));
//        }
//
//        //검증에 실패하면 다시 입력 폼으로
//        if(bindingResult.hasErrors()){
//            log.info("error = {}" , bindingResult);
//            return "validation/v2/addForm";
//        }
//
//        //성종 로직
//        Item savedItem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/validation/v2/items/{itemId}";
//    }
///***********************************************/
//    @PostMapping("/add")
//    public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult , RedirectAttributes redirectAttributes, Model model) {
//
//        //검증 오류 결과를 보관
//
//        //검증 로직
//        if(!StringUtils.hasText(item.getItemName())){
//            bindingResult.addError(new FieldError("item" , "itemName" ,item.getItemName() , false ,  null , null,"상품 이름은 필수 입니다."));
//        }
//        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
//            bindingResult.addError(new FieldError("item" , "price" , item.getPrice(), false , null , null,"가격은 필수 입니다."));
//        }
//        if(item.getQuantity() == null || item.getQuantity() >= 9999){
//            bindingResult.addError(new FieldError("item" , "quantity" ,item.getQuantity() , false , null , null, "수량은 필수 입니다."));
//        }
//        //특정 필드가 아닌 복합 룰 검증 //값이 넘어오는게 아니라 binding실패할 일이 없다.
//        if( item.getPrice()!= null && item.getQuantity() != null && item.getQuantity()*item.getPrice() < 10000){
//            bindingResult.addError(new ObjectError("item" , null , null , "가격 * 수량의 합은 10,000원 이상이어야 합니다."));
//        }
//
//        //검증에 실패하면 다시 입력 폼으로
//        if(bindingResult.hasErrors()){
//            log.info("error = {}" , bindingResult);
//            return "validation/v2/addForm";
//        }
//
//        //성종 로직
//        Item savedItem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/validation/v2/items/{itemId}";
//    }

    /***********************************************/
//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, BindingResult bindingResult , RedirectAttributes redirectAttributes, Model model) {

        log.info("objectName = {}" , bindingResult.getObjectName());
        log.info("target ={}" , bindingResult.getTarget());

        //검증 오류 결과를 보관

        //검증 로직
        if(!StringUtils.hasText(item.getItemName())){
            bindingResult.addError(new FieldError("item" , "itemName" ,item.getItemName() , false ,  new String[]{"required.item.itemName"} , null,"상품 이름은 필수 입니다."));
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            bindingResult.addError(new FieldError("item" , "price" , item.getPrice(), false , new String[]{"required.item.price"} , new Object[]{1000,1000000},"가격은 필수 입니다."));
        }
        if(item.getQuantity() == null || item.getQuantity() >= 9999){
            bindingResult.addError(new FieldError("item" , "quantity" ,item.getQuantity() , false , new String[]{"required.item.quantity"} , new Object[]{9999}, "수량은 필수 입니다."));
        }
        //특정 필드가 아닌 복합 룰 검증 //값이 넘어오는게 아니라 binding실패할 일이 없다.
        if( item.getPrice()!= null && item.getQuantity() != null && item.getQuantity()*item.getPrice() < 10000){
            bindingResult.addError(new ObjectError("item" , new String[]{"totalPriceMin"} , new Object[]{10000 , item.getQuantity()*item.getPrice()} , "가격 * 수량의 합은 10,000원 이상이어야 합니다."));
        }

        //검증에 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()){
            log.info("error = {}" , bindingResult);
            return "validation/v2/addForm";
        }

        //성종 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    /***********************************************/
//    @PostMapping("/add")
    public String addItemV4(@ModelAttribute Item item, BindingResult bindingResult , RedirectAttributes redirectAttributes, Model model) {

        log.info("objectName = {}" , bindingResult.getObjectName());
        log.info("target ={}" , bindingResult.getTarget());

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
        return "redirect:/validation/v2/items/{itemId}";
    }

    /***********************************************/
//    @PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item, BindingResult bindingResult , RedirectAttributes redirectAttributes, Model model) {

        log.info("objectName = {}" , bindingResult.getObjectName());
        log.info("target ={}" , bindingResult.getTarget());

        //검증 오류 결과를 보관
        itemValidator.validate(item , bindingResult);

        //검증에 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()){
            log.info("error = {}" , bindingResult);
            return "validation/v2/addForm";
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
        return "redirect:/validation/v2/items/{itemId}";
    }

    /***********************************************/
    @PostMapping("/add")
    public String addItemV6(@Validated @ModelAttribute Item item, BindingResult bindingResult , RedirectAttributes redirectAttributes, Model model) {

        log.info("objectName = {}" , bindingResult.getObjectName());
        log.info("target ={}" , bindingResult.getTarget());
        /**
         * 등록된 검증기 하나하나 뒤지고 support되는거 있으면 자동 사용*/
        //검증 오류 결과를 보관
//       @validated가 이거 대신 해준다. itemValidator.validate(item , bindingResult);

        //검증에 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()){
            log.info("error = {}" , bindingResult);
            return "validation/v2/addForm";
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
        return "redirect:/validation/v2/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

}

