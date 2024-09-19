package kr.co.uracle.framework.configs.mybatis;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;


@Configuration
@MapperScan(basePackages="${mybatis.base-packages}")
public class MyBatisConfig {

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${spring.datasource.username}")
	private String dbUserName;

	@Value("${spring.datasource.password}")
	private String dbPassword;

	@Value("${spring.datasource.driver-class-name}")
	private String dbDriverClassName;

	@Value("${mybatis.mapper-locations}")
	private String mapperLocations;

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

		// logging 설정
//		sessionFactory.getObject().getConfiguration().setLogImpl(Slf4jImpl.class);
		
		// 매퍼 XML 파일 경로 설정
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
		
		return sessionFactory.getObject();
	}
	

//	@Bean
//	public SqlSessionTemplate sqlSession (SqlSessionFactory sqlSessionFactory) {
//		return new SqlSessionTemplate(sqlSessionFactory);
//	}

}
