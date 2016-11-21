package com.efun.platform.http.dao.impl;

import com.efun.platform.http.request.bean.BaseRequestBean;
import com.efun.platform.http.response.bean.BaseResponseBean;

/**
 * 接口
 * 
 * @author itxuxxey
 * 
 */
public interface IPlatformRequest {
	/**
	 * 获取本地数据
	 */
	public static final int REQ_LOCAL_DATA = -1;

	/**
	 * 资讯-获取首页信息
	 */
	public static final int REQ_SUMMARY_HOME = 1;
	
	/**
	 * 资讯-点赞
	 */
	public static final int REQ_SUMMARY_PRAISE = 10;

	/**
	 * 资讯-获取资讯列表
	 */
	public static final int REQ_SUMMARY_LIST_ALL = 11;
	public static final int REQ_SUMMARY_LIST_NEWS = 12;
	public static final int REQ_SUMMARY_LIST_BULLETIN = 13;
	public static final int REQ_SUMMARY_LIST_ACTIVITY = 14;
	public static final int REQ_SUMMARY_LIST_STRATEGY = 15;
	public static final int REQ_SUMMARY_LIST_VIDEO = 17;
	public static final int REQ_SUMMARY_LIST_ABOUT_US = 18;

	/**
	 * 好康-活動列表
	 */
	public static final int REQ_ACT_LIST = 32;

	/**
	 * 用户-更新用户信息
	 */
	public static final int REQ_USER_UPDATE_INFO = 5;

	/**
	 * 用户-更新用户头像
	 */
	public static final int REQ_USER_UPDATE_HEADER = 51;
	
	/**
	 * 用户-更新用户头像-new
	 */
	public static final int REQ_USER_UPDATE_HEADER_NEW = 54;

	/**
	 * /** 账号-登陆
	 */
	public static final int REQ_ACCOUNT_LOGIN = 8;
	/**
	 * /** 账号-第三方登陆
	 */
	public static final int REQ_ACCOUNT_THIRD_LOGIN = 80;
	/**
	 * 账号-注册
	 */
	public static final int REQ_ACCOUNT_REGISTER = 81;
	/**
	 * 账号-找回密码(邮箱)
	 */
	public static final int REQ_ACCOUNT_FORGET_PWD_BY_EMAIL = 82;
	/**
	 * 账号-找回密码（手机）
	 */
	public static final int REQ_ACCOUNT_FORGET_PWD_BY_PHONE = 821;
	/**
	 * 账号-修改密码
	 */
	public static final int REQ_ACCOUNT_RESET_PWD = 83;
	/**
	 * 账号-刷新数据
	 */
	public static final int REQ_ACCOUNT_UPDATE = 86;

	/**
	 * 客服-提交问题
	 */
	public static final int REQ_CS_ASK = 7;

	/**
	 * 客服-获取FB账号对应的用户信息
	 */
	public static final int REQ_CS_GET_FB_USER_INFO = 793;
	
	/**
	 * 设置-检查更新
	 */
	public static final int REQ_SETTING_CHECK_VERSION = 9;
	
	/**
	 * 统计相关数据（APP打开游戏，APP下载等）
	 */
	public static final int REQ_STAT_LOGINFO = 92;
	
	/**
	 * 统计相关数据（推送信息）
	 */
	public static final int REQ_STAT_PUSHINFO = 93;
	
	/**
	 * 获取文件的平台相关配置信息
	 */
	public static final int REQ_TXT_PLATFORM_CONFIG_INFOS = 101;
	/**
	 * 获取文件的玩家地区
	 */
	public static final int REQ_TXT_PLAYER_AREA = 103;
	
	/**
	 * 福利-获取福利列表
	 */
	public static final int REQ_WELFARE_INFOS = 31;
	/**
	 * 福利-序列号中心
	 */
	public static final int REQ_GIFT_SELF_LIST = 35;

	/**
	 * 资讯首页
	 * @param request {@link BaseRequestBean}
	 * @return @return {@link BaseResponseBean} 
	 */
	public BaseResponseBean summaryHome(BaseRequestBean request);

