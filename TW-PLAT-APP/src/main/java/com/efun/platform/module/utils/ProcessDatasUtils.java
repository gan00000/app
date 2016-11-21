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
	
	public static final String TYPE_BIND_PHONE_AREA = "1-1";//绑定手机模块
	public static final String TYPE_CHARGE_AREA = "1-2";//储值模块
	public static final String TYPE_LINK_AREA = "1-4";//链接区域
	public static final String TYPE_CHARGE = "1";//储值模块-储值
	public static final String TYPE_PHNOE_DESC = "1";//绑定手机模块-绑定手机文案
	public static final String TYPE_CHARGE_RECORD = "2";//储值模块-储值记录
	public static final String TYPE_LINK_TURNINT_POINT = "1";//設置轉點密碼
	public static final String TYPE_LINK_EMAIL_SYS = "2";//信件系统

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
	 * 通过地区标识，获取城市列表
	 * @param areaKey
	 * @return
	 */
	public static String[] getAllCitys(String areaKey){
		ArrayList<PlayerCityBean> citys = getAllCityInfoByPlayArea(areaKey);
		String[] mValues = null; 
		if(citys != null){
			mValues = new String[citys.size()];
			for (int i = 0; i < citys.size(); i++) {
				mValues[i] = citys.get(i).getText();
			}
		}
		return mValues;
	}
	
	/**
	 * 获取我页面中的绑定手机文案内容项,储值内容，储值记录
	 * @return
	 * type 文案内容项：TYPE_BIND_PHONE_AREA 储值内容项：TYPE_CHARGE_AREA
	 * secondType 除了储值记录，其它都是1
	 */
	public static ConfigInfoBean getPersonTextInfo(String type,String secondType){
		ArrayList<ConfigInfoBean> configInfos = IPlatApplication.get().getConfigInfos();
		ConfigInfoBean bean = null;
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
					if(subList.get(i).getId().equals(type)){
						subList1 = subList.get(i).getSubList();
					}
				}
				
			}
			if(subList1 != null){
				for(int i = 0 ; i < subList1.size() ; i++){
					if(subList1.get(i).getId().equals(type+"-"+secondType)){
						bean = subList1.get(i);
					}
				}
			}
		}
		return bean;
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
