package com.hanhwa.rsrvprogramh.dao;

import com.hanhwa.rsrvprogramh.model.ReserveRequest;
import com.hanhwa.rsrvprogramh.model.ReserveRequestInfo;
import com.hanhwa.rsrvprogramh.model.ReserveResponse;
import com.hanhwa.rsrvprogramh.model.ReserveResponseInfo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ReserveRepository {
    // JdbcTemplate : DAO객체에서 DB와 연동하기 위해 SQL연산들을 수행할 수 있도록 도와줌
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final String selectQuery = "SELECT * FROM room_reserve WHERE loc_cd = :locCd AND cust_no = :custNo AND inhs_nm = :inhsNm";
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

    public int insertReserve(ReserveRequest reserveRequest) {
        reserveRequest.getReserveRequestInfoList().get(0).setRsrvNo("2308743091");
        reserveRequest.getReserveRequestInfoList().get(0).setRsrvCmplDate(LocalDate.now());
        reserveRequest.getReserveRequestInfoList().get(0).setRoomRate("131000");
        SqlParameterSource params = new BeanPropertySqlParameterSource(reserveRequest.getReserveRequestInfoList().get(0));
        int rsrvNo = simpleJdbcInsert.execute(params);
        return rsrvNo;
    }

    public ReserveResponse selectReserve(ReserveRequest reserveRequest) {
        ReserveRequestInfo requestInfo = reserveRequest.getReserveRequestInfoList().get(0);
        System.out.println("값확인");
        System.out.println(requestInfo.toString());

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("locCd", requestInfo.getLocCd())
                .addValue("custNo", requestInfo.getCustNo())
                .addValue("inhsNm", requestInfo.getInhsNm());

        //namedParameterJdbcTemplate.query(selectQuery, params)
        ReserveResponseInfo responseInfo = namedParameterJdbcTemplate.queryForObject(selectQuery, params, new ResponseMapper());
        ReserveResponse response = new ReserveResponse();
        response.setReserveResponseInfoList(new ArrayList<>());
        response.getReserveResponseInfoList().add(responseInfo);
        System.out.println("응답 생성");
        System.out.println(response.toString());
        return response;
    }

    // RowMapper : JDBC의 인터페이스인 ResultSet에서 원하는 객체로 가상화를 변환하는 역할
    private static final class ResponseMapper implements RowMapper<ReserveResponseInfo> {
        @Override
        public ReserveResponseInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            ReserveResponseInfo responseInfo = new ReserveResponseInfo();
            responseInfo.setProcDs(LocalDateTime.now());
            responseInfo.setProcCd("00");
            responseInfo.setCustNo(rs.getString("cust_no"));
            responseInfo.setMembNo(rs.getString("memb_no"));
            responseInfo.setRsrvNo(rs.getString("rsrv_no"));
            responseInfo.setRoomRate(rs.getString("room_rate"));
            responseInfo.setRsrvCmplDate(LocalDate.now());
            responseInfo.setRsrvCmplSt("Y");
            return responseInfo;
        }
    }
}
