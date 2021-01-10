package com.tr.mita.base.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

public class DataSourceCfg {

    @Autowired
    ApplicationContext applicationContext;//引入上下文
    
    @Autowired
    DataSourceProperties dataSourceProperties;//引入数据源参数
    
    @Bean
//  @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {

	    DataSource dataSource = applicationContext.getBean(DataSource.class);
	
	    return dataSource;
    }

    

    // 提供SqlSeesion(数据库事务操作相关的配置)
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();//创建SqlSessionFactoryBean类
        sqlSessionFactoryBean.setDataSource(dataSource());//设置数据库链接
        //如果你不想写mapper.xml文件来实现功能的话，下面两行可以注释。
//      PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//      sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/com/example/mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 事务配置引入
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {

        return new DataSourceTransactionManager(dataSource());//事务声明

    }

}
