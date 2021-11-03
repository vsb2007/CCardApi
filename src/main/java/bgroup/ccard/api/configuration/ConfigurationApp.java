package bgroup.ccard.api.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "bgroup")
@MapperScan("bgroup.ccard.api.mapper")
@PropertySource(value = {"classpath:jdbc.properties"})
public class ConfigurationApp {


    static final Logger logger = LoggerFactory.getLogger(ConfigurationApp.class);
    @Autowired
    private Environment environment;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        return sessionFactory.getObject();
    }

    @Bean(name = "dataSource")
    protected DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.mysql.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.mysql.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.mysql.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.mysql.password"));
        return dataSource;
    }
/*
    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(getDataSource());
    }
*/
    @Bean
    public EnvVariable envVariable() {
        EnvVariable envVariable = new EnvVariable();
        envVariable.setXmlApiKey(environment.getRequiredProperty("xml.api.key"));
        envVariable.setXmlApiUrl(environment.getRequiredProperty("xml.api.url"));
        envVariable.setXmlApiUrlWrite(environment.getRequiredProperty("xml.api.url.write"));
        envVariable.setXmlApiUrlRead(environment.getRequiredProperty("xml.api.url.read"));
        envVariable.setCardPrefix(environment.getRequiredProperty("card.prefix"));
        envVariable.setCardMifarPrefix(environment.getRequiredProperty("card.mifar.prefix"));
        envVariable.setXmlApiKeyPc(environment.getRequiredProperty("xml.api.key.pc"));
        try {
            envVariable.setXmlApiMethod(environment.getRequiredProperty("xml.api.method"));
        } catch (Exception e) {
            envVariable.setXmlApiMethod("POST");
        }
        envVariable.setSalt(environment.getRequiredProperty("xml.api.salt"));
        return envVariable;
    }
}

