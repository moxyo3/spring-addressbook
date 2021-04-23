package com.example.addressbook.accessingdatamysql;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Where(clause = "delete_flag = 0")
public class AddressData extends BaseEntity {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="first_name")
	//null、空文字、半角スペースを許容しない
	@NotBlank(message = "必須項目です")
	@Size(max = 32)
	private String firstName;

	@Column(name="family_name")
	@NotBlank(message = "必須項目です")
	@Size(max = 32)
	private String familyName;

	@Column(name="kana_family")
	@Pattern(regexp="/[ぁ-ん]+$/", message="全角ひらがなで入力してください")
	@Size(max = 32)
	private String kanaFamily;

	@Column(name="kana_name")
	@Pattern(regexp="/[ぁ-ん]+$/", message="全角ひらがなで入力してください")
	@Size(max = 32)
	private String kanaName;

	@Column(name="post_num")
	@Pattern(regexp="\\d{3}-\\d{4}$", message="ハイフンを含む半角数字で入力してください")
	private String postNum;


	@Column(name="address1")
	@NotBlank(message = "必須項目です")
	@Size(max = 64)
	private String address1;

	@Column(name="address2")
	@Size(max = 64)
	private String address2;

	@Column(name="phone_num")
	@Size(max = 32)
	private String phoneNum;

	@Column(name="mail_address")
	@Email
	private String mailAddress;

	@Column(name="memo")
	@Size(max = 1000)
	private String memo;
	
	@Column(name="deleted_at")
	private LocalDateTime deletedAt;
	
	@Column(name="delete_flag")
	private int deleteFlag;
}