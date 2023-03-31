package com.project.zoopiter.domain.member.dao;

import com.project.zoopiter.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberDAOImpl implements MemberDAO {

  private final NamedParameterJdbcTemplate template;

  /**
   * 가입
   * @param member 회원정보
   * @return
   */
  @Override
  public Member save(Member member) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into member( ");
    sql.append(" USER_ID, ");
    sql.append(" USER_PW, ");
    sql.append(" USER_NICK, ");
    sql.append(" USER_EMAIL ");
    sql.append(") values( ");
    sql.append(" :userId, ");
    sql.append(" :userPw, ");
    sql.append(" :userNick, ");
    sql.append(" :userEmail) ");
//    sql.append(" :gubun) ");

    SqlParameterSource param = new BeanPropertySqlParameterSource(member);
    template.update(sql.toString(),param);

    return member;
  }

  /**
   * 회원정보수정
   *
   * @param userId 아이디
   * @param member 회원정보
   */
  @Override
  public void update(String userId, Member member) {
    StringBuffer sql = new StringBuffer();
    sql.append("update member ");
    sql.append("   set ");
//    sql.append("       USER_ID = :USER_ID, ");
    sql.append("       USER_PW = :USER_PW, ");
    sql.append("       USER_NICK = :USER_NICK, ");
    sql.append("       USER_EMAIL = :USER_EMAIL, ");
//    sql.append("       GUBUN = :GUBUN, ");
//    sql.append("       USER_PHOTO = :USER_PHOTO, ");
    sql.append("       USER_UPDATE = systimestamp ");
    sql.append(" where USER_ID = :USER_ID ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("USER_PW",member.getUserPw())
        .addValue("USER_NICK",member.getUserNick())
        .addValue("USER_EMAIL",member.getUserEmail())
//        .addValue("USER_PHOTO",member.getUserPhoto())
        .addValue("USER_ID",userId);

    template.update(sql.toString(),param);
  }

  /**
   * 조회 by email
   *
   * @param userEmail 이메일
   * @return
   */
  @Override
  public Optional<Member> findbyEmail(String userEmail) {
    StringBuffer sql = new StringBuffer();

    sql.append("select USER_ID, ");
    sql.append("    USER_PW, ");
    sql.append("    USER_NICK, ");
    sql.append("    USER_EMAIL, ");
    sql.append("    GUBUN, ");
    sql.append("    USER_PHOTO, ");
    sql.append("    USER_CREATE_DATE, ");
    sql.append("    USER_UPDATE ");
    sql.append("from member ");
    sql.append("where USER_EMAIL = :USER_EMAIL ");

    try {
      Map<String, String> param = Map.of("USER_EMAIL", userEmail);
      Member member = template.queryForObject(
          sql.toString(),
          param,
          BeanPropertyRowMapper.newInstance(Member.class)
      );
      return Optional.of(member);
    } catch (EmptyResultDataAccessException e) {
      //조회결과가 없는 경우
      return Optional.empty();
    }
  }

  /**
   * 조회 by user_id
   *
   * @param userId 아이디
   * @return
   */
  @Override
  public Optional<Member> findById(String userId) {
    StringBuffer sql = new StringBuffer();

    sql.append("select USER_ID, ");
    sql.append("    USER_PW, ");
    sql.append("    USER_NICK, ");
    sql.append("    USER_EMAIL, ");
    sql.append("    GUBUN, ");
    sql.append("    USER_PHOTO, ");
    sql.append("    USER_CREATE_DATE, ");
    sql.append("    USER_UPDATE ");
    sql.append("from member ");
    sql.append("where USER_ID = :USER_ID ");

    try {
      Map<String, String> param = Map.of("USER_ID", userId);
      Member member = template.queryForObject(
          sql.toString(),
          param,
          BeanPropertyRowMapper.newInstance(Member.class)
      );
      return Optional.of(member);
    } catch (EmptyResultDataAccessException e) {
      //조회결과가 없는 경우
      return Optional.empty();
    }
  }

  /**
   * 전체 조회
   *
   * @return
   */
  @Override
  public List<Member> findAll() {
    StringBuffer sql = new StringBuffer();

    sql.append("select USER_ID, ");
    sql.append("    USER_PW, ");
    sql.append("    USER_NICK, ");
    sql.append("    USER_EMAIL, ");
    sql.append("    GUBUN, ");
    sql.append("    USER_PHOTO, ");
    sql.append("    USER_CREATE_DATE, ");
    sql.append("    USER_UPDATE ");
    sql.append("from member ");
    sql.append("order by USER_CREATE_DATE desc ");

    List<Member> list = template.query(
        sql.toString(),
        BeanPropertyRowMapper.newInstance(Member.class)
    );

    return list;
  }

  /**
   * 탈퇴
   *
   * @param userId 아이디
   */
  @Override
  public void delete(String userId) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from member ");
    sql.append(" where USER_ID = :USER_ID ");

    Map<String, String> param = Map.of("USER_ID", userId);
    template.update(sql.toString(), param);
  }

  /**
   * @param userEmail 이메일
   * @return
   */
  @Override
  public boolean isExistEmail(String userEmail) {
    boolean flag = false;
    String sql = "select count(USER_EMAIL) from member where USER_EMAIL = :USER_EMAIL ";

    Map<String, String> param = Map.of("USER_EMAIL", userEmail);

    Integer cnt = template.queryForObject(sql, param, Integer.class);

    return cnt == 1 ? true : false;
  }

  /**
   * @param userId 아이디
   * @return
   */
  @Override
  public boolean isExistId(String userId) {
    boolean flag = false;
    String sql = "select count(USER_ID) from member where USER_ID = :USER_ID ";

    Map<String, String> param = Map.of("USER_ID", userId);

    Integer cnt = template.queryForObject(sql, param, Integer.class);

    return cnt == 1 ? true : false;
  }

  /**
   * 로그인
   *
   * @param userId 아이디
   * @param userPw 비밀번호
   * @return
   */
  @Override
  public Optional<Member> login(String userId, String userPw) {
    StringBuffer sql = new StringBuffer();
    sql.append("select USER_ID, USER_EMAIL, USER_NICK, GUBUN ");
    sql.append("  from member ");
    sql.append(" where USER_ID = :USER_ID and USER_PW = :USER_PW ");

    Map<String, String> param = Map.of("USER_ID", userId,"USER_PW",userPw);
    // 레코드1개를 반환할경우 query로 list를 반환받고 list.size() == 1 ? list.get(0) : null 처리하자!!
    List<Member> list = template.query(
        sql.toString(),
        param,
        BeanPropertyRowMapper.newInstance(Member.class) //자바객체 <=> 테이블 레코드 자동 매핑
    );

    return list.size() == 1 ? Optional.of(list.get(0)) : Optional.empty();

  }

  /**
   * 아이디 찾기
   *
   * @param userEmail 이메일
   * @return
   */
  @Override
  public Optional<String> findIdByEmail(String userEmail) {
    StringBuffer sql = new StringBuffer();
    sql.append("select USER_ID ");
    sql.append("  from member ");
    sql.append(" where USER_EMAIL = :USER_EMAIL ");

    Map<String, String> param = Map.of("USER_EMAIL", userEmail);
    List<String> result = template.query(
        sql.toString(),
        param,
        (rs, rowNum)->rs.getNString("USER_ID")
    );

    return (result.size() == 1) ? Optional.of(result.get(0)) : Optional.empty();
  }
}
