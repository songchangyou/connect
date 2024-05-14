package com.mirth.connect.client.ui.message.impl;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.mirth.connect.client.ui.message.ILoginPanelMessageBundle;
import com.mirth.connect.client.ui.util.MessageBundleUtils;


public class LoginPanelMessageBundleImpl implements ILoginPanelMessageBundle {
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
	public void afterInit(JFrame frame,JLabel headerLabel, JLabel serverLabel,JLabel userNameLabel,JLabel passwordLabel
			,JLabel corpLabel,JButton loginButton,JButton closeButton) {
		String headerText = MessageBundleUtils.getMessage(getLabelKey("headerText"));
		String headerTextFont = MessageBundleUtils.getFontFamily(getLabelKey("headerFont"));
		headerLabel.setFont(new java.awt.Font(headerTextFont, 1, 18));
		headerLabel.setText(headerText);
		serverLabel.setText(MessageBundleUtils.getMessage(getLabelKey("serverText")));
		userNameLabel.setText(MessageBundleUtils.getMessage(getLabelKey("userNameText")));
		passwordLabel.setText(MessageBundleUtils.getMessage(getLabelKey("passwordText")));
//		corpLabel.setText(MessageBundleUtils.getMessage(getLabelKey("corpText")));
		loginButton.setText(MessageBundleUtils.getMessage(getLabelKey("loginButtonText")));
		closeButton.setText(MessageBundleUtils.getMessage(getLabelKey("closeButtonText")));
	}
	
	/**
	 * 获取标题
	 * @param version 系统版本
	 */
	public String getTitle(String version) {
		return MessageBundleUtils.getMessageFormat(getLabelKey("title"),version);
	}
	
	private String getLabelKey(String key) {
		return MESSAGE_ROOT_KEY +"label."+key;
	}
	
	private static final String MESSAGE_ROOT_KEY = "loginPanel.";
}
