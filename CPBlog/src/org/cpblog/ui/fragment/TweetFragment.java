package org.cpblog.ui.fragment;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.cpblog.adapter.TweetAdapter;
import org.cpblog.domain.SimpleBackPage;
import org.cpblog.domain.Tweet;
import org.cpblog.domain.TweetsList;
import org.cpblog.ui.R;
import org.cpblog.ui.SimpleBackActivity;
import org.cpblog.ui.widget.listview.PullToRefreshBase;
import org.cpblog.ui.widget.listview.PullToRefreshBase.OnRefreshListener;
import org.cpblog.ui.widget.listview.PullToRefreshList;
import org.cpblog.util.LogCp;
import org.cpblog.util.Parser;
import org.cpblog.widget.EmptyLayout;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpConfig;
import org.kymjs.kjframe.ui.BindView;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

public class TweetFragment extends TitleBarFragment {

	@BindView(id = R.id.empty_layout)
	private EmptyLayout mEmptyLayout;
	@BindView(id = R.id.listview)
	private PullToRefreshList mRefreshLayout;
	private ListView mListView;

	private KJHttp kjh;
	private final Set<Tweet> tweets = new TreeSet<Tweet>();
	private TweetAdapter adapter;

	private final String OSCTWEET_HOST = "http://www.oschina.net/action/api/tweet_list?pageSize=20&pageIndex=";

	@Override
	protected View inflaterView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		return View.inflate(outsideAty, R.layout.frag_pull_refresh_list, null);
	}

	 
	@Override
	protected void setActionBarRes(ActionBarRes actionBarRes) {
		// TODO Auto-generated method stub
		super.setActionBarRes(actionBarRes);
		actionBarRes.title = getString(R.string.str_tweet_title);
		actionBarRes.backImageId = R.drawable.titlebar_back;
		actionBarRes.menuImageId = R.drawable.titlebar_add;

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		super.initData();
		HttpConfig config = new HttpConfig();
		config.cacheTime = 0;
		config.useDelayCache = false;
		kjh = new KJHttp(config);

	}

	@Override
	protected void initWidget(View parentView) {
		// TODO Auto-generated method stub
		super.initWidget(parentView);
		mEmptyLayout.setOnLayoutClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mEmptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
				refresh();
			}
		});

		mListView = mRefreshLayout.getRefreshView();
		mListView.setDivider(new ColorDrawable(android.R.color.transparent));
		mListView.setSelector(new ColorDrawable(android.R.color.transparent));
		mRefreshLayout.setPullLoadEnabled(true);
		mRefreshLayout.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				refresh(0);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				refresh();
			}

		});
		fillUI();
	}

	private void fillUI() {
		refresh(0);
	}

	/**
	 * 刷新
	 * 
	 * @param page
	 *            请求第几页数据
	 */
	private void refresh() {
		double page = tweets.size() / 20;
		page += 1.9; // 因为服务器返回的可能会少于20条，所以采用小数进一法加载下一页
		refresh((int) page);
	}

	/**
	  * 
	  */
	private void refresh(final int page) {
		kjh.get(OSCTWEET_HOST + page, new HttpCallBack() {
			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				LogCp.i(LogCp.CP, TweetFragment.class + "请示回来的Tweet 数据 " + t);
				List<Tweet> datas = Parser.xmlToBean(TweetsList.class, t)
						.getList();
				tweets.addAll(datas);
				if (adapter == null) {
					adapter = new TweetAdapter(mListView, datas,
							R.layout.item_list_tweet);
					mListView.setAdapter(adapter);
				} else {
					adapter.refresh(datas);
				}
				mEmptyLayout.dismiss();

			}

			@Override
			public void onFailure(int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(errorNo, strMsg);

				if (adapter != null && adapter.getCount() > 0) {
					return;
				} else {
					mEmptyLayout.setErrorType(EmptyLayout.NODATA);
				}

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

	
 
 @Override
	public void onBackClick() {
		// TODO Auto-generated method stub
		super.onBackClick();
		outsideAty.finish();
	}

	@Override
	public void onMenuClick() {
		// TODO Auto-generated method stub 
		super.onMenuClick();
		
		
		LogCp.i(LogCp.CP, TweetFragment.class + "监听到添加Twett 的事件 ，， ");
		
		SimpleBackActivity.postShowForResult(this, 1,
				SimpleBackPage.OSC_TWEET_SEND);
	} 

}
