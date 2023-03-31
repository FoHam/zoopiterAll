package com.project.zoopiter.domain.member.dao;

import com.project.zoopiter.domain.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberDAOImplTest {

  @Autowired
  private MemberDAO memberDAO;

  @Test
  @Order(1)
  @DisplayName("가입")
  void save() {
    Member member = new Member();
    member.setUserId("member2");
    member.setUserPw("member4321");
    member.setUserEmail("member22@gmail.com");
    member.setUserNick("회원4");
//    member.setGubun("M0101");

    Member savedMember = memberDAO.save(member);

    Assertions.assertThat(savedMember.getUserId()).isEqualTo("member2");
    Assertions.assertThat(savedMember.getUserPw()).isEqualTo("member4321");
    Assertions.assertThat(savedMember.getUserEmail()).isEqualTo("member22@gmail.com");
    Assertions.assertThat(savedMember.getUserNick()).isEqualTo("회원4");
//    Assertions.assertThat(savedMember.getGubun()).isEqualTo("M0101");
  }

    @Test
    @Order(2)
    @DisplayName("수정")
    void update(){
      String userId = "member2";
      Member member = new Member();
      member.setUserPw("member9999");
      member.setUserNick("회원9");
      member.setUserEmail("member99@gmail.com");

      memberDAO.update(userId,member);
      Optional<Member> findedMember = memberDAO.findById(userId);
      Assertions.assertThat(findedMember.get().getUserPw()).isEqualTo(member.getUserPw());
      Assertions.assertThat(findedMember.get().getUserNick()).isEqualTo(member.getUserNick());
      Assertions.assertThat(findedMember.get().getUserEmail()).isEqualTo(member.getUserEmail());
    }

    @Test
    @Order(3)
    @DisplayName("조회 by email")
    void findbyEmail(){
      String userEmail = "member99@gmail.com";
      Optional<Member> member = memberDAO.findbyEmail(userEmail);
      Member findedMember = member.orElseThrow();
      Assertions.assertThat(findedMember.getUserPw()).isEqualTo("member9999");
      Assertions.assertThat(findedMember.getUserNick()).isEqualTo("회원9");
      Assertions.assertThat(findedMember.getUserEmail()).isEqualTo("member99@gmail.com");
    }

    @Test
    @Order(4)
    @DisplayName("조회 by user_id")
    void findById(){
      String userId = "member2";
      Optional<Member> member = memberDAO.findById(userId);
      Member findedMember = member.orElseThrow();
      Assertions.assertThat(findedMember.getUserPw()).isEqualTo("member9999");
      Assertions.assertThat(findedMember.getUserNick()).isEqualTo("회원9");
      Assertions.assertThat(findedMember.getUserEmail()).isEqualTo("member99@gmail.com");
    }

    @Test
    @Order(5)
    @DisplayName("전체 조회")
    void findAll(){
      List<Member> list = memberDAO.findAll();
      Assertions.assertThat(list.size()).isGreaterThan(0);
    }

    @Test
    @Order(6)
    @DisplayName("탈퇴")
    void delete(){
      String userId = "member2";
      memberDAO.delete(userId);
      Optional<Member> findedMember = memberDAO.findById(userId);
      Assertions.assertThatThrownBy(()->findedMember.orElseThrow())
          .isInstanceOf(NoSuchElementException.class);

    }

    @Test
    @Order(7)
    @DisplayName("이메일유무")
    void isExistEmail(){
      String userEmail = "test1@kh.com";
      boolean exist = memberDAO.isExistEmail(userEmail);
      Assertions.assertThat(exist).isTrue();
    }

  @Test
  @Order(8)
  @DisplayName("이메일유무2")
  void isExistEmail2(){
    String userEmail = "asd@kh.com";
    boolean exist = memberDAO.isExistEmail(userEmail);
    Assertions.assertThat(exist).isFalse();
  }

    @Test
    @Order(9)
    @DisplayName("아이디유무")
    void isExistId(){
      String userId = "test1";
      boolean exist = memberDAO.isExistId(userId);
      Assertions.assertThat(exist).isTrue();
    }

  @Test
  @Order(10)
  @DisplayName("아이디유무2")
  void isExistId2(){
    String userId = "asdasd";
    boolean exist = memberDAO.isExistId(userId);
    Assertions.assertThat(exist).isFalse();
  }

//    @Test
//    @DisplayName("로그인")
//    void login(){}
  }

