package org.zerock.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTests {

	@Setter(onMethod_ = @Autowired)
	private BoardService service;
	
	@Test
	public void testDelete() {	//삭제 기능 테스트
		log.info("REMOVE RESULT : "+service.remove(2L));
		
	}
	
	@Test
	public void testUpdate() {	//업데이트 기능 테스트
		BoardVO board = service.get(6L);	//1번 게시물의 데이터 모두 board에 저장
		
		if(board==null) {
			return;
		}
		
		board.setTitle("제목 수정합니다");		//board 객체의 title 수정
		log.info("MODIFY RESULT : "+ service.modify(board));		//데이터베이스에 다시 update
	}
	
	
	
	  @Test public void testGet() { log.info(service.get(1L)); }
	 

	
	  @Test public void testExist() { //BoardService 객체가 제대로 주입이 가능한지 확인하는 작업
	  
	  log.info(service+".............................."); 
	  assertNotNull(service); 
	  }
	 

	
	  @Test public void testregister() { BoardVO board=new BoardVO();
	  board.setTitle("새로 작성하는 글"); board.setContent("새로 작성하는 내용");
	  board.setWriter("newbie");
	  
	  service.register(board); //create 처리
	  
	  log.info("생성된 게시물의 번호: "+ board.getBno()); }
	 
	
	  @Test public void testGetList() { //bno>0 인 데이터 select
	  
	 // service.getList().forEach(board->log.info(board)); //forEach문은 for문을 좀더 쓰기  편하게 만드는 메소드인데 //for문의 경우 끝나는 지점을 알아야 하지만 forEach는 알아서해줌
	  //forEach(a->log.info(a)); 해도 똑같이 돌아감
		  
		  service.getList(new Criteria(1,10)).forEach(board->log.info(board));
	  
	  }
	 
}
