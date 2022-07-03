package com.jojoldu.book.freelecspringboot.domain.posts;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostsRepositoryTest {

  @Autowired
  PostsRepository postsRepository;

  @AfterEach
  void setUp() {
    postsRepository.deleteAll();
  }

  @Test
  void 게시글저장_불러오기() {
    // given
    String title = "테스트 게시글";
    String content = "테스트 본문";

    postsRepository.save(Posts.builder()
        .title(title)
        .content(content)
        .author("hani@gmail.com")
        .build());

    // when
    List<Posts> postList = postsRepository.findAll();

    // then
     Posts posts = postList.get(0);
    assertThat(posts.getTitle()).isEqualTo(title);
    assertThat(posts.getContent()).isEqualTo(content);


  }
}
