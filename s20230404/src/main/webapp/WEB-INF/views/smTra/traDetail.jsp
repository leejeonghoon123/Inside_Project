<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ include file="header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id="img_banner">
		<img src="img/main-picture.png" alt="배너">
	</div>
	<h1>숙소글 상세		</h1>
	<!-- 사진 올리기 -->
	 <img alt="UpLoad Image" src="${pageContext.request.contextPath}/upload/${savedName}">
	<table style="margin:auto;">
		<tr><th>여행지명</th>			<td>${travel.t_name}</td></tr>
		<tr><th>여행지주소</th>			<td>${travel.t_address}</td></tr>
		<tr><th>입장료</th>			<td>${travel.t_fee}</td></tr>
		<tr><th>운영시간</th>			<td>${travel.t_hour}</td></tr>
		<tr><th>문의전화</th>			<td>${travel.t_call}</td></tr>
		<tr><th>주차장여부</th>			<td>${travel.t_parking}</td></tr>
		<tr><th>여행지 정보</th>		<td>${travel.t_content}</td></tr>
		<tr> <th hidden> ${travel.travel_id} </th>  </tr>
		

		<tr><td colspan="2">
	<!-- 	    <input type="button" value="목록" 
				onclick="location.href='tra'"> -->
			<a href="tra" class="button">목록</a>
				<a href="traUpdateForm?travel_id=${travel.travel_id}" class="button">수정</a>
				<a href="traDelete?travel_id=${travel.travel_id}" class="button">삭제</a>
		</tr>
	</table>
	
		<h3>리뷰		</h3>
	<table style="margin:auto;">
				<tr>
					<td>리뷰번호</td>													
					<td>아이디</td>
					<td>내용</td>
					<td>평점</td>
					<td>작성일</td>
					
					

				</tr>
					<c:forEach items="${traRevList}" var="traRev">
					<tr>
						<td>${traRev.review_id}</td>
					 	<td>${traRev.member_id}</td>
					 	<td>${traRev.r_content}</td>
					 	<td>${traRev.r_score}</td>
					 	<td>${traRev.create_date}</td>
					 <td><a href="traRevUpdateForm?travel_id=${traRev.travel_id}&review_id=${traRev.review_id}">수정</a></td>
					 	<td>
					 <%-- 	<a href="traRevDelete?review_id=${review_id}">삭제</a> --%>
					 	

					 	 <a href="traRevDelete?review_id=${traRev.review_id}" onclick="return confirm('정말로 삭제하시겠습니까?')">삭제</a>
					 	 </td>
						
											
					</tr>
				</c:forEach>
				<tr>
					<td colspan="5"><a href="traRevWriteForm?travel_id=${travel.travel_id}">리뷰작성</a></td>
				</tr>
			</table>
	
	
	
	
</body>
	<c:import url="footer.jsp"/>
</html>