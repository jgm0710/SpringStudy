package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;
import org.zerock.mapper.BoardMapper;
import org.zerock.mapper.ReplyMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReplyServiceImpl implements ReplyService{

	@Setter(onMethod_ =@Autowired )
	private ReplyMapper mapper;
	
	@Setter(onMethod_ = @Autowired)		//replycnt(댓글의 수 처리)를 위해 반정규화 작업을 위해서 BoardMapper클래스를 주입받음
	private BoardMapper boardMapper;

	@Transactional		//댓글의 등록과 삭제를 담당하는 메서드는 트랜젝션 처리가 필요함
	@Override
	public int register(ReplyVO vo) {
		
		log.info("register....."+vo);
		
		boardMapper.updateReplyCnt(vo.getBno(),1);	//댓글이 한개 추가될 때마다 replycnt를 +1씩 업데이트
		
		return mapper.insert(vo);
		
	}

	@Override
	public ReplyVO get(Long rno) {
		
		log.info("get......"+rno);
		
		return mapper.read(rno);
	}

	@Override
	public int modify(ReplyVO vo) {
		
		log.info("modify......"+vo);
		
		return mapper.update(vo);
	}

	@Transactional	//댓글의 등록,삭제를 담당하는 메서드는 트랜젝션 처리 필요
	@Override
	public int remove(Long rno) {
		
		log.info("remove....."+rno);
		
		ReplyVO vo =mapper.read(rno);		//bno값을 알기위해 vo객체에 rno에 따른 객체를 가져옴
		
		boardMapper.updateReplyCnt(vo.getBno(),-1); //댓글이 삭제되면 replycnt칼럼의 값을 -1 해서 업데이트
		
		return mapper.delete(rno);
	}

	@Override
	public List<ReplyVO> getList(Criteria cri, Long bno) {
		
		log.info("get Reply List of a Board "+ bno);
		return mapper.getListWithPaging(cri, bno);
	}

	@Override
	public ReplyPageDTO getListPage(Criteria cri, Long bno) {
		
		return new ReplyPageDTO(mapper.getCountByBno(bno),mapper.getListWithPaging(cri, bno));
	}
	
	
}
