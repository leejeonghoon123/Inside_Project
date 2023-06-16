<br><br>

<h1>나 혼자 여행 (내부 프로젝트)</h1>

중앙정보기술인재개발원 프로젝트 기반 자바 응용 sw 개발자 과정

작업기간 : 2023.04.04 ~ 2023.05.16

조원: 김시아, 김명현, 강선규, 김리오, 이상미, 이정훈, 조경민 ( 총 7명)

혼자 여행떠나는 사람들을 위한 여행 정보 및 동행 커뮤니티 웹 사이트

<h3>프로젝트 PPT 링크</h3>

https://docs.google.com/presentation/d/178Kth7KhLZwpo95xBHwDO4oGZURn7eDX/edit?usp=sharing&ouid=111395906769681143444&rtpof=true&sd=true

<h3>시연영상 링크</h3>

https://drive.google.com/file/d/1nLhq2J0ERVnUseb9K_XnLkZEVF-jUPxo/view?usp=drive_link

<br><br><br><br>


<h2>내가 만든 파트</h2>

나 혼자 여행이라는 웹 사이트를 개발하며 여러 사람들이 여행 정보도 공유하고,

동행할 친구를 찾거나,커뮤니티 기능을 활발히 사용 할 수있도록 노력하였다.

코드는 MVC 모델을 이용해 controller > service > dao 로 흐르게 하였고, 

model 과 mapper를 통해 oracle db와 연결하여 정보를 가져오게 하였다.

커뮤니티 파트를 맡아서 밑에 참고 부분의 코드들을 개발하였다.

    
    참고      커뮤니티 관련 코드들
    view       =  s20230404 / src / main / webapp/WEB-INF/view / ro / *.jsp
    model      =  s20230404 / src / main / java/com/travleAlone/s20230404 / model / Board.java, BodImg.java, Warning.java
    controller =  s20230404 / src / main / java/com/travleAlone/s20230404 / controller / BoardController.java
    service    =  s20230404 / src / main / java/com/travleAlone/s20230404 / service / board / *.java
    dao        =  s20230404 / src / main / java/com/travleAlone/s20230404 / dao / board / *.java
    mapper     =  s20230404 / src / main / resources / mappers / Board.xml, Bod_Img.xml, Warning.xml


<br><br><br><br>


<h1>디자인</h1>


이번 내부 프로젝트의 주제가 '혼자 떠나는 여행'이기 때문에 최대한 주제에 맞는 색감과 디자인을 구성하였다.

혼자 여행다니는 사람들의 기대되고 걱정되는 마음을 고슴도치를 이용하여 이번 프로젝트의 캐릭터로 디자인 하였다.

![1](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/bc727182-7732-4f0e-9659-56c7a0514d72)
![2](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/b56e3d9d-7d91-47f0-b2d2-2a931ac39db4)
![3](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/cb4330d2-15c6-4dd9-afaf-f7c3a11d5ca7)

<h3>고슴도치 캐릭터 디자인, 로고</h3><br>



![4](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/5d92d7a5-f40c-489a-8744-18e6921f19c9)

<h3>전체적인 디자인 색상</h3><br>



이 외에 필요한 이미지가 있으면 컨셉에 맞추어 디자인하여 사용하였다.

또한 여행이라는 컨셉에 맞게 여행지를 나타내는 청록색계열의 색을 사용하고,

둥글둥글한 css디자인으로 조금 더 라이트한 커뮤니티 느낌을 주었다.
![3](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/6e54f979-e291-4e38-a12e-d8e74703afef)

![4](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/4427e898-e010-4440-95af-2fe6a3fb538e)

    참고 
    이미지  = s20230404 / src / main / resources / static / img / *.png
    CSS     = s20230404 / src / main / resources / static / *.css

<br><br><br><br>


<h1>페이징</h1>


커뮤니티 형식의 웹 사이트이기 때문에 데이터들을 조각들로 나누어 효율적으로 관리,탐색하기 위해 페이징처리를 해두었다. 

![페이징](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/36115bc5-61ac-48c9-8478-1ad9128c966d)


위 사진같은 형식으로 10개의 리스트를 1개의 페이지로 묶어두었다.

    참고
    s20230404 / src / main / java/com/travleAlone/s20230404 / service / Paging.java
    위의 Paging.java model을 import하여 사용
    사용된 예시
    s20230404 / src / main / webapp/WEB-INF/view / ro / boardAllListForm.jsp 96줄~104줄 페이징 처리
    s20230404 / src / main / webapp/WEB-INF/view / ro / boardListForm.jsp 106줄~114줄 페이징 처리 
    

<br><br><br><br>

<h1>필터링</h1>

커뮤니티 페이지에서 편의성을 위해 선택한 항목에 대한 필터링처리를 해두었다.
<br>

<h3>1</h3>
현재 위치한 게시판 위치를 표시, 해당하는 게시판의 게시글만 보임.

b_common_board 라는 공용 테이블로 각각의 게시판 구분

