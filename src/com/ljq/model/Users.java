package com.ljq.model;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;

@TableBind(tableName = "sys_users", pkName = "id")
public class Users extends Model<Users> {
	private static final long serialVersionUID = 2920278346134539131L;
	public static Users dao = new Users();
}
