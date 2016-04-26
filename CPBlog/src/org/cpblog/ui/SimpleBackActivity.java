package org.cpblog.ui;

import org.cpblog.domain.SimpleBackPage;
import org.cpblog.ui.fragment.TitleBarFragment;
import org.kymjs.kjframe.ui.KJFragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class SimpleBackActivity extends TitleBarActivity {
	public static String CONTENT_KEY = "sba_content_key";
	public static String DATA_KEY = "sba_datat_key";

	private TitleBarFragment currentFragment;

	@Override
	public void setRootView() {
		// TODO Auto-generated method stub
		super.setRootView();
		setContentView(R.layout.aty_simple_back);
		int value = getIntent().getIntExtra(CONTENT_KEY, -1);
		if (value != -1) {
			try {
				currentFragment = (TitleBarFragment) SimpleBackPage
						.getPageByValue(value).newInstance();
				changeFragment(currentFragment);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	/**
	 * 
	 */
	public void changeFragment(KJFragment targetFragment) {
		super.changeFragment(R.id.main_content, targetFragment);
	}

	/**
	 * 跳转到SimpleBackActivity时，只能使用该方法跳转
	 * 
	 * @param cxt
	 * @param page
	 * @param data
	 */
	public static void postShowWith(Context cxt, SimpleBackPage page,
			Bundle data) {
		Intent intent = new Intent(cxt, SimpleBackActivity.class);
		intent.putExtra(CONTENT_KEY, page.getValue());
		intent.putExtra(DATA_KEY, data);
		cxt.startActivity(intent);
	}

	/**
	 * 跳转到SimpleBackActivity时，只能使用该方法跳转
	 * 
	 * @param cxt
	 * @param page
	 */
	public static void postShowWith(Context cxt, SimpleBackPage page) {
		postShowWith(cxt, page, null);
	}

	/**
	 * 
	 * @param frgment
	 * @param code
	 * @param page
	 * @param data
	 */
	public static void postShowForResult(Fragment frgment, int code,
			SimpleBackPage page, Bundle data) {
		Intent intent = new Intent(frgment.getActivity(),
				SimpleBackActivity.class);
		intent.putExtra(CONTENT_KEY, page.getValue());
		intent.putExtra(DATA_KEY, data);
		frgment.startActivityForResult(intent, code);

	}

	/**
	 * 
	 * @param fragment
	 * @param code
	 * @param page
	 */
	public static void postShowForResult(Fragment fragment, int code,
			SimpleBackPage page) {
		postShowForResult(fragment, code, page);
	}

}
