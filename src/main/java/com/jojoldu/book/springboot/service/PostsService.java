package com.jojoldu.book.springboot.service;

import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.web.dto.PostsListResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor    // final 인자를 가진 생성자를 대신해 lombok 이 bean 으로 만들어 줌
    // 의존성 관계가 변경될 때마다 생성자 코드를 계속 수정 안해도 됨!
    // 1. 해당 controller 에 새로운 service 추가 하는 경우
    // 2. 기존 컨포넌트 제거 하는 경우
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    // JPA 의 핵심: Entity 가 영속성 컨텍스트에(트랜잭션 안에서 디비자료 가져옴) 포함되냐 안되냐?
    // 값이 변경되면 트랜잭션이 끝나는 시점에 디비 테이블에 반영.
    // 즉 Entity 객체의 값만 변경하면 별도로 update 쿼리를 날릴 필요가 없다. = 더티 체킹( dirty checking )
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        postsRepository.delete(posts);
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) // 트랜잭션 범위는 유지하되, 조회 기능만 남겨두어 조회 속도 개선! (등록,수정,삭제 기능이 없는 경우)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)    // 람다식   // .map(posts -> new PostsListResponseDto(posts))
                .collect(Collectors.toList());
    }
}
