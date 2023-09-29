package me.heeyun.blog.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;

import java.util.Base64;

//public class CookieUtil {
//    // 요청값(이름, 값, 만료 기간)을 바탕으로 쿠키 추가
//    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge){
//        Cookie cookie = new Cookie(name, value);
//        cookie.setPath("/");
//        cookie.setMaxAge(maxAge);
//        response.addCookie(cookie);
//    }
//
//    // 쿠키의 이름을 입력 받아 쿠키 삭제
//    // 실제로 삭제하는 방법은 없기 때문에 파라미터로 넘어온 키의 쿠키를 빈 값으로 바꾸고 만료 시간을 0으로 설정해
//    // 쿠키가 재생성 되자마자 만료 처리한다.
//    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name){
//        Cookie[] cookies = request.getCookies();
//        if(cookies == null){
//            return;
//        }
//
//        for(Cookie cookie : cookies) {
//            if(name.equals(cookie.getName())){
//                cookie.setValue("");
//                cookie.setPath("/");
//                cookie.setMaxAge(0);
//                response.addCookie(cookie);
//            }
//        }
//    }
//
//    // 객체를 직렬화해 쿠키의 값으로 변환
//    public static String serialize(Object object){
//        return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(object));
//    }
//
//    // 쿠키글 역직렬화해 객체로 변환
//    public static <T> T deserialize(Cookie cookie, Class<T> cls){
//        return cls.cast(
//                SerializationUtils.deserialize(
//                        Base64.getUrlDecoder().decode(
//                                cookie.getValue()
//                        )
//                )
//        );
//    }
//}

public class CookieUtil {

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);

        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return;
        }

        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }

    public static String serialize(Object obj) {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(obj));
    }

    public static <T> T deserialize(Cookie cookie, Class<T> cls) {

        return cls.cast(
                SerializationUtils.deserialize(
                        Base64.getUrlDecoder().decode(cookie.getValue())
                )
        );
    }
}
