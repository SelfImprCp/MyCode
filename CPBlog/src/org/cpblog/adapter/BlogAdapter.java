package org.cpblog.adapter;

import java.util.List;

import org.cpblog.domain.Blog;
import org.cpblog.ui.R;
import org.cpblog.util.UIHelper;
import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.utils.StringUtils;
import org.kymjs.kjframe.widget.AdapterHolder;
import org.kymjs.kjframe.widget.KJAdapter;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ImageView;

/**
 * 
 * @author ym 博客的界面适配器
 * 
 */

public class BlogAdapter extends KJAdapter<Blog> {
	private final KJBitmap kjb = new KJBitmap();

	public BlogAdapter(AbsListView view, List<Blog> mDatas) {
		super(view, mDatas, org.cpblog.ui.R.layout.item_list_blog);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void convert(AdapterHolder helper, Blog item, boolean isScrolling) {

		helper.getView(R.id.item_blog_tip_recommend).setVisibility(
				item.getIsRecommend() == 0 ? View.GONE : View.VISIBLE);
		helper.getView(R.id.item_blog_tip_tody).setVisibility(
				item.getIsAuthor() == 0 ? View.GONE : View.VISIBLE);
		ImageView image = helper.getView(R.id.item_blog_img);
		String url = item.getImageUrl();
		if (StringUtils.isEmpty(url)) {
			image.setVisibility(View.GONE);
		} else {
			image.setVisibility(View.VISIBLE);
			onPicClick(image, url);
			if (isScrolling) {
				kjb.displayCacheOrDefult(image, url, R.drawable.pic_bg);
			} else {
				kjb.display(image, url, 480, 420, R.drawable.pic_bg);
			}
		}
		helper.setText(R.id.item_blog_tv_title, item.getTitle());
		helper.setText(R.id.item_blog_tv_description, item.getDescription());
		helper.setText(R.id.item_blog_tv_author, "宝宝");
		helper.setText(R.id.item_blog_tv_date,
				StringUtils.friendlyTime(item.getDate()));

	}

	private void onPicClick(View view, final String url) {
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				UIHelper.toGallery(arg0.getContext(), url);
			}
		});
	}

}
