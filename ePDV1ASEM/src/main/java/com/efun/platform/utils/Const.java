package com.efun.platform.utils;

import android.support.v4.app.FragmentTabHost;

/**
 * 公共常量类
 * 
 * @author itxuxxey
 * 
 */
public class Const {
	public final static String VER_NAME = "V1";
	public final static int VER_CODE = 1;
	public final static String TAB_ACTION = "com.efunen.pd.v1.tab";
	public final static long BETWEENTIME = 24*60*60*1000;
	
	public final static String AES_ENCRYPT_KEY = "__EFUN_PF_TO_GAME_KEY__";
	
	public final static String RECEIVER_UPDATEAPP_ACTION = "com.efun.platform.update.app";
	
	public static final String namespace = "http://schemas.android.com/apk/res-auto";

	/**
	 * {@link FragmentTabHost} 中 Tab 的Tag
	 * 
	 * @author itxuxxey
	 */
	public static class Tab {
		public final static int TAB_ITEM_TAG_SUMMARY = 0;
		public final static int TAB_ITEM_TAG_FIND = 1;
		public final static int TAB_ITEM_TAG_WELFARE = 2;
		public final static int TAB_ITEM_TAG_INTERACTION = 3;
		public final static int TAB_ITEM_TAG_PLAYERSELF = 4;
	}

	/**
	 * 启动Web的方式
	 */
	public static class Web {
		public final static String WEB_PERFIX = "http://m.efun.com/page/";
		/**
		 * Fragment 启动 FragmentActivity 方式的key值
		 */
		public final static String WEB_GO_KEY = "WEB_GO_KEY";
		/**
		 * 
		 */
		public final static String WEB_LOAD_URL_KEY = "WEB_LOAD_URL_KEY";
		

		/**
		 * 来自资讯
		 */
		public final static int WEB_GO_SUMMARY = 1;
		/**
		 * 来自Banner广告
		 */
		public final static int WEB_GO_BANNER = 2;
		/**
		 * 来自活动
		 */
		public final static int WEB_GO_ACT = 3;
		/**
		 * 来自游戏
		 */
		public final static int WEB_GO_GAME = 4;
		/**
		 * 来自FB粉丝页
		 */
		public final static int WEB_GO_GAME_FB = 41;
		/**
		 * 來自游戲成就列表
		 */
		public final static int WEB_GO_GAME_ACHIEVE = 42;
		/**
		 * 来自砸蛋
		 */
		public final static int WEB_GO_EGG = 5;
		/**
		 * 来自会员说明
		 */
		public final static int WEB_GO_GOLDVALUE = 6;
		/**
		 * 来自关于我们
		 */
		public final static int WEB_GO_US = 7;
		/**
		 * VIP专区
		 */
		public final static int WEB_GO_VIP = 8;
		/**
		 * 储值按钮
		 */
		public final static int WEB_GO_PERSON_RECHAR = 9;
		/**
		 * 储值记录
		 */
		public final static int WEB_GO_PERSON_RECHAR_RECORD = 91;
		/**
		 * 储值刮刮乐
		 */
		public final static int WEB_GO_PERSON_SCATCH= 92;
		/**
		 * 信件系统
		 */
		public final static int WEB_GO_PERSON_EMAIL_SYS= 93;
		/**
		 * 資訊列表
		 */
		public final static int WEB_GO_SUMMARY_LIST = 10;
		/**
		 * 我的页面中的其它功能模块
		 */
		public final static int WEB_GO_PERSON_FUNCTIONS = 11;
		/**
		 * 福利列表
		 */
		public final static int WEB_GO_WELFARE_LIST = 32;
		/**
		 * 发贴
		 */
		public final static int WEB_GO_CONNECTED_POST_TIPS = 33;
		/**
		 * 只有URL的请求
		 */
		public final static int WEB_GO_WITH_URL = 34;
	}
	
