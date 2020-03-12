package com.jojoldu.book.springboot.web.dto;

import com.jojoldu.book.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
    // Controller 에서 결과값으로 여러 테이블을 join 해줘야 할 경우가 많으므로 Entity 클래스 만으로 표현하기 어렵다!
    // 꼭 Entity 클래스와 Controller 에서 쓸 Dto 는 분리해서 사용해야 함!
    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }

}
