package hello.login.web.session;


import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
    public static final String SESSION_COOKIE_NAME = "mySessionId";
    private Map<String , Object> sessionStore = new ConcurrentHashMap<>(); //쿠키 저장소

    /**
     * 세션 생성
     * */

    public void createSession(Object value , HttpServletResponse response){
        //sessionId 생성, 값을 session에 저장 universal unique Id
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId , value);

        //쿠키 생성
        Cookie cookie = new Cookie(SESSION_COOKIE_NAME , sessionId);
        response.addCookie(cookie);
    }

    /***
     * 세션 조회
     */
    public Object getSession(HttpServletRequest request){
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if(sessionCookie == null){
            return null;
        }
        return sessionStore.get(sessionCookie.getValue());
    }

    public Cookie findCookie(HttpServletRequest request , String cookieName){
        if(request.getCookies() == null){
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(cookieName))
                .findAny().orElse(null);
    }
    /**
     * 세션 만료
     * */

    public void expire(HttpServletRequest request){
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if(sessionCookie != null){
            sessionStore.remove(sessionCookie.getValue());
        }
    }


}
