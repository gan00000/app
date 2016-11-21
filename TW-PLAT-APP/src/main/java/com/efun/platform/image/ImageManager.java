package com.efun.platform.image;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import com.efun.core.tools.EfunScreenUtil;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.image.core.DisplayImageOptions;
import com.efun.platform.image.core.ImageLoader;
import com.efun.platform.image.core.display.RoundedBitmapDisplayer;
import com.efun.platform.image.core.imageaware.ImageViewAware;
import com.efun.platform.image.core.listener.ImageLoadingListener;
/**
 * 图片管理类
 * @author Jesse
 *
 */
public class ImageManager {
	/**
	 * {@link ImageLoader}
	 */
	private static ImageLoader imageLoader = ImageLoader.getInstance();
	/**
	 * 正方形默认图片
	 */
	public static final int IMAGE_SQUARE = E_drawable.efun_pd_default_square_icon;
	/**
	 * 圆形默认图片
	 */
	public static final int IMAGE_ROUND = E_drawable.efun_pd_default_round_icon;
	
	public static final int IMAGE_ROUND_USER = E_drawable.efun_pd_default_round_user_boy_icon;
	
	/**
	 * 长方形默认图片，高大于宽
	 */
	public static final int IMAGE_RECTANGLE_H = E_drawable.efun_pd_default_rectangle_h_icon;
	/**
	 * 长方形默认图片，宽大于高
	 */
	public static final int IMAGE_RECTANGLE_V = E_drawable.efun_pd_default_rectangle_v_icon;

	/**
	 * 显示图片
	 * @param url 图片地址
	 * @param imageView 展现图片的控件
	 * @param imageType 图片形状类型
	 */
	public static void displayImage(String url, ImageView imageView,int imageType) {
		imageLoader.displayImage(url, imageView,createDisplayImageOptions(imageType));
	}
	
	
	/**
	 * 显示图片
	 * @param url 图片地址
	 * @param imageView 展现图片的控件
	 * @param imageType 图片形状类型
	 * @param round 角的弯曲像素   
	 */
	public static void displayImage(String url, ImageView imageView,int imageType,int round) {
		imageLoader.displayImage(url, imageView,createDisplayImageOptions(imageType,round));
	}
	
	
	/**
	 * 显示图片
	 * @param url 图片地址
	 * @param imageView 展现图片的控件
	 */
	public static void displayImage(String url, ImageView imageView) {
		imageLoader.displayImage(url, imageView);
	}
	
	/**
	 * 显示图片
	 * @param url 图片地址
	 * @param imageView 展现图片的控件
	 * @param options 图片属性
	 */
	public static void displayImage(String url, ImageView imageView,DisplayImageOptions options){
		imageLoader.displayImage(url, imageView,options);
	}
	/**
	 * 创建样式和功能
	 * @param id 资源ID
	 * @return
	 */
	private static DisplayImageOptions createDisplayImageOptions(int id) {
		return new DisplayImageOptions.Builder().showImageOnLoading(id)
				.showImageForEmptyUri(id).showImageOnFail(id).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.build();
	}
	
	/**
	 * 创建样式和功能
	 * @param id 资源ID
	 * @param round 角的弯曲像素   
	 * @return
	 */
	private static DisplayImageOptions createDisplayImageOptions(int id,int round) {
		return new DisplayImageOptions.Builder().showImageOnLoading(id)
				.showImageForEmptyUri(id).showImageOnFail(id)
				.displayer(new RoundedBitmapDisplayer(round))
				.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.build();
	}
	
	/**
	 * 显示圆角图片
	 * @param imageView  展现图片的控件
	 * @param bitmap 原图
	 * @param round 角的弯曲像素   
	 */
	public static void displayImage(ImageView imageView,Bitmap bitmap,int round) {
		try {
			imageView.setImageBitmap(getRoundCornerBitmap(bitmap, round));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/**
	 * 获取圆角图片
	 * @param bitmap	原图
	 * @param round	角的弯曲像素   
	 * @return
	 */
	public static Bitmap getRoundCornerBitmap(Bitmap bitmap, float round){
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap bitmap2 = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap2);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, width, height);
		final RectF rectF = new RectF(rect);
		paint.setColor(color);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawRoundRect(rectF, round, round, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return bitmap2;
	}
	
	 /**
     * @param bitmap 将位图转换为 base64
     * @return string
     */
    
	public static String bitmap2Base64(Bitmap bitmap) {   
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();   
        bitmap.compress(CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();   
        return Base64.encodeToString(bytes, Base64.DEFAULT);   
    }
	
	public static Bitmap createBitmap(Context context, int id){
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();  
        options.inJustDecodeBounds = true;//不加载bitmap到内存中  
        bitmap = BitmapFactory.decodeResource(context.getResources(), id, options);
        int optionsWidth = options.outWidth;
        int optionsHeight= options.outHeight;
        
        EfunScreenUtil screen = EfunScreenUtil.getInStance((Activity)context);
        int width = screen.getPxWidth();
        int height= screen.getPxHeight();
        
        if(optionsWidth > optionsHeight){
        	options.inSampleSize = optionsWidth/width;  
        }else{
        	options.inSampleSize = optionsHeight/height;  
        }
        	
        options.inDither = false;  
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;  
       
        options.inJustDecodeBounds = false;  
        bitmap = BitmapFactory.decodeResource(context.getResources(), id, options);
		return bitmap;
	}
	
	public static BitmapFactory.Options getOptions(Context context){
		BitmapFactory.Options options = new BitmapFactory.Options();  
        options.inJustDecodeBounds = true;//不加载bitmap到内存中  
        int optionsWidth = options.outWidth;
        int optionsHeight= options.outHeight;
        
        EfunScreenUtil screen = EfunScreenUtil.getInStance((Activity)context);
        int width = screen.getPxWidth();
        int height= screen.getPxHeight();
        
        if(optionsWidth > optionsHeight){
        	options.inSampleSize = optionsWidth/width;  
        }else{
        	options.inSampleSize = optionsHeight/height;  
        }
        	
        options.inDither = false;  
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;  
       
        options.inJustDecodeBounds = false; 
        return options;
	}
	
	/**
	 * 用于Banner 和  游戏截屏，减少图片所占内存
	 */
	private  static BitmapDrawable rectangle_bd_H;
	private static BitmapDrawable rectangle_bd_V;
	@SuppressWarnings("deprecation")
	public static BitmapDrawable getRectangle_BD_H(Context context){
		if(rectangle_bd_H==null){
			rectangle_bd_H = new BitmapDrawable(createBitmap(context, IMAGE_RECTANGLE_H));
		}
		return rectangle_bd_H;
	}
	@SuppressWarnings("deprecation")
	public static BitmapDrawable getRectangle_BD_V(Context context){
		if(rectangle_bd_V==null){
			rectangle_bd_V = new BitmapDrawable(createBitmap(context, IMAGE_RECTANGLE_V));
		}
		return rectangle_bd_V;
	}
}
