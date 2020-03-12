package com.jojoldu.book.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // 메인 꺼 주석처리 JPA Auditing 활성화
public class JpaConfig {}