	public static class Summary{
		/**
		 * Summary的啟動類型
		 */
		public final static String SUMMARY_GO_KEY = "SUMMARY_GO_KEY";
		/**
		 * 新闻
		 */
		public final static int SUMMARY_NEWS= 1;
		/**
		 * 公告
		 */
		public final static int SUMMARY_BULLETIN= 0;
		/**
		 * 活动
		 */
		public final static int SUMMARY_ACTIVITY= 4;
		/**
		 * 攻略
		 */
		public final static int SUMMARY_STRATEGY= 2;
		/**
		 * 视频
		 */
		public final static int SUMMARY_VIDEO= 7;
	}
	
	public static class ToastType{
		public final static int COMMONTYPE = 1;
		public final static int HASCONTENTTYPE = 2;
		public final static int CHOSETYPE = 3;
	}
	
	public static class JsWithAndroidKey{
		public final static String KEY_MAP_DEVICEINFO = "deviceInfo";
		public final static String KEY_MAP_GAMEINFO = "gameInfo";
		public final static String KEY_MAP_PLATFORMINFO = "platformInfo";
		
		public final static String KEY_DEVICETYPE = "deviceType";
		public final static String KEY_SYSTEMVERSION = "systemVersion";
		public final static String KEY_MAC = "mac";
		public final static String KEY_IMEI = "imei";
		public final static String KEY_IP = "ip";
		public final static String KEY_SIMOPERATOR = "simOperator";
		
		public final static String KEY_USERID = "userid";
		public final static String KEY_SIGN = "sign";
		public final static String KEY_TIMESTAMP = "timestamp";
		public final static String KEY_PLATFORM = "platform";
		public final static String KEY_VERSION = "version";
		public final static String KEY_PACKAGEVERSION = "packageVersion";
		public final static String KEY_LANGUAGE = "language";
		public final static String KEY_FROMTYPE = "fromType";
		
		public final static String KEY_SERVERCODE = "serverCode";
		public final static String KEY_ROLELEVEL = "roleLevel";
		public final static String KEY_GAMECODE = "gameCode";
		public final static String KEY_ROLEID = "roleId";
		public final static String KEY_CHANNEL = "channel";
		public final static String KEY_LOGINTYPE = "loginType";
		public final static String KEY_APPGAMECODE = "appGameCode";
	}

	/**
	 * 键值
	 * 
	 * @author itxuxxey
	 * 
	 */
	public final class Key {
		/**
		 * Bundle存放简单数据
		 */
		public final static String BOOLEAN_KEY = "BOOLEAN_KEY";
		/**
		 * Bundle存放简单数据
		 */
		public final static String INTEGER_KEY = "INTEGER_KEY";
		/**
		 * Bundle存放简单数据
		 */
		public final static String STRING_KEY = "STRING_KEY";
		/**
		 * Facebook bundle list
		 */
		public final static String APPS_KEY = "APPS_KEY";
		/**
		 * 切换账号
		 */
		public final static String USER_KEY = "USER_KEY";
	}

	/**
	 * Fragment 与 FragmentActivity 传值Bundle的key值
	 */
	public final static String DATA_KEY = "DATA_KEY";
	/**
	 * Bundle存放Bean的key值
	 */
	public final static String BEAN_KEY = "BEAN_KEY";
	
	public final static String PUSH_KEY = "PUSH_KEY";

	public static class HttpParam {
		public final static String LANGUAGE = "zh_CH";
		public final static String REGION = "";
		public final static String PLATFORM = "android";

		public final static String RESULT_200 = "200";
		public final static String RESULT_0000 = "0000";
		public final static String RESULT_1000 = "1000";
		public final static String RESULT_1010 = "1010";
		public final static String RESULT_1011 = "1011";
		public final static String RESULT_1013 = "1013";
		public final static String RESULT_1003 = "1003";
		public final static String RESULT_1004 = "1004";
		public final static String RESULT_1006 = "1006";
		public final static String RESULT_1002 = "1002";
		public final static String RESULT_1100 = "1100";

