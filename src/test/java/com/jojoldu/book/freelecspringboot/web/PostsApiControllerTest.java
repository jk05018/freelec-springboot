package com.jojoldu.book.freelecspringboot.web;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.jojoldu.book.freelecspringboot.domain.posts.Posts;
import com.jojoldu.book.freelecspringboot.domain.posts.PostsRepository;
import com.jojoldu.book.freelecspringboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.freelecspringboot.web.dto.PostsUpdateRequestDto;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private PostsRepository postsRepository;

  @AfterEach
  void tearDown() {
    postsRepository.deleteAll();
  }

  @Test
  void 프스트_등록() {
    // given
    final LocalDateTime now = LocalDateTime.now();

    String title = "title";
    String content = "content";
    // 만약 objectNode말고 dto로 만들어 사용하려면 builder 패턴을 사용하는 것이 좋아보인다.
    PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
        .title(title)
        .content(content)
        .author("author")
        .build();

    String url = "http://localhost:" + port + "/api/v1/posts";

    // when
    ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto,
        Long.class);

    // then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isGreaterThan(0L);

    List<Posts> posts = postsRepository.findAll();

    assertThat(posts.get(0).getTitle()).isEqualTo(title);
    assertThat(posts.get(0).getContent()).isEqualTo(content);


    assertThat(posts.get(0).getCreatedDate()).isAfter(now);
    assertThat(posts.get(0).getModifiedDate()).isAfter(now);


  }

  @Test
  void 프스트_수정() {
    // given
    String title = "title";
    String content = "content";
    Posts savedPosts = postsRepository.save(Posts.builder()
        .title(title)
        .content(content)
        .author("author")
        .build());

    Long updatedId = savedPosts.getId();
    String expectedTitle = "title2";
    String expectedContent = "content2";

    PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
        .title(expectedTitle)
        .content(expectedContent)
        .build();

    String url = "http://localhost:" + port + "/api/v1/posts/" + updatedId;

    HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

    // when
    ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT,
        requestEntity, Long.class);

    // then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isGreaterThan(0L);

    List<Posts> posts = postsRepository.findAll();

    assertThat(posts.get(0).getTitle()).isEqualTo(expectedTitle);
    assertThat(posts.get(0).getContent()).isEqualTo(expectedContent);
  }
}
