package spring.changyong.search.utils;


public class ExecutionTimeHolder {
	private final static ThreadLocal<Long> EXECUTION_TIME = new ThreadLocal<>();

	public static void set(Long time){
		EXECUTION_TIME.set(time);
	}

	public static Long get(){
		return EXECUTION_TIME.get();
	}

	public static void clear(){
		EXECUTION_TIME.remove();
	}
}
