package io.github.housingfinancial.common.aspect;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class ExceptionLogger {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String []EXCLUDE_EXCEPTION = {"DataNotFoundException" };

    @AfterThrowing(pointcut = "execution(* io.github.housingfinancial..*.*(..))", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e) throws Throwable {

        /**
         * 데이터가 없을 경우, 발생한 Exception 은 response 로 204 를 리턴하기 때문에
         * 별도의 로그를 남길 필요가 없으므로, 생략한다.
         */
        for(String excludeException : EXCLUDE_EXCEPTION) {
            if(excludeException.equals(e.getClass().getSimpleName())) {
                return;
            }
        }

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        logger.error("========================= Exception Information Begin =============================== ");
        String requestUrl = request.getScheme() + "://" + request.getServerName() + ":"
                            + request.getServerPort() + request.getRequestURI();

        requestUrl += request.getQueryString() == null ? "" : "?" + request.getQueryString();
        Signature signature = joinPoint.getSignature();
        logger.error("Remote Ip : " + request.getRemoteAddr());
        logger.error("User Id : " + request.getRemoteUser());
        logger.error("request Url :" + requestUrl);

        //request parameter
        Enumeration enumeration = request.getParameterNames();
        while(enumeration.hasMoreElements()) {
            String parameterName =  (String) enumeration.nextElement();
            logger.error("request parameter : {} = {}", parameterName, request.getParameter(parameterName));
        }

        //method arguments
        Object []args = joinPoint.getArgs();
        logger.error("Exception location : " + signature.toLongString());
        for(int i = 0; i < args.length; i++) {
            if(args[i] != null) {
                logger.error("arguments [" + args[i].getClass().getName() + "] = {}", args[i]);
            }
        }
        logger.error("Exception Detail ", e);
        logger.error("========================= Exception Information End =============================== ");
    }

}
