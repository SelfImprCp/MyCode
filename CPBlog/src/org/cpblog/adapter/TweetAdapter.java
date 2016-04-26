package org.cpblog.adapter;

import java.util.Collection;

import org.cpblog.domain.Tweet;
import org.cpblog.ui.R;
import org.cpblog.util.LogCp;
import org.cpblog.util.UIHelper;
import org.cpblog.widget.CollapsibleTextView;
 import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.utils.StringUtils;
import org.kymjs.kjframe.widget.AdapterHolder;
import org.kymjs.kjframe.widget.KJAdapter;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;

/**
 * 
 * @author ym
 * 
 */
public class TweetAdapter extends KJAdapter<Tweet> {

	private final KJBitmap kjb = new KJBitmap();

	public TweetAdapter(AbsListView view, Collection<Tweet> mDatas,
			int itemLayoutId) {
		super(view, mDatas, itemLayoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void convert(AdapterHolder helper, Tweet item, boolean isScrolling) {
		// TODO Auto-generated method stub
		String headUrl = item.getPortrait();
		int end = headUrl.indexOf('?');
		if (end > 0) {
			headUrl = headUrl.substring(0, end);
		}
		if (isScrolling) {
			kjb.displayCacheOrDefult(helper.getView(R.id.msg_item_img_head),
					headUrl, R.drawable.default_head);
		} else {
			kjb.display(helper.getView(R.id.msg_item_img_head), headUrl, 135,
					135, R.drawable.default_head);
		}
		
		
		helper.setText(R.id.msg_item_text_uname, item.getAuthor());
		
		
		CollapsibleTextView content = helper
				.getView(R.id.msg_item_text_content);
//		content.setText(InputHelper.displayEmoji(content.getResources(),
//				item.getBody(), "[", "]"));

		helper.setText(R.id.msg_item_text_time,
				StringUtils.friendlyTime(item.getPubDate()));

		LogCp.i(LogCp.CP, TweetAdapter.class + "友好化时间的类型：" + item.getPubDate());
		View image = helper.getView(R.id.msg_item_img);
		if (StringUtils.isEmpty(item.getImgBig())) {
			image.setVisibility(View.GONE);
		} else {
			image.setVisibility(View.VISIBLE);
			onPicClick(image, item.getImgBig());
			if (isScrolling) {
				kjb.displayCacheOrDefult(image, item.getImgBig(),
						R.drawable.pic_bg);
			} else {
				kjb.displayWithLoadBitmap(image, item.getImgBig(),
						R.drawable.pic_bg);
			}

		}

	}
	/**
	 * 
	 * @param view
	 * @param url
	 */
	
	private void onPicClick(View view,final String url)
	{
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			 
				UIHelper.toGallery(arg0.getContext(),url);
				
			}
		});
		
		
	}
	
	
}
