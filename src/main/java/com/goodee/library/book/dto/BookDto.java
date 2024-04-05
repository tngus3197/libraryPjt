package com.goodee.library.book.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {

	private long b_no;
	private String b_thumbnail;
	private String b_name;
	private String b_writer;
	private Date b_reg_date;
	private Date b_mod_date;
	
	
}
