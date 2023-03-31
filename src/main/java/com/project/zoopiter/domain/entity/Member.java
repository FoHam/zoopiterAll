package com.project.zoopiter.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
  private String userId;
  private String userPw;
  private String userNick;
  private String userEmail;
  private String gubun;
//  private byte[] userPhoto;
//  private LocalDateTime cdate;
//  private LocalDateTime udate;
}
