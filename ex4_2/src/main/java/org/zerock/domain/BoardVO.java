package org.zerock.domain;

import java.util.Date;

import lombok.Data;

@Data  //생성자 getter...등을 만들어기 위해 사용
public class BoardVO {
	private Long bno;		
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updateDate;
	
	private int replyCnt;
}
