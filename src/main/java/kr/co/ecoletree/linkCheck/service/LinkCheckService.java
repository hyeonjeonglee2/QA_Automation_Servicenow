/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : 이현정
 * Create Date : 2024. 11. 05.
 * DESC : Link Check Service
 *****************************************************************/
package kr.co.ecoletree.linkCheck.service;

import java.util.Map;


public interface LinkCheckService {
    /**
     * 과거에 유사한 기록이 있는 경우 검색 
     *
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String, Object> getSameHistory(Map<String, Object> params) throws Exception;

    /**
     * 검색을 끝냈는 지에 대한 상태 카운트
     * @param params
     * @return
     */
    public Map<String, Object> getCheckFinishedCnt(Map<String, Object> params);

    /**
     * 상태 값이 에러인 목록 조회
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String, Object> getErrorResultList(Map<String, Object> params) throws Exception;
}
