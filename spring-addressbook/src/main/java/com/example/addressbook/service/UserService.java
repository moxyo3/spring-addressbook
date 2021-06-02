package com.example.addressbook.service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.addressbook.accessingdatamysql.UserData;
import com.example.addressbook.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	UserRepository repository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	//バリデーションエラー時の例外をキャッチ
	public UserData createUser(UserData user) throws ConstraintViolationException {
		try {
		//パスワードハッシュ化
		String rawPassword = user.getPassword();
		String encodedPassword = passwordEncoder.encode(rawPassword);
		user.setPassword(encodedPassword);
		user.setEnabled(true);
		user.setRole("ROLE_USER");

		return repository.save(user);
		}
		catch (ConstraintViolationException e){
			throw e;
		}
		
	}
	
	//ユーザー情報はUserDetailsで管理する
	//UserDetailsの実装クラスを返す
	//AuthenticationProviderとしてDaoAuthenticationProviderを使う場合
	@Transactional
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			UserData userdata;
			try {
			userdata = repository.findByUsername(username);
			}
			catch(UsernameNotFoundException e) {
				//ユーザーが見つからない場合はUserDataをnewして返す
				userdata  = new UserData();
			}
			return userdata;
	}
}
