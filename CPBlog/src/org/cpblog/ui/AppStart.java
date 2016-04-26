package org.cpblog.ui;

import org.kymjs.kjframe.KJActivity;

 
 

import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * 
 * @author ym
 * @since 15-8
 * 
 */

public class AppStart extends KJActivity {

	@Override
	public void setRootView() {

		ImageView image = new ImageView(aty);
		image.setImageResource(R.drawable.splash_bg);
		Animation anim = AnimationUtils.loadAnimation(aty, R.anim.splash_start);
		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				jumpTo();
			}
		});
		image.setAnimation(anim);
		setContentView(image);

	}

	private void jumpTo() {
		 Intent jumpIntent = new Intent();
		 jumpIntent.setClass(aty, Main.class);
		 startActivity(jumpIntent);
		 finish();
	}

}
