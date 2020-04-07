package org.zerock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;
import org.zerock.service.ReplyService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RequestMapping("/replies/")
@RestController
@Log4j
public class ReplyController {

	@Setter(onMethod_ = @Autowired)
	private ReplyService service;

	@PostMapping(value = "/new", consumes = "application/json", produces = { MediaType.TEXT_PLAIN_VALUE }) // json 타입의
																											// 데이터만
																											// 처리하도록 함
	public ResponseEntity<String> create(@RequestBody ReplyVO vo) { // @RequestBody 어노테이션을 사용하여 json 데이터를 ReplyVO타입의 데이털
																	// 변환하도록 지정

		log.info("Reply : " + vo);

		int insertCount = service.register(vo); // service.register는 댓글이 추가된 숫자를 반환

		log.info("Reply INSERT COUNT : " + insertCount);

		return insertCount == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

	}

	/*
	 * @GetMapping(value = "/pages/{bno}/{page}", produces = {
	 * MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })
	 * public ResponseEntity<List<ReplyVO>> getList(@PathVariable("page") int
	 * page, @PathVariable("bno") Long bno) { // 특정 // 게시물의 // 댓글 // 목록 // 확인
	 * 
	 * log.info("getList............."); Criteria cri = new Criteria(page, 10);
	 * 
	 * log.info(cri);
	 * 
	 * return new ResponseEntity<>(service.getList(cri, bno), HttpStatus.OK); }
	 */

	@GetMapping(value = "/pages/{bno}/{page}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<ReplyPageDTO> getList(@PathVariable("page") int page, @PathVariable("bno") Long bno) { // 특정  게시물의	 댓글	 목록	 확인

		
		Criteria cri = new Criteria(page, 10);
		log.info("get Reply List bno : "+bno);
		log.info("cri : " + cri);

		return new ResponseEntity<>(service.getListPage(cri, bno), HttpStatus.OK);
	}

	@GetMapping(value = "/{rno}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno) { // 댓글 조회
		log.info("get : " + rno);

		return new ResponseEntity<>(service.get(rno), HttpStatus.OK);

	}

	@DeleteMapping(value = "/{rno}", produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> remove(@PathVariable("rno") Long rno) { // 댓글 삭제
		log.info("remove : " + rno);

		return service.remove(rno) == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(method = { RequestMethod.PUT,
			RequestMethod.PATCH }, value = "/{rno}", consumes = "application/json", produces = {
					MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> modify(@RequestBody ReplyVO vo, @PathVariable("rno") Long rno) { // 댓글 수정

		vo.setRno(rno);

		log.info("rno : " + rno);

		log.info("modify : " + vo);

		return service.modify(vo) == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
