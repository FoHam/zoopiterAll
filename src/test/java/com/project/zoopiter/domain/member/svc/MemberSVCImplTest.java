package com.project.zoopiter.domain.member.svc;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MemberSVCImplTest {

  @Autowired
  MemberSVC memberSVC;

  @Test
  @DisplayName("회원이메일 존재유무확인")
  void isExistEmail() {
    boolean exist = memberSVC.isExistEmail("test1@kh.com");
    Assertions.assertThat(exist).isTrue();

    exist = memberSVC.isExistEmail("test1@kh.com111");
    Assertions.assertThat(exist).isFalse();
  }

  @Test
  @DisplayName("회원아이디 존재유무확인")
  void isExistId() {
    boolean exist = memberSVC.isExistId("test1");
    Assertions.assertThat(exist).isTrue();

    exist = memberSVC.isExistId("test11");
    Assertions.assertThat(exist).isFalse();
  }
}