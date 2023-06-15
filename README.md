
나 혼자 여행 (내부 프로젝트)

중앙정보기술인재개발원 프로젝트 기반 자바 응용 sw 개발자 과정

작업기간 : 2023.04.04 ~ 2023.05.16

조원: 김시아, 김명현, 강선규, 김리오, 이상미, 이정훈, 조경민 ( 총 7명)

혼자 여행떠나는 사람들을 위한 여행 정보 및 동행 커뮤니티 웹 사이트

프로젝트 PPT 링크

https://docs.google.com/presentation/d/178Kth7KhLZwpo95xBHwDO4oGZURn7eDX/edit?usp=sharing&ouid=111395906769681143444&rtpof=true&sd=true

시연영상 링크

https://drive.google.com/file/d/1nLhq2J0ERVnUseb9K_XnLkZEVF-jUPxo/view?usp=drive_link

------------------------------------------------------------------------------------------------------------------------

    내가 만든 파트
    
------------------------------------------------------------------------------------------------------------------------

    커뮤니티

나 혼자 여행이라는 웹 사이트를 개발하며 여러 사람들이 여행 정보도 공유하고, 동행할 친구를 찾거나,
커뮤니티 기능을 활발히 사용 할 수있도록 노력하였다.

코드는 MVC 모델을 이용해 controller > service > dao 로 흐르게 하였고, 
model 과 mapper를 통해 oracle db와 연결하여 정보를 가져오게 하였다.

일단 기본적으로 비로그인자들도 모든 글들을 볼 수 있도록 구성하였고, 로그인한 회원일 경우 모든 기능들을 사용 할 수 있게 하였다.
    
    참고      커뮤니티 관련 코드들
    view       =  s20230404 / src / main / webapp/WEB-INF/view / ro / *.jsp
    model      =  s20230404 / src / main / java/com/travleAlone/s20230404 / model / Board.java, BodImg.java, Warning.java
    controller =  s20230404 / src / main / java/com/travleAlone/s20230404 / controller / BoardController.java
    service    =  s20230404 / src / main / java/com/travleAlone/s20230404 / service / board / *.java
    dao        =  s20230404 / src / main / java/com/travleAlone/s20230404 / dao / board / *.java
    mapper     =  s20230404 / src / main / resources / mappers / Board.xml, Bod_Img.xml, Warning.xml

------------------------------------------------------------------------------------------------------------------------

    디자인

------------------------------------------------------------------------------------------------------------------------


이번 내부 프로젝트의 주제가 '혼자 떠나는 여행'이기 때문에 최대한 주제에 맞는 색감과 디자인을 구성하였다.
혼자 여행다니는 사람들의 기대되고 걱정되는 마음을 고슴도치를 이용하여 이번 프로젝트의 캐릭터로 디자인 하였다.

![11](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/a14c32eb-f3a2-4d10-b244-bced7df9d045)![22](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/09a9c05b-9ba6-4e0a-9c8e-02ad0671ebc1)![33](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/c2cf46f8-8478-483d-b17a-426e38f4ed06)


이 외에 필요한 이미지가 있으면 컨셉에 맞추어 디자인하여 사용하였다.

또한 여행이라는 컨셉에 맞게 여행지를 나타내는 청록색계열의 색을 사용하고, 둥글둥글한 css디자인으로 조금 더 라이트한 커뮤니티 느낌을 주었다.
![3](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/6e54f979-e291-4e38-a12e-d8e74703afef)

![4](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/4427e898-e010-4440-95af-2fe6a3fb538e)

    참고 
    이미지  = s20230404 / src / main / resources / static / img / *.png
    CSS     = s20230404 / src / main / resources / static / *.css


------------------------------------------------------------------------------------------------------------------------

    주요 기능
    
------------------------------------------------------------------------------------------------------------------------

    페이징


커뮤니티 형식의 웹 사이트이기 때문에 데이터들을 조각들로 나누어 효율적으로 관리,탐색하기 위해 페이징처리를 해두었다. 

![페이징](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/36115bc5-61ac-48c9-8478-1ad9128c966d)


위 사진같은 형식으로 10개의 리스트를 1개의 페이지로 묶어두었다.

------------------------------------------------------------------------------------------------------------------------

    필터링

커뮤니티 페이지에서 편의성을 위해 선택한 항목에 대한 필터링처리를 해두었다.

1. 현재 위치한 게시판 위치를 표시, 해당하는 게시판의 게시글만 보임.
b_common_board 라는 공용 테이블로 각각의 게시판 구분

![필터링1](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/821df295-4d97-44eb-afb5-fd7018248f67)


2. 위치한 게시판에서 이미지가 첨부된 게시글들만 필터링 해줌.
체크박스 클릭 시 board 의 img_stored_file_yn 칼럼 값으로 필터링
   
![필터링2](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/c8920656-a107-46c7-b75e-e8f590cf41b9)


3. 게시판의 리스트 정렬방식을 원하는 방식으로 바꿔서 볼 수 있게 작성함.
기본 값은 최신순으로 되어있음. 조회수 순이나 추천 순으로 바꿀 시 sql문에서 해당하는 조건으로 리스트 정렬

![필터링3](https://github.com/leejeonghoon123/LJH_Portfolio2/assets/127282120/633daeec-aec3-4866-948c-8c4720a1d397)


------------------------------------------------------------------------------------------------------------------------

    
