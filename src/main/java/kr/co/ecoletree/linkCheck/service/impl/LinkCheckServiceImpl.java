/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : 이현정
 * Create Date : 2024. 11. 05.
 * DESC : Link Check Service Impl
 *****************************************************************/
package kr.co.ecoletree.linkCheck.service.impl;

import kr.co.ecoletree.common.base.service.ETBaseService;
import kr.co.ecoletree.linkCheck.mapper.LinkCheckMapper;
import kr.co.ecoletree.linkCheck.service.LinkCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LinkCheckServiceImpl extends ETBaseService implements LinkCheckService {

    @Autowired
    LinkCheckMapper mapper;

    /**
     * 과거에 유사한 기록이 있는 경우 검색 
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> getSameHistory(Map<String, Object> params) throws Exception {
        Map<String, Object> resultMap = mapper.selectSameHistory(params);
        if (resultMap != null && resultMap.containsKey("sessionId")) {
            int cnt = mapper.selectResultCountBySessionId(resultMap);
            resultMap.put("count", cnt);
            resultMap.put("id", resultMap.get("sessionId"));
        }

        return resultMap;
    }

    /**
     *
     * 실행한 check_session_Id가 실행을 마쳤는지 확인
     */
    @Override
    public Map<String, Object> getCheckFinishedCnt(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Map<String, Object>> list = mapper.selectCompletedResult(params);
        int totalCount = mapper.selectCheckCompletedCnt(params);
        resultMap.put("recordsTotal", totalCount);
        resultMap.put("recordsFiltered", totalCount);
        resultMap.put("data", list);

        return resultMap;
    }

    /**
     * 상태값이 Error인 목록 조회
     */
    @Override
    public Map<String, Object> getErrorResultList(Map<String, Object> params){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Map<String, Object>> list = mapper.selectCheckErrorResult(params);
        int totalCount = mapper.selectCheckErrorResultCnt(params);
        resultMap.put("recordsTotal", totalCount);
        resultMap.put("recordsFiltered", totalCount);
        resultMap.put("data", list);

        return resultMap;
    }
}
