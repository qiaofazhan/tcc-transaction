package org.mengyun.tcctransaction.spring;

import org.mengyun.tcctransaction.recover.TransactionRecovery;
import org.mengyun.tcctransaction.spring.recover.RecoverScheduledJob;
import org.mengyun.tcctransaction.spring.support.SpringBeanFactory;
import org.mengyun.tcctransaction.spring.support.SpringTransactionConfigurator;
import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @Auther: Fazhan.Qiao
 * @Date: 2019/3/26 17:52
 * @Description:
 */
@Configuration
@EnableScheduling
public class TccRootConfig {
    @Bean
    public SpringBeanFactory springBeanFactory(){
        SpringBeanFactory springBeanFactory=new SpringBeanFactory();
        return springBeanFactory;
    }

    @Bean(initMethod = "init")
    public SpringTransactionConfigurator transactionConfigurator(){
        SpringTransactionConfigurator transactionConfigurator=new SpringTransactionConfigurator();
        return  transactionConfigurator;
    }

    @Bean(initMethod = "init")
    public ConfigurableTransactionAspect compensableTransactionAspect(){
        ConfigurableTransactionAspect compensableTransactionAspect=new ConfigurableTransactionAspect();
        compensableTransactionAspect.setTransactionConfigurator(transactionConfigurator());
        return  compensableTransactionAspect;
    }

    @Bean(initMethod = "init")
    public ConfigurableCoordinatorAspect resourceCoordinatorAspect(){
        ConfigurableCoordinatorAspect resourceCoordinatorAspect=new ConfigurableCoordinatorAspect();
        resourceCoordinatorAspect.setTransactionConfigurator(transactionConfigurator());
        return  resourceCoordinatorAspect;
    }

    @Bean
    public TransactionRecovery transactionRecovery(){
        TransactionRecovery transactionRecovery=new TransactionRecovery();
        transactionRecovery.setTransactionConfigurator(transactionConfigurator());
        return  transactionRecovery;
    }

    @Bean
    public Scheduler recoverScheduler(){
        SchedulerFactoryBean recoverScheduler=new SchedulerFactoryBean();
        return  recoverScheduler.getScheduler();
    }

    @Bean(initMethod = "init")
    public RecoverScheduledJob recoverScheduledJob(){
        RecoverScheduledJob recoverScheduledJob=new RecoverScheduledJob();
        recoverScheduledJob.setScheduler(recoverScheduler());
        recoverScheduledJob.setTransactionConfigurator(transactionConfigurator());
        recoverScheduledJob.setTransactionRecovery(transactionRecovery());
        return  recoverScheduledJob;
    }

}
