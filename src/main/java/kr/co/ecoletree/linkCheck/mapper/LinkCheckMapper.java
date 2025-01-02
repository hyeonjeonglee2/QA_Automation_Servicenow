/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : 이현정
 * Create Date : 2024. 11. 05.
 * DESC : Link Check Mapper
 *****************************************************************/
package kr.co.ecoletree.linkCheck.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LinkCheckMapper {
    /**
     * 과거에 유사한 기록이 있는 경우 검색 -> 프로그레스바 생성 이유
     * @param params
     * @return
     */
    public Map<String, Object> selectSameHistory(Map<String, Object> params);

    /**
     * session check id로 result의 count 조회
     *
     * @param params
     * @return
     */
    public int selectResultCountBySessionId(Map<String, Object> params);

    /**
     * session_state가 completed 인 결과 목록 조회
     * @param params
     * @return
     */
    public List<Map<String, Object>> selectCompletedResult(Map<String, Object> params);

    /**
     * session id로 session state 가 completed 인 count 조회
     *
     * @param params
     * @return
     */
    public int selectCheckCompletedCnt(Map<String, Object> params);

    /**
     * result 가 Error 인 결과 목록 조회
     * @param params
     * @return
     */
    public List<Map<String, Object>> selectCheckErrorResult(Map<String, Object> params);

    /**
     * result 가 Error 인 결과 카운트
     * @param params
     * @return
     */
    public int selectCheckErrorResultCnt(Map<String, Object> params);
}