![필터링1](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/821df295-4d97-44eb-afb5-fd7018248f67)

    참고
    s20230404 / src / main / webapp / WEB-INF / views / ro / boardHeader.jsp

<br><br>

<h3>2</h3>
위치한 게시판에서 이미지가 첨부된 게시글들만 필터링 해줌.

체크박스 클릭 시 board 의 img_stored_file_yn 칼럼 값으로 필터링

   
![필터링2](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/c8920656-a107-46c7-b75e-e8f590cf41b9)

    참고
    s20230404 / src / main / webapp / WEB-INF / views / ro / boardListForm.jsp 80줄 ~ 102줄
    
    SQL
    s20230404 / src / main / resources / mappers / Board.xml 292줄 ~ 297줄

<br><br>

<h3>3</h3>
게시판의 리스트 정렬방식을 원하는 방식으로 바꿔서 볼 수 있게 작성함.

기본 값은 최신순으로 되어있음. 조회수 순이나 추천 순으로 바꿀 시 sql문에서 해당하는 조건으로 리스트 정렬

![필터링3](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/633daeec-aec3-4866-948c-8c4720a1d397)

    참고
    s20230404 / src / main / webapp / WEB-INF / views / ro / boardListForm.jsp 59줄 ~ 62
    
    SQL
    s20230404 / src / main / resources / mappers / Board.xml  9줄~ 161줄
   

<br><br><br><br>



<h1>추천</h1>

게시글의 본문 아래 쪽에 추천 버튼을 누를 시 추천수가 증가되도록 작성하였다.

![추천1](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/865805e8-06d3-46ed-b559-8abd38ed2354)

![추천2](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/d9aa0079-4c87-4509-b0ac-3afa67476d6a)

만약 로그인하지 않은 회원의 경우 로그인 페이지로, 

추천한 게시글일 경우 추천이 취소된다.

![추천3](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/74861e1c-5db1-4dbb-8304-fcb0ca48d00c)

![추천4](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/6fe9c6c0-b07f-431c-aeef-3b2a7968e5bf)

    참고
    
    화면에 나타나는 버튼
    s20230404 / src / main / webapp / WEB-INF / views / ro / detailBoardForm.jsp 76줄 ~ 82줄
    
    Controller
    s20230404 / src / main / java/com/travelAlone / s20230404 / controller / BoardController.java 338줄 ~ 393줄

    SQL문
    s20230404 / src / main / resources / mappers / Board.xml 279줄~290줄

<br><br><br><br>

<h1>이미지 업로드, 수정, 삭제</h1>

글 작성시 이미지를 첨부할수 있어야 작성자가 조금 더 시각적 정보제공이 편리하기 때문에 기능을 추가하였다.

드래그하여 업로그시 여러장의 이미지를 업로드 할 수 있다. 

![이미지업로드](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/e7a46e22-649a-4b8d-b73e-1d1e5dd710bb)

자신이 작성한 글을 수정시 이미지또한 수정가능하도록 원래의 이미지를 작은 형태로 보여주고, 다시 업로드 할 수도 있다.

![이미지업로드2](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/556ae3c1-397b-4e48-936d-989c7000eeaa)

    참고
    
    s20230404 / src / main / webapp / WEB-INF / views / ro / writeBoardForm.jsp 35줄 ~ 42줄
    s20230404 / src / main / webapp / WEB-INF / views / ro / updateBoardForm.jsp 14줄 ~ 35줄, 56줄~86줄
    
    Controller
    s20230404 / src / main / java/com/travelAlone / s20230404 / controller / BoardController.java
    191줄 ~ 208줄, 264줄 ~ 279
    
    Service
    s20230404 / src / main / java/com/travelAlone / s20230404 / service / board/BoardServiceImpl.java 
    104줄 ~ 122줄, 145줄 ~ 168줄, 188줄 ~ 234줄
    s20230404 / src / main / java/com/travelAlone / s20230404 / service / board / UploadHandler.java 

    SQL
    s20230404 / src / main / resources / mappers / Bod_Img.xml

<br><br><br><br>

<h1>댓글, 대댓글</h1>

게시글의 댓글, 대댓글 기능또한 구현해두었다. 

댓글의 나열 순서는 먼저 작성한 순서대로 나열되게 해두었다.

게시글과 댓글을 DB상으로 따로 테이블을 두지 않고, 하나의 BOARD테이블에 넣어 LEVEL로 나누어 구분한다.

그리고 자신이 쓴 댓글의 경우, 수정,삭제가 나타나게 해 두었다.

![대댓글](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/81d02ce6-7e26-4ee8-aa7d-e33994d1aa9f)

    참고
    s20230404 / src / main / webapp / WEB-INF / views / ro / detailBoardForm.jsp 110줄 ~ 211줄

    Controller
    s20230404 / src / main / java/com/travelAlone / s20230404 / controller / BoardController.java
    210줄 ~ 247줄, 297줄 ~ 322줄

    SQL
    s20230404 / src / main / resources / mappers / Board.xml 192줄 ~ 234줄, 250줄 ~ 267줄
     

