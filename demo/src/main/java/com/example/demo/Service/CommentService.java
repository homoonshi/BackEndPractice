package com.example.demo.Service;

import com.example.demo.Entity.Comment;
import com.example.demo.Entity.CommentDTO;
import com.example.demo.Entity.CommentSendDTO;
import com.example.demo.Entity.Pagination;
import com.example.demo.Repository.CommentRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;

  public Long getIndex(String specificationId){

    Long index = commentRepository.findMaxIdBySpecificationId(specificationId);

    return index;
  }

  public Slice<CommentDTO> loadComments(String specificationId, Pagination pagination) {
    Pageable pageable = PageRequest.of(0, pagination.getSize());

    System.out.println("id"+pagination.getId());
    // 먼저 쿼리 결과 확인을 위해 디버깅
    Slice<Comment> comments = commentRepository.findClosestCommentsBySpecificationId(
            specificationId, pagination.getId(), pageable
    );

    // 결과가 있는지 확인하는 디버그 출력
    if (comments.isEmpty()) {
      System.out.println("No comments found for specificationId " + specificationId);
    } else {
      System.out.println("Comments fetched: " + comments.getContent().size());
    }

    // DTO 변환 후 디버깅
    Slice<CommentDTO> commentDTOS = comments.map(comment -> {
      boolean isMine = comment.getWriterNickname().equals(pagination.getUserNickname());
      CommentDTO commentDTO = new CommentDTO(
              comment.getId(),
              comment.getSpecificationId(),
              comment.getWriterNickname(),
              comment.getDate(),
              comment.getContent(),
              isMine
      );
      System.out.println(commentDTO.getId());
      return commentDTO;
    });

    if (commentDTOS.isEmpty()) {
      System.out.println("CommentDTO conversion resulted in an empty Slice.");
    } else {
      System.out.println("CommentDTOs created: " + commentDTOS.getContent().size());
    }

    return commentDTOS;
  }

  public Comment saveComment(String specificationId, CommentSendDTO commentSendDTO){
    Comment comment = new Comment();

    comment.setSpecificationId(specificationId);
    comment.setWriterNickname(commentSendDTO.getWriterNickname());
    comment.setContent(commentSendDTO.getContent());

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedNow = now.format(formatter);

    comment.setDate(formattedNow);

    commentRepository.save(comment);

    return comment;
  }

}
