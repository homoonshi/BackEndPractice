package com.example.demo.Controller;

import com.example.demo.Entity.Comment;
import com.example.demo.Entity.CommentDTO;
import com.example.demo.Entity.CommentSendDTO;
import com.example.demo.Entity.Pagination;
import com.example.demo.Service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @GetMapping("/index")
  public ResponseEntity<Long> findIndex(@RequestParam String specificationId){
    return new ResponseEntity<>(commentService.getIndex(specificationId), HttpStatus.OK);
  }


  @GetMapping
  public Slice<CommentDTO> findMessage(@RequestParam String specificationId,
                                       @RequestParam long id,
                                       @RequestParam int size,
                                       @RequestParam String userNickname){

    Pagination pagination = new Pagination();
    pagination.setId(id);
    pagination.setSize(size);
    pagination.setUserNickname(userNickname);

    return commentService.loadComments(specificationId, pagination);
  }

  @MessageMapping("/save/{specificationId}")
  @SendTo("/apiComment/save/{specificationId}")
  public Comment sendMesssages(@DestinationVariable String specificationId,
                                @Payload CommentSendDTO commentSendDTO){
    return commentService.saveComment(specificationId, commentSendDTO);
  }

}
