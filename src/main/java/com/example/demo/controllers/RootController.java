package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

import com.example.demo.models.InquiryForm;
import com.example.demo.models.InquiryForm2;
import com.example.demo.repositries.InquiryRepository;
import com.example.demo.repositries.InquiryRepository2;

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
	
    /**
     * 商品情報を削除するときの処理。
     * @param id    編集対象となるID（URLパスから取得）
     * @return "redirect:https://127.0.0.1/list" 商品一覧を表示するビュー名
     */
    @GetMapping("/list/{id}")
    public String deleteProduct(@PathVariable long id) {
        // 商品操作サービスを使って、指定された商品情報を削除します。
    	repository2.deleteById(id);
        // 削除が完了したら、ホームページに戻ります。
    	return "redirect:https://127.0.0.1/list";
    }
   
    /**
     * 商品情報を編集するときの処理
     * @param id    編集対象となるID（URLパスから取得）
     * @param model ビューにデータを渡すためのオブジェクト
     * @return "root/edit" 編集フォームを表示するビュー名
     * @throws IllegalArgumentException 指定されたIDのデータが存在しない場合
     */
    @GetMapping("/list/edit/{id}") // ユーザーがアクセスするURLを決定する
    public String editForm(@PathVariable Long id, Model model) {
    	// Optional は「値が存在するかもしれないし、存在しないかもしれない」ことを表すクラスです。
    	// .orElseThrow(...) は「中身がなければ例外を投げる」という操作です。
    	// 見つからなければ、IllegalArgumentException("Invalid ID") を投げる(不正な引数（argument）が渡されましたというエラー)
        InquiryForm2 inquiry = repository2.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ID"));
        model.addAttribute("inquiryForm2", inquiry);
        return "root/edit"; //表示するテンプレート（HTML）を決定するが、URLには影響しない
    }
    
    /**
     * 商品情報を編集するときの処理
     * @param id    編集対象となるID（URLパスから取得）
     * @param inquiryForm フォームで入力された値
     * @param result バリデーション結果（エラーの有無や詳細）を格納する
     * @param model ビューにデータを渡すためのオブジェクト
     * @return "redirect:https://127.0.0.1/list" 商品一覧画面を表示するビュー名
     */
	@PostMapping("/list/edit/{id}")
	public String updateInquiry(@PathVariable Long id, @Validated InquiryForm2 inquiryForm, BindingResult result, Model model) {
	    // 入力値にバリデーションエラー（未入力・形式不正など）がある場合
		if (result.hasErrors()) {
	        return "root/edit";
	    }
		// データベースから id に対応するデータ取得
	    InquiryForm2 existing = repository2.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + id));
	    existing.setMail(inquiryForm.getMail());
	    existing.setName(inquiryForm.getName());
	    existing.setContent(inquiryForm.getContent());
	    // データベースに保存(上書き)
	    repository2.save(existing);
	    return "redirect:https://127.0.0.1/list";
	}

	@GetMapping("/form")
	public String form(InquiryForm inquiryForm) {
		return "root/form";
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
	
	@GetMapping("/form2")
	public String form2(InquiryForm2 inquiryForm) {
		return "root/form2";
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