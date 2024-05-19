package com.mirth.connect.client.ui;

import com.mirth.connect.client.ui.message.ILoginPanelMessageBundle;
import com.mirth.connect.client.ui.message.impl.LoginPanelMessageBundleImpl;

public class MessageBundleFactory {

	/**
	 * 登录界面
	 */
	public static ILoginPanelMessageBundle getLoginPanelBundle() {
		return new LoginPanelMessageBundleImpl();
	}
	
}
