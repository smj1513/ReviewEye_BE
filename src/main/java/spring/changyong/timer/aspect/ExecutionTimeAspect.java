package spring.changyong.timer.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import spring.changyong.timer.ExecutionTimeHolder;

@Aspect
@Component
@Log4j2
public class ExecutionTimeAspect {

	@Pointcut("@annotation(spring.changyong.timer.aspect.ExeTimer)")
	private void timer(){}

	@Around("timer()")
	public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Object proceed = joinPoint.proceed();
		stopWatch.stop();
		long totalTimeMillis = stopWatch.getTotalTimeMillis();
		log.info(joinPoint.getSignature() + " executed in " + totalTimeMillis + "ms");
		ExecutionTimeHolder.set(totalTimeMillis);
		return proceed;
	}

}
