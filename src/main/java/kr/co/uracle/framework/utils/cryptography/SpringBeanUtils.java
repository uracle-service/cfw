package kr.co.uracle.framework.utils.cryptography;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBeanUtils implements ApplicationContextAware {
	private static ApplicationContext applicationContext;
	@Override
	public void setApplicationContext (ApplicationContext applicationContext) throws BeansException {
		SpringBeanUtils.applicationContext = applicationContext;
	}

	/**
	 * Bean 이름으로 Spring Bean 가져오기
	 * @param beanName 가져 올 Bean의 이름
	 * @return beanName에 해당하는 Bean
	 */
	public static Object getBean(String beanName){
		return applicationContext.getBean(beanName);
	}

	/**
	 * Bean 타입을 사용하여 Spring Bean을 가져오는 메서드
	 * @param beanClass 가져올 Bean의 클래스 타입
	 * @param <T> 리턴 타입 제네릭
	 * @return 해당 타입의 Bean 객체
	 */
	public static <T> T getBean(Class<T> beanClass) {
		return applicationContext.getBean(beanClass);
	}

	/**
	 * Bean 이름과 타입을 사용하여 Spring Bean을 가져오는 메서드
	 * @param beanName 가져올 Bean의 이름
	 * @param beanClass 가져올 Bean의 클래스 타입
	 * @param <T> 리턴 타입 제네릭
	 * @return 해당 이름과 타입의 Bean 객체
	 */
	public static <T> T getBean(String beanName, Class<T> beanClass) {
		return applicationContext.getBean(beanName, beanClass);
	}
}
