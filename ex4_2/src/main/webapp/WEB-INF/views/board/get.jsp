<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@include file="../includes/header.jsp"%>
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">Tables</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">

			<div class="panel-heading">Board Register</div>
			<!-- /.panel-heading -->
			<div class="panel-body">

				<div class="form-group">
					<label>Bno</label> <input class="form-control" name='bno'
						value='<c:out value="${board.bno }"/>' readonly="readonly">
				</div>


				<div class="form-group">
					<label>Title</label> <input class="form-control" name='title'
						value='<c:out value="${board.title }"/>' readonly="readonly">
				</div>

				<div class="form-group">
					<label>Text area</label>
					<textarea class="form-control" name='content' readonly="readonly"><c:out
							value="${board.content }" /></textarea>
				</div>


				<div class="form-group">
					<label>Writer</label> <input class="form-control" name='writer'
						value='<c:out value="${board.writer }"/>' readonly="readonly">
				</div>

				<button data-oper='modify' class="btn btn-dufault">Modify</button>

				<button data-oper='list' class="btn btn-info">List</button>

				<form id='operForm' action="/board/modify" method="get">
					<input type='hidden' id='bno' name='bno'
						value='<c:out value="${board.bno }"/>'> <input
						type='hidden' name="pageNum"
						value='<c:out value="${cri.pageNum }"/>'> <input
						type='hidden' name='amount'
						value='<c:out value="${cri.amount }"/>'> <input
						type='hidden' name='keyword'
						value='<c:out value="${cri.keyword }"/>'> <input
						type='hidden' name='type' value='<c:out value="${cri.type }"/>'>
				</form>


			</div>
		</div>
	</div>
</div>

<!-- 댓글용 div -->
<div class='row'>
	<div class="col-lg-12">
		<!-- /.panel -->
		<div class="panel panel-default">
			<!-- <div class="panel-heading">
				<i class="fa fa-comments fa-fw"></i> Reply
			</div> -->
			<div class="panel-heading">
				<i class="fa fa -comments fa-fw"></i>Reply
				<!-- 댓글 추가를 위한 버튼. 댓글 추가는 모달창을 이용해서 진행.-->
				<button id='addReplyBtn' class='btn btn-primary btn-xs pull-right'>New
					Reply</button>
			</div>

			<!-- /.panel-heading -->
			<div class="panel-body">
				<ul class="chat">
					<li class="left clearfix" data-rno='32'>
						<div>
							<div class="header">
								<strong class="primary-font"> user00 </strong> <small
									class="pull-right text-muted">2018-01-01 13:13</small>
							</div>
							<p>Good job!</p>
						</div>
					</li>
					<!-- ./end reply -->
				</ul>
				<!-- ./end ul -->
			</div>
			<!-- ./panel .chat-panel -->
			<!-- 댓글의 페이징 처리를 위해 추가 -->
			<div class="panel-footer">
		
		</div>
	</div>
	<!-- ./end row -->
</div>

<%@include file="../includes/footer.jsp"%>



<!-- Modal -->
<!-- 댓글 추가를 위한 모달 창  -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label>Reply</label> <input class="form-control" name='reply'
						value='New Reply!!!!'>
				</div>
				<div class="form-group">
					<label>Replyer</label> <input class="form-control" name='replyer'
						value='replyer'>
				</div>
				<div class="form-group">
					<label>Reply Date</label> <input class="form-control"
						name='replyDate' value=''>
				</div>
			</div>
			<div class="modal-footer">
				<button id='modalModBtn' type="button" class="btn btn-warning">Modify</button>
				<button id='modalRemoveBtn' type="button" class="btn btn-danger">Remove</button>
				<button id='modalRegisterBtn' type="button" class="btn btn-primary">Register</button>
				<button id='modalCloseBtn' type="button" class="btn btn-default">Close</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->



<script type="text/javascript" src="/resources/js/reply.js"></script>

