package org.cpblog.ui.fragment;

import org.cpblog.domain.SimpleBackPage;
import org.cpblog.ui.Login;
import org.cpblog.ui.R;
import org.cpblog.ui.SimpleBackActivity;
import org.cpblog.util.LogCp;
import org.cpblog.util.UIHelper;
import org.cpblog.widget.RoundImageView;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.ui.BindView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 发现界面
 * 
 * @author ym
 * 
 */
@SuppressLint("NewApi")
public class FindFragment extends TitleBarFragment {

	@BindView(id = R.id.find_img_head, click = true)
	private RoundImageView mImgHead;
	@BindView(id = R.id.find_tv_name)
	private TextView mTvName;

	
	 @BindView(id = R.id.find_plugin_1 ,click = true)
	 private TextView mTvTweet;
	
	@SuppressLint("NewApi")
	@Override
	protected View inflaterView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		View view = View.inflate(getActivity(), R.layout.frag_find, null);

		return view;
	}

	@Override
	protected void setActionBarRes(ActionBarRes actionBarRes) {

		actionBarRes.title = getString(R.string.app_name);

	}

	@Override
	protected void initWidget(View parentView) {
		// TODO Auto-generated method stub
		super.initWidget(parentView);

	}

	@Override
	protected void widgetClick(View v) {
		// TODO Auto-generated method stub
		super.widgetClick(v);

		switch (v.getId()) {
		case R.id.find_img_head:
			int id = UIHelper.getUser(outsideAty).getUid();
			if (id == 1) {
				outsideAty.showActivity(outsideAty, Login.class);
			}

			break;
		// 动弹
		case R.id.find_plugin_1:
			SimpleBackActivity.postShowWith(outsideAty,
					SimpleBackPage.OSC_TWEET_LIST);
			LogCp.i(LogCp.CP, FindFragment.class + "监听到点击");
			break;

		default:
			break;
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		KJBitmap kjb = new KJBitmap();
		kjb.displayWithErrorBitmap(mImgHead, UIHelper.getUser(outsideAty)
				.getPortrait(), R.drawable.default_head);

		Log.i("cp", "加载图片的地址。 " + UIHelper.getUser(outsideAty).getPortrait());
		mTvName.setText(UIHelper.getUser(outsideAty).getName());
	}

}
