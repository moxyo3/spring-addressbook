package com.example.addressbook.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.addressbook.accessingdatamysql.AddressData;

@Repository
public interface AddressRepository extends JpaRepository<AddressData, Long>, JpaSpecificationExecutor<AddressData>{
	//Collection<AddressData> findByFirstNameLike(String name);
	
	Collection<AddressData> findByFirstNameLikeOrFamilyNameLikeOrKanaFamilyLikeOrKanaNameLikeOrPostNumLikeOrAddress1LikeOrAddress2LikeOrPhoneNumLikeOrMailAddressLikeOrMemoLike(String familyName, String FirstName, String kanaFamily, String kanaName, String postNum, String address1, String address2, String mailAddress, String phoneNum, String memo);
	
}