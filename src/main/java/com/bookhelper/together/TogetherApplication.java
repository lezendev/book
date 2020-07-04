package com.bookhelper.together;

import com.bookhelper.together.domain.Member;
import com.bookhelper.together.repository.MemberRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class TogetherApplication {



	public static void main(String[] args) {
		SpringApplication.run(TogetherApplication.class, args);



	}

}