	/**
	 * 资讯列表
	 * 
	 * @return
	 */
	public BaseResponseBean summaryList(BaseRequestBean request);


	/**
	 * 登陆
	 * @param request {@link BaseRequestBean}
	 * @return @return {@link BaseResponseBean} 
	 */
	public BaseResponseBean login(BaseRequestBean request);
	/**
	 * 第三方登陆
	 * @param request {@link BaseRequestBean}
	 * @return @return {@link BaseResponseBean} 
	 */
	public BaseResponseBean thirdLogin(BaseRequestBean request);
	
	/**
	 * 登陆
	 * @param request {@link BaseRequestBean}
	 * @return @return {@link BaseResponseBean} 
	 */
	public BaseResponseBean update(BaseRequestBean request);

	/**
	 * 注册
	 * @param request {@link BaseRequestBean}
	 * @return @return {@link BaseResponseBean} 
	 */
	public BaseResponseBean register(BaseRequestBean request);

	/**
	 * 忘记密码
	 * @param request {@link BaseRequestBean}
	 * @return @return {@link BaseResponseBean} 
	 */
	public BaseResponseBean forgetPwd(BaseRequestBean request);

	/**
	 * 修改密码
	 * @param request {@link BaseRequestBean}
	 * @return @return {@link BaseResponseBean} 
	 */
	public BaseResponseBean resetPwd(BaseRequestBean request);

	/**
	 * 账户-更新用户头像
	 * @param request {@link BaseRequestBean}
	 * @return @return {@link BaseResponseBean} 
	 */
	public BaseResponseBean userUpdateHeader(BaseRequestBean request);

	/**
	 * 更新用户信息
	 * @param request {@link BaseRequestBean}
	 * @return @return {@link BaseResponseBean} 
	 */
	public BaseResponseBean userUpdateInfo(BaseRequestBean request);




	/**
	 * 客服-提交问题
	 * @param request {@link BaseRequestBean}
	 * @return @return {@link BaseResponseBean} 
	 */
	public BaseResponseBean csAsk(BaseRequestBean request);

	/**
	 * 客服-获取FB用户信息列表
	 * @param request {@link BaseRequestBean}
	 * @return @return {@link BaseResponseBean} 
	 */
	public BaseResponseBean accountGetUserFBUidsByUid(BaseRequestBean request);

	/**
	 * 系统-检测版本
	 * @param request {@link BaseRequestBean}
	 * @return @return {@link BaseResponseBean} 
	 */
	public BaseResponseBean checkVersion(BaseRequestBean request);
	
	/**
	 * 统计APP相关数据
	 * @param request
	 * @return
	 */
	public BaseResponseBean statLogInfo(BaseRequestBean request);
	/**
	 * 统计推送的相关数据
	 * @param request
	 * @return
	 */
	public BaseResponseBean statPushInfo(BaseRequestBean request);
	/**
	 * 通过手机找回密码
	 * 
	 * @return
	 */
	public BaseResponseBean findPwdByPhone(BaseRequestBean request);
	/**
	 * 文件-获取平台配置信息
	 * 
	 * @return
	 */
	public BaseResponseBean getPlatformConfigInfosOfText(BaseRequestBean request);
	/**
	 * 文件-获取玩家地区
	 * 
	 * @return
	 */
	public BaseResponseBean getPlayerAreaOfText(BaseRequestBean request);
	
	/**
	 * 资讯 - 活动列表
	 * @param request
	 * @return
	 */
	public BaseResponseBean actsList(BaseRequestBean request);
	
	
	/**
	 *新更新玩家头像
	 * 
	 * @return
	 */
	public BaseResponseBean refreshPlayerHeaderIcon(BaseRequestBean request);

	
	/**
	 * 获取福利列表
	 * @param request
	 * @return
	 */
	public BaseResponseBean getWelfares(BaseRequestBean request);
	/**
	 * 资讯点赞
	 * 
	 * @return
	 */
	public BaseResponseBean summaryPraise(BaseRequestBean request);
	
	/**
	 * 福利 - 序号列表
	 * @param request
	 * @return
	 */
	public BaseResponseBean giftSelfList(BaseRequestBean request);
	
	
}
