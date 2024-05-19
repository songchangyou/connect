package com.mirth.connect.client.ui.message;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 登录界面
 *
 */
public interface ILoginPanelMessageBundle {

	/**
	 * 初始化完成调用
	 * @param frame 登录控件
	 * @param headerLabel 头部标题
	 * @param serverLabel 服务地址
	 * @param userNameLabel 用户名
	 * @param passwordLabel 密码
	 * @param corpLabel 公司图标
	 * @param loginButton 登录按钮
	 * @param closeButton 退出按钮
	 */
	void afterInit(JFrame frame,JLabel headerLabel, JLabel serverLabel,JLabel userNameLabel,JLabel passwordLabel
			,JLabel corpLabel,JButton loginButton,JButton closeButton);
	
	/**
	 * 获取标题
	 * @param version 系统版本
	 */
	String getTitle(String version);
}
