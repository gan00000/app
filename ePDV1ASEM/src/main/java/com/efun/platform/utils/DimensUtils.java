package com.efun.platform.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.efun.core.tools.EfunScreenUtil;

public class DimensUtils {
	
	 /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static float px2dip(Context context, double pxValue) {  
        final double scale = context.getResources().getDisplayMetrics().density;  
        return Float.parseFloat((pxValue / scale + 0.5f)+"");  
    } 
    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static float dip2px(Context context, double dpValue) {  
        final double scale = context.getResources().getDisplayMetrics().density;  
        return Float.parseFloat((dpValue * scale + 0.5f)+"");   
    } 
    
	
	public static void px2dp(Activity context,double f){
		Log.e("efun", "dpi:"+context.getResources().getDisplayMetrics().densityDpi);
		Log.e("efun",EfunScreenUtil.getInStance(context).getPxWidth()+"-W");
		Log.e("efun",EfunScreenUtil.getInStance(context).getPxHeight()+"-H");
		
	    Log.e("efun", "<dimen name=\"e_size_1\">"+px2dip(context,1*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_2\">"+px2dip(context,2*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_5\">"+px2dip(context,5*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_8\">"+px2dip(context,8*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_10\">"+px2dip(context,10*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_12\">"+px2dip(context,12*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_13\">"+px2dip(context,13*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_14\">"+px2dip(context,14*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_15\">"+px2dip(context,15*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_16\">"+px2dip(context,16*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_18\">"+px2dip(context,18*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_20\">"+px2dip(context,20*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_21\">"+px2dip(context,21*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_22\">"+px2dip(context,22*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_24\">"+px2dip(context,24*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_25\">"+px2dip(context,25*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_28\">"+px2dip(context,28*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_30\">"+px2dip(context,30*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_32\">"+px2dip(context,32*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_33\">"+px2dip(context,33*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_35\">"+px2dip(context,35*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_37\">"+px2dip(context,37*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_38\">"+px2dip(context,38*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_39\">"+px2dip(context,39*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_40\">"+px2dip(context,40*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_41\">"+px2dip(context,41*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_42\">"+px2dip(context,42*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_44\">"+px2dip(context,44*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_45\">"+px2dip(context,45*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_46\">"+px2dip(context,46*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_47\">"+px2dip(context,47*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_48\">"+px2dip(context,48*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_50\">"+px2dip(context,50*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_53\">"+px2dip(context,53*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_57\">"+px2dip(context,57*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_60\">"+px2dip(context,60*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_62\">"+px2dip(context,62*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_64\">"+px2dip(context,64*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_67\">"+px2dip(context,67*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_70\">"+px2dip(context,70*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_78\">"+px2dip(context,78*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_80\">"+px2dip(context,80*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_83\">"+px2dip(context,83*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_86\">"+px2dip(context,86*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_88\">"+px2dip(context,88*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_90\">"+px2dip(context,90*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_93\">"+px2dip(context,93*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_100\">"+px2dip(context,100*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_103\">"+px2dip(context,103*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_104\">"+px2dip(context,104*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_105\">"+px2dip(context,105*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_106\">"+px2dip(context,106*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_110\">"+px2dip(context,110*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_117\">"+px2dip(context,117*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_118\">"+px2dip(context,118*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_120\">"+px2dip(context,120*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_126\">"+px2dip(context,126*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_130\">"+px2dip(context,130*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_132\">"+px2dip(context,132*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_136\">"+px2dip(context,136*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_150\">"+px2dip(context,150*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_156\">"+px2dip(context,156*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_160\">"+px2dip(context,160*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_167\">"+px2dip(context,167*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_170\">"+px2dip(context,170*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_180\">"+px2dip(context,180*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_188\">"+px2dip(context,188*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_190\">"+px2dip(context,190*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_200\">"+px2dip(context,200*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_212\">"+px2dip(context,212*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_217\">"+px2dip(context,217*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_220\">"+px2dip(context,220*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_222\">"+px2dip(context,222*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_240\">"+px2dip(context,240*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_250\">"+px2dip(context,250*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_260\">"+px2dip(context,260*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_264\">"+px2dip(context,264*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_290\">"+px2dip(context,290*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_300\">"+px2dip(context,300*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_300_neg\">-"+px2dip(context,300*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_320\">"+px2dip(context,320*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_350\">"+px2dip(context,350*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_355\">"+px2dip(context,355*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_360\">"+px2dip(context,360*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_400\">"+px2dip(context,400*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_480\">"+px2dip(context,480*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_510\">"+px2dip(context,510*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_515\">"+px2dip(context,515*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_520\">"+px2dip(context,520*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_540\">"+px2dip(context,540*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_546\">"+px2dip(context,546*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_600\">"+px2dip(context,600*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_650\">"+px2dip(context,650*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_700\">"+px2dip(context,700*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_768\">"+px2dip(context,768*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_800\">"+px2dip(context,800*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_900\">"+px2dip(context,900*f)+"dp</dimen>");
	    Log.e("efun", "<dimen name=\"e_size_1024\">"+px2dip(context,1024*f)+"dp</dimen>");
	}
}
