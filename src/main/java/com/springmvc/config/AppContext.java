package com.springmvc.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:database.properties")
@EnableTransactionManagement(proxyTargetClass = true) // bat buoc phai co enabletranmanagaement va transactional de lam
// vc sessionfactory
//@EnableJpaRepositories(basePackages = "com.springmvc.*", entityManagerFactoryRef="entityManagerFactoryBean", transactionManagerRef = "transactionManagerBean")
public class AppContext {

	
	@Autowired
	public Environment environment;
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory()
	{
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPackagesToScan(new String[] { "com.springmvc.entity" });
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setJpaProperties(hibernateProperties());
		return entityManagerFactoryBean;
	}
	
	  @Bean //se tao ra 1 spring bean mang ten sessionfactoruy trong spring ioc container
	  public LocalSessionFactoryBean sessionFactory() 
	  {
	  LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	  sessionFactory.setDataSource(dataSource());
	  sessionFactory.setPackagesToScan(new String[] { "com.springmvc.entity" });
	  sessionFactory.setHibernateProperties(hibernateProperties()); 
	  return sessionFactory; 
	  }
	
	//se tao ra mot spring bean mang ten hibernateproperties
	@Bean
	public Properties hibernateProperties() {
		Properties properties = new Properties();// java.util.Properties
		properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
		properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
		return properties;
	}

	// se tao ra 1 spring bean mang ten datasource trong spring ioc container
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource(); //java.sql.dataSource
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName")); //com.mysql.jdbc.Driver
		dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
		return dataSource;
	}
	
	
	@Bean //tao ra 1 spring bean voi ten gettransactionmanager torng Spring IoCcontrainer 
	  public HibernateTransactionManager getTransactionManager() {
	  HibernateTransactionManager transactionManager = new HibernateTransactionManager();
	  transactionManager.setSessionFactory(sessionFactory().getObject()); 
	  return transactionManager; 
	  }
}
