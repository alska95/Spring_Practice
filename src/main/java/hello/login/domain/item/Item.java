package hello.login.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 스프링이 빈 벨리데이터를 자동으로 글로벌 벨리데이터로 등록한다.
 * */

@Data
public class Item {

    private Long id;

    @NotBlank(message = "공백 x")
    private String itemName;

    @NotNull
    @Range(min = 1000 , max = 1000000)
    private Integer price;

    @NotNull
    @Max(9999)
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}