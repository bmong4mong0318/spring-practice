package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTraceAop {
    //AOP: Aspect Oriented Programming
    //공통 관심 사항(cross-cutting concern) vs 핵심 관심 사항(core concern) 분리

    //공통 관심사를 어디에 적용할지
    @Around("execution(* hello.hellospring..*(..))") // 패키지 하위에 다 적용하겠다.
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try{
            return joinPoint.proceed(); //다음 메서드로 진행이 된다.
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
