package hello.login.domain.member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class Member {
    private Long id;

    @NotEmpty(message = "please write")
    private String loginId;
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;
}
