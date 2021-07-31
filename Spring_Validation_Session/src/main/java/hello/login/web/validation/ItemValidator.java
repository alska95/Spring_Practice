package hello.login.web.validation;

/**
 * 컨트롤러에서 벨리데이션까지 모두 담당하면 할일이 너무 많아지고
 * 복잡해진다.
 * 이 때문에 검증부분을 따로 나누어 관리하면
 * 가시성도 높아지고, 유지보수도 쉬워진다.*/

/**spring validator을 사용하는 이유:
 * .validate(object, bindingresult)줄일 수 있다.*/

import hello.login.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
        //item == clazz인가?
        //item이 맞는 자식 클레스를 가지고있으면 통과
    }

    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target;
        /** errors는 bindingresult의 부모클래스*/

        //검증 오류 결과를 보관

        //검증 로직
        if(!StringUtils.hasText(item.getItemName())){
            errors.rejectValue("itemName","required"); //조건.객체명.필드명
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            errors.rejectValue("price" , "range" , new Object[]{1000 , 1000000} , null);
        }
        if(item.getQuantity() == null || item.getQuantity() >= 9999){
            errors.rejectValue("quantity"  , "max" , new Object[]{9999} , null);
        }
        //특정 필드가 아닌 복합 룰 검증 //값이 넘어오는게 아니라 binding실패할 일이 없다.
        if( item.getPrice()!= null && item.getQuantity() != null && item.getQuantity()*item.getPrice() < 10000){
            errors.reject("totalPriceMin" , new Object[]{10000, item.getQuantity()*item.getPrice()} , null);
        }

        //검증에 실패하면 다시 입력 폼으로
    }
}
