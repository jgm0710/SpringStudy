package org.zerock.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration		//Servlet의 ServletContext를 이용하기 위해서 사용. 스프링에서는 WebApplicationContext를 이용하기 위해서 사용

@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
@Log4j
public class BoardControllerTests {

	@Setter(onMethod_ = @Autowired)
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;	//가짜 mvc. 가짜로 url과 파라미터 등을 브라우저에서 사용하는 것처럼 만들어서 Controller를 실행해 볼 수 있다.
	
	@Before		//@Before가 적용된 메서드는 모든 테스트 전에 매번 실행되는 메서드가 된다.
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
		
	}
	
	@Test
	public void testListPagin() throws Exception{
		
		log.info(mockMvc.perform(
				MockMvcRequestBuilders.get("/board/list")
				.param("pageNum","1")
				.param("amount","10"))
				.andReturn().getModelAndView().getModelMap());
	}
	
	
	@Test
	public void testRenove() throws Exception{
		
		String resultPage = mockMvc.perform(
				MockMvcRequestBuilders.post("/board/remove")
				.param("bno","26")
				).andReturn().getModelAndView().getViewName();
		
		log.info(resultPage);
	}
	
	
	/*
	@Test
	public void testModify() throws Exception{
		String resultPage=mockMvc.perform(
				MockMvcRequestBuilders.post("/board/modify")
				.param("bno","1")
				.param("title","수정된 테스트 새글 제목")
				.param("content","수정된 테스트 새글 내용")
				.param("writer","user00")
				).andReturn().getModelAndView().getViewName();
		
		log.info(resultPage);
	}
	*/
	
	
	
	
	
	/*
	@Test
	public void testGet() throws Exception{// 조회 기능 테스트 
		
		log.info(mockMvc.perform(MockMvcRequestBuilders
					.get("/board/get")
					.param("bno","4"))
					.andReturn()
					.getModelAndView()
					.getModelMap());
	}	
	*/
	
	
	
	/*
	@Test
	public void testRegister() throws Exception{ //등록처리 테스트. 등록은 Post로 처리
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/register") //Post방식으로 데이터 전달
				.param("title","테스트 새글 제목")	//param()을 이용해서 전달해야 하는 파라미터들을 지정할 수있음. <input>태그와 유사한 역할
				.param("content","테스트 새글 내용")		//이러한 방식으로 코드를 작성하면 최초 작성 시에는 번거로워도 매번 입력할 필요가 없기 때문에 오류가 수정에 수월
				.param("writer","user00"))
				.andReturn().getModelAndView().getViewName();
		
		log.info(resultPage);
		
	}
	*/
	
	/*
	@Test
	public void testList() throws Exception{//목록처리 테스트. 조회는 get으로 처리
		log.info(
				mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))	//get방식의 호출을 함
				.andReturn()
				.getModelAndView()
				.getModelMap());
		
	}
	*/
}
