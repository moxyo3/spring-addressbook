package com.example.addressbook.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.addressbook.service.UserService;

//セキュリティ設定クラス
@Configuration
//@EnableWebSecurityアノテーションは不要になった
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsService userDetailsService;
	

	//静的リソースフォルダを許可
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/webjars/**");
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		//認可設定
		http.authorizeRequests()
		//全ユーザーアクセス許可
		.antMatchers("/login", "/signup", "/createUser")
		.permitAll()
		//上記以外認証を要求
		.anyRequest().authenticated();
		
		//CSRF無効化
		http.csrf().disable();

		//ログイン設定
		//ログイン対象URLは認証対象から外す（ログイン失敗時に無限ループになるため
		//フォーム認証
		http.formLogin()
		.loginPage("/login")
		.successForwardUrl("/") //ログイン成功時に表示するURL
		//デフォルトは("/login?error")
		.failureUrl("/login_error")

		//ユーザーID入力欄のname属性
		.usernameParameter("username")
		//パスワードの入力欄のname属性
		.passwordParameter("password")
		//("ログイン成功後の遷移先", 遷移先に強制遷移するかどうか)
		.defaultSuccessUrl("/", true)
		.permitAll();
		
		//ログアウト設定
		http.logout()
		.logoutSuccessUrl("/login")
		.deleteCookies("JSESSIONID")
		.invalidateHttpSession(true)
		.permitAll();

	}
	
	//認証設定
	//パスワードエンコーダー設定
	/*
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService())
			.passwordEncoder(new BCryptPasswordEncoder());
	}
	*/
	
	@Autowired
	public void configuGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder());
	}
	
	//パスワードエンコーダーの実体
	//Beanがついてるエンコーダーが取得元になる
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//AuthenticationConfiurationインナークラス
	//認証時に自動で呼ばれる、入力されたパスワードに対してハッシュ化、入力値が正しいか認証処理を行う
	@Configuration
	protected static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {
		@Autowired
		UserService userService;
		
		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userService)
			.passwordEncoder(new BCryptPasswordEncoder());
		}
	}
}
