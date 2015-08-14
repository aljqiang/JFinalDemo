package com.ljq.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.ljq.model.Code;

public class CodeController extends Controller {

	@RequiresRoles(value = { "user", "admin" }, logical = Logical.OR)
	@ActionKey("/code/")
	public void index() {
		render("/pages/admin/code.html");
	}

	// @RequiresPermissions("showCode")
	@ActionKey("/code/listtree")
	public void listTree() {
		renderJson(JsonKit.toJson(Code.dao.listTree()));
	}

	// @RequiresPermissions("showCode")
	@ActionKey("/code/listsub")
	public void listSub() {
		renderJson(JsonKit.toJson(Code.dao.listSub(getParaToInt("id"))));
	}

	@ActionKey("/code/add")
	public void add() {
		render("/admin/codeedt.html");
	}

	@ActionKey("/code/addbatch")
	public void addbatch() {
		render("/admin/codeedtb.html");
	}

	// @RequiresPermissions("addCode")
	@ActionKey("/code/save")
	public void save() {
		renderJson(getModel(Code.class).save());
	}

	// @RequiresPermissions("addCode")
	@ActionKey("/code/saveb")
	public void saveb() {
		String pid = getPara("codePid");
		String codes = getPara("codeValues").replaceAll("\r\n", ";");
		String[] value = codes.split(";");
		for (int i = 0; i < value.length; i++) {
			if (value[i].indexOf("=") > 0) {
				String[] attr = value[i].split("=");
				Code code = new Code();
				code.set("codePid", pid).set("codeName", attr[0])
						.set("codeValue", attr[1]).set("codeSub", 1)
						.set("codeValid", 0).save();
			}
		}
		renderJson("true");
	}

	@ActionKey("/code/edt")
	public void edt() {
		setAttr("code", Code.dao.findById(getParaToInt("id")));
		render("/pages/admin/codeedt.html");
	}

	// @RequiresPermissions("editCode")
	@ActionKey("/code/update")
	public void update() {
		renderJson(getModel(Code.class).update());
	}

	// @RequiresPermissions("deleteCode")
	@ActionKey("/code/delete")
	public void delete() {
		renderJson(getModel(Code.class).delete());
	}
}
