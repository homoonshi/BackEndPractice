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

  public Slice<CommentDTO> loadComments(String specificationId, Pagination pagination){

    Pageable pageable = PageRequest.of(0, pagination.getSize());
    Slice<Comment> comments =
        commentRepository.findBySpecificationIdAndIdLessThan
            (specificationId, pagination.getId(), pageable);

    Slice<CommentDTO> commentDTOS = comments.map(comment -> {
      boolean isMine = comment.getWriterNickname().equals(pagination.getUserNickname());
      return new CommentDTO(
          comment.getId(),
          comment.getSpecificationId(),
          comment.getWriterNickname(),
          comment.getDate(),
          comment.getContent(),
          isMine
      );
    });

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
