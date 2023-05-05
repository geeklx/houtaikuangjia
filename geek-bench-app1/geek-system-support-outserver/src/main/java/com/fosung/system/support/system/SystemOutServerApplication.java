package com.fosung.system.support.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@Slf4j
//@Configuration
//@ConditionalOnBean(AppDaoProperties.DataSource.class)
//@ConditionalOnProperty(name = AppDaoProperties.PREFIX + ".jpa.enabled" , matchIfMissing = true , havingValue = "true")
//@Import(value = { AppJPATransactionAutoConfiguration.class })
//@EnableJpaRepositories(basePackages= {"com.fosung"}  ,
//        repositoryFactoryBeanClass = AppJpaRepositoryFactoryBean.class)
//@ComponentScan(basePackages = {"com.fosung"})
@SpringBootApplication
public class SystemOutServerApplication {
    public static void main(String[] args) {
        new SpringApplication(SystemOutServerApplication.class).run(args);
    }
}
