package org.zerock.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	
	private int pageNum;	
	private int amount;		//한 페이지당 게시물 개수
	private int limit_pageNum;  // 검색시 들어갈 pageNum
	
	private String type;  //검색 조건
	private String keyword;	// 검색 키워드

	

	
	public Criteria() {
		this(1,10);
		this.ResetPageNum();
		
	
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum=pageNum;
		this.amount=amount;
		this.ResetPageNum();
	
	}
	
	public String[] getTypeArr() {
		return type == null? new String[] {} : type.split("");
	}
	
	public String getListLink() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
				.queryParam("pageNum",this.getPageNum())
				.queryParam("amount",this.getAmount())
				.queryParam("type",this.getType())
				.queryParam("keyword",this.getKeyword());
		return builder.toUriString();
			
	}
	
	public void ResetPageNum() {  //limit select조건에선 연산이 안돼서 추가
		this.limit_pageNum=(this.pageNum-1)*this.amount;
	}
	

}
