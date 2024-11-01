package com.example.demo.Service;

import com.example.demo.Entity.Comment;
import com.example.demo.Repository.CommentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class CommentServiceTest {

  private final CommentRepository commentRepository;

  @Autowired
  public CommentServiceTest(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  @Test
  void save() {

    // Given
    Comment comment = new Comment();
    comment.setSpecificationId("3");
    comment.setContent("테스트입니다");
    comment.setWriterNickname("커비사냥꾼");
    comment.setDate("2024-01-01 11:11:11");

    // When
    commentRepository.save(comment);

    // Then
    Comment findComment = commentRepository.findBySpecificationId(comment.getSpecificationId());
    Assertions.assertThat(findComment.getSpecificationId()).isEqualTo(comment.getSpecificationId());
    Assertions.assertThat(findComment.getContent()).isEqualTo(comment.getContent());
    Assertions.assertThat(findComment.getWriterNickname()).isEqualTo(comment.getWriterNickname());
    Assertions.assertThat(findComment.getDate()).isEqualTo(comment.getDate());

  }
}
