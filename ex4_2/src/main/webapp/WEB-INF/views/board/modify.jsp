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

				<form role="form" action="/board/modify" method="post">

					<div class="form-group">
						<label>Bno</label> <input class="form-control" name='bno'
							value='<c:out value="${board.bno }"/>' readonly="readonly">
					</div>

					<div class="form-group">
						<label>Title</label> <input class="form-control" name='title'
							value='<c:out value="${board.title }"/>'>
					</div>

					<div class="form-group">
						<label>Text area</label>
						<textarea class="form-control" rows="3" name='content'>
							<c:out value="${board.content }" />
						</textarea>
					</div>
					
					<div class="form-group">
						<label>Writer</label> <input class="form-control" name='writer'
							value='<c:out value="${board.writer }"/>' readonly="readonly">
					</div>
					
					<div class="form-group">
						<label>RegDate</label> <input class="form-control" name='regDate'
							value='<fmt:formatDate pattern="yyyy/MM/dd" value="${board.regdate }"/>' readonly="readonly">
					</div>
					
					<div class="form-group">
						<label>Update Date</label> <input class="form-control" name='updateDate'
							value='<fmt:formatDate pattern="yyyy/MM/dd" value="${board.updateDate }"/>' readonly="readonly">
					</div>
					
					
					<button type="submit" data-oper='modify' class="btn btn-dufault">Modify</button>
					
					<button  type="submit" data-oper='remove' class="btn btn-danger">Remove</button>
					
					<button  type="submit" data-oper='list' class="btn btn-info">List</button>
					
					<!-- 페이징 처리 정보의 처리 -->
					<input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum }"/>'>
					<input type='hidden' name='amount' value='<c:out value="${cri.amount }"/>'>
					<input type='hidden' name='keyword' value='<c:out value="${cri.keyword }"/>'>
					<input type='hidden' name='type' value='<c:out value="${cri.type }"/>'>
					

				</form>

			</div>
		</div>
	</div>
</div>

<%@include file="../includes/footer.jsp"%>

<script type="text/javascript">

$(document).ready(function(){
	var formObj=$("form");
	
	$("button[data-oper='remove']").on("click",function(e){	//요방식이 좀더 직관적이고 쓰기편하게 느껴짐
		e.preventDefault();
		formObj.attr("action","/board/remove").submit();
		
	});
	$("button[data-oper='list']").click(function(e){ 	//on("click",function(e){});
		e.preventDefault();
		formObj.attr("action","/board/list").attr("method","get");
		var pageNumTag=$("input[name='pageNum']").clone();
		var amountTag=$("input[name='amount']").clone();
		var keywordTag=$("input[name='keyword']").clone();
		var typeTag=$("input[name='type']").clone();
		
		formObj.empty();
		formObj.append(pageNumTag);
		formObj.append(amountTag);
		formObj.append(keywordTag);
		formObj.append(typeTag);
		
		formObj.submit();		
	});
	
});



 /* $(document).ready(function(){ ////책에 나온 대로 하면 요렇게
	console.log("ready");
	var formObj=$("form");
	
	$('button').on("click", function(e){
		
		e.preventDefault();
		
		var operation = $(this).data("oper");
		
		console.log(operation+"......................................@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		
		if(operation === 'remove'){
			
			formObj.attr("action","/board/remove");
		}
		else if(operation === 'list'){
			// self.location="/board/list";
			//return ; 
	 	formObj.attr("action","/board/list").attr("method","get");
			formObj.empty();
		}
		formObj.submit();
	}); 
	
}) */

</script>
