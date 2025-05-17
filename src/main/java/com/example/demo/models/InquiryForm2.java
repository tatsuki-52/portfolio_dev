package com.example.demo.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "inquiry2")
public class InquiryForm2 implements Serializable {
	private static final long serialVersionUID = -6647247658748349084L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	// バリデーション
	//	@NotBlank	空白（空文字・スペースだけ）NG	「メールアドレスは必須です」
	//	@Email	メールアドレスの形式チェック	「正しいメールアドレスを入力してください」
	//	@Size(min=2, max=30)	文字数の最小・最大制限	「名前は2文字以上30文字以下で入力してください」
	//	@Size(max=500)	最大文字数制限	「内容は500文字以内で入力してください」
	@NotBlank
	@Size(max = 10)
	private String name;

	@NotBlank
	@Email
	private String mail;

	@NotBlank
	@Size(max = 400)
	private String content;
	
	public void clear() {
		name = null;
		mail = null;
		content = null;
	}
	
	// ゲッターとセッター
    public Long getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
