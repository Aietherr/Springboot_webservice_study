package com.study.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After // 테스트가 끝난후 전부삭제
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void save_load() {

        // 값 할당

        String title = "Test";
        String content = "asdf";

        postsRepository.save(Posts.builder().title(title).content(content).author("Tester").build());

        List<Posts> postsList = postsRepository.findAll();


        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }
}
