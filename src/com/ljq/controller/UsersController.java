package com.ljq.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.ljq.model.Users;

public class UsersController extends Controller {

	@RequiresRoles(value = { "user", "admin" }, logical = Logical.OR)
	@ActionKey("/users/")
	public void index() {
		render("/pages/users/index.html");
	}

	@RequiresPermissions("showUser")
	@ActionKey("/users/list")
	public void list() {
		Page<Users> page = Users.dao.paginate(getParaToInt("page", 1),
				getParaToInt("size", 10),
				"select id,username,status,create_time ",
				"from sys_users order by create_time desc");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalRow());
		map.put("rows", page.getList());
		renderJson(map);
	}

	@RequiresPermissions("addUser")
	@ActionKey("/users/add")
	public void add() {
		renderJson(getModel(Users.class).save());
	}

	@RequiresPermissions("editUser")
	@ActionKey("/users/edt")
	public void edt() {
		renderJson(getModel(Users.class).update());
	}

	@RequiresPermissions("deleteUser")
	@ActionKey("/users/delete")
	public void delete() {
		renderJson(getModel(Users.class).delete());
	}
}
