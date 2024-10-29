package com.example.demo.Controller;

import com.example.demo.Entity.Comment;
import com.example.demo.Entity.CommentDTO;
import com.example.demo.Entity.CommentSendDTO;
import com.example.demo.Entity.Pagination;
import com.example.demo.Service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @MessageMapping("/load/{specificationId}")
  @SendTo("/apiComment/load/{specificationId}")
  public Slice<CommentDTO> findMessage(@DestinationVariable String specificationId,
                                    @Payload Pagination pagination){
    return commentService.loadComments(specificationId, pagination);
  }

  @MessageMapping("/save/{specificationId}")
  @SendTo("/apiComment/save/{specificationId}")
  public Comment sendMesssages(@DestinationVariable String specificationId,
                                @Payload CommentSendDTO commentSendDTO){
    return commentService.saveComment(specificationId, commentSendDTO);
  }

}
