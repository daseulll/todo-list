package com.originality.todo.global.resolver;

import com.originality.todo.domain.Member;
import com.originality.todo.global.annotation.RequestUser;
import com.originality.todo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class RequestUserResolver implements HandlerMethodArgumentResolver {
    private final MemberService memberService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestUser.class)
                && parameter.getParameterType().equals(Member.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        final String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null) {
            throw new IllegalArgumentException("Authorization 헤더 값은 필수입니다.");
        }

        return memberService.findByToken(token)
                .orElseThrow(() -> new UsernameNotFoundException("올바르지 않은 token입니다. 유저를 찾을 수 없습니다.: " + token));
    }
}
