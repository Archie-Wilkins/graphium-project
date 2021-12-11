package client_project.y2s1.team2.graphium.Security;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
@Configuration
public class JpaConfig {

//    @Bean(name = "mariaDataSource")
//    public DataSource getDataSource()
//    {
//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.driverClassName("org.mariadb.jdbc.Driver");
//        dataSourceBuilder.url("jdbc:mariadb://localhost:3306/graphium_schema?useSSL=false&requireSSL=false&serverTimezone=UTC");
//        dataSourceBuilder.username("root");
//        dataSourceBuilder.password("comsc");
//        return dataSourceBuilder.build();
//    }
}
