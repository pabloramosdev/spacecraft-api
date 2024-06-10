package com.movies.spacecraft.configuration;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class LoginAspect {

    @Before("execution(* com.movies.spacecraft.service.SpacecraftService.findSpacecraftById(..))")
    public void logIndex(JoinPoint point) {
        Object o = Arrays.stream(point.getArgs()).findFirst().get();
        log.warn(String.format("A negative id %d was found", (Long) o));
    }

}
