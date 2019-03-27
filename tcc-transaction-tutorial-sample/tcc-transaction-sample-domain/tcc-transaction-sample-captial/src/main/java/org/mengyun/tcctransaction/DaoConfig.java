package org.mengyun.tcctransaction;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mengyun.tcctransaction.sample.capital.infrastructure.dao.CapitalAccountDao;
import org.mengyun.tcctransaction.sample.capital.infrastructure.dao.TradeOrderDao;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @Auther: Fazhan.Qiao
 * @Date: 2019/3/26 20:08
 * @Description:
 */
@Configuration
public class DaoConfig {
    @Value("${app.dao.mapper.path}")
    private String mapperLocations;


    @Bean
    @Qualifier(value = "capitalDataSource")
    @Primary
    @ConfigurationProperties(prefix = "jdbc")
    public DataSource capitalDataSource() {
        return DataSourceBuilder.create().type(com.mchange.v2.c3p0.ComboPooledDataSource.class).build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("capitalDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory () {
        SqlSessionFactory sqlSessionFactory=null;
        SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(capitalDataSource());
        try{
            // 加载MyBatis配置文件
            PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            // 能加载多个，所以可以配置通配符(如：classpath*:mapper/**/*.xml)
            sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources(mapperLocations));
            sqlSessionFactory=sqlSessionFactoryBean.getObject();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return  sqlSessionFactory;
    }
    private <T> MapperFactoryBean getMapper(Class<T> mapperInterface) {
        MapperFactoryBean<T> mapperFactoryBean = new MapperFactoryBean<T>();
        try {
            mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory());
            mapperFactoryBean.setMapperInterface(mapperInterface);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return mapperFactoryBean;
    }

    @Bean
    public MapperFactoryBean capitalAccountDao() {
        return getMapper(CapitalAccountDao.class);
    }

    @Bean
    public MapperFactoryBean tradeOrderDao() {
        return getMapper(TradeOrderDao.class);
    }


}
