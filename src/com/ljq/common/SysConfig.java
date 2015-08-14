package com.ljq.common;

import org.beetl.core.GroupTemplate;
import org.beetl.ext.jfinal.BeetlRenderFactory;

import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.ext.plugin.shiro.ShiroInterceptor;
import com.jfinal.ext.plugin.shiro.ShiroPlugin;
import com.jfinal.ext.plugin.tablebind.AutoTableBindPlugin;
import com.jfinal.ext.route.AutoBindRoutes;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.ljq.tools.ShiroExt;

public class SysConfig extends JFinalConfig {

	private Routes routes;

	@Override
	public void configConstant(Constants me) {
		loadPropertyFile("classes/jdbc.properties");
		me.setDevMode(getPropertyToBoolean("devMode", false));
		// Beetl
		me.setMainRenderFactory(new BeetlRenderFactory());
		GroupTemplate gt = BeetlRenderFactory.groupTemplate;
		gt.registerFunctionPackage("so", new ShiroExt());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void configRoute(Routes me) {
		this.routes = me;
		AutoBindRoutes routes = new AutoBindRoutes();
		routes.addExcludeClasses(Controller.class);
		me.add(routes);
	}

	@Override
	public void configPlugin(Plugins me) {
		String jdbcUrl = getProperty("jdbcUrl");
		String driver = getProperty("driverClass");
		String username = getProperty("username");
		String password = getProperty("password");

		// Druid
		DruidPlugin druidPlugin = new DruidPlugin(jdbcUrl, username, password,
				driver);
		WallFilter wallFilter = new WallFilter();
		wallFilter.setDbType("mysql");
		druidPlugin.addFilter(wallFilter);
		me.add(druidPlugin);

		// ActiveRecord插件
		AutoTableBindPlugin arp = new AutoTableBindPlugin(druidPlugin);
		// arp.setContainerFactory(new CaseInsensitiveContainerFactory());
		arp.setShowSql(getPropertyToBoolean("showSql", false));
		me.add(arp);

		// 加载Shiro插件
		me.add(new ShiroPlugin(routes));

		// 缓存插件
		me.add(new EhCachePlugin());
	}

	@Override
	public void configInterceptor(Interceptors me) {
		//me.add(new SessionInViewInterceptor());
		me.add(new ShiroInterceptor());

	}

	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub
	}

	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}
}
