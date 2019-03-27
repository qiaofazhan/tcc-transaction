package org.mengyun.tcctransaction.config;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.mengyun.tcctransaction.sample.http.capital.api.CapitalAccountService;
import org.mengyun.tcctransaction.sample.http.capital.api.CapitalTradeOrderService;
import org.mengyun.tcctransaction.sample.http.capital.service.CapitalAccountServiceImpl;
import org.mengyun.tcctransaction.sample.http.capital.service.CapitalTradeOrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.SimpleHttpInvokerServiceExporter;
import org.springframework.remoting.support.SimpleHttpServerFactoryBean;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Fazhan.Qiao
 * @Date: 2019/3/26 19:21
 * @Description:
 */
@Configuration
public class AppConfig {
    @Bean
    public CapitalTradeOrderServiceImpl capitalTradeOrderService(){
        CapitalTradeOrderServiceImpl capitalTradeOrderService=new CapitalTradeOrderServiceImpl();
        return  capitalTradeOrderService;
    }

    @Bean
    public CapitalAccountServiceImpl capitalAccountService(){
        CapitalAccountServiceImpl capitalAccountService=new CapitalAccountServiceImpl();
        return  capitalAccountService;
    }

    @Bean
    public SimpleHttpInvokerServiceExporter capitalTradeOrderServiceExporter(){
        SimpleHttpInvokerServiceExporter capitalTradeOrderServiceExporter=new SimpleHttpInvokerServiceExporter();
        capitalTradeOrderServiceExporter.setService(capitalTradeOrderService());
        capitalTradeOrderServiceExporter.setServiceInterface(CapitalTradeOrderService.class);
        return  capitalTradeOrderServiceExporter;
    }

    @Bean
    public SimpleHttpInvokerServiceExporter capitalAccountServiceExporter(){
        SimpleHttpInvokerServiceExporter capitalAccountServiceExporter=new SimpleHttpInvokerServiceExporter();
        capitalAccountServiceExporter.setService(capitalTradeOrderService());
        capitalAccountServiceExporter.setServiceInterface(CapitalAccountService.class);
        return  capitalAccountServiceExporter;
    }

    @Bean
    public HttpServer httpServer(){
        SimpleHttpServerFactoryBean httpServer=new SimpleHttpServerFactoryBean();
        Map<String, HttpHandler> map=new HashMap<String, HttpHandler>();
        map.put("/remoting/CapitalTradeOrderService",capitalTradeOrderServiceExporter());
        map.put("/remoting/CapitalAccountService",capitalAccountServiceExporter());
        httpServer.setContexts(map);
        httpServer.setPort(8081);
        return  httpServer.getObject();
    }
}
