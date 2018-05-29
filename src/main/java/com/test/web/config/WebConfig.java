package com.test.web.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
/*@ComponentScan(basePackages = {"com.vyuhkrama.web"})*/
@PropertySource("classpath:jdbc.properties")

public class WebConfig extends WebMvcConfigurerAdapter{

	@Resource
    private Environment env;
     
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
    
    @Bean
    public ViewResolver getViewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        //MySQL database we are using
        System.out.println(env.getProperty("jdbc.username"));
        dataSource.setDriverClassName(env.getProperty("jdbc.drivername"));
        dataSource.setUrl(env.getProperty("jdbc.url"));//change url
        dataSource.setUsername(env.getProperty("jdbc.username"));//change userid
        dataSource.setPassword(env.getProperty("jdbc.password"));//change pwd
        
        return dataSource;
    }
    
     public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
     }
     
     
     @Bean
     public LocalSessionFactoryBean  sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(
          new String[] { "com.test.web.model" });
        sessionFactory.setHibernateProperties(getHibernateProperties());
   
        return sessionFactory;
     }
   
     @Bean
     public Properties getHibernateProperties()
     {
         Properties properties = new Properties();
         properties.put("hibernate.dialect",env.getProperty("hibernate.dialect"));
         properties.put("hibernate.show_sql",env.getProperty("hibernate.show_sql"));
         properties.put("hibernate.hbm2ddl.auto",env.getProperty("hibernate.hbm2ddl.auto"));
         
         return properties;
     }
     
     @Bean
     @Autowired
     public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
     }
     
     /*@Bean
     @Autowired
     public HibernateTemplate getHibernateTemplate(SessionFactory sessionFactory)
     {
         HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory);
         return hibernateTemplate;
     }*/
         
    /* @Bean
     public AnnotationSessionFactoryBean getSessionFactory()
     {
         AnnotationSessionFactoryBean asfb = new AnnotationSessionFactoryBean();
         asfb.setDataSource(dataSource());
         asfb.setHibernateProperties(getHibernateProperties());        
         asfb.setPackagesToScan(new String[]{"com.test.web"});
         return asfb;
     }*/

     
}
