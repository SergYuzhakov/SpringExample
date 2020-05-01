package testgroup.filmography.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.Basic;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "testgroup.filmography")
@EnableTransactionManagement // позволяет использовать TransactionManager для управления транзакциями. Hibernate работает с БД с помощью транзакций, они нужны чтобы какой-то набор операций выполнялся как единое целое,
@PropertySource(value = "classpath:db.properties") // подключение файла свойств, который мы недавно создавали.
public class HibernateConfig {

    private Environment environment;  // для того, чтобы получить свойства из property файла.

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
/*
этот метод нужен чтобы представить свойства Hibernate в виде объекта Properties
 */
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        return properties;
    }
/*
Метод используется для создания соединения с БД.
Это альтернатива DriverManager, которой мы использовали ранее,
когда создавали для проверки подключения метод main.
В документации сказано, что DataSource использовать предпочтительнее.
Так и поступим, естественно не забыв почитать в интернете в чем разница и преимущества.
В частности, одним из преимуществ является возможность создания пула соединений Database Connection Pool (DBCP).
Небольшое замечание насчет DataSource.
В документации сказано, что использовать стандартную реализацию, а именно DriverManagerDataSource,
не рекомендуется, т.к. это только замена нормального пула соединений и в целом подходит только для тестов
и всего такого. Для нормального приложения предпочтительнее использовать какую-нибудь DBCP библиотеку.
 */
    @Bean
    public DataSource dataSource() {
       // DriverManagerDataSource dataSource = new DriverManagerDataSource();
     //и поэтому заменим DriverManagerDataSource на BasicDataSource из пакета org.apache.tomcat.dbcp.dbcp2:

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        return dataSource;
    }
/*
Метод для создания сессий, с помощью которых осуществляются операции с объектами-сущностями.
Здесь мы устанавливаем источник данных, свойства Hibernate и в каком пакете нужно искать
классы-сущности.
 */
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("testgroup.filmography.model");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }
/*
метод для настройки менеджера транзакций.
 */
    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
}
