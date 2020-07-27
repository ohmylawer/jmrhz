package com.config;

import com.common.utils.CommonUtil;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

	@Bean(destroyMethod="", name="EmbeddeddataSource")
	public DataSource dataSource() {
		String url = "jdbc:sqlite:"+CommonUtil.getDBUrl()+"//mainplance.db";
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("org.sqlite.JDBC");
		dataSourceBuilder.url(url);
		dataSourceBuilder.type(SQLiteDataSource.class);
		
		return dataSourceBuilder.build();
	}
}
