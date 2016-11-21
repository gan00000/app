package com.efun.platform.module.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efun.core.tools.EfunResourceUtil;
import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.AndroidScape.E_color;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.AndroidScape.E_style;
import com.efun.platform.module.base.impl.OnEfunItemClickListener;
import com.efun.platform.module.base.impl.OnEfunListener;
import com.efun.platform.module.base.impl.OnToastClickListener;
import com.efun.platform.utils.Const.ToastType;
import com.efun.platform.widget.list.XListView;

/**
 * 创建View类
 * 
 * @author Jesse
 * 
 */
public class ViewUtils {
	public static View createView(LayoutInflater inflater, int layoutId) {
		return inflater.inflate(layoutId, null);
	}

	public static View createView(Context context, int layoutId) {
		return LayoutInflater.from(context).inflate(layoutId, null);
	}

	/**
	 * 创建Margin 40px View
	 * 
	 * @return
	 */
	public static View createMargin(Context context) {
		View margin = createView(context, E_layout.efun_pd_margin_40);
		return margin;
	}

	/**
	 * 创建 View
	 * 
	 * @return
	 */
	public static View createActivityHeader(Context context) {
		View header = createView(context,
				E_layout.efun_pd_summary_list_activity_header);
		return header;
	}

	/**
	 * 创建加载框
	 * 
	 * @param context
	 * @return
	 */
	public static View createLoading(Context context) {
		View view = createView(context, E_layout.efun_pd_async_loading);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		view.setBackgroundColor(context.getResources().getColor(
				E_color.e_efeff4));
		return view;
	}

	/**
	 * 创建底部按钮
	 * 
	 * @param context
	 * @return
	 */
	public static View createBottom(Context context) {
		return createView(context, E_layout.efun_pd_game_detail_tab_bottom);
	}

	/**
	 * 创建WebView
	 * 
	 * @param context
	 * @return
	 */
	public static View createWebView(Context context) {
		return createView(context, E_layout.efun_pd_website);
	}

	/**
	 * 创建ListView
	 * 
	 * @return
	 */
	public static XListView createListView(Context context) {
		XListView listDetail = new XListView(context);
		listDetail.setPullRefreshEnable(false);
		listDetail.setPullLoadEnable(false);
		listDetail.setVerticalScrollBarEnabled(false);
		listDetail.setDivider(new ColorDrawable(Color.TRANSPARENT));
		listDetail.setDividerHeight(0);
		listDetail
				.setPersistentDrawingCache(ViewGroup.PERSISTENT_SCROLLING_CACHE);
		listDetail.setSelector(new ColorDrawable(Color.TRANSPARENT));
		return listDetail;
	}

	/**
	 * 创建ListView
	 * 
	 * @return
	 */
	public static XListView createListView2(Context context) {
		XListView listDetail = new XListView(context);
		listDetail.setPullLoadEnable(true);
		listDetail.setVerticalScrollBarEnabled(false);
		listDetail.setDivider(new ColorDrawable(Color.TRANSPARENT));
		listDetail.setDividerHeight(0);
		listDetail
				.setPersistentDrawingCache(ViewGroup.PERSISTENT_SCROLLING_CACHE);
		listDetail.setSelector(new ColorDrawable(Color.TRANSPARENT));
		return listDetail;
	}

	/**
	 * 得到自定义的progressDialog
	 * 
	 * @param context
	 * @param msg
	 * @return
	 */
	public static Dialog createLoadingDialog(Context context, String msg) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(E_layout.efun_pd_loading, null);
		TextView msgTv = (TextView) v.findViewById(E_id.text_1);
		msgTv.setText(msg);
		Dialog loadingDialog = new Dialog(context, E_style.DL_Dialog);
		loadingDialog.setCancelable(false);
		loadingDialog.setContentView(v, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		return loadingDialog;

	}

	public static Dialog createLoginWaitingDialog(Context context, String msg) {
		return createLoginWaitingDialog(context, msg, null, null);
	}

