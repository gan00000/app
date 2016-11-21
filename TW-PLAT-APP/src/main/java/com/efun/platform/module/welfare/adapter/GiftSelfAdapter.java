package com.efun.platform.module.welfare.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.AndroidScape.E_style;
import com.efun.platform.module.utils.TrackingGoogleUtils;
import com.efun.platform.module.utils.TrackingUtils;
import com.efun.platform.module.utils.ViewUtils;
import com.efun.platform.module.welfare.bean.GiftSelfItemBean;
import com.efun.platform.utils.TimeFormatUtil;
/**
 * 我的序列号列表适配器
 * @author Jesse
 */
public class GiftSelfAdapter extends BaseAdapter {
	private LayoutInflater mLayoutInflater;
	private Context mContext;
	private ArrayList<GiftSelfItemBean> mArray; 
	public GiftSelfAdapter(Context context) {
		this.mContext = context;
		this.mLayoutInflater = LayoutInflater.from(context);
		this.mArray = new ArrayList<GiftSelfItemBean>();
	}

	@Override
	public int getCount() {
		return mArray.size();
	}

	@Override
	public Object getItem(int position) {
		return mArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	/**
	 * 翻页
	 * @param array
	 */
	public void append(ArrayList<GiftSelfItemBean> array){
		mArray.addAll(array);
		notifyDataSetChanged();
	}
	/**
	 * 刷新
	 * @param array
	 */
	public void refresh(ArrayList<GiftSelfItemBean> array){
		mArray.clear();
		append(array);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(E_layout.efun_pd_welfare_gift_self_list_item, null);
			holder.mCaption= (TextView) convertView.findViewById(E_id.text_1);
			holder.mTime = (TextView) convertView.findViewById(E_id.item_time);
			holder.mTitle = (TextView) convertView.findViewById(E_id.item_title);
			holder.mLinearOfAccount =  (LinearLayout) convertView.findViewById(E_id.contaier_linear_1);
			holder.mContent = (TextView) convertView.findViewById(E_id.item_content);
			holder.mButton = (TextView) convertView.findViewById(E_id.item_text);
			
			holder.mLinearOfPassword =  (LinearLayout) convertView.findViewById(E_id.contaier_linear_2);
			holder.mContentOfPassword = (TextView) convertView.findViewById(E_id.text_2);
			holder.mButonOfPassword = (TextView) convertView.findViewById(E_id.text_3);
			holder.line = (View) convertView.findViewById(E_id.line_between);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if(position==0){
			holder.mCaption.setVisibility(View.VISIBLE);
		}else{
			holder.mCaption.setVisibility(View.GONE);
		}
		final GiftSelfItemBean bean = mArray.get(position);
		holder.mTitle.setText(bean.getGoodsName());
		holder.mTime.setText(TimeFormatUtil.LongFormatDate(bean.getRewardTime()));
		holder.mContent.setText(bean.getSerial());
		
		holder.mButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TrackingUtils.track(mContext,TrackingUtils.ACTION_GIFT_SELF_CENTER, TrackingUtils.NAME_GIFT_SELF_CENTER_COPY);
				TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_GIFT_SELF_CENTER, TrackingGoogleUtils.NAME_GIFT_SELF_CENTER_COPY);
				copy(bean.getSerial(),mContext.getString(E_string.efun_pd_copy_success));
			}
		});
		
		holder.mContentOfPassword.setText(bean.getSecretCode());
		if(TextUtils.isEmpty(bean.getSecretCode())){
			holder.line.setVisibility(View.GONE);
			holder.mLinearOfPassword.setVisibility(View.GONE);
		}else{
			holder.mLinearOfPassword.setVisibility(View.VISIBLE);
			holder.mButonOfPassword.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					TrackingUtils.track(mContext,TrackingUtils.ACTION_GIFT_SELF_CENTER, TrackingUtils.NAME_GIFT_SELF_CENTER_COPY);
					TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_GIFT_SELF_CENTER, TrackingGoogleUtils.NAME_GIFT_SELF_CENTER_COPY);
					copy(bean.getSecretCode(),mContext.getString(E_string.efun_pd_copy_pw));
				}
			});
		}
		
		return convertView;
	}

	/**
	 * 复制文本内容
	 * @param content
	 */
	private void copy(String content,String toastStr){
		ClipboardManager clip = (ClipboardManager)this.mContext.getSystemService(Context.CLIPBOARD_SERVICE); 
		clip.setText(content); // 复制
		ViewUtils.createToast(this.mContext, toastStr,E_layout.efun_pd_toast_copy,E_style.DL_Transparent);
		//ToastUtils.toast(this.mContext, E_string.efun_pd_copy_success);
	}
	
	public static class ViewHolder {
		public LinearLayout mLinearOfAccount,mLinearOfPassword;
		public TextView mCaption;
		public TextView mTitle;
		public TextView mContentOfPassword;
		public TextView mContent;
		public TextView mButonOfPassword;
		public TextView mButton;
		public TextView mTime;
		public View line;
	}

}
