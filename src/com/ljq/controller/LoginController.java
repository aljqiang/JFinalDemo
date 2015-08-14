package com.ljq.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ljq.ext.render.CaptchaRender;

public class LoginController extends Controller {

	@ActionKey("/logout")
	public void logout() {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isAuthenticated()) {
			currentUser.logout();
		}
		redirect("/");
	}

	@ActionKey("/action/login")
	public void loginAction() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!CaptchaRender.validate(this, getPara("code"), "key")) {
			map.put("success", false);
			map.put("data", "验证码错误");
			renderJson(map);
			return;
		}

		String username = getPara("username");
		String password = getPara("password");

		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username,
				password);
		token.setRememberMe(getParaToBoolean("rm", true));
		try {
			currentUser.login(token);
			Record users = Db
					.findFirst(
							"select id,username,create_time,status from sys_users where username=?",
							username);
			if (!users.get("status").toString().equals("1"))
				throw new LockedAccountException("账号 [" + users.get("username")
						+ "]已经锁定.");
			map.put("success", true);
			map.put("data", users);
			renderJson(map);
		} catch (UnknownAccountException e) {
			map.put("success", false);
			map.put("data", "未知账号");
			renderJson(map);
		} catch (IncorrectCredentialsException e) {
			map.put("success", false);
			map.put("data", "密码错误");
			renderJson(map);
		} catch (LockedAccountException e) {
			map.put("success", false);
			map.put("data", e.getMessage());
			renderJson(map);
		} catch (Exception e) {
			// 登录失败
			map.put("success", false);
			map.put("data", "验证码错误");
			renderJson(map);
		}
	}

}
