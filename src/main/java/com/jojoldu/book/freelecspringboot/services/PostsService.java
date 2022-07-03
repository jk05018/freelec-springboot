package com.jojoldu.book.freelecspringboot.services;

import com.jojoldu.book.freelecspringboot.domain.posts.Posts;
import com.jojoldu.book.freelecspringboot.domain.posts.PostsRepository;
import com.jojoldu.book.freelecspringboot.web.dto.PostsListResponseDto;
import com.jojoldu.book.freelecspringboot.web.dto.PostsResponseDto;
import com.jojoldu.book.freelecspringboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.freelecspringboot.web.dto.PostsUpdateRequestDto;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PostsService {

  private final PostsRepository postsRepository;

  @Transactional
  public Long save(PostsSaveRequestDto requestDto){
    return postsRepository.save(requestDto.toEntity()).getId();
  }

  @Transactional
  public Long update(Long id, PostsUpdateRequestDto requestDto) {
    Posts posts = findPost(id);

    posts.update(requestDto.getTitle(), requestDto.getContent());

    return id;
  }

  public List<PostsListResponseDto> findAllDesc(){
    return postsRepository.findAllDesc()
        .stream().map(PostsListResponseDto::new)
        .collect(Collectors.toList());
  }

  public PostsResponseDto findById(Long id) {
    Posts posts = findPost(id);

    return new PostsResponseDto(posts);
  }

  @Transactional
  public void delete(Long id){
    Posts post = findPost(id);

    postsRepository.delete(post);
  }

  private Posts findPost(Long id) {
    return postsRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당하는 게시물이 없습니다."));
  }
}
