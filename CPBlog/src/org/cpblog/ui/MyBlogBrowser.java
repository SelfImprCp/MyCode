package org.cpblog.ui;

import java.util.List;

import org.cpblog.domain.CollectData;
import org.cpblog.util.LogCp;
import org.cpblog.widget.EmptyLayout;
import org.kymjs.kjframe.KJDB;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.StringUtils;

 
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MyBlogBrowser extends TitleBarActivity {

	@BindView(id = R.id.webview)
	WebView mWebView;
	@BindView(id = R.id.progress)
	ProgressBar mProgress;
	@BindView(id = R.id.empty_layout)
	private EmptyLayout mEmptyLayout;

	public static final String BROWSER_KEY = "browser_url";
	public static final String BROWSER_TITLE_KEY = "browser_title_url";
	public static final String DEFAULT = "http://blog.kymjs.com/";

	private String mCurrentUrl = DEFAULT;
	private String strTitle;

	private final CollectData mCollectData = new CollectData();
	private KJDB mKjdb;

	@Override
	public void setRootView() {
		// TODO Auto-generated method stub
		super.setRootView();
		setContentView(R.layout.aty_browser);
	}

	@Override
	protected void onBackClick() {
		// TODO Auto-generated method stub
		super.onBackClick();
		LogCp.i(LogCp.CP, MyBlogBrowser.class + "调用 onBackClick  ");
		finish();
	}

	@Override
	protected void onMenuClick() {
		// TODO Auto-generated method stub
		super.onMenuClick();
		Object tag = mImgMenu.getTag();
		// 如果没有tag ，且tag为真，则把tag改为false取消收藏
		if (tag != null && tag instanceof Boolean) {
			if ((Boolean) tag) {
				mImgMenu.setTag(Boolean.valueOf(false));
				mImgMenu.setImageResource(R.drawable.titlebar_unstar);
				mKjdb.deleteByWhere(CollectData.class, "url ='" + mCurrentUrl
						+ "'");
				return;
			}

		}
		mImgMenu.setTag(Boolean.valueOf(true));
		mImgMenu.setImageResource(R.drawable.titlebar_star);
		mCollectData.setName(mWebView.getTitle());
		mCollectData.setUrl(mCurrentUrl);
		mKjdb.save(mCollectData);

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
		Intent intent = getIntent();
		if (intent != null) {
			mCurrentUrl = intent.getStringExtra(BROWSER_KEY);
			strTitle = intent.getStringExtra(BROWSER_TITLE_KEY);
			if (StringUtils.isEmpty(mCurrentUrl)) {
				mCurrentUrl = DEFAULT;
			}
			if (StringUtils.isEmpty(strTitle)) {
				strTitle = getString(R.string.app_name);
			}
		}
		mKjdb = KJDB.create(aty);
	}

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		super.initWidget();
		initWebView();
		mProgress.setVisibility(View.GONE);
		mWebView.loadUrl(mCurrentUrl);
	}

	/**
	  *  
	  */
	@SuppressLint("NewApi")
	private void initWebView() {
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(false);
		webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webSettings.setAllowFileAccess(true);
		webSettings.setBuiltInZoomControls(true);
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			webSettings.setDisplayZoomControls(false);
		}
		mWebView.setWebViewClient(new MyWebViewClient());
		mWebView.setWebChromeClient(new MyWebChromeClient());

	}

	private class MyWebChromeClient extends WebChromeClient {
		@Override
		public void onReceivedTitle(WebView view, String title) {
			// TODO Auto-generated method stub
			super.onReceivedTitle(view, title);
			onWebTitle(view, title);
		}

		@Override
		public void onReceivedIcon(WebView view, Bitmap icon) {
			// TODO Auto-generated method stub
			super.onReceivedIcon(view, icon);
			onWebIcon(view, icon);
		}

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			// TODO Auto-generated method stub
			super.onProgressChanged(view, newProgress);
			if (newProgress > 60) {
				mEmptyLayout.dismiss();
				mProgress.setVisibility(View.GONE);
			}
		}

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mImgMenu.setTag(Boolean.valueOf(false));
		mImgMenu.setImageResource(R.drawable.titlebar_unstar);
		if (!StringUtils.isEmpty(strTitle)) {
			mTvTitle.setText(strTitle);
		}

	}

	/**
	 * 当前WebView 显示页面标题
	 */

	protected void onWebTitle(WebView view, String title) {

		if (StringUtils.isEmpty(strTitle) && mTvTitle != null) {
			mTvTitle.setText(mWebView.getTitle());
		}
	}

	/**
	 * 当前WebView显示页面的图标
	 * 
	 * @param view
	 *            WebView
	 * @param icon
	 *            web页面图标
	 */
	protected void onWebIcon(WebView view, Bitmap icon) {
	}

	/**
	 * 
	 * @author ym
	 * 
	 */
	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			onUrlLoading(view, url);
			boolean flag = super.shouldOverrideUrlLoading(view, url);
			mCurrentUrl = url;
			return flag;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
			onUrlFinished(view, url);

		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			// TODO Auto-generated method stub
			super.onReceivedError(view, errorCode, description, failingUrl);
			ViewInject.toast("没有找到数据");
			mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
		}

	}

	/**
	 * 载入链接之前会被调用
	 * 
	 * @param view
	 *            WebView
	 * @param url
	 *            链接地址
	 */
	protected void onUrlLoading(WebView view, String url) {

		LogCp.i(LogCp.CP, MyBlogBrowser.class + "onUrlLoading url =  " + url);
	}

	/**
	 * 链接载入成功后会被调用
	 * 
	 * @param view
	 *            WebView
	 * @param url
	 *            链接地址
	 * */

	protected void onUrlFinished(WebView view, String url) {

		LogCp.i(LogCp.CP, MyBlogBrowser.class + "onUrlFinished url =  " + url);

		mCurrentUrl = url;
		List<CollectData> datas = mKjdb.findAllByWhere(CollectData.class,
				"url='" + url + "'");
		if (datas != null && datas.size() != 0) {
			mImgMenu.setImageResource(R.drawable.titlebar_star);
			mImgMenu.setTag(Boolean.valueOf(true));
		} else {
			mImgMenu.setImageResource(R.drawable.titlebar_unstar);
			mImgMenu.setTag(Boolean.valueOf(false));
		}

	}

}
