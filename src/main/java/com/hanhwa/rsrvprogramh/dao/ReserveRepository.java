package com.hanhwa.rsrvprogramh.dao;

import com.hanhwa.rsrvprogramh.model.ReserveRequest;
import com.hanhwa.rsrvprogramh.model.ReserveRequestInfo;
import com.hanhwa.rsrvprogramh.model.ReserveResponse;
import com.hanhwa.rsrvprogramh.model.ReserveResponseInfo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;

@Repository
public class ReserveRepository {
    // JdbcTemplate : DAO객체에서 DB와 연동하기 위해 SQL연산들을 수행할 수 있도록 도와줌
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final String selectQuery = "SELECT * FROM room_reserve WHERE rsrv_no = :rsrvNo";
    public ReserveRepository(DataSource dataSource) {
        // NamedParameterJdbcTemplate : 기존의 JdbcTemplate과 달리 ?대신 :파라미터명을 이용하여 파라미터를 바인딩함(식별성 보완)
        // Java : 클래스의 인스턴스 변수명으로 주로 Camel Case 사용 / SQL : Column명으로 주로 SnakeCase 사용
        // 서로 통신할 때 parsing하는 작업을 지원함
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                // withTableName : Insert할 테이블 이름 설정
                .withTableName("room_reserve")
                .usingGeneratedKeyColumns("cust_no");
    }

    public String insertReserve(ReserveRequest reserveRequest) {
        reserveRequest.getReserveRequestInfoList().get(0).setRsrvNo("2308743091");
        reserveRequest.getReserveRequestInfoList().get(0).setRsrvCmplDate(LocalDate.now());
        reserveRequest.getReserveRequestInfoList().get(0).setRoomRate("131000");
        System.out.println(reserveRequest.getReserveRequestInfoList().get(0));
        SqlParameterSource params = new BeanPropertySqlParameterSource(reserveRequest.getReserveRequestInfoList().get(0));
        int rsrvNo = simpleJdbcInsert.execute(params);
        System.out.println(rsrvNo);
        return "2308743091";
    }

    public ReserveResponse selectReserve(String rsrvNo) {
        RowMapper<ReserveResponseInfo> rowMapper = BeanPropertyRowMapper.newInstance(ReserveResponseInfo.class);
        SqlParameterSource params = new MapSqlParameterSource("rsrvNo", rsrvNo);
        // 모든 Bean Property를 담아주는 RowMapper을 자동으로 생성해주는 BeanPropertyRowMapper 객체.
        //namedParameterJdbcTemplate.query(selectQuery, params)
        ReserveResponse response = new ReserveResponse();
        return response;
    }

}
