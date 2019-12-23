package com.cool.demo.common.service;

import com.alibaba.fastjson.JSON;
import com.cool.demo.common.entity.DingCard;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 钉钉服务类
 * Created by vincent on 2019-12-20
 */
@Service
public class DingTalkService {

    private static final String APP_KEY = "";

    private static final String APP_SECRET = "";

    private static final Long AGENT_ID = 0L;

    /**
     * 获取access_token
     */
    public String getAccessToken() {
        OapiGettokenRequest request;
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
            request = new OapiGettokenRequest();
            request.setAppkey(APP_KEY);
            request.setAppsecret(APP_SECRET);
            request.setHttpMethod("GET");
            OapiGettokenResponse response = client.execute(request);
            return response.getAccessToken();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取用户userid
     * @param accessToken
     * @param requestAuthCode
     */
    public String getUserId(String accessToken, String requestAuthCode) {
        OapiUserGetuserinfoResponse response;
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getuserinfo");
            OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
            request.setCode(requestAuthCode);
            request.setHttpMethod("GET");
            response = client.execute(request, accessToken);
            System.out.println(JSON.toJSONString(response));
            return response.getUserid();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取用户详情
     * @param accessToken
     * @param userId
     */
    public Object getUserDetail(String accessToken, String userId) {
        OapiUserGetRequest request;
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
            request = new OapiUserGetRequest();
            request.setUserid(userId);
            request.setHttpMethod("GET");
            OapiUserGetResponse response = client.execute(request, accessToken);
            return response;
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送工作通知消息
     * @param userIds 接收者的用户userid列表，最大列表长度：100
     * @param card 消息内容
     * @return task_id 消息任务id
     */
    public Long asyncsend(List<String> userIds, DingCard card, String accessToken){
        OapiMessageCorpconversationAsyncsendV2Response response;
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");

            OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
            request.setUseridList(toString(userIds));
            request.setAgentId(AGENT_ID);
            request.setToAllUser(false);

            OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();

            msg.setMsgtype("action_card");
            msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
            msg.getActionCard().setTitle(card.getTitle()); // 标题
            msg.getActionCard().setMarkdown(card.getMarkdown()); // 内容
            msg.getActionCard().setBtnOrientation(String.valueOf(card.getBtnOrientation())); // 按钮排列方式
            List<OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList> btnJsonList = new ArrayList<>();
            for (DingCard.BtnJsonList btnJson : card.getBtnJsonList()){
                OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList jsonList = new OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList();
                jsonList.setTitle(btnJson.getTitle()); // 按钮标题
                jsonList.setActionUrl(btnJson.getActionUrl()); // 按钮超链接
                btnJsonList.add(jsonList);
            }
            msg.getActionCard().setBtnJsonList(btnJsonList); // 按钮

            request.setMsg(msg);
            response = client.execute(request, accessToken);
            return response.getTaskId();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 查询工作通知消息的发送结果
     * @param taskId
     * @param accessToken
     * @return 发送失败的用户id
     */
    public List<String> getSendResult(Long taskId, String accessToken){
        OapiMessageCorpconversationGetsendresultResponse response;
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/getsendresult");
            OapiMessageCorpconversationGetsendresultRequest request  = new OapiMessageCorpconversationGetsendresultRequest();
            request.setAgentId(AGENT_ID);
            request.setTaskId(taskId);
            response = client.execute(request, accessToken);
            return response.getSendResult().getFailedUserIdList();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 入参字符串数组格式化
     */
    private static String toString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            sb.append(str).append(",");
        }
        if (list.size()>0 && sb.length()>0){
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

}
