package hello.itemservice.validation;

import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.*;

public class MessageCodesResolverTest {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        /**
         * required.item , required 두가지 메시지 코드 들어감
         * 디테일한거 (required.item)먼저 objectError이 찾음.
         * rejectValue가 작동하는 방식.*/
        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    void messageCodesResolverField() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        assertThat(messageCodes).containsExactly(
                "required.item.itemName",
                "required.itemName",
                "required.java.lang.String",
                "required"
        );
        /**
         * 과정 bindingResult.rejectValue("itemName" , "required");
         * new FieldError("item", itemName",null , true, false, codes)
         * 여기서 codes는 위 messageCodes에서 받은 codes이다.
         * 위 4가지가 순서대로 들어간다.(레벨순으로)
         *
         * 출력되는법
         * thymeleaf.errors가 실행되며 오류가 있다면 오류 메시지 코드를
         * 순서대로 돌아가면서 메시지를 찾는다. 없으면 디폴트 메시지 출력력         *
         * */
    }

}
