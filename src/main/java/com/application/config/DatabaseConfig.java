package com.application.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@MapperScan("com.application.mybatis.mapper")
public class DatabaseConfig {
    @Value("${jdbc.server}")
    private String server;
    @Value("${jdbc.port}")
    private int port;
    @Value("${jdbc.username}")
    private String jdbcUserName;
    @Value("${jdbc.password}")
    private String jdbcPassword;
    @Value("${jdbc.databaseName}")
    private String databaseName;
    @Value("${jdbc.dataSourceClassName}")
    private String dataSourceClassName;
    @Value("${jdbc.poolSize}")
    private int poolSize;

    @Bean(name = "dataSource")
    @Primary
    public DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setMaximumPoolSize(this.poolSize);
        config.setDataSourceClassName(this.dataSourceClassName);
        config.addDataSourceProperty("serverName", this.server);
        config.addDataSourceProperty("portNumber", this.port);
        config.addDataSourceProperty("databaseName", this.databaseName);
        config.addDataSourceProperty("user", this.jdbcUserName);
        config.addDataSourceProperty("password", this.jdbcPassword);
        config.setTransactionIsolation("TRANSACTION_READ_COMMITTED");
        return new HikariDataSource(config);  //pass in HikariCon
    }

    @Bean
    public DataSourceTransactionManager getMyBatisTransactionManager() {
        return new DataSourceTransactionManager(getDataSource());
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(getDataSource());
        SqlSessionFactory sessionFactory = sessionFactoryBean.getObject();
        sessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
        return sessionFactory;
    }
}
