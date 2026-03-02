package br.com.myaccounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MyAccountsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyAccountsBackendApplication.class, args);
	}

}
