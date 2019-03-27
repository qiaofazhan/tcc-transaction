package org.mengyun.tcctransaction.config;

import org.mengyun.tcctransaction.serializer.KryoPoolSerializer;
import org.mengyun.tcctransaction.spring.recover.DefaultRecoverConfig;
import org.mengyun.tcctransaction.spring.repository.SpringJdbcTransactionRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

/**
 * @Auther: Fazhan.Qiao
 * @Date: 2019/3/26 14:12
 * @Description:
 */
@Configuration
public class TccBaseConfig {
    @Bean
    public DefaultRecoverConfig defaultRecoverConfig() {
        DefaultRecoverConfig defaultRecoverConfig = new DefaultRecoverConfig();
        defaultRecoverConfig.setMaxRetryCount(5);
        defaultRecoverConfig.setRecoverDuration(60);
        defaultRecoverConfig.setCronExpression("0/30 * * * * ?");
        return defaultRecoverConfig;
    }

    @Bean
    public SpringJdbcTransactionRepository transactionRepository() {
        SpringJdbcTransactionRepository repository = new SpringJdbcTransactionRepository();
        repository.setDomain("CAPITAL");
        repository.setTbSuffix("_CAP");
        repository.setDataSource(tccDataSource());
        return repository;
    }

    @Bean
    @Qualifier(value = "tccDataSource")
    @Primary
    @ConfigurationProperties(prefix = "c3p0")
    public DataSource tccDataSource() {
        return DataSourceBuilder.create().type(com.mchange.v2.c3p0.ComboPooledDataSource.class).build();
    }


    @Bean
    public KryoPoolSerializer kryoSerializer() {
        KryoPoolSerializer kryoSerializer = new KryoPoolSerializer();
        return kryoSerializer;
    }
}
