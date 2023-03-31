package com.project.zoopiter.web;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 회원 세션 정보
 */
@Data
@AllArgsConstructor
public class LoginMember {
  private String userId;
  private String userPw;
  private String userNick;
  private String userEmail;
  private String gubun;
}
