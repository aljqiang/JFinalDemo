package com.demo.controller;

import com.demo.model.Blog;
import com.demo.interceptor.BlogInterceptor;
import com.demo.validator.BlogValidator;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;

/**
 * BlogController
 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
@Before(BlogInterceptor.class)
public class BlogController extends Controller {

	private static Logger log = Logger.getLogger(BlogController.class);

	public void index() {
		setAttr("blogPage", Blog.me.paginate(getParaToInt(0, 1), 10));
		render("blog.jsp");
	}

	public void add() {
	}

	@Before(BlogValidator.class)
	public void save() {
		log.info("===================测试断点2");
//		System.out.println("编码格式为："+java.nio.charset.Charset.defaultCharset());
		getModel(Blog.class).save();
		redirect("/blog");
	}

	public void edit() {
		setAttr("blog", Blog.me.findById(getParaToInt()));
	}

	@Before(BlogValidator.class)
	public void update() {
		getModel(Blog.class).update();
		redirect("/blog");
	}

	public void delete() {
		Blog.me.deleteById(getParaToInt());
		redirect("/blog");
	}
}


