package kr.co.uracle.framework.configs;

import org.apache.catalina.Context;
import org.apache.tomcat.JarScanType;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class TomcatConfig {

	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
		return factory -> factory.addContextCustomizers((Context context) -> {
			StandardJarScanner jarScanner = (StandardJarScanner) context.getJarScanner();
			jarScanner.setScanAllDirectories(true);
			jarScanner.setScanClassPath(true);

		});
	}
}
