package com.project.zoopiter.web.form.member;

import lombok.Data;

@Data
public class JoinForm {
  private String userId;
  private String userPw;
  private String userPwChk;
  private String userEmail;
  private String userNick;
  private String gubun;
}
