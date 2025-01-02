/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : 이현정
 * Create Date : 2024. 11. 05.
 * DESC : Link Check(BLF) Controller
 *****************************************************************/
package kr.co.ecoletree.linkCheck.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.ecoletree.common.ETCommonConst;
import kr.co.ecoletree.common.api.HttpAPIConnection;
import kr.co.ecoletree.common.base.web.ETBaseController;
import kr.co.ecoletree.common.util.ResultUtil;
import kr.co.ecoletree.linkCheck.service.LinkCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/")
public class LinkCheckController extends ETBaseController {

    @Autowired
    LinkCheckService service;

    public static final String LINK_URL = "/engine/api/blf/check";

    public String home(){
        return "home";
    }
    /**
     * Form이 정상적으로 동작하였을때 api를 호출함
     *
     * @param params
     * @return map
     * @throws Exception
     */
    @RequestMapping("/callBrokenLinkFinder")
    public @ResponseBody Map<String, Object> callBrokenLinkFinder(@RequestBody Map<String, Object> params) throws Exception {
        String resultMsg = ETCommonConst.SUCCESS;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        org.json.JSONObject json =  new org.json.JSONObject(params);
        Map<String, Object> tempMap = new HashMap<String, Object>();
        tempMap.put("scanUrl", json.get("scanUrl").toString());
        tempMap.put("fullScanJson", "true");
        tempMap.put("ignoreOptions", json.get("ignoreOptions").toString());
        tempMap.put("excludeUrl", json.get("excludeUrl").toString());
        tempMap.put("includeOptions", null);
        tempMap.put("includeUrl", null);

        Map<String, Object> historyMap = service.getSameHistory(tempMap);
        if (historyMap != null && historyMap.containsKey("id")) {
            resultMap.put("historyId", historyMap.get("id"));
            resultMap.put("historyCount", historyMap.get("count"));
        }

        Map<String, Object> checkMap = callCheckLinkApi(params); // api 호출
        if (checkMap.containsKey("sessionId")) {
            resultMap.put("sessionId", checkMap.get("sessionId"));

        } else {
            resultMsg = ETCommonConst.FAILED;
            if (checkMap.containsKey("message")) {
                resultMap.put("resultMsg", checkMap.get("message"));
            } else {
                resultMap.put("resultMsg", resultMsg);
            }

        }
        return ResultUtil.getResultMap(true, resultMap, resultMsg);
    }

    /**
     * 실행한 session_Id가 실행을 마쳤는지 확인
     *
     * @param params
     * @return map
     */
    @RequestMapping("/doCheck")
    public @ResponseBody Map<String, Object> doCheck(@RequestBody Map<String, Object> params){
        Map<String, Object> resultMap = service.getCheckFinishedCnt(params);
        return resultMap;
    }

    /**
     * check_session_id 값으로 상태가 Error 인 목록 조회
     *
     * @param params
     * @return map
     * @throws Exception
     */
    @RequestMapping("/getErrorResult")
    public @ResponseBody Map<String, Object> getProcessingResult(@RequestBody Map<String, Object> params) throws Exception {
        Map<String, Object> resultMap = service.getErrorResultList(params);
        return resultMap;
    }

    /**
     * check API 호출
     *
     * @param params
     * @return map
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> callCheckLinkApi(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {

            HttpAPIConnection connection = new HttpAPIConnection();
            String strParams = connection.createUrlParameterJSON(params);

            Map<String, String> header = new HashMap<>();
            header.put("Content-Type", "application/json");
            String serverUrl = "http://localhost:8188";

            String result = connection.callPostHttpApi(serverUrl+LINK_URL, strParams, true, header);
            resultMap = new ObjectMapper().readValue(result, Map.class);

        } catch (Exception e) {
            if (e.getMessage() != null) {
                logError(e.getMessage());
                resultMap.put("message", e.getMessage());
            }
        }
        return resultMap;
    }


}
