CREATE TABLE room_reserve (
    /* UUID : 안전하지만 VARCHAR/BINARY(16)사용으로 메모리를 더 많이 차지하고
          Insert시 시간이 더 걸림(서버에서 UUID 생성) */
    rsrv_no VARCHAR(15) NOT NULL comment '예약 번호',
    loc_cd VARCHAR(6) NOT NULL comment '객장 코드',
    arrv_date DATE NOT NULL comment '투숙 시작 일자',
    ovnt_cnt VARCHAR(2) NOT NULL comment '예약 박수',
    room_type_cd VARCHAR(5) NOT NULL comment '객실 타입 코드',
    rsrv_loc_div_cd VARCHAR(2) NOT NULL comment '객장 구분 코드',
    cust_no VARCHAR(15) NOT NULL DEFAULT '0000000002' comment '회원 번호',
    memb_no VARCHAR(9) NULL comment '한화 리조트 고객 코드',
    rsrv_room_cnt VARCHAR(2) NOT NULL comment '예약 객실 수',
    room_rate INT(9) NOT NULL comment '객실 요금',
    cont_no VARCHAR(10) NULL comment '계약 번호',
    pakg_no VARCHAR(2) NULL comment '패키지 번호',
    cpon_no VARCHAR(2) NULL comment '쿠폰 번호',
    inhs_nm VARCHAR(30) NOT NULL comment '투숙 고객명',
    inhs_phone_no2 VARCHAR(5) NOT NULL comment '투숙 고객 전화번호1',
    inhs_phone_no3 VARCHAR(5) NOT NULL comment '투숙 고객 전화번호2',
    inhs_phone_no4 VARCHAR(5) NOT NULL comment '투숙 고객 전화번호3',
    cust_nm VARCHAR(30) NOT NULL comment '회원명',
    cust_phone_no2 VARCHAR(5) NOT NULL comment '회원 전화번호1',
    cust_phone_no3 VARCHAR(5) NOT NULL comment '회원 전화번호2',
    cust_phone_no4 VARCHAR(5) NOT NULL comment '회원 전화번호3',
    rsrv_cmpl_date DATE NOT NULL comment '예약 완료 날짜',
    CONSTRAINT room_reserve_pk PRIMARY KEY(rsrv_no)
);