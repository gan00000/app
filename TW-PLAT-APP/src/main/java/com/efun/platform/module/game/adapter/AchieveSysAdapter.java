package com.efun.platform.module.game.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.AndroidScape.E_array;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.image.ImageManager;
import com.efun.platform.module.PopWindow;
import com.efun.platform.module.base.impl.OnEfunItemClickListener;
import com.efun.platform.module.game.bean.AchieveSysItemBean;
import com.efun.platform.module.game.bean.GameItemBean;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.module.utils.PopUtils;
import com.efun.platform.module.utils.TrackingUtils;
import com.efun.platform.utils.Const.AppStatus;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.Web;
import com.efun.platform.utils.Const;
import com.efun.platform.utils.StatLogInfoUtils;
/**
 * 成就系統游戲列表适配器
 * @author itxuxxey
 *
 */
public class AchieveSysAdapter extends BaseAdapter {
	private LayoutInflater mLayoutInflater;
	private ArrayList<AchieveSysItemBean> mArray;
	private Context mContext;
	public AchieveSysAdapter(Context context) {
		this.mLayoutInflater = LayoutInflater.from(context);
		this.mContext = context;
		this.mArray = new ArrayList<AchieveSysItemBean>();
	}
	/**
	 * 翻页
	 * @param array
	 */
	public void append(ArrayList<AchieveSysItemBean> array){
		mArray.addAll(array);
		notifyDataSetChanged();
	}
	/**
	 * 刷新
	 * @param array
	 */
	public void refresh(ArrayList<AchieveSysItemBean> array){
		mArray.clear();
		append(array);
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

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(E_layout.efun_pd_achieve_sys_list_item, null);
			holder.mIcon = (ImageView) convertView.findViewById(E_id.item_icon);
			holder.mTitle = (TextView) convertView.findViewById(E_id.item_title);
//			holder.mText = (TextView) convertView.findViewById(E_id.item_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ImageManager.displayImage(mArray.get(position).getGameIcon(), holder.mIcon,ImageManager.IMAGE_SQUARE);
		holder.mTitle.setText(mArray.get(position).getGameName());
		return convertView;
	}

	public static class ViewHolder {
		public TextView mTitle;
		public ImageView mIcon;
	}

}
