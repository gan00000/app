package com.efun.platform.module.utils;

import java.util.ArrayList;

import com.efun.platform.IPlatApplication;
import com.efun.platform.module.account.bean.PhoneAreaBean;
import com.efun.platform.module.bean.ConfigInfoBean;
import com.efun.platform.module.bean.PlayerAreaBean;
import com.efun.platform.module.bean.PlayerCityBean;

/**
 * 处理数据
 * @author itxuxxey
 *
 */
public class ProcessDatasUtils {
	
	public static final String TYPE_APPFIRSTPAGE_FB = "1-1";//facebook
	public static final String TYPE_APPFIRSTPAGE_GOVWEB = "1-2";//官网
	public static final String TYPE_APPFIRSTPAGE_DOWNLOAD = "1-3";//下载
	public static final String TYPE_APPFIRSTPAGE_GUIDE = "1-4";//攻略
	public static final String TYPE_APPFIRSTPAGE_NOTICE = "1-5";//公告
	public static final String TYPE_APPFIRSTPAGE_ACTIVITY = "1-6";//活动
	public static final String TYPE_APPFIRSTPAGE_VIDEO = "1-7";//视频
	public static final String TYPE_APPFIRSTPAGE_JOB = "1-8";//职业
	public static final String TYPE_APPFIRSTPAGE_PARTNER = "1-9";//partner

	/**
	 * 获取手机区号名称列表
	 * @return
	 */
	public static String[] getAllPhoneAreaCode(){
		ArrayList<PhoneAreaBean> phoneBeans = IPlatApplication.get().getmPhoneAreas();
		String[] mValues = null; 
		if(phoneBeans != null){
			mValues = new String[phoneBeans.size()];
			for (int i = 0; i < phoneBeans.size(); i++) {
				mValues[i] = phoneBeans.get(i).getText();
			}
		}
		return mValues;
	}
	/**
	 * 获取玩家地区列表
	 * @return
	 */
	public static String[] getAllPlayerArea(){
		ArrayList<PlayerAreaBean> mPlayerAreas = IPlatApplication.get().getmPlayerAreas();
		String[] mValues = null; 
		if(mPlayerAreas != null){
			mValues = new String[mPlayerAreas.size()];
			for (int i = 0; i < mPlayerAreas.size(); i++) {
				mValues[i] = mPlayerAreas.get(i).getText();
			}
		}
		return mValues;
	}
	
	/**
	 * 通过地区标识，得到城市列表全部数据
	 * @param area
	 * @return
	 */
	public static ArrayList<PlayerCityBean> getAllCityInfoByPlayArea(String areaKey){
		ArrayList<PlayerAreaBean> mPlayerAreas = IPlatApplication.get().getmPlayerAreas();
		ArrayList<PlayerCityBean> citys = null; 
		if(mPlayerAreas != null){
			for (int i = 0; i < mPlayerAreas.size(); i++) {
				if(mPlayerAreas.get(i).getKey().equals(areaKey)){
					citys = mPlayerAreas.get(i).getCitys();
				}
			}
		}
		return citys;
	}
	
	/**
	 * 获取我页面中功能区的所有按钮数据
	 * @return
	 */
	public static ArrayList<ConfigInfoBean> getPersonFunctions(){
		ArrayList<ConfigInfoBean> configInfos = IPlatApplication.get().getConfigInfos();
		ArrayList<ConfigInfoBean> beans = null;
		if(configInfos != null){
			ArrayList<ConfigInfoBean> subList = null;
			for(int i = 0 ; i < configInfos.size() ; i++){
				if(configInfos.get(i).getId().equals("1")){
					subList = configInfos.get(i).getSubList();
				}
			}
			if(subList != null){
				for(int i = 0 ; i < subList.size() ; i++){
					if(subList.get(i).getId().equals("1-3")){
						beans = subList.get(i).getSubList();
						for(int j = 0 ; j < beans.size() ; j++){//过滤掉关闭状态的按钮
							if(!beans.get(j).isOpen()){
								beans.remove(j);
								j--;
							}
						}
					}
				}
			}
		}
		return beans;
	}
	
	/**
	 * 获取我页面中功能区中的礼包中心item下标
	 * @return
	 */
	public static int getGiftFunctionIndex(){
		ArrayList<ConfigInfoBean> configInfos = IPlatApplication.get().getConfigInfos();
		int index = -1;
		if(configInfos != null){
			ArrayList<ConfigInfoBean> subList = null;
			ArrayList<ConfigInfoBean> subList1 = null;
			for(int i = 0 ; i < configInfos.size() ; i++){
				if(configInfos.get(i).getId().equals("1")){
					subList = configInfos.get(i).getSubList();
				}
			}
			if(subList != null){
				for(int i = 0 ; i < subList.size() ; i++){
					if(subList.get(i).getId().equals("1-3")){
						subList1 = subList.get(i).getSubList();
					}
				}
				
			}
			if(subList1 != null){
				for(int i = 0 ; i < subList1.size() ; i++){
					if(subList1.get(i).getId().equals("1-3-4")){
						index = i;
					}
				}
			}
		}
		return index;
	}
	
