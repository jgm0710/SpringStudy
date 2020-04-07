package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller		//스프링의 빈으로 인식할 수 있도록 표시 
				//@Controller 어노테이션의 가장 편리한 기능은 파라미터가 자동으로 수집되는 기능이다!!!@#@!#@!#
@Log4j

@RequestMapping("/board/")		// board로 시작하는 모든 처리를 BoardController 클래스가 하도록 지정 
@AllArgsConstructor		//BoardController는 BoardService에 대해서 의존적 이 어노테이션을 사용하여 생성자를 만들고 자동으로 주입하도록 함.
public class BoardController {	//url로써 처리를 하기 위해서 사용하는 프레젠테이션 계층(?)

	//@Setter(onMethod_=@Autowired)
	private   BoardService service;		//비즈니스 계층을 주입받음.
	
	/*
	 * @GetMapping("/list") //get 방식의 url요청을 처리하는 매핑 . 조회기능이라 get방식 호출처리 public void
	 * list(Model model) {//목록 조회 기능 log.info("list"); model.addAttribute("list",
	 * service.getList()); //list라는 이름의 속성에 service.getList()의 결과를 담음 }
	 */
	
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		
		log.info("list : "+ cri);
		model.addAttribute("list",service.getList(cri));
		
		int total = service.getTotal(cri);
		model.addAttribute("pageMaker",new PageDTO(cri,total));
	}
	
	
	@PostMapping("/register")//등록이라 post방식 처리
	public String register(BoardVO board, RedirectAttributes rttr) {//등록 기능 
		//등록 작업이 끝난 후 다시 목록 화면으로 이동하기 위해 RedirectAttributes를 파라미터로 지정. 추가적으로 새롭게 등록된 게시물의 번호를 같이 전달하기 위해 사용
		//의문점 !! 테스트 파일에서는 레지스터에 대한 함수 호출이 없음.
		//위 의문점 해결... @Controller 어노테이션에 의해 파라미터가 자동으로 수집됨.
		//test 파일에서testRegister()에 의해 보내진 속성 값들이 자동으로 들어감.
	
		log.info("register : "+board);
		
		service.register(board);
		
		rttr.addFlashAttribute("result", board.getBno());
		
		return "redirect:/board/list";	//'redirect :' 접두어를 사용하면 mvc가 내부적으로 response.sendRedirect()처리를 해줌
	}
	@GetMapping("/register")
	public void register() {
		
		
	}
	
	@GetMapping({"/get","/modify"})
	public void get(@RequestParam("bno") Long bno,@ModelAttribute("cri") Criteria cri ,Model model) {	//조회 기능
																	//get()메서드 에서는 bno 값을 좀더 명시적으로 처리하는
															//@RequestParam을 이용해서 지정. 
													//파라미터의 이름과 변수 이름을 기준으로 동작하기 때문에 생략해도 무방.
		
		log.info("/get of modify");
		model.addAttribute("board", service.get(bno));
		
	}
	
	@PostMapping("/modify")	//수정은 주로 Post 방식
	public String modify(BoardVO board,@ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {//수정 기능
		log.info("modify : "+board);
		
		if(service.modify(board)) {
			rttr.addFlashAttribute("result","success");
		}
		
		/*
		 * rttr.addAttribute("pageNum", cri.getPageNum()); rttr.addAttribute("amount",
		 * cri.getAmount()); rttr.addAttribute("type",cri.getType());
		 * rttr.addAttribute("keyword",cri.getKeyword());
		 */
		return "redirect:/board/list"+cri.getListLink();
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno,Criteria cri, RedirectAttributes rttr) {//삭제 기능
		log.info("remove....."+ bno);
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result","success");
		}
		
		/*
		 * rttr.addAttribute("pageNum",cri.getPageNum());
		 * rttr.addAttribute("amount",cri.getAmount());
		 * rttr.addAttribute("type",cri.getType());
		 * rttr.addAttribute("keyword",cri.getKeyword());
		 */
		
		return "redirect:/board/list"+cri.getListLink();
	}
}
