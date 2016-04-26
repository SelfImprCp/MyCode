package org.cpblog.ui;

import org.cpblog.util.LogCp;
 
import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.KJActivityStack;
 

 

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author ym Ӧ��Activity�Ļ���
 * 
 */
public class TitleBarActivity extends KJActivity {
	public ImageView mImgBack;
	public TextView mTvTitle;
	public ImageView mImgMenu;
	public RelativeLayout mRLTitleBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		if (!"org.cpblog.ui".equals(getApplication().getPackageName())) {
			LogCp.i(LogCp.CP, TitleBarActivity.class + "��֤����");
			KJActivityStack.create().AppExit(aty);
		}
	}

	@Override
	protected void onStart() {

		try {
			mRLTitleBar = (RelativeLayout) findViewById(R.id.titlebar);

			mImgBack = (ImageView) findViewById(R.id.titlebar_img_back);
			mTvTitle = (TextView) findViewById(R.id.titlebar_text_title);
			mImgMenu = (ImageView) findViewById(R.id.titlebar_img_menu);
			mImgBack.setOnClickListener(this);
			mImgMenu.setOnClickListener(this);
			   LogCp.i(LogCp.CP, TitleBarActivity.class + "调用 onStart ");
		} catch (NullPointerException e) {
			throw new NullPointerException(
					"TitleBar Notfound from Activity layout");
		}

		super.onStart();
	}

	@Override
	public void widgetClick(View v) {
		// TODO Auto-generated method stub
		super.widgetClick(v);
		switch (v.getId()) {
		case R.id.titlebar_img_back:
			onBackClick();
			break;
		case R.id.titlebar_img_menu:
			onMenuClick();
			break;

		default:
			break;
		}

	}

	protected void onBackClick() {
	}

	protected void onMenuClick() {
	}

	@Override
	public void setRootView() {
		// TODO Auto-generated method stub

	}

}
