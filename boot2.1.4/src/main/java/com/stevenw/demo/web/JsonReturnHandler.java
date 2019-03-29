package com.stevenw.demo.web;

import com.stevenw.demo.annotation.JSON;
import com.stevenw.demo.filter.MyJsonFilter;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * @author stevenw
 * @date 2019/3/29
 */
public class JsonReturnHandler implements HandlerMethodReturnValueHandler{
    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        boolean hasJsonAnno = methodParameter.getMethodAnnotation(JSON.class) != null;
        return hasJsonAnno;
    }

    @Override
    public void handleReturnValue(@Nullable Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
        //设置最终的处理类，处理完成后不去找下一个处理类
        modelAndViewContainer.setRequestHandled(true);
        // 获得注解并执行filter方法 最后返回
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        Annotation[] annos = methodParameter.getMethodAnnotations();
        MyJsonFilter jsonSerializer = new MyJsonFilter();
        Arrays.asList(annos).forEach(a -> {
            if (a instanceof JSON) {
                JSON json = (JSON) a;
                jsonSerializer.filter(json.type(), json.include(), json.filter());
            }
        });

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String json = jsonSerializer.toJson(o);
        response.getWriter().write(json);
    }
}
