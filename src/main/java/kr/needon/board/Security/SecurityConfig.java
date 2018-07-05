package kr.needon.board.Security;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.sql.DataSource;

@Log
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;

    @Autowired
    NeedonUserService needonUserService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf()
                .csrfTokenRepository(csrfTokenRepository());

        http
                .formLogin()
                .and()
                .formLogin().loginProcessingUrl("/")
                .and()
                .logout().logoutUrl("/user/login.info").invalidateHttpSession(true)
                .and()
                .exceptionHandling().accessDeniedPage("/info/accessDenied")
                .and()
                .formLogin().loginPage("/user/login.info")
                .and()
                .formLogin().failureUrl("/?error")
                .and()
                .userDetailsService(needonUserService);

//        http
//                .rememberMe()
//                .key("needon")
//                .userDetailsService(needonUserService);

        http
                .rememberMe()
                .key("needon")
                .userDetailsService(needonUserService)
                .tokenRepository(getJDBCRepository())
                .tokenValiditySeconds(60*60*24);


        log.info("Security Confing Run...");
    }

    private PersistentTokenRepository getJDBCRepository() {

        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }

//    @Autowired
//    public void testGlobal(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password("{noop}1111")
//                .roles("ADMIN");
//    }

    private CsrfTokenRepository csrfTokenRepository()
    {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
    }

    @Autowired
    public void MemberDataConfig(AuthenticationManagerBuilder auth) throws Exception {

        String query1 = "SELECT nb_username username, nb_password password, true enabled FROM nb_member" +
                " WHERE nb_username = ?";

        String query2 = "SELECT member nb_username, nb_role_name role FROM nb_member_roles WHERE member = ?";

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(query1)
                .authoritiesByUsernameQuery(query2)
                .rolePrefix("ROLE_");

    }

    @Bean
    public PasswordEncoder passwordEncoder() { //패스워드 암호화
        return new BCryptPasswordEncoder();
    }

}
