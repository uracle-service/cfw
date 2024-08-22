package kr.co.uracle.framework.configs.filter;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "filter")
public class HeaderConfig {

	private List<DecryptionMapping> decryptionMappings;
    
	public List<DecryptionMapping> getDecryptionMappings() {
		return decryptionMappings;
	}

	public void setDecryptionMappings(List<DecryptionMapping> decryptionMappings) {
		this.decryptionMappings = decryptionMappings;
	}

	public static class DecryptionMapping {
        private String decryptionMethod;
        private List<String> headers;

        public String getDecryptionMethod() {
            return decryptionMethod;
        }

        public void setDecryptionMethod(String decryptionMethod) {
            this.decryptionMethod = decryptionMethod;
        }

        public List<String> getHeaders() {
            return headers;
        }

        public void setHeaders(List<String> headers) {
            this.headers = headers;
        }
    }
}
