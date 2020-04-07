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
			<div class="panel-heading">
				Board List Page
				<button id='regBtn' type="button" class="btn btn-xs pull-right">Register
					New Board</button>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover"
						id="dataTables-example">
						<thead>
							<tr>
								<th>#번호</th>
								<th>제목</th>
								<th>작성자</th>
								<th>작성일</th>
								<th>수정일</th>
							</tr>
						</thead>

						<c:forEach items="${list}" var="board">
							<tr>
								<td><c:out value="${board.bno }" /></td>

								<td><a class='move' href='<c:out value="${board.bno }"/>'><c:out
											value="${board.title }" /><b>[<c:out value="${board.replyCnt }"/>]</b></a></td>
								<td><c:out value="${board.writer }" /></td>
								<td><fmt:formatDate pattern="yyyy-MM-dd"
										value="${board.regdate }" /></td>
								<td><fmt:formatDate pattern="yyyy-MM-dd"
										value="${board.updateDate }" /></td>
							</tr>
						</c:forEach>

					</table>

					<div class='row'>
						<div class="col-lg-12">
							<form id='searchForm' action="/board/list" method='get'>
								<!-- 검색기능 -->
								<select name='type'>
									<option value=""
										<c:out value="${pageMaker.cri.type == null?'selected':'' }"/>>---</option>
									<option value="T"
										<c:out value="${pageMaker.cri.type eq 'T'?'selected':'' }"/>>제목</option>
									<option value="C"
										<c:out value="${pageMaker.cri.type eq 'C'?'selected':'' }"/>>내용</option>
									<option value="W"
										<c:out value="${pageMaker.cri.type eq 'W'?'selected':'' }"/>>작성자</option>
									<option value="TC"
										<c:out value="${pageMaker.cri.type eq 'TC'?'selected':'' }"/>>제목
										or 내용</option>
									<option value="TW"
										<c:out value="${pageMaker.cri.type eq 'TW'?'selected':'' }"/>>제목
										or 작성자</option>
									<option value="TWC"
										<c:out value="${pageMaker.cri.type eq 'TWC'?'selected':'' }"/>>제목
										or 내용 or 작성자</option>

								</select> <input type='text' name='keyword'
									value='<c:out value="${pageMaker.cri.keyword }"/>' /> <input
									type='hidden' name='pageNum'
									value='<c:out value="${pageMaker.cri.pageNum }"/>'> <input
									type='hidden' name='amount'
									value='<c:out value="${pageMaker.cri.amount }"/>'>
								<button class='btn btn-default'>Search</button>
							</form>
						</div>
					</div>

					<div class='pull-right'>
						<ul class="pagination">
							<!-- 페이징 기능 -->
							<c:if test="${pageMaker.prev }">
								<li class="paginate_button previous"><a
									href="${pageMaker.startPage -1 }">Previous</a></li>
							</c:if>

							<c:forEach var="num" begin="${pageMaker.startPage }"
								end="${pageMaker.endPage }">
								<li class="paginate_button ${pageMaker.cri.pageNum == num ? "active":"" }">
									<a href="${num }">${num }</a>
								</li>

							</c:forEach>

							<c:if test="${pageMaker.next }">
								<li class="paginate_button next"><a
									href="${pageMaker.endPage +1 }">Next</a></li>
							</c:if>
						</ul>
						<form id='actionForm' action="/board/list" method='get'>
							<input type='hidden' name='pageNum'
								value='${pageMaker.cri.pageNum }'> <input type="hidden"
								name='amount' value='${pageMaker.cri.amount }'> <input
								type='hidden' name='type'
								value='<c:out value="${pageMaker.cri.type }"/>'> <input
								type='hidden' name='keyword'
								value='<c:out value="${pageMaker.cri.keyword }"/>'>
						</form>
					</div>
					<!-- end Pagination -->


					<!-- Modal 추가 -->
					<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
						aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-hidden="true">&times;</button>
									<h4 class="modal-title" id="myModalLabel">Modal title</h4>
								</div>
								<div class="modal-body">처리가 완료되었습니다.</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">Close</button>
									<button type="button" class="btn btn-primary">Save
										changes</button>
								</div>
							</div>
						</div>
					</div>
					<!-- /.modal -->



				</div>
			</div>
		</div>
	</div>
</div>

<%@include file="../includes/footer.jsp"%>

<script type="text/javascript">
	$(document)
			.ready(
					function() {

						var result = '<c:out value="${result}"/>'; //bno 값을 result에 저장

						checkModal(result);

						history.replaceState({}, null, null); //모달을 다시 띄울 필요가 없다는 것을 처리하기 위해 history에 기록

						function checkModal(result) {//모달 기능 동작

							if (result === '' || history.state) {

								return;
							}

							if (parseInt(result) > 0) {//bno가 0보다 크면

								$(".modal-body").html(
										"게시글" + parseInt(result)
												+ " 번이 등록되었습니다.");
							}

							$("#myModal").modal("show");
						}

						$("#regBtn").on("click", function() { //    /board/register url요청 동작 
							self.location = "/board/register";
						});

						var actionForm = $("#actionForm");
						$(".paginate_button a").click(
								function(e) {// 페이징 처리  - 페이지에 해당하는 숫자를 눌렀을때의 처리 

									e.preventDefault();

									console.log('click');

									actionForm.find("input[name='pageNum']")
											.val($(this).attr("href"));
									actionForm.submit();
								});

						$(".move")
								.click(
										function(e) { //제목을 눌렀을 때 href로 링크를 주는게 아니라 pageNum을 주도록 변경

											e.preventDefault();
											actionForm
													.append("<input type='hidden' name='bno' value='"
															+ $(this).attr(
																	"href")
															+ "'>");
											actionForm.attr("action",
													"/board/get");
											actionForm.submit();
										});

						var searchForm = $("#searchForm");

						$("#searchForm button").click(
								//검색기능 처리 
								function(e) {

									if (!searchForm.find("option:selected")
											.val()) {
										alert("검색종류를 선택하세요");
										return false;
									}

									if (!searchForm.find(
											"input[name='keyword']").val()) {
										alert("키워드를 입력하세요");
										return false;
									}

									searchForm.find("input[name='pageNum']")
											.val("1");
									e.preventDefault();

									searchForm.submit();
								});
					});
</script>
