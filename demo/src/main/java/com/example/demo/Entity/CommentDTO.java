package com.example.demo.Entity;

import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {

  @GeneratedValue
  private long id;
  private String specificationId;
  private String writerNickname;
  private String date;
  private String content;
  private boolean isMine;

  public CommentDTO(long id, String specificationId,
      String writerNickname, String date,
      String content, boolean isMine) {
    this.id = id;
    this.specificationId = specificationId;
    this.writerNickname = writerNickname;
    this.date = date;
    this.content = content;
    this.isMine = isMine;
  }
}
