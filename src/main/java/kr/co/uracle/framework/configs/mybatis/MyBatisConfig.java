package kr.co.uracle.framework.configs.mybatis;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(value = {"kr.co.uracle.*.mapper"})
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
	//	private String basePackages;

	@Value("${mybatis.mapper-locations}")
	private String mapperLcations;

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

		System.out.println("[mapperLocation ]==> " + mapperLcations);
		// 매퍼 XML 파일 경로 설정
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLcations));

		// 필요 시 Type Alias 설정
		// sessionFactoryBean.setTypeAliasesPackage("com.example.yourpackage.model");

		return sessionFactory.getObject();
	}

	/* 이거 안받아짐...;;
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		
		MapperScannerConfigurer configurer = new MapperScannerConfigurer();
		
		if (myBatisProperties.getBasePackages() == null || myBatisProperties.getBasePackages().isEmpty()) {
			System.out.println("[BasePackages ]==> " + myBatisProperties.getBasePackages());
			throw new IllegalArgumentException("Base packages must not be null or empty");
	    }
		
		configurer.setBasePackage(String.join(",", myBatisProperties.getBasePackages()));	// 다중 설정 가능.	
		return configurer;
	}
	*/

	@Bean
	public SqlSessionTemplate sqlSession (SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
