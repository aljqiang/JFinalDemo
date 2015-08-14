package com.ljq.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;

@TableBind(tableName = "sys_menu", pkName = "mnuId")
public class Menu extends Model<Menu> {
	private static final long serialVersionUID = 2922278341134539131L;
	public static Menu dao = new Menu();

	public List<Menu> listTree() {
		return dao
				.find("select mnuId,mnuPid,mnuName,mnuRemark,mnuIco,mnuUrl,mnuModule,mnuType from sys_menu");
	}

	public List<Menu> listSub(Integer id) {
		return dao
				.find("select mnuId,mnuPid,mnuName,mnuRemark,mnuIco,mnuUrl,mnuModule,mnuType from sys_menu where");
	}
}
