package com.example.addressbook.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.addressbook.accessingdatamysql.UserData;
import com.example.addressbook.service.UserService;

@Controller
public class UserController {
	@Autowired
	UserService userservice;

	//ログイン画面
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("iserror", false);
		return "login";
	}
	
	@RequestMapping(value="/login_error", method=RequestMethod.GET)
	public String loginError(@Validated @ModelAttribute UserData user, BindingResult result, Model model) {
		if(result.hasErrors()) {
			List<String> errorMessage = new ArrayList<String>();
			for (ObjectError error: result.getAllErrors()) {
				errorMessage.add(error.getDefaultMessage());
			}
			model.addAttribute("validationError", errorMessage);
			model.addAttribute("haserror", true);
			return "login";
		} else {
			model.addAttribute("haserror", false);
			return "redirect:/";
		}
	}

	//アカウント新規登録画面へ
	@RequestMapping(value = "/signup", method=RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("isexist", false);
		return "signup";
	}
	
	@RequestMapping(value = "/signup_error", method=RequestMethod.GET)
	public String signuperror(Model model) {
		model.addAttribute("isexist", true);
		return "signup";
	}

	//新規登録処理
	@PostMapping("/createUser")
	//BindingResultにリクエストからのデータバインディング時のエラー内容が記録される
	//必ず対象のModelの後ろに定義する
	public String addUser(@Validated @ModelAttribute UserData user, BindingResult result, Model model) throws DataIntegrityViolationException {
		try {
			if(result.hasErrors()) {
				List<String> errorMessage = new ArrayList<String>();
				for (ObjectError error : result.getAllErrors()) {
					errorMessage.add(error.getDefaultMessage());
				}
				model.addAttribute("validationError" , errorMessage);
				return "signup";
			} else {
					userservice.createUser(user);
					model.addAttribute("success", true);
					model.addAttribute("isexist", false);
					return "login";
			}
		}
		//重複ユーザー名の場合
		catch(DataIntegrityViolationException e) {
			model.addAttribute("success", false);
			model.addAttribute("isexist", true);
			return "signup";
		}
	}
}