	/**
	 * 获取我页面中功能区中的序号中心item下标
	 * @return
	 */
	public static int getGiftSerialFunctionIndex(){
		ArrayList<ConfigInfoBean> configInfos = IPlatApplication.get().getConfigInfos();
		int index = -1;
		if(configInfos != null){
			ArrayList<ConfigInfoBean> subList = null;
			ArrayList<ConfigInfoBean> subList1 = null;
			for(int i = 0 ; i < configInfos.size() ; i++){
				if(configInfos.get(i).getId().equals("1")){
					subList = configInfos.get(i).getSubList();
				}
			}
			if(subList != null){
				for(int i = 0 ; i < subList.size() ; i++){
					if(subList.get(i).getId().equals("1-3")){
						subList1 = subList.get(i).getSubList();
					}
				}
				
			}
			if(subList1 != null){
				for(int i = 0 ; i < subList1.size() ; i++){
					if(subList1.get(i).getId().equals("1-3-3")){
						index = i;
					}
				}
			}
		}
		return index;
	}
	
	/**
	 * 获取游戏成就系统文案
	 * @return
	 */
	public static ConfigInfoBean getGameAchieveSysTextInfo(){
		ArrayList<ConfigInfoBean> configInfos = IPlatApplication.get().getConfigInfos();
		ConfigInfoBean bean = null;
		if(configInfos != null){
			ArrayList<ConfigInfoBean> subList = null;
			for(int i = 0 ; i < configInfos.size() ; i++){
				if(configInfos.get(i).getId().equals("4")){
					subList = configInfos.get(i).getSubList();
				}
			}
			if(subList != null){
				for(int i = 0 ; i < subList.size() ; i++){
					if(subList.get(i).getId().equals("4-1")){
						bean = subList.get(i);
					}
				}
				
			}
		}
		return bean;
	}
	
	/**
	 * 获取首页子项信息
	 * @return
	 */
	public static ConfigInfoBean getAPPFirstPageInfo(String type){
		ArrayList<ConfigInfoBean> configInfos = IPlatApplication.get().getConfigInfos();
		ConfigInfoBean bean = null;
		if(configInfos != null){
			ArrayList<ConfigInfoBean> subList = null;
			for(int i = 0 ; i < configInfos.size() ; i++){
				if(configInfos.get(i).getId().equals("1")){
					subList = configInfos.get(i).getSubList();
				}
			}
			if(subList != null){
				for(int i = 0 ; i < subList.size() ; i++){
					if(subList.get(i).getId().equals(type)){
						bean = subList.get(i);
					}
				}
				
			}
		}
		return bean;
	}
	
	/**
	 * 获取资讯信息
	 * @return
	 */
	public static ConfigInfoBean getSummaryInfo(){
		ArrayList<ConfigInfoBean> configInfos = IPlatApplication.get().getConfigInfos();
		ConfigInfoBean bean = null;
		if(configInfos != null){
			ArrayList<ConfigInfoBean> subList = null;
			for(int i = 0 ; i < configInfos.size() ; i++){
				if(configInfos.get(i).getId().equals("2")){
					bean = configInfos.get(i);
				}
			}
		}
		return bean;
	}
	
	/**
	 * 获取交流信息
	 * @return
	 */
	public static ConfigInfoBean getConnectionInfo(){
		ArrayList<ConfigInfoBean> configInfos = IPlatApplication.get().getConfigInfos();
		ConfigInfoBean bean = null;
		if(configInfos != null){
			ArrayList<ConfigInfoBean> subList = null;
			for(int i = 0 ; i < configInfos.size() ; i++){
				if(configInfos.get(i).getId().equals("4")){
					bean = configInfos.get(i);
				}
			}
		}
		return bean;
	}
	
	/**
	 * 获取交流发贴信息
	 * @return
	 */
	public static ConfigInfoBean getPostTipsInfo(){
		ArrayList<ConfigInfoBean> configInfos = IPlatApplication.get().getConfigInfos();
		ConfigInfoBean bean = null;
		if(configInfos != null){
			ArrayList<ConfigInfoBean> subList = null;
			for(int i = 0 ; i < configInfos.size() ; i++){
				if(configInfos.get(i).getId().equals("4")){
					subList = configInfos.get(i).getSubList();
				}
			}
			if(subList != null){
				for(int i = 0 ; i < subList.size() ; i++){
					if(subList.get(i).getId().equals("4-1")){
						bean = subList.get(i);
					}
				}
				
			}
		}
		return bean;
	}
	
	/**
	 * 通过总经验，获取玩家的等级
	 * @param playerExp
	 * @return
	 */
	public static int getPlayerLevelByExp(int playerExp){
		int level = 1;
		if(playerExp >= 0 && playerExp < 76){
			level = 1;
		}else if(playerExp > 75 && playerExp < 296){
			level = 2;
		}else if(playerExp > 295 && playerExp < 686){
			level = 3;
		}else if(playerExp > 685 && playerExp < 1476){
			level = 4;
		}else if(playerExp > 1475 && playerExp < 3026){
			level = 5;
		}else if(playerExp > 3025 && playerExp < 6376){
			level = 6;
		}else if(playerExp > 6375 && playerExp < 15376){
			level = 7;
		}else if(playerExp > 15375 && playerExp < 38376){
			level = 8;
		}else if(playerExp > 38375 && playerExp < 95376){
			level = 9;
		}
		return level;
	}
	
	
}
