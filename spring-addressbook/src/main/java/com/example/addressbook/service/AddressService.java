package com.example.addressbook.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.addressbook.accessingdatamysql.AddressData;
import com.example.addressbook.repository.AddressRepository;

@Service
public class AddressService {
	//ここにDI、コンストラクタが1つの場合はAutowired省略可
	@Autowired
	AddressRepository repository;
	
	//全件取得
	public List<AddressData> getAddress() {
		return repository.findAll();
	}
	
	//新規登録
	public AddressData createAddress(AddressData addressdata) {
		return repository.save(addressdata);
	}
	
	//編集するレコードを取得
	public Optional<AddressData> editAddress(Long id) {
		return repository.findById(id);
	}
	
	//更新処理
	public AddressData updateAddress(AddressData addressdata) {
		return repository.save(addressdata);
	}
	
	//物理削除処理
	public void deleteAddress(Long id) {
		Optional <AddressData> record = repository.findById(id);
		AddressData deleterecord =  record.get();
		repository.delete(deleterecord);
	}
	
	//論理削除処理
	public void softdeleteAddress(Long id) {
		Optional<AddressData> record = repository.findById(id);
		AddressData deleterecord = record.get();
		deleterecord.setDeleteFlag(1);
		deleterecord.setDeletedAt(LocalDateTime.now());
		repository.save(deleterecord);
	}
	
	//あいまい検索
	public Collection<AddressData> searchAddress(String searchword) {
		//検索ワードであいまい検索
		String search = "%" + searchword + "%";
		Collection <AddressData> addresslist = repository.findByFirstNameLikeOrFamilyNameLikeOrKanaFamilyLikeOrKanaNameLikeOrPostNumLikeOrAddress1LikeOrAddress2LikeOrPhoneNumLikeOrMailAddressLikeOrMemoLike(search,search,search,search,search,search,search,search,search,search);

		//該当するAddressDataリストを返す
		return addresslist;
	}
}
