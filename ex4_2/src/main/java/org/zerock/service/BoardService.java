package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardService {		//비즈니스 계층-- 로직을 기준으로 처리하는 방식, 고객의 요구사항을 반영
									//이에 대한 구현은 BoardServiceImpl에 있음.
	public void register(BoardVO board);	//create를 처리하는 메소드

	public BoardVO get(Long bno);		//특정 게시물을 가져오는 메소드

	public boolean modify(BoardVO board);	//수정 기능을 구현 , void로 설계할 수도 있지만 엄격하게 처리하기 위해 Boolean 타입으로 처리

	public boolean remove(Long bno);	//삭제 기능을 구현

	//public List<BoardVO> getList();		//전체 리스트를 구하는 메서드
	
	public List<BoardVO> getList(Criteria cri); //페이징 처리 추가에 따른 get list

	public int getTotal(Criteria cri);
}
