package com.example.addressbook.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.addressbook.accessingdatamysql.AddressData;
import com.example.addressbook.service.AddressService;

@Controller
public class AddressController {
	//Serviceを注入
	@Autowired
	AddressService addressservice;
	
	//ルーティング情報
	@RequestMapping("/")
	public String showList(@ModelAttribute AddressData addressData, BindingResult result, Model model) {
		model.addAttribute("addresslist", addressservice.getAddress());
		return "index";
	}
	
	//新規登録画面へ
	@GetMapping("/create")
	public String create(@ModelAttribute AddressData addressData) {
		return "create";
	}
	
	//新規登録処理
	@PostMapping("/create")
	//BindingResultにリクエストからのデータバインディング時のエラー内容が記録される
	//必ず対象のModelの後ろに定義する
	public String addAddress(@Validated @ModelAttribute AddressData addressData, BindingResult result) {
		if(result.hasErrors()) {
			return "create";
		} else {
		addressservice.createAddress(addressData);
		return "redirect:/";
		}
	}
	
	//編集画面へ遷移
	@RequestMapping("/edit{id}")
	public String edit(Model model, @RequestParam("id") Long id) {
		//Modelクラスでコントローラーからビュー側へデータの値を渡す
		//文字列で遷移先のテンプレートをreturn
		//クエリパラメータからid取得、レコード取得
		model.addAttribute("addressData", addressservice.editAddress(id));
		return "edit";
	}
	
	//編集内容で更新処理
	@PostMapping("/updateAddress")
	public String updateAddress(@Validated @ModelAttribute AddressData addressData, BindingResult result) {
		if(result.hasErrors()) {
			return "edit";
		} else {
		addressservice.updateAddress(addressData);
		return "redirect:/";
		}
	}
	
	//削除処理
	@RequestMapping("/delete")
	public String delete (String deleteid) {
		Long id = Long.parseLong(deleteid);
		//論理削除
		addressservice.softdeleteAddress(id);

		//物理削除
		//addressservice.deleteAddress(id);
		return "forward:/";
	}
	
	@PostMapping("/search{searchword}")
	public String searchAddress(@ModelAttribute AddressData addressdata, @RequestParam String searchword, Model model) {
		//nullまたは空文字だったらトップページにリダイレクト
		if(searchword == null || searchword == "") {
			return "forward:/";
		}
		//検索ワードで検索
		Collection<AddressData> addresslist = addressservice.searchAddress(searchword);
		model.addAttribute("addresslist", addresslist);
		return "searchresult";
	}
	
}