<br><br><br><br>

<h1>신고</h1>


로그인한 회원의 경우 게시글작성자, 댓글 작성자를 사유에 따라 신고 가능하다.

같은 사유로 중복신고 악용을 막기 위해 작성글PK + 신고사유 + 신고자 로 쿠키 키를 만들어 중복 검사를 하였다.

신고 버튼을 클릭 시 신고사유에 관한 창이 나타나도록 script로 숨겨두었다.

![신고1](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/aa02b30e-c8d2-4f20-bc31-c01a30f6deba)

![신고2](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/e3b678b4-5d9b-4d1c-b0b0-fb68a4a38b8b)

    참고
    s20230404 / src / main / webapp / WEB-INF / views / ro / detailBoardForm.jsp 89줄 ~ 104줄, 170줄 ~ 182줄
    
    js
    s20230404 / src / main / resources / static / js / detailBoardWarning.js

    Controller
    s20230404 / src / main / java/com/travelAlone / s20230404 / controller / BoardController.java
    396줄 ~ 448줄
    
    SQL
    s20230404 / src / main / resources / mappers / Warning.xml


    
<br><br><br><br>

<h1>맴버페이지</h1>

커뮤니티에서는 게시글이나 댓글의 작성자 닉네임을 클릭 시, 해당 작성자의 기본 정보창이 뜨도록 구성해두었다. 

해당하는 정보창의 점수를 올려줄 수 있으며, 과도한 점수증가를 막기 위해 해당회원 + 추천항목 + 추천자 로 쿠키 키를 만들어 중복 확인 하였다.

![회원페이지1](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/f68ec436-fe73-45de-a33b-6108447c999a)

![회원페이지2](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/40452517-52b6-4b5d-b754-5a3d81874a62)


    참고
    s20230404 / src / main / webapp / WEB-INF / views / ro / boardListForm.jsp 94줄 닉네임 클릭 시 이동.
    
    모달 창
    s20230404 / src / main / webapp / WEB-INF / views / km / userpage.jsp
    
    js
    s20230404 / src / main / resources / static / js / userPage.js

    Controller
    s20230404 / src / main / java/com/travelAlone / s20230404 / controller / BoardController.java
    484줄 ~ 494줄, 563줄 ~ 612줄

    SQL
    s20230404 / src / main / resources / mappers / Board.xml 321줄 ~ 326줄

<br><br><br><br>

<h1>마이 페이지</h1>

로그인한 회원이 자신이 작성한 글, 상세정보 등을 볼 수 있는 페이지다.

    참고
    s20230404 / src / main / webapp / WEB-INF / views / km / mypage.jsp
    

회원이 작성한 글, 리뷰등 활동한 내역들을 정리해둔 페이지로 갈 수 있게 작성해 두었다.

![1](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/51706296-a44e-49bc-b2a2-b866a3bb66b3)
![2](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/7665040a-3630-4162-a626-45f2eb447f0b)

내가 작성한 글 의 경우, 커뮤니티 게시판에 작성한 글들을 리스트형식으로 보여주게 된다.

10개의 글을 1개의 페이지로 페이징처리 해두었으며, 제목을 클릭 시 해당 게시글로 이동하게된다. 

제목 옆의 작성한 게시판, 작성일, 추천수, 조회수를 표기하였다.

    참고
    s20230404 / src / main / webapp / WEB-INF / views / ro / myPageCommunityList.jsp

    Controller
    s20230404 / src / main / java/com/travelAlone / s20230404 / controller / BoardController.java
    452줄 ~ 481줄

    SQL
    s20230404 / src / main / resources / mappers / Board.xml 299줄 ~ 319줄

![5](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/d5d425fa-4995-4eaf-9b1b-aaa4170aaea2)
![6](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/ea6b24b6-d5dc-4926-9f11-598591a1feed)

내가 작성한 리뷰의 경우, 여행지, 숙소, 맛집 게시판에서 작성한 리뷰들을 모아서 보여주게된다.

제목 클릭 시, 해당 리뷰를 작성한 게시글로 이동하게 된다.

제목, 리뷰내용, 별점, 작성일 순으로 표기해두었다.

    참고
    s20230404 / src / main / webapp / WEB-INF / views / ro / reviewPageHou.jsp, reviewPageRes.jsp, reviewPageTra.jsp
    
    Controller
    s20230404 / src / main / java/com/travelAlone / s20230404 / controller / BoardController.java
    496줄 ~ 560줄

    SQL
    s20230404 / src / main / resources / mappers / Board.xml 328줄 ~ 403줄


![3](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/bacbd51b-ddf0-4213-a490-734224ed3daf)
![4](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/7ff4606d-b4b1-48a9-8f82-fdace2d36069)



![7](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/73d524aa-8183-4b54-b00a-d61fa4cd902c)
![8](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/951d60d7-0c9e-414f-9bd5-18ec5b290024)





