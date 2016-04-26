package org.cpblog.ui.fragment;

import java.util.List;

import org.cpblog.adapter.BlogAdapter;
import org.cpblog.domain.Blog;
import org.cpblog.ui.Main;
import org.cpblog.ui.R;
import org.cpblog.ui.widget.listview.FooterLoadingLayout;
import org.cpblog.ui.widget.listview.PullToRefreshBase;
import org.cpblog.ui.widget.listview.PullToRefreshBase.OnRefreshListener;
import org.cpblog.ui.widget.listview.PullToRefreshList;
import org.cpblog.util.LogCp;
import org.cpblog.util.Parser;
import org.cpblog.util.UIHelper;
import org.cpblog.widget.EmptyLayout;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpConfig;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.utils.StringUtils;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 主界面博客模块
 * 
 * @author ym
 * 
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class BlogFragment extends TitleBarFragment {

	@BindView(id = R.id.empty_layout)
	private EmptyLayout mEmptyLayout;
	@BindView(id = R.id.bolg_swiperfreshlayout)
	private PullToRefreshList mRefreshLayout;
	private ListView mList;

	private Main aty;
	private KJHttp kjh;
	private String cache;
	private BlogAdapter adapter;
	private final String MY_BLOG_HOST = "http://www.kymjs.com/api/json_blog_list";

	@Override
	protected View inflaterView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
		aty = (Main) getActivity();

		return View.inflate(aty, R.layout.frag_blog, null);
	}

	@Override
	protected void setActionBarRes(ActionBarRes actionBarRes) {
		actionBarRes.title = getString(R.string.app_name);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		super.initData();
		HttpConfig config = new HttpConfig();
		int hour = StringUtils.toInt(StringUtils.getDataTime("HH"), 0);
		if (hour > 12 && hour < 22) {
			config.cacheTime = 10;
		} else {
			config.cacheTime = 300;
		}
		kjh = new KJHttp(config);

	}

	@Override
	protected void initWidget(View parentView) {
		// TODO Auto-generated method stub
		super.initWidget(parentView);
		mEmptyLayout.setOnLayoutClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
			}
		});

		listViewPreference();
		fillUI();
	}

	/**
	 * 初始化ListView样式
	 */
	private void listViewPreference() {
		mList = mRefreshLayout.getRefreshView();
		mList.setDivider(new ColorDrawable(android.R.color.transparent));
		mList.setOverscrollFooter(null);
		mList.setOverscrollHeader(null);
		mList.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
		mRefreshLayout.setPullLoadEnabled(true);
		((FooterLoadingLayout) mRefreshLayout.getFooterLoadingLayout())
				.setNoMoreDataText("学习不可贪多哦。。。");
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				UIHelper.toBrowser(aty, adapter.getItem(arg2).getUrl());
			}
		});
		mRefreshLayout.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				refresh();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				mRefreshLayout.setHasMoreData(false);

			}

		});

	}

	/**
	  * 
	  */
	private void fillUI() {
		cache = kjh.getStringCache(MY_BLOG_HOST);
		if (!StringUtils.isEmpty(cache)) {
			List<Blog> datas = Parser.getBlogList(cache);
			adapter = new BlogAdapter(mList, datas);
			mList.setAdapter(adapter);
			mEmptyLayout.dismiss();
		}
		refresh();
	}

	/**
	 * 
	 */
	private void refresh() {
		kjh.get(MY_BLOG_HOST, new HttpCallBack() {

			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				LogCp.i(LogCp.CP, BlogFragment.class + "请求回的数据了，，" + t);
				if (t != null) {
					List<Blog> datas = Parser.getBlogList(t);
					adapter = new BlogAdapter(mList, datas);
					mList.setAdapter(adapter);

				}
				mEmptyLayout.dismiss();
			}

			@Override
			public void onFailure(int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(errorNo, strMsg);
				if (adapter != null && adapter.getCount() > 0)
					return;
				else
					mEmptyLayout.setErrorType(EmptyLayout.NODATA);
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				mRefreshLayout.onPullDownRefreshComplete();
				mRefreshLayout.onPullUpRefreshComplete();
			}
		});

	}

}
