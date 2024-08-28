package kr.co.uracle.framework.configs.mybatis;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import kr.co.uracle.framework.model.MyBatisProperties;

@Configuration
//@MapperScan(value = {"kr.co.uracle.*.mapper"})
@MapperScan(basePackages="${mybatis.base-packages}")
//@EnableConfigurationProperties(MyBatisProperties.class)
public class MyBatisConfig {

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${spring.datasource.username}")
	private String dbUserName;

	@Value("${spring.datasource.password}")
	private String dbPassword;

	@Value("${spring.datasource.driver-class-name}")
	private String dbDriverClassName;

//	@Value("${mybatis.base-packages}")	//additional-spring-configuration-metadata 추가.
//	private String[] basePackages;
//
//	@Value("${mybatis.mapper-locations}")
//	private String mapperLcations;
	
	private final MyBatisProperties mybatisProp;
	
	@Autowired
	public MyBatisConfig(MyBatisProperties mybatisProp) {
		this.mybatisProp = mybatisProp;
		
		System.out.println(this.mybatisProp.getMapperLocations());
		System.out.println(this.mybatisProp.getBasePackages().length);
		
	}

	@Bean
	public DataSource dataSource () {
		System.out.println("==> dbUrl ==> " + dbUrl);
		return DataSourceBuilder.create()
								.url(dbUrl)
								.username(dbUserName)
								.password(dbPassword)
								.driverClassName(dbDriverClassName)
								.build();
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory (DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);

		System.out.println("[mapperLocation ]==> " + mybatisProp.getMapperLocations());
//		System.out.println("[basePackages ]==> " + String.join(",", mybatisProp.getBasePackages()));
		// 매퍼 XML 파일 경로 설정
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mybatisProp.getMapperLocations()));

//		MapperScannerConfigurer configurer = new MapperScannerConfigurer();
//		if (mybatisProp == null || mybatisProp.getBasePackages() == null || mybatisProp.getBasePackages().length == 0) {
//			throw new IllegalArgumentException("Base packages must not be null or empty");
//		}
//	
//		configurer.setBasePackage(String.join(",", mybatisProp.getBasePackages()));	// 다중 설정 가능.
		
		return sessionFactory.getObject();
	}


//	@Bean
//	public MapperScannerConfigurer mapperScannerConfigurer() {
//		
//		MapperScannerConfigurer configurer = new MapperScannerConfigurer();
//		
//		if (mybatisProp == null || mybatisProp.getBasePackages() == null || mybatisProp.getBasePackages().length == 0) {
//			throw new IllegalArgumentException("Base packages must not be null or empty");
//	    }
//		
//		configurer.setBasePackage(String.join(",", mybatisProp.getBasePackages()));	// 다중 설정 가능.
//		
//		System.out.println(String.join(",", mybatisProp.getBasePackages()));
//		return configurer;
//	}
	

//	@Bean
//	public SqlSessionTemplate sqlSession (SqlSessionFactory sqlSessionFactory) {
//		return new SqlSessionTemplate(sqlSessionFactory);
//	}

}
