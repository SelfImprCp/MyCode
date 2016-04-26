package org.cpblog.ui;

import java.util.Currency;

import org.cpblog.ui.fragment.BlogFragment;
import org.cpblog.ui.fragment.FindFragment;
import org.cpblog.ui.fragment.TitleBarFragment;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.KJFragment;

import android.view.View;
import android.widget.RadioButton;

/**
 * 
 * @author ym
 * @since 15-8
 */

public class Main extends TitleBarActivity {

	@BindView(id = R.id.bottombar_content1, click = true)
	private RadioButton mRbtnContent1;
	@BindView(id = R.id.bottombar_content2, click = true)
	private RadioButton mRbtnContent2;
	@BindView(id = R.id.bottombar_content3, click = true)
	private RadioButton mRbtnContent3;

	private TitleBarFragment contentFragment1;
	private TitleBarFragment contentFragment2;
	private TitleBarFragment contentFragment3;
	private TitleBarFragment currentFragment;

	private float titleBarHeight;

	@Override
	public void setRootView() {
		// TODO Auto-generated method stub
		super.setRootView();
		setContentView(R.layout.aty_main);

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
		contentFragment1 = new BlogFragment();
		contentFragment2 = new FindFragment();

		titleBarHeight = getResources().getDimension(R.dimen.titlebar_height);

	}

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		super.initWidget();
		changeFragment(contentFragment1);

	}

	@Override
	public void widgetClick(View v) {
		// TODO Auto-generated method stub
		super.widgetClick(v);

		switch (v.getId()) {
		case R.id.bottombar_content1:
			changeFragment(contentFragment1);
			break;

		case R.id.bottombar_content2:
			changeFragment(contentFragment2);
			break;
		case R.id.bottombar_content3:
			break;

		default:
			break;
		}
	}

	public void changeFragment(TitleBarFragment targetFragment) {
		// TODO Auto-generated method stub
		currentFragment = targetFragment;
		super.changeFragment(R.id.main_content, targetFragment);
	}

}