<script>
	$(document)
			.ready(
					function() {

						var bnoValue = '<c:out value="${board.bno}"/>';
						var replyUL = $(".chat");

						showList(1);

						function showList(page) {	// 게시물의 댓글을 보여주기 위한 처리
							
							console.log("show list "+page);

							replyService
									.getList(
											{
												bno : bnoValue,
												page : page || 1
											},
											
											function(replyCnt, list) {//댓글 수를 카운터하고 목록을 불러오기 위한 처리
												console.log("replyCnt : "+ replyCnt);
												console.log("list : "+list);
												console.log(list);
												console.log("현재 페이지 요청 : "+page);
												
												if(page == 0){//page값이 -1이면 마지막 페이지를 호출
													console.log("register에 의한 페이지 호출 : "+page)
													pageNum = Math.ceil(replyCnt/10.0);
													showList(pageNum);
													return;
												}
												
												
												

												var str = "";
												if (list == null
														|| list.length == 0) {
													replyUL.html("");

													return;
												}
												for (var i = 0, len = list.length || 0; i < len; i++) {

													str += "<li class='left clearfix' data-rno='"+list[i].rno+"'>";
													str += "	<div><div class='header'><strong class='primary-font'>"
															+ list[i].replyer
															+ "</strong>";
													str += "		<small class='pull-right text-muted'>"
															+ replyService
																	.displayTime(list[i].replyDate)
															+ "</small></div>";
													str += "		<p>"
															+ list[i].reply
															+ "</p></div></li>";

												}

												replyUL.html(str);
												
												showReplyPage(replyCnt);
											}); //end function
	
						} //end showList
						
						//모달을 외한 처리부분 추가
						
						var modal = $(".modal");
						var modalInputReply = modal.find("input[name='reply']");
						var modalInputReplyer = modal.find("input[name='replyer']");
						var modalInputReplyDate = modal.find("input[name=replyDate]");
						
						var modalModBtn = $("#modalModBtn");
						var modalRemoveBtn = $("#modalRemoveBtn");
						var modalRegisterBtn = $("#modalRegisterBtn");
						
						$("#addReplyBtn").click(function(e){
							
							modal.find("input").val("");
							modalInputReplyDate.closest("div").hide();
							modal.find("button[id != 'modalCloseBtn']").hide();
							
							modalRegisterBtn.show();
							
							$(".modal").modal("show");
						}); 
						
						modalRegisterBtn.on("click",function(e){//모달에서 댓글 등록처리
							
							var reply = {
									
									reply : modalInputReply.val(),
									replyer : modalInputReplyer.val(),
									bno : bnoValue
							};
							replyService.add(reply, function(result){
								
								alert(result);
								
								modal.find("input").val("");
								modal.modal("hide");
								
								//showList(1);		//댓글 추가시 추가된 댓글을 보여주기 위해 추가
								showList(0); //댓글 추가시 마지막페이지를 호출하기 위해 -1
								//-1로 할려했으니 500에러가 발생해서 일단 0으로 함
							});
						});
						
						$(".chat").on("click","li",function(e){	//댓클 조회 클릭 이벤트 처리
							
							var rno = $(this).data("rno");
							
							replyService.get(rno, function(reply){
								
								modalInputReply.val(reply.reply);
								modalInputReplyer.val(reply.replyer);
								modalInputReplyDate.val(replyService.displayTime(reply.replyDate)).attr("readonly","readonly");
								modal.data("rno",reply.rno);
								
								modal.find("button[id !='modalCloseBtn']").hide();
								modalModBtn.show();
								modalRemoveBtn.show();
								
								$(".modal").modal("show");
							});
						});
						
						modalModBtn.on("click",function(e){	//댓글 수정 버튼 처리
							
							var reply = {rno:modal.data("rno"), reply: modalInputReply.val()};
							
							replyService.update(reply,function(result){
								
								alert(result);
								modal.modal("hide");
								showList(pageNum);
							});
						});
						
						modalRemoveBtn.on("click", function(e){ //댓글 삭제 버튼 처리
							
							var rno = modal.data("rno");
							
							replyService.remove(rno, function(result){
								
								alert(result);
								modal.modal("hide");
								showList(pageNum);
							});
						});
						
						//댓글의 페이징 처리를 위한 추가 부분
						var pageNum=1;
						var replyPageFooter = $(".panel-footer");
						
						function showReplyPage(replyCnt){
							console.log("showReplyPage(총 댓글 수) : "+replyCnt);
							
							var endNum = Math.ceil(pageNum/10.0)*10;
							var startNum = endNum-9;
							
							var prev = startNum != 1;
							var next = false;
							
							if(endNum * 10 >= replyCnt){
								endNum = Math.ceil(replyCnt/10.0);
							}
							
							if(endNum * 10 < replyCnt){
								next=true;
							}
							
							var str="<ul class='pagination pull-right'>";
							
							if(prev){
								str+="<li class='page-item'><a class='page-link' href='"+(startNum -1)+"'>Previous</a></li>";
							}
							
							for(var i =startNum; i<=endNum; i++){
								
								var active = pageNum == i?"active":"";
								
								str+="<li class='page-item "+active+" '><a class='page-link' href='"+i+"'>"+i+"</a></li>";
							}
							
							if(next){
								str+="<li class='page-item'><a class='page-link' href='"+(endNum+1)+"'>Next</a></li>";
							}
							
							str += "</ul></div>";
							
							console.log(str);
							
							replyPageFooter.html(str);
						}
						
						//댓글의 페이지를 눌렀을 때의 처리
						replyPageFooter.on("click","li a", function(e){
							
							e.preventDefault();
							console.log("page click");
							
							var targetPageNum=$(this).attr("href");
							
							console.log("targetPageNum : "+ targetPageNum);
							
							pageNum = targetPageNum;
							
							showList(pageNum);
						});
					});
</script>

<script>
	console.log("=========================");
	console.log("JS TEST");

	var bnoValue = '<c:out value="${board.bno}"/>'

	//댓글 조회 테스트
	replyService.get(32, function(data) {
		console.log(data);
	});

	/* //31번 댓글 수정 테스트
	replyService.update({
		rno : 31,
		bno : bnoValue,
		reply : "Modified Reply..."
	}, function(result) {
		alert("수정 완료...");
	}); */

	/* //23번 댓글 삭제 테스트
	replyService.remove(30, function(count) {
		console.log(count);

		if (count === "success") {
			alert("REMOVED");
		}
	}, function(err) {
		alert('ERROR......');
	});
	 */

	/* //reply List Test
	replyService.getList({
		bno : bnoValue,
		page : 1
	}, function(list) { // 댓글 목록 처리

		for (var i = 0, len = list.length || 0; i < len; i++) {
			console.log(list[i]);
		}
	});
	 */
	//for replyService add test
	/* replyService.add({		//댓글 등록 
	reply : "JS Test",
	replyer : "tester",
	bno : bnoValue
	}, function(result) {
	alert("RESULT: " + result);
	});  */
</script>


<script type="text/javascript">
	$(document).ready(function() {
		console.log(replyService);

		var operForm = $("#operForm");

		$("button[data-oper='modify']").on("click", function(e) { //수정 버튼 처리

			operForm.attr("action", "/board/modify").submit();
		});

		$("button[data-oper='list']").on("click", function(e) { //게시글 목록 처리

			operForm.find("#bno").remove();
			operForm.attr("action", "/board/list");
			operForm.submit();
		});
	});
</script>




