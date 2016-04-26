package org.cpblog.util;

import java.util.List;

import org.cpblog.domain.User;
import org.cpblog.ui.ImageActivity;
import org.cpblog.ui.MyBlogBrowser;
import org.kymjs.kjframe.KJDB;
import org.kymjs.kjframe.utils.StringUtils;

import android.content.Context;
import android.content.Intent;

/**
 * 
 * @author ym
 * 
 */
public class UIHelper {

	private static User user = null;

	/*
    * 
    */
	public static void toGallery(Context cxt, String url) {
		if (!StringUtils.isEmpty(url)) {
//			Intent intent = new Intent();
//			intent.putExtra(ImageActivity.URL_KEY, url);
//			intent.setClass(cxt, ImageActivity.class);
//			cxt.startActivity(intent);
		}

	}

	/**
	  * 
	  */
	public static void toBrowser(Context cxt, String url) {
		LogCp.i(LogCp.CP, UIHelper.class + " 要打开的url :" + url);
		if (StringUtils.isEmpty(url)) {
			return;
		}
		if (url.indexOf("blog.kymjs.com") > 0) {
			Intent intent = new Intent(cxt, MyBlogBrowser.class);
			intent.putExtra(MyBlogBrowser.BROWSER_KEY, url);
			intent.putExtra(MyBlogBrowser.BROWSER_TITLE_KEY, "博客详情");
			cxt.startActivity(intent);
		}

	}

	/**
	 * 
	 */
	public static void saveUser(Context cxt, User u) {
		KJDB kjdb = KJDB.create(cxt);
		kjdb.deleteByWhere(User.class, "");
		user = u;
		kjdb.save(u);

	}

	/*
	 * 
	 */
	public static User getUser(Context cxt) {
		if (user != null)
			return user;

		KJDB kjdb = KJDB.create(cxt);
		List<User> datas = kjdb.findAll(User.class);
		if (datas != null && datas.size() > 0) {
			user = datas.get(0);
		} else {
			user = new User();
			user.setUid(1);
		}
		return user;
	}

}
