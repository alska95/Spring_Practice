package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm")LoginForm form){
        return "login/loginForm";
    }
//    @PostMapping("/login")
    public String loginV3(@Valid @ModelAttribute LoginForm form , BindingResult bindingResult , HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if(loginMember == null){
            bindingResult.reject("loginFail" , "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }
        //세션종료시 종료 / 세션쿠키
        //세션 있으면 있는세션 , 없으면 신규세션
        HttpSession session = request.getSession();
        //세션에 로그인 회원정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return "redirect:/";
    }


    /**
    * redirect를 추가
    * */
    @PostMapping("/login")
    public String loginV4(@Valid @ModelAttribute LoginForm form , BindingResult bindingResult ,
                          @RequestParam(defaultValue = "/") String redirectURL,
                          HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if(loginMember == null){
            bindingResult.reject("loginFail" , "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }
        //세션종료시 종료 / 세션쿠키
        //세션 있으면 있는세션 , 없으면 신규세션
        HttpSession session = request.getSession();
        //세션에 로그인 회원정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return "redirect:"+redirectURL;
    }
//    @PostMapping("/login")
//    public String login2(@Valid @ModelAttribute LoginForm form , BindingResult bindingResult , HttpServletResponse response){
//        if(bindingResult.hasErrors()){
//            return "login/loginForm";
//        }
//
//        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
//
//        if(loginMember == null){
//            bindingResult.reject("loginFail" , "아이디 또는 비밀번호가 맞지 않습니다.");
//            return "login/loginForm";
//        }
//        //세션종료시 종료 / 세션쿠키
//        sessionManager.createSession(loginMember, response);
//        return "redirect:/";
//    }
//    @PostMapping("/login")
//    public String login(@Valid @ModelAttribute LoginForm form , BindingResult bindingResult , HttpServletResponse response){
//        if(bindingResult.hasErrors()){
//            return "login/loginForm";
//        }
//
//        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
//
//        if(loginMember == null){
//            bindingResult.reject("loginFail" , "아이디 또는 비밀번호가 맞지 않습니다.");
//            return "login/loginForm";
//        }
//        //세션종료시 종료 / 세션쿠키
//        Cookie idCookie = new Cookie("memberId" , String.valueOf(loginMember.getId()));
//        response.addCookie(idCookie);
//        return "redirect:/";
//    }
//    @PostMapping("/logout")
//    public String logOut(HttpServletResponse response){
//        expireCookie(response , "memberId");
//        return "redirect:/";
//    }
//    @PostMapping("/logout")
//    public String logOutV2(HttpServletRequest request){
//        sessionManager.expire(request);
//        return "redirect:/";
//    }
//    private void expireCookie(HttpServletResponse response , String cookieName){
//        Cookie cookie = new Cookie(cookieName , null);
//        cookie.setMaxAge(0);
//        response.addCookie(cookie);
//    }
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/logout")
    public String logOutV3(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session!= null){
            session.invalidate();
        }
        return "redirect:/";
    }

}
