package com.example.addressbook.accessingdatamysql;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

//UserDetailsの実装クラス
//またはUserクラスを継承
@Entity
public class UserData implements UserDetails {
	@Id
	@Column(name="id")
	//id自動採番シーケンスの名前を指定
	//デフォルトはhibernate_sequence
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_generator")
	@SequenceGenerator(name = "user_id_generator", sequenceName="user_id_seq", allocationSize = 1)
	private Long id;
	
	@Column(name = "user_name", unique=true)
	@Size(max=32, message="ユーザー名は32文字以内で入力してください")
	@NotBlank(message = "ユーザー名は必須項目です")
	private String username;
	
	@Column(name = "password")
	@Size(max=64, message="パスワードは64文字以内で入力してください")
	@NotBlank(message = "パスワードは必須項目です")
	private String password;

	private String role;

	private boolean enabled;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
}
