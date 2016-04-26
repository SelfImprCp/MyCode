package org.cpblog.ui.fragment;

import org.cpblog.ui.R;
import org.cpblog.widget.RecordButton;
import org.cpblog.widget.RecordButton.OnFinishedRecordListener;
import org.cpblog.widget.RecordButtonUtil.OnPlayListener;
import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.utils.DensityUtils;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

/**
 * 语音动弹发布界面
 * 
 * @author ym
 * 
 */
public class TweetRecordFragment extends TitleBarFragment {

	@BindView(id = R.id.tweet_btn_record)
	RecordButton mBtnRecord;

	@BindView(id = R.id.tweet_time_record)
	TextView mTvTime;
	@BindView(id = R.id.tweet_text_record)
	TextView mTvInputLen;
	@BindView(id = R.id.tweet_edit_record)
	EditText mEtSpeech;
	@BindView(id = R.id.tweet_img_volume)
	ImageView mImgVolume;
	@BindView(id = R.id.tweet_img_add, click = true)
	ImageView mImgAdd;
	@BindView(id = R.id.tweet_layout_record, click = true)
	RelativeLayout mLayout;

	public static int MAX_LEN = 160;
	private final KJBitmap kjb = new KJBitmap();
	// 录音播放时的动画背景
	private AnimationDrawable drawable;
	private String filePath;

	@Override
	protected View inflaterView(LayoutInflater inflater, ViewGroup containner,
			Bundle bundle) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.frag_tweet_pub_record, containner,
				false);

	}

	@Override
	protected void setActionBarRes(ActionBarRes actionBarRes) {
		// TODO Auto-generated method stub
		super.setActionBarRes(actionBarRes);
		actionBarRes.title = getString(R.string.str_tweet_pub_record);
		actionBarRes.backImageId = R.drawable.titlebar_back;
		actionBarRes.menuImageId = R.drawable.titlebar_add;

	}

	@Override
	public void onMenuClick() {
		// TODO Auto-generated method stub
		super.onMenuClick();
		Intent intent = new Intent();
		// intent

	}

	@Override
	public void onBackClick() {
		// TODO Auto-generated method stub
		super.onBackClick();
		outsideAty.finish();

	}

	@Override
	protected void initWidget(View parentView) {
		// TODO Auto-generated method stub
		super.initWidget(parentView);

		RelativeLayout.LayoutParams params = (LayoutParams) mBtnRecord
				.getLayoutParams();
		params.width = DensityUtils.getScreenW(getActivity());
		params.height = (int) (DensityUtils.getScreenH(getActivity()) * 0.4);
		mBtnRecord.setLayoutParams(params);

		// 录音组件录音完成的监听

		mBtnRecord.setOnFinishedRecordListener(new OnFinishedRecordListener() {

			@Override
			public void onFinishedRecord(String audioPath, int recordTime) {

				mLayout.setVisibility(View.VISIBLE);
				if (recordTime < 10) {
					mTvTime.setText("0" + recordTime + "\"");
				} else {
					mTvTime.setText(recordTime + "\"");
				}
				mImgAdd.setVisibility(View.GONE);
				filePath = null;

			}

			@Override
			public void onCancleRecord() {
				mLayout.setVisibility(View.GONE);
			}
		});
		// 录单组件播放的监听
		drawable = (AnimationDrawable) mImgVolume.getBackground();
		mBtnRecord.getAudioUtil().setOnPlayListener(new OnPlayListener() {

			@Override
			public void stopPlay() {
				drawable.stop();
				mImgVolume.setBackgroundDrawable(drawable.getFrame(0));
			}

			@Override
			public void starPlay() {
				// TODO Auto-generated method stub
				mImgVolume.setBackgroundDrawable(drawable);
				drawable.start();
			}
		});

		// 输入框的监听
		mEtSpeech.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				if (arg0.length() > MAX_LEN) {
					mTvInputLen.setText("已达最大长度");
				} else {
					mTvInputLen.setText("还可以输入" + (MAX_LEN - arg0.length())
							+ "个字");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

				  
			}
		});

	}
	
	 @Override
	protected void widgetClick(View v) {
		// TODO Auto-generated method stub
		super.widgetClick(v);
		switch (v.getId()) {
		case R.id.tweet_layout_record:
			
			break;

		default:
			break;
		}
	}
	

}