		public final static String RESULT_1001 = "1001";
		public final static String RESULT_1012 = "1012";
		public final static String RESULT_1026 = "1026";
		public final static String RESULT_1039 = "1039";
		public final static String RESULT_1041 = "1041";
		public final static String RESULT_1028 = "1028";
		public final static String RESULT_1029 = "1029";
		public final static String RESULT_1030 = "1030";
		public final static String RESULT_1031 = "1031";

		public final static String GIFT_STATUS_QUERY = "q";
		public final static String GIFT_STATUS_UPDATE = "u";
		
		public final static String PLATFORM_AREA = "en";
		public final static String JSONP = "1";
		public final static String APP = "app";
		public final static String PLATFORM_CODE = "smjqzs";
		public final static String PHONE = "phone";
		public final static String PLATFORM_APP_KEY = "7F1920B9FEBA15A9B4BC135FB751CB09";//appKey 秘钥
		public final static String PLATFORM_APP_PLATFORM = "e00000";//平台标识
		public final static String PLATFORM_APP_SERVERCODE = "0";
		public final static String PLATFORM_APP_ROLEID = "0";
		public final static String ANDORIOS = "andorios";
		public final static String FEEDBACK_TYPE = "113";
		public final static String PHONE_AREA_TYPE = "areaCode";
		public final static String CHARGE_FLAG = "platformStore";
		public final static String BINDEMAIL = "bindEmail";
		public final static String GAMELIST = "gamelist";
		public final static String GAMEDETAIL = "gameDetail";
		public final static String FREEPOINT = "freepoint";
		public final static String DOWNLOADCLICK = "downloadClick";
		public final static String UPDATECLICK = "updateClick";
		public final static String STARTAPP = "startApp";
		public final static String GAME_HK = "hk";
		public final static String GAME_TW = "tw";
		public final static String GAME_DETAIL_BAHA = "baha";
		public final static String GAME = "game";
		public final static String ABOUT = "about";
		public final static String COMPINFOGIFT = "compInfoGift";
		public final static String REWARD_GIFT = "reward_gift";
		public final static String COMPINFO = "compInfo";

		// 获取游戏接口
		public final static String CS_PID = "1";
		public final static String CS_DEPT_TW = "33";
		public final static String CS_GPID = "1";
		public final static String CS_PLAYER = "player";
		public final static String CS_CHECK = "check";
		
		/**
		 * 成功
		 */
		public static final int SUCCESS = 1;
		/**
		 * 异常
		 */
		public static final int ERROR = -1;
		/**
		 * 超时
		 */
		public static final int TIMEOUNT = 0;
	}

	public static class LoginPlatform {
		public final static String FACEBOOK = "fb";
		public final static String BAHAMUT = "gamer";
		public final static String GOOGLE = "google";
		public final static String EFUN = "efun";
		public final static String TWITTER = "twitter";
	}
	
	public static class ClickButtonType {
		public final static int ZAN = 111;
		public final static int SHARE = 222;
	}

	public static class RequestCode {
		public final static int REQ_CODE_UPDATE_NICK = 2000;
		public final static int REQ_USER_INFO_USER = 2001;
		public final static int REQ_USER_INFO_HEAD_PHONE = 2002;
		public final static int REQ_USER_INFO_HEAD_THUMB = 2003;
		public final static int REQ_USER_INFO_HEAD_PHONE_CUT = 2004;
		public final static int REQ_BIND_PHONE = 2005;
		public final static int REQ_ACCOUNT_FACEBOOK_LOGIN = 2006;
		public final static int REQ_CS_REPLY_QUESTION_REQUEST = 2007;
		public final static int REQ_REQUEST = 2008;
		public final static int REQ_GAME_COMMEND = 2009;
		public final static int REQ_LOGOUT = 2010;
		public final static int REQ_PIC_PHOTO_CAMERA_RETURN = 2012;
		public final static int REQ_RET_PWD = 2013;
		public final static int REQ_LOGIN_AFTER_RET_PWD = 2015;
		public final static int REQ_CHANGE_ACCOUNT = 2017;
		public final static int REQ_RESET_PERINFO = 2019;
		public final static int REQ_SETTING = 2021;
		public final static int REQ_FRAGMENT_JS_UPLOAD_PICTURE = 2023;
	}

