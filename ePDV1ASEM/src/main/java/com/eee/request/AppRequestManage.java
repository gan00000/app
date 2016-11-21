package com.eee.request;

import android.app.Activity;

import com.efun.core.res.EfunResConfiguration;
import com.efun.jqzs.sm.R;
import com.facebook.efun.EfunResourceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Efun on 2016/10/9.
 */

public class AppRequestManage {

    public static final String androidapp = "androidapp";

    public static String getLoginUrlRelate(Activity activity,String relate){
       return activity.getResources().getString(R.string.eee_login_url) + EfunResourceUtil.findStringByName(activity,relate);
    }

    public static String getLoginUrlRelate(Activity activity,int relate){
        return activity.getResources().getString(R.string.eee_login_url) +  activity.getResources().getString(relate);
    }

    public static void appLogin(Activity activity, String loginName, String password, AppRequest.RequestCallBack callback){
        Map<String,String> loginData = new HashMap<>();
        loginData.put("loginName",loginName);
        loginData.put("loginPwd",password);
        loginData.put("gameCode", EfunResConfiguration.getGameCode(activity));
        loginData.put("language",EfunResConfiguration.getSDKLanguage(activity));
        loginData.put("source",androidapp);

        AppRequest appRequest = new AppRequest(activity);

        appRequest.request(getLoginUrlRelate(activity,R.string.interface_login),loginData,callback);
    }

    public static void appThirdLogin(Activity activity, String loginId,String thirdPlate,
                                     String apps,
                                     String partner,
                                     AppRequest.RequestCallBack callback){
        Map<String,String> loginData = new HashMap<>();
        loginData.put("loginid",loginId);
        loginData.put("thirdPlate",thirdPlate);
        loginData.put("partner",partner);
        loginData.put("partner",apps);
        loginData.put("gameCode", EfunResConfiguration.getGameCode(activity));
        loginData.put("language",EfunResConfiguration.getSDKLanguage(activity));
        loginData.put("deviceType","android");

        AppRequest appRequest = new AppRequest(activity);

        appRequest.request(getLoginUrlRelate(activity,R.string.interface_third_login),loginData,callback);
    }

    public static void appRegister(Activity activity, String loginName,String loginPwd,
                                     String platForm,
                                     String code,
                                     String phone,
                                     String email,
                                     AppRequest.RequestCallBack callback){
        Map<String,String> loginData = new HashMap<>();
        loginData.put("loginName",loginName);
        loginData.put("loginPwd",loginPwd);
        loginData.put("platForm","web");
        loginData.put("code",code);
        loginData.put("phone",phone);
        loginData.put("email",email);
        loginData.put("gameCode", EfunResConfiguration.getGameCode(activity));
        loginData.put("language",EfunResConfiguration.getSDKLanguage(activity));
        loginData.put("deviceType","android");

        AppRequest appRequest = new AppRequest(activity);

        appRequest.request(getLoginUrlRelate(activity,R.string.interface_third_register),loginData,callback);
    }


}
