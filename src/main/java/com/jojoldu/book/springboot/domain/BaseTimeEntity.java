package com.jojoldu.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass   // BaseTimeEntity 상속시 필드들(createdDate, modifiedDate)도 칼럼으로 인식시킴
@EntityListeners(AuditingEntityListener.class)  // Auditing 기능 포함
public abstract class BaseTimeEntity {

    @CreatedDate    // Entity 가 생성 저장 될 때 시간이 자동 저장
    private LocalDateTime createdDate;

    @LastModifiedDate   // 죄회한 Entity 값 변경 시 시간이 자동 저장
    private LocalDateTime modifiedDate;

}