	public static class ResultCode {
		public final static int RST_CODE_UPDATE_NICK = 2000;
		public final static int RST_ACCOUNT_FACEBOOK_LOGIN = 2001;
		public final static int RST_BIND_PHONE = 2002;
		public final static int RST_CS_REPLY_QUESTION_RESULT = 2003;
		public final static int RST_CS_REPLY_FINISH = 2004;
		public final static int RST_CS_REPLY_READ = 2005;
		public final static int RST_REQUEST = 2006;
		public final static int RST_GAME_COMMEND = 2010;
		public final static int RST_LOGOUT = 2011;
		public final static int RST_RET_PWD = 2014;
		public final static int RST_LOGIN_AFTER_RET_PWD = 2016;
		public final static int RST_CHANGE_ACCOUNT = 2018;
		public final static int RST_RESET_PERINFO = 2020;
		public final static int RST_SETTING = 2022;
		public final static int RST_FRAGMENT_JS_UPLOAD_PICTURE = 2024;
	}
	
	public static class BeanType{
		
		public final static int BEAN_SETTINGBEAN = 9000;//SettingBean
		public final static int BEAN_GAMENEWSBEAN = 9001;//GameNewsBean
		public final static int BEAN_SUMMARYITEMBEAN = 9019;//SummaryItemBean
		public final static int BEAN_ACTITEMBEAN = 9020;//ActItemBean
		
	}
	
	public static class Share{
		public final static String SHARE_LINESHARE_URL = "http://line.naver.jp/R/msg/text/?";
//		public final static String SHARE_APPSHARE_URL = "http://ad.efun.com/ads_download.shtml?advertiser=efunapp&gameCode=smjqzs&adsid=efunapp_smjqzs_sea_2_pfshare";
		public final static String SHARE_APPSHARE_URL = "http://ad.efun.com/ads_download.shtml?advertiser=efunactivity1&gameCode=smjqzs&adsid=efunactivity1_smjqzs_my_2_unionurl_other";
	}
	
	public static class Score{
		public final static String SCORE_THIS_APP = "https://play.google.com/store/apps/details?id=com.efun.jqzs.sm&hl=zh_CH";
	}
	
	public static class AppStatus{
		public final static int DOWNLOAD = 1;
		public final static int START = 2;
		public final static int UPDATE = 3;
	}
	
	public static class StartAPPParams{
		public final static String GAMECODE = "GAMECODE";//游戏gameCode
		public final static String GAMENAME = "GAMENAME";//游戏gameName
		public final static String USERUID = "USERUID";//用户UID
		public final static String USERSIGN = "USERSIGN";//用户签名
		public final static String USERTIMESTAMP = "USERTIMESTAMP";//用户时间戳
		public final static String USERNAME = "USERNAME";//用户名
		public final static String LOGINTYPE = "LOGINTYPE";//登录类型
		public final static int PFACTIVITY_AURESULT = 63333;//intent 授权resultCode
		public final static int PFACTIVITY_RETURNRESULT = 62222;//intent 返回resultCode
		public final static String DATA_KEY = "DATA_KEY";//bundle数据存放的key值
		
		public final static int AU_CHANGERETURN = 61111;//切换账号的返回
		public final static int AU_UNLOGINRETURN = 61112;//未登录的返回
		public final static int AU_CHANGETOLOGIN = 61113;//授权页面的切换账号
		public final static int AU_UNLOGINTOLOGIN = 61114;//未登录时的跳转
		public final static int AU_LOGIN_SUCCESS = 61115;//登录成功时的跳转
	}
}
