package dy.springboot.demo1.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 类的描述
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/4/16
 */
@Aspect
@Component
public class LogAspect {

    //@Pointcut("bean(sayService)")
    @Pointcut("execution(* dy.springboot.demo1.service..*.*(..))")
    public void pointcut() {}

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("---------- start of AOP-before ----------");
        System.out.println("LogAspect in AOP-Before");
        System.out.println("args length : " + joinPoint.getArgs().length);
        System.out.println("---------- end of AOP-before ----------");
    }

    @After("pointcut()")
    public void doAfter(JoinPoint joinPoint) {
        System.out.println("---------- end of AOP-after ----------");
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "error")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable error) {
        System.out.println("AOP AfterThrowing : " + error);
    }
}
