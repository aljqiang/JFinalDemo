package com.ljq.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;

@TableBind(tableName = "sys_code", pkName = "codeId")
public class Code extends Model<Code> {
	private static final long serialVersionUID = 2920278340134539131L;
	public static Code dao = new Code();

	public List<Code> listTree() {
		return dao
				.find("select codeId,codePid,codeName,codeValue,codeSub,codeValid from sys_code where codeSub=0");
	}

	public List<Code> listSub(Integer id) {
		return dao
				.find("select codeId,codePid,codeName,codeValue,codeSub,codeValid from sys_code where codeSub=1 and codePid=?",
						id);

	}
}
