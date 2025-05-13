package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

import com.example.demo.models.InquiryForm;
import com.example.demo.models.InquiryForm2;
import com.example.demo.repositries.InquiryRepository;
import com.example.demo.repositries.InquiryRepository2;
import org.springframework.data.jpa.repository.JpaRepository;

@Controller
@RequestMapping("/")
public class RootController {

	@Autowired
	InquiryRepository repository;
	
	@Autowired
	InquiryRepository2 repository2;

	@GetMapping
	public String index() {
		return "root/index";
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		List<InquiryForm2> list = repository2.findAll();
		model.addAttribute("list", list);
		return "root/list";
	}

	@GetMapping("/form")
	public String form(InquiryForm inquiryForm) {
		return "root/form";
	}
	
	@GetMapping("/form2")
	public String form2(InquiryForm2 inquiryForm) {
		return "root/form2";
	}

	@PostMapping("/form")
	public String form(@Validated InquiryForm inquiryForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "root/form";
		}

		// RDBと連携できることを確認しておきます。
		repository.saveAndFlush(inquiryForm);
		inquiryForm.clear();
		model.addAttribute("message", "お問い合わせを受け付けました。");
		return "root/form";
	}
	
	@PostMapping("/form2")
	public String form2(@Validated InquiryForm2 inquiryForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "root/form2";
		}

		// RDBと連携できることを確認しておきます。
		repository2.saveAndFlush(inquiryForm);
		inquiryForm.clear();
		model.addAttribute("message", "お問い合わせを受け付けました。");
		return "root/form2";
	}
}