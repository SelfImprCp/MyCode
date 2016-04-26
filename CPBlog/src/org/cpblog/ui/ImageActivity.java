package org.cpblog.ui;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.bitmap.BitmapCallBack;
import org.kymjs.kjframe.ui.BindView;

//import uk.co.senab.photoview.PhotoView;
//import uk.co.senab.photoview.PhotoViewAttacher;

import android.view.View;
import android.widget.ProgressBar;

/**
 * 图片预览
 * 
 * @author ym
 * 
 */

public class ImageActivity extends KJActivity {

	@Override
	public void setRootView() {
		// TODO Auto-generated method stub
		
	}

//	public static String URL_KEY = "ImageActivity_url";
//
//	@BindView(id = R.id.progress)
//	private ProgressBar mpProgressBar;
//	@BindView(id = R.id.images)
////	private PhotoView mImg;
//
//	private String url;
//	private KJBitmap kjb;
//
//	public void setRootView() {
//		setContentView(R.layout.aty_image);
//	}
//
//	@Override
//	public void initData() {
//		// TODO Auto-generated method stub
//		super.initData();
//		url = getIntent().getStringExtra(URL_KEY);
//		kjb = new KJBitmap();
//	}
//
//	@Override
//	public void initWidget() {
//		// TODO Auto-generated method stub
//		super.initWidget();
//		kjb.display(mImg, url, new BitmapCallBack() {
//			@Override
//			public void onPreLoad() {
//				// TODO Auto-generated method stub
//				super.onPreLoad();
//				mpProgressBar.setVisibility(View.VISIBLE);
//			}
//
//			@Override
//			public void onFinish() {
//				// TODO Auto-generated method stub
//				super.onFinish();
//				mpProgressBar.setVisibility(View.GONE);
//			}
//		});
//		mImg.setOnFinishListener(new PhotoViewAttacher.OnPhotoTapListener() {
//
//			@Override
//			public void onPhotoTap(View arg0, float arg1, float arg2) {
//				// TODO Auto-generated method stub
//				ImageActivity.this.finish();
//			}
//		});
//	}
}
