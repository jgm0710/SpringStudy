package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;


@Service // 가장 중요한 부분 -- 계층 구조상 주로 비즈니스 영역을 담당하는 객체임을 표시하기 위해 사용. 패키지를 읽어들이는 동안 처리
@AllArgsConstructor		//모든 파라미터를 이용하는 생성자를 만듬.(?)
//BpardService 인터페이스의 구현체
@Log4j
public class BoardServiceImpl implements BoardService {

	// spring 4.3 이상에서 자동처리 . @Setter(onMethod_=@Autowired)를 이용해서 직접 설정해 줄 수도 있음
	//spring 4.3 부터는 단일 파라미터를 받는 생성자의 경우에는 필요한 파라미터를 자동으로 주입할 수 있음.(?)
	//@Setter(onMethod_=@Autowired)	//없어도 자동으로 처리 가능
	private BoardMapper mapper; // BoardServiceImpl가 정상적으로 동작하기 위해 필요.

	@Override				//상속 시 부모클래스의 메소드를 모두 선언해야 오류가 안남.
	public void register(BoardVO board) {		//insertSelectKey 방식 create
		log.info("register....."+board);
		
		mapper.insertSelectKey(board);
		
	}
	
	@Override
	public BoardVO get(Long bno) {		//특정 번호의 데이터를 반환하는 메소드
		
		log.info("get.........."+bno);
		
		return mapper.read(bno);		
	}

	@Override
	public boolean modify(BoardVO board) {		//수정 기능을 구현
		
		log.info("modify........"+ board);
		return mapper.update(board)==1;	//수정이 이루어지면 1값을 반환하기 때문에 == 연산자를 이용해서 true/false 기능 처리
	}

	@Override
	public boolean remove(Long bno) {	//삭제 기능을 구현
		log.info("remove............"+bno);
		return mapper.delete(bno)==1;		// 수정이 이루어지면 1값을 반환하기때문에 == 연산자를 사용해서 true/false 기능 처리
	}

	/*
	 * @Override public List<BoardVO> getList() { //모든 리스트 불러오는 메소드
	 * 
	 * log.info("getList.........."); return mapper.getList(); }
	 */
	
	@Override
	public List<BoardVO> getList(Criteria cri){
		log.info("get List with criteria : "+ cri);
		cri.ResetPageNum();
		
		return mapper.getListWithPaging(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		 log.info("get total count");
		 return mapper.getTotalCount(cri);
	}

}
