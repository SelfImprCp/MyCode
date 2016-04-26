package org.cpblog.ui;

import java.util.Map;

import org.cpblog.domain.LoginData;
import org.cpblog.util.KJAnimations;
import org.cpblog.widget.RoundImageView;
import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpConfig;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.StringUtils;
import org.xml.sax.Parser;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 * 
 * @author ym
 * 
 */
public class Login extends KJActivity {

	@BindView(id = R.id.login_layout_input)
	private RelativeLayout mLayoutInput;
	@BindView(id = R.id.login_et_uid)
	private EditText mEtUid;
	@BindView(id = R.id.login_et_pwd)
	private EditText mEtPwd;
	@BindView(id = R.id.login_btn_login, click = true)
	private Button mBtnLogin;

	@BindView(id = R.id.login_img_head)
	private RoundImageView mImgHead;

	@Override
	public void setRootView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.aty_login);
	}

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		super.initWidget();

		KJAnimations.openLoginAnim(mLayoutInput);
	}

	@Override
	public void widgetClick(View v) {
		// TODO Auto-generated method stub
		super.widgetClick(v);

		switch (v.getId()) {
		case R.id.login_btn_login:
			doLogin();
			break;

		default:
			break;
		}
	}

	/**
	 * 输入合法性检测
	 */

	private boolean inputCheck() {
		if (StringUtils.isEmpty(mEtUid.getText().toString())) {
			ViewInject.toast(getString(R.string.account_not_empty));
			return false;
		}
		if (StringUtils.isEmpty(mEtPwd.getText().toString())) {
			ViewInject.toast(getString(R.string.password_not_empty));
			return false;
		}
		return true;
	}

	private void doLogin() {
		if (!inputCheck()) {
			return;
		}
		HttpConfig config = new HttpConfig();
		config.cacheTime = 0;
		KJHttp kjh = new KJHttp(config);
		HttpParams params = new HttpParams();
		params.put("username", mEtUid.getText().toString());
		params.put("pwd", mEtPwd.getText().toString());
		kjh.post("http://www.oschina.net/action/api/login_validate", params,
				new HttpCallBack() {

					@Override
					public void onSuccess(Map<String, String> headers, byte[] t) {
						// TODO Auto-generated method stub
						super.onSuccess(headers, t);
						String cookie = headers.get("Set-Cookie");
						if (t != null) {
							String str = new String(t);
							LoginData data = org.cpblog.util.Parser.xmlToBean(
									LoginData.class, str);

							try {
								if (1 == data.getResult().getErrorCode()) {
									org.cpblog.domain.User user = data
											.getUser();
									user.setCookie(cookie);
									user.setAccount(mEtUid.getText().toString());
									user.setPwd(mEtPwd.getText().toString());
									org.cpblog.util.UIHelper
											.saveUser(aty, user);
									finish();
								} else {
									mEtPwd.setText(null);
									mEtUid.setText(null);
								}
								ViewInject.toast(data.getResult()
										.getErrorMessage());
							} catch (NullPointerException e) {
								ViewInject.toast("登陆失败");
								mEtPwd.setText(null);
								mEtUid.setText(null);
							}

						}

					}

				});
	}

}
