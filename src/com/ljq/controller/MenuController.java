package com.ljq.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.ljq.model.Menu;

public class MenuController extends Controller {

	@RequiresRoles(value = { "user", "admin" }, logical = Logical.OR)
	@ActionKey("/menu/")
	public void index() {
		render("/pages/admin/menu.html");
	}

	// @RequiresPermissions("showMenu")
	@ActionKey("/menu/listtree")
	public void listTree() {
		renderJson(JsonKit.toJson(Menu.dao.listTree()));
	}

	// @RequiresPermissions("showMenu")
	@ActionKey("/menu/listsub")
	public void listSub() {
		renderJson(JsonKit.toJson(Menu.dao.listSub(getParaToInt("id"))));
	}

	// @RequiresPermissions("addMenu")
	@ActionKey("/menu/add")
	public void add() {
		render("/pages/admin/menuedt.html");
	}

	@ActionKey("/menu/save")
	public void save() {
		renderJson(getModel(Menu.class).save());
	}

	// @RequiresPermissions("editMenu")
	@ActionKey("/menu/update")
	public void update() {
		renderJson(getModel(Menu.class).update());
	}

	// @RequiresPermissions("addMenu")
	@ActionKey("/menu/edt")
	public void edt() {
		setAttr("menu", Menu.dao.findById(getParaToInt("id")));
		render("/pages/admin/menuedt.html");
	}

	// @RequiresPermissions("deleteMenu")
	@ActionKey("/menu/delete")
	public void delete() {
		renderJson(getModel(Menu.class).delete());
	}
}
