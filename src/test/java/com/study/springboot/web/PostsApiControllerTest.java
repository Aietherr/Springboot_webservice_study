package com.study.springboot.web;

import com.study.springboot.domain.posts.Posts;
import com.study.springboot.domain.posts.PostsRepository;
import com.study.springboot.web.dto.PostsSaveRequestDto;
import com.study.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void cleanup() throws Exception {
        postsRepository.deleteAll();;
    }

    @Test
    public void Posts_save() throws Exception {

        String title = "ApiTest";
        String content = "ㅁㄴㅇㄹ";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder().title(title).content(content).author("Tester").build();
    }

    @Test
    public void Posts_update() throws Exception {

        Posts savedPosts = postsRepository.save(Posts.builder().title("Test").content("ㅁㄴㅇㄹ").author("Tester").build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "Test2";
        String expectedContent = "asdf";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder().title(expectedTitle).content(expectedContent).build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();

        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}