	public static Dialog createToast(Context context, String msg,
			int layoutRes, int theme) {
		LayoutInflater inflater = LayoutInflater.from(context);
		final View v = inflater.inflate(layoutRes, null);
		TextView msgTv = (TextView) v.findViewById(E_id.text_1);
		msgTv.setText(msg);
		final Dialog loadingDialog = new Dialog(context, theme);
		loadingDialog.setCancelable(false);
		loadingDialog.setContentView(v, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		loadingDialog.show();
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
			}
		});
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
			}
		}, 3000);
		return loadingDialog;
	}

	/**
	 * 应用更新弹窗
	 * 
	 * @param context
	 * @param onEfunItemClickListener
	 * @param isForce
	 * @return
	 */
	public static Dialog createToastUpdate(Context context,
			final OnEfunItemClickListener onEfunItemClickListener,
			final boolean isForce) {
		LayoutInflater inflater = LayoutInflater.from(context);
		final View v = inflater.inflate(E_layout.efun_pd_toast_update, null);
		TextView msgTv = (TextView) v.findViewById(E_id.item_content);
		TextView okBtn = (TextView) v.findViewById(E_id.text_1);
		TextView cancelBtn = (TextView) v.findViewById(E_id.text_2);
		TextView forceOkBtn = (TextView) v.findViewById(E_id.text_3);
		LinearLayout bodyLayout = (LinearLayout) v
				.findViewById(E_id.update_body);
		LinearLayout btnLayout = (LinearLayout) v
				.findViewById(E_id.contaier_relative_1);
		LinearLayout btnLayoutForce = (LinearLayout) v
				.findViewById(E_id.contaier_relative_2);
		final Dialog loadingDialog = new Dialog(context, E_style.Transparent);
		loadingDialog.setContentView(v, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		if (isForce) {// 强更
			bodyLayout
					.setBackgroundResource(E_drawable.efun_pd_update_force_bg);
			msgTv.setText(E_string.efun_pd_notification_update_2);
			forceOkBtn.setText(E_string.efun_pd_sure);
			btnLayout.setVisibility(View.GONE);
			btnLayoutForce.setVisibility(View.VISIBLE);
			loadingDialog.setCancelable(false);
			forceOkBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (loadingDialog != null) {
						if (onEfunItemClickListener != null) {
							onEfunItemClickListener.onItemClick(0);
						}
					}
				}
			});
		} else {// 普通更新
			bodyLayout
					.setBackgroundResource(E_drawable.efun_pd_update_common_bg);
			msgTv.setText(E_string.efun_pd_notification_update_1);
			btnLayout.setVisibility(View.VISIBLE);
			btnLayoutForce.setVisibility(View.GONE);
			okBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (onEfunItemClickListener != null) {
						onEfunItemClickListener.onItemClick(0);
						loadingDialog.dismiss();
					}
				}
			});
			cancelBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (loadingDialog != null) {
						loadingDialog.dismiss();
					}
				}
			});
			loadingDialog.getWindow().setType(
					(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
			loadingDialog.show();
		}
		return loadingDialog;
	}

	/**
	 * 弹窗
	 * @param context
	 * @param titleStr
	 * @param onEfunItemClickListener
	 * @return
	 */
	public static Dialog createToastCommon(Context context, String titleStr,
			final OnEfunItemClickListener onEfunItemClickListener) {
		LayoutInflater inflater = LayoutInflater.from(context);
		final View v = inflater.inflate(E_layout.efun_pd_toast_common_dialog, null);
		TextView msgTv = (TextView) v.findViewById(E_id.text_1);
		TextView okBtn = (TextView) v.findViewById(E_id.confirm);
		TextView cancelBtn = (TextView) v.findViewById(E_id.cancle);
		final Dialog loadingDialog = new Dialog(context, E_style.Transparent);
		loadingDialog.setContentView(v, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		// 普通更新
		msgTv.setText(titleStr);
		okBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (onEfunItemClickListener != null) {
					onEfunItemClickListener.onItemClick(0);
					loadingDialog.dismiss();
				}
			}
		});
		cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (onEfunItemClickListener != null) {
					onEfunItemClickListener.onItemClick(1);
					loadingDialog.dismiss();
				}
			}
		});
		loadingDialog.getWindow().setType(
				(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
		loadingDialog.show();
		return loadingDialog;
	}

	/**
	 * 弹窗对话框
	 * 
	 * @param context
	 * @param titleStr
	 *            标题
	 * @param content
	 *            内容项（一般用到规则说明，现在用在转点帮助文档）
	 * @param params
	 *            选择对话框的内容列表
	 * @param toastType
	 *            弹窗类型 : 1、普通对话框；2、有内容的对话框；3、选择对话框
	 * @param onEfunItemClickListener
	 *            确认按钮的点击事件监听
	 * @param listener
	 *            选择对话框的选择内容监听
	 * @return
	 */
	public static Dialog createToastCommon(final Context context,
			String titleStr, String content, final String[] params,
			int toastType,
			final OnEfunItemClickListener onEfunItemClickListener,
			final OnDialogSelect listener) {
		LayoutInflater inflater = LayoutInflater.from(context);
		final View v = inflater.inflate(E_layout.efun_pd_toast_common_model,
				null);
		TextView title = (TextView) v.findViewById(E_id.text_1);// 标题
		View singleCommView = (View) v.findViewById(E_id.text_2);// 简单对话框
		TextView secondText = (TextView) v.findViewById(E_id.text_3);// 有内容的对话框
		LinearLayout choseLayout = (LinearLayout) v
				.findViewById(E_id.chose_item_layout);// 选择对话框布局
		final TextView choseText = (TextView) v
				.findViewById(E_id.chose_item_tv);// 选择对话框item
		TextView confirm = (TextView) v.findViewById(E_id.confirm);// 确认按钮
		final Dialog loadingDialog = new Dialog(context, E_style.Transparent);
		loadingDialog.setContentView(v, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		title.setText(titleStr);
		secondText.setText("    " + content);
		if (toastType == ToastType.COMMONTYPE) {// 普通简单对话框
			singleCommView.setVisibility(View.VISIBLE);
			secondText.setVisibility(View.GONE);
			choseLayout.setVisibility(View.GONE);
			loadingDialog.setCancelable(false);
			confirm.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 确认按钮
					if (loadingDialog != null) {
						if (onEfunItemClickListener != null) {
							onEfunItemClickListener.onItemClick(0);
						}
						loadingDialog.dismiss();
					}
				}
			});
		} else if (toastType == ToastType.HASCONTENTTYPE) {// 有内容的对话框
			singleCommView.setVisibility(View.GONE);
			secondText.setVisibility(View.VISIBLE);
			choseLayout.setVisibility(View.GONE);
			loadingDialog.setCancelable(true);
			confirm.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 确认按钮
					if (loadingDialog != null) {
						if (onEfunItemClickListener != null) {
							onEfunItemClickListener.onItemClick(0);
						}
						loadingDialog.dismiss();
					}
				}
			});
			// loadingDialog.show();
		} else if (toastType == ToastType.CHOSETYPE) {// 带有选择按钮的对话框
			singleCommView.setVisibility(View.GONE);
			secondText.setVisibility(View.GONE);
			choseLayout.setVisibility(View.VISIBLE);
			if (params != null) {
				choseText.setText(params[0]);
			}
			loadingDialog.setCancelable(true);
			confirm.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 确认按钮
					if (loadingDialog != null) {
						if (onEfunItemClickListener != null) {
							onEfunItemClickListener.onItemClick(0);
						}
						loadingDialog.dismiss();
					}
				}
			});
			choseText.setOnClickListener(new OnClickListener() {// 选择游戏

						@Override
						public void onClick(View paramView) {
							// TODO Auto-generated method stub
							// 选择列表
							paramListDialog2(params, context, choseText,
									listener);
						}
					});
		}
		return loadingDialog;
	}
	
	public static Dialog createCommonToast(Context context,String content) {
		LayoutInflater inflater = LayoutInflater.from(context);
		final View v = inflater.inflate(EfunResourceUtil.findLayoutIdByName(context, "efun_pd_common_dialog_simple"), null);
		ImageView msgTv = (ImageView) v.findViewById(EfunResourceUtil.findViewIdByName(context, "dialog_close"));
		TextView contentTv = (TextView) v.findViewById(EfunResourceUtil.findViewIdByName(context, "dialog_content"));
		final Dialog loadingDialog = new Dialog(context, EfunResourceUtil.findStyleIdByName(context, "CS_Dialog"));
		loadingDialog.setCancelable(true);
		contentTv.setText(content);
		loadingDialog.setContentView(v, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		loadingDialog.show();
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
			}
		});
		msgTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				// TODO Auto-generated method stub
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
			}
		});
		return loadingDialog;
	}
	
	public static Dialog createCommonToast2(Context context,String content,String giftSerial) {
		LayoutInflater inflater = LayoutInflater.from(context);
		final View v = inflater.inflate(EfunResourceUtil.findLayoutIdByName(context, "efun_pd_common_dialog_sign"), null);
		ImageView msgTv = (ImageView) v.findViewById(EfunResourceUtil.findViewIdByName(context, "dialog_close"));
		TextView contentTv = (TextView) v.findViewById(EfunResourceUtil.findViewIdByName(context, "dialog_content"));
		LinearLayout giftSerialLayout = (LinearLayout) v.findViewById(EfunResourceUtil.findViewIdByName(context, "dialog_gift_serial_layout"));
		TextView serialTv = (TextView) v.findViewById(EfunResourceUtil.findViewIdByName(context, "dialog_gift_serial"));
		TextView confirm = (TextView) v.findViewById(EfunResourceUtil.findViewIdByName(context, "confirm"));
		final Dialog loadingDialog = new Dialog(context, EfunResourceUtil.findStyleIdByName(context, "CS_Dialog"));
		loadingDialog.setCancelable(false);
		contentTv.setText(content);
		if(EfunStringUtil.isEmpty(giftSerial)){
			giftSerialLayout.setVisibility(View.GONE);
		}else{
			serialTv.setText(giftSerial);
		}
		loadingDialog.setContentView(v, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		loadingDialog.show();
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
			}
		});
		confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
			}
		});
		msgTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				// TODO Auto-generated method stub
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
			}
		});
		return loadingDialog;
	}

	public static Dialog createLoginWaitingDialog(Context context, String msg,
			final OnEfunListener onEfunListener, Handler handler) {
		LayoutInflater inflater = LayoutInflater.from(context);
		final View v = inflater.inflate(E_layout.efun_pd_toast, null);
		TextView msgTv = (TextView) v.findViewById(E_id.text_1);
		msgTv.setText(msg);
		final Dialog loadingDialog = new Dialog(context, E_style.DL_Transparent);
		loadingDialog.setCancelable(false);
		loadingDialog.setContentView(v, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		loadingDialog.show();
		if (handler != null) {
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					if (onEfunListener != null) {
						loadingDialog.dismiss();
						((OnToastClickListener) onEfunListener).onClick();
					}
				}
			}, 3000);
		}
		return loadingDialog;
	}

	// 参数选择列表对话框
	public static void paramListDialog(final String[] params, Context mContext,
			final OnDialogSelect listener) {
		// 新建AlertDialog对话框
		new AlertDialog.Builder(mContext)
		// .setCancelable(false)
				.setItems(params, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (listener != null) {
							listener.onSelect(which);
						}
					}
				}).show();
	}

	// 参数选择列表对话框
	public static void paramListDialog2(final String[] params,
			Context mContext, final TextView contentTv,
			final OnDialogSelect listener) {
		// 新建AlertDialog对话框
		new AlertDialog.Builder(mContext)
		// .setCancelable(false)
				.setItems(params, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (listener != null) {
							contentTv.setText(params[which]);
							listener.onSelect(which);
						}
					}
				}).show();
	}

	public interface OnDialogSelect {
		public void onSelect(int postion);
	}
}
