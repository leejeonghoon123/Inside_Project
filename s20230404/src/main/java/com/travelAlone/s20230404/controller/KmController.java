package com.travelAlone.s20230404.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.travelAlone.s20230404.config.km.Login2User;
import com.travelAlone.s20230404.config.km.LoginUser;
import com.travelAlone.s20230404.config.km.SessionUser;
import com.travelAlone.s20230404.domain.km.MemberJpa;
import com.travelAlone.s20230404.model.*;
import com.travelAlone.s20230404.model.dto.km.*;
import com.travelAlone.s20230404.model.mh.Inquire;
import com.travelAlone.s20230404.service.Paging;
import com.travelAlone.s20230404.service.km.MypageService;
import com.travelAlone.s20230404.service.mh.HouseService;
import com.travelAlone.s20230404.service.sk.SkService;
import com.travelAlone.s20230404.service.sm.TravelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.travelAlone.s20230404.service.km.MemberService;
import com.travelAlone.s20230404.vaildator.km.CheckEmailValidator;
import com.travelAlone.s20230404.vaildator.km.CheckNicknameValidator;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Controller
public class KmController {

    private final MemberService memberService;
    private final CheckEmailValidator checkEmailValidator;
    private final CheckNicknameValidator checkNicknameValidator;
    private final PasswordEncoder passwordEncoder;
    private final MypageService mypageService;
    private final HouseService houseService;
    private final SkService skService;
    private final TravelService travelService;


    /**
     * 2023-04-17 조경민
     * 설명 : 커스텀 유효성 검증을 위해 추가
     *
     * @InitBinder : 특정 컨트롤러에서 바인딩 또는 검증 설정을 변경하고 싶을때 사용
     * WebDataBinder : Http 요청정보를 컨트롤러 메소드의 파라미터나 모델에 바인딩할 때 사용되는 바인딩 객체
     * */
    @InitBinder
    public void validatorBinder(WebDataBinder binder){
        binder.addValidators(checkEmailValidator);
        binder.addValidators(checkNicknameValidator);
    }


    /**
     * 2023-04-17 조경민
     * 설명 : 회원가입창 이동
     * */
    @GetMapping("/join")
    public String goJoin(@ModelAttribute MemberFormDto requestDto, Model model){

        model.addAttribute("memberDto", requestDto);

        return "km/join";
    }

    /**
     * 2023-04-17 조경민
     * 설명 : 회원가입 요청 처리 메서드
     * */
    @PostMapping("api/v1/join")
    public String join(@Valid @ModelAttribute("memberDto") MemberFormDto requestDto, BindingResult errors, Model model){
        if (errors.hasErrors()){
            // 회원가입 실패시 입력 데이터값 유지
            model.addAttribute("memberDto", requestDto);

            // 유효성 통과 못한 필드와 메세지 핸들링
            Map<String, String> validatorResult = memberService.validateHandling(errors);

            // 반복문 이용하여 애러메세지 담기
            for (String key : validatorResult.keySet()){
                model.addAttribute(key, validatorResult.get(key));
            }

            // 회원가입 요청처리 화면 보여주기
            return "km/join";


        }

        // 검사에 이상이 없을경우 암호화 하여 저장
        memberService.save(requestDto,passwordEncoder);

        return "redirect:/login";
    }


    /**
     * 2023-04-19 조경민
     * 설명 : 로그인 페이지 이동
     * */
    @GetMapping("/login")
    public String goLogin(HttpServletRequest request){


        // 이전 페이지로 되돌아가기 위한 Referer 헤더값을 세션의 prevPage attribute로 저장
        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/login")) {
            request.getSession().setAttribute("prevPage", uri);
        }

        return "km/login";
    }

    /**
     * 2023-04-19 조경민
     * 설명 : 로그인 처리 에러시 표시될 메세지 출력
     * */
    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "km/login";
    }


    /**
     * 2023-04-20 조경민
     * 설명 : 아이디 찾기 화면 이동
     * */
    @GetMapping("/id")
    public String goFindId() {

        return "find-id";
    }


    /**
     * 2023-04-20 조경민
     * 설명 : form 이용하여 이름과 휴대폰번호를 전달, 가입 이메일을 찾아온다
     *      전달값 : String name , String phone
     *      반환값 : String email
     * */
    @PostMapping("api/v1/id")
    public String findId(@ModelAttribute MemberFindIdRequestDto requestDto, Model model){

        // 이메일을 찾아온다
        List<String> findEmails = memberService.findEmailByNameAndPhone(requestDto);

        // 비어있다면 error 모델에 받아 다시 페이지에 출력
        if (findEmails == null || findEmails.isEmpty()){
            model.addAttribute("error", "조건에 맞는 아이디가 없습니다.");
            return "find-id";
        }

        // 모델에 담아준다
        model.addAttribute("email", findEmails);

        // 찾은 id 조회페이지로 이동
        return "checking-id";
    }

    /**
     *  2023-04-20 조경민
     *  설명 : 비밀번호 찾기 화면 이동
     * */
    @GetMapping("/password")
    public String goFindPassword(){

        return "find-password";
    }

    /**
     * 2023-04-20 조경민
     * 설명 : form 이용하여 이메일, 이름과 휴대폰번호를 전달, 해당하는 유저를 찾고 id를 반환하여 비밀번호 변경 페이지로 이동한다
     *      전달값 : String email, String name , String phone
     *      반환값 : Long id, String email, String name, String phone ,아이디 존재 여부
     * */
    @GetMapping("/password/info")
    public String findPassword(@ModelAttribute MemberFindPasswordRequestDto requestDto, Model model){

        // 해당하는 member id 를 찾아온다
        Long memberId = memberService.findMemberIdByEmailNamePhone(requestDto);

        if (memberId == null){

            //찾아온 memberId가 null이라면 다시 페이지 반환
            return "find-password";

        }else {

            // 있다면 정보를 담아 비밀번호 변경창으로 이동
            model.addAttribute("info", requestDto);
            model.addAttribute("memberId", memberId);

            return "change-password";
        }
    }

    /**
     * 2023-04-20 조경민
     * 설명 : 비밀번호 찾기 후 변경 페이지에서 정보들을 받아 확인하고 변경한다(ajax 사용)
     * */
    @PostMapping("api/v1/password/{id}")
    public String changePassword(@PathVariable Long id,@RequestBody MemberFindAndChangePasswordRequestDto requestDto){

        memberService.checkMemberAndChangePassword(id, requestDto, passwordEncoder);

        return "km/login";
    }

    //마이페이지 Controller (MyBatis 사용)---------------------------------------------------------------

    /**
     * 2023-04-24 조경민
     * 설명 : 마이페이지 정보 불러오기
     * */
    @GetMapping("/mypage")
    public String mypageMain(@Login2User SessionUser sessionUser, Model model){

        MypageResponseDto responseDto = mypageService.mypageMain(sessionUser.getId());

        responseDto.addMemberInfo(sessionUser);

        model.addAttribute("response", responseDto);


        return "km/mypage";
    }

    /**
     * 2023-04-26 조경민
     * 설명 : 마이페이지 회원정보 수정창 띄우기
     * */
    @GetMapping("/mypage/member-info")
    public String mypageMemberInfo(@LoginUser MemberJpa memberJpa, Model model){
        model.addAttribute("email", memberJpa.getEmail());
        model.addAttribute("name", memberJpa.getName());
        model.addAttribute("nickName", memberJpa.getNickname());
        model.addAttribute("phone", memberJpa.getPhone());

        return "km/mypage-member-info";
    }

    /**
     * 2023-04-26 조경민
     * 설명 : 마이페이지 회원정보 변경
     * */
    @PostMapping("/api/v1/mypage/info")
    @ResponseBody
    public String mypageMemberInfoUpdate(@RequestBody Member member, @LoginUser MemberJpa memberJpa){

        member.setMember_id(memberJpa.getId());

        mypageService.memberInfoUpdate(member);

        return "성공";
    }

    /**
     * 2023-05-04 조경민
     * 설명 : 마이페이지 프로필 변경창 이동
     * */
    @GetMapping("/mypage/profile")
    public String mypageProfileProfile(@Login2User SessionUser sessionUser, Model model){

        model.addAttribute("storedImgName", sessionUser.getImgStoredFile());

        return "km/mypage-member-profile";
    }

    /**
     * 2023-04-26 조경민
     * 설명 : 마이페이지 프로필 사진 수정
     * */
    @PostMapping("/api/v1/mypage/profile")
    @ResponseBody
    public String mypageMemberProfileUpdate(@RequestBody List<MultipartFile> file, @LoginUser MemberJpa memberJpa) throws Exception {

        Member member = new Member();
        member.of(memberJpa);

        mypageService.memberProfileUpdate(file, member);

        return "성공";
    }

    /**
     * 2023-05-01 조경민
     * 설명 : 마이페이지 프로필 사진 기본으로 변경
     * */
    @PostMapping("/api/v1/mypage/profile/normal")
    @ResponseBody
    public String mypageMemberProfileReset(@LoginUser MemberJpa memberJpa){

        Member member = new Member();
        member.of(memberJpa);

        mypageService.memberProfileReset(member);
        return "성공";
    }

    /**
     * 2023-05-03 조경민
     * 설명 : 회원 탈퇴 창 이동
     * */
    @GetMapping("/mypage/withdrawal")
    public String mypageMemberWithdrawal(@LoginUser MemberJpa memberJpa, Model model){
        model.addAttribute("memberId", memberJpa.getEmail());

        return "km/mypage-member-withdrawal";
    }

    /**
     * 2023-05-01 조경민
     * 설명 : 회원 탈퇴
     * */
    @DeleteMapping("/api/v1/mypage/withdrawal")
    @ResponseBody
    public String mypageMemberWithdrawal(@RequestBody MypageMemberWithdrawalRequestDto requestDto, @LoginUser MemberJpa memberJpa, Model model){

        if (passwordEncoder.matches(requestDto.getPassword(),memberJpa.getPassword()) &&
                requestDto.getMemberEmail().equals(memberJpa.getEmail())){

            mypageService.memberWithdrawal(memberJpa.getId());

            return memberJpa.getEmail();
        }else {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            throw new IllegalArgumentException("회원정보가 일치하지 않습니다.");
        }

    }

    /**
     * 2023-05-01 조경민
     * 설명 : 작성 리뷰(종류별) 가져오기
     * category는 주소창으로, page는 쿼리스트링으로
     * category : {travel, house, restaurant}
     * */
    @GetMapping("/mypage/review")
    public String mypageReview(@RequestParam(required = false) String category,
                               @RequestParam(required = false) Integer page,
                               @LoginUser MemberJpa memberJpa,
                               Model model){
        if (category == null){
            category = "travel";
        }

        if (page == null){
            page = 1;
        }
        List<MypageReviewResponseDto> responseDtos =
                mypageService.mypageReviewShow(new MypageReviewRequestDto(memberJpa.getId(), category, page));

        model.addAttribute("category", category);
        model.addAttribute("page", page);
        model.addAttribute("responseDtos", responseDtos);

        return "km/mypage-review";
    }

    // 마이페이지 문의내역 리스트
    @GetMapping("/mypage/inquire")
    public String myPageInquireList(@LoginUser MemberJpa memberJpa, Inquire inquire, String currentPage, Model model) {

        int myPageInquireListCnt = mypageService.myPageInquireListCnt(memberJpa.getId());

        // Paging 작업
        Paging page = new Paging(myPageInquireListCnt, currentPage);

        // inquire에 추가 setting
        inquire.setStart(page.getStart());
        inquire.setEnd(page.getEnd());
        inquire.setMember_id(memberJpa.getId());

        List<Inquire> myPageInquireList = mypageService.myPageInquireList(inquire);

        model.addAttribute("myPageInquireListCnt", myPageInquireListCnt);
        model.addAttribute("myPageInquireList", myPageInquireList);
        model.addAttribute("page", page);
        model.addAttribute("user_id", memberJpa.getId());

        return "km/mypage-inquire";

    }

    /**
     * 2023-05-09 조경민
     * 설명 : 마이페이지 내 즐겨찾기 모음 화면 이동
     * */
    @GetMapping("/mypage/favorite")
    public String mypageFavorites(@RequestParam(defaultValue = "tra") String category,
                                  @RequestParam(defaultValue = "1") int page,
                                  @Login2User SessionUser sessionUser,
                                  Model model){
        // 해당 카테고리 갯수를 가져오고 페이징 번호처리
        int countFavorite = mypageService.mypageFavoritesPageCount(sessionUser.getId(), category);
        model.addAttribute("totalPage", (countFavorite / 10) +1 );

        // 즐겨찾기 가져오기
        List<MypageFavoriteResponseDto> mypageFavoriteResponseDtos = mypageService.mypageFavorites(sessionUser.getId(), category, page);
        model.addAttribute("favorites",mypageFavoriteResponseDtos);

        return "km/mypage-favorites";
    }

    /**
     * 2023-05-10 조경민
     * 설명 : 마이페이지 즐겨찾기 취소 혹은 재등록
     * Dto를 만들지 않고 받기 위해 Map 사용
     * */
    @PatchMapping("/api/v1/mypage/favorite")
    @ResponseBody
    public String mypageFavoritesUpdate(@RequestBody Map<String, Object> info,
                                        @Login2User SessionUser sessionUser){
        Long id = ((Integer) info.get("id")).longValue();
        String category =(String) info.get("category");
        boolean checked = (boolean) info.get("checked");

        System.out.println("checked = " + checked);
        System.out.println("id = " + id);
        System.out.println("category = " + category);



        int result=0;
        switch (category){
            case "hou":
                Hou_Fav hou_Fav = new Hou_Fav();
                hou_Fav.setMember_id(sessionUser.getId());
                hou_Fav.setHouse_id(id);

                result = checked ? houseService.insertHouFav(hou_Fav) : houseService.deleteHouFav(hou_Fav);
                break;
            case "tra":
                Tra_Fav tra_Fav = new Tra_Fav();
                tra_Fav.setMember_id(sessionUser.getId());
                tra_Fav.setTravel_id(id);

                result = checked ? travelService.insertTraFav(tra_Fav) : travelService.deleteTraFav(tra_Fav);
                break;
            case "res":
                Res_Fav res_Fav = new Res_Fav();
                res_Fav.setMember_id(sessionUser.getId());
                res_Fav.setRestaurant_id(id);

                result = checked ? skService.insertResFav(res_Fav) : skService.deleteResFav(res_Fav);
        }

        if (result==0){
            throw new IllegalArgumentException("즐겨찾기 수정 실패");
        }else {
            return "성공";
        }

    }

    @GetMapping("/mypage/tag")
    public String mypageTag(@Login2User SessionUser sessionUser,
                            Model model){
        MypageTagResponseDto mypageTagResponseDto = mypageService.mypageInterest(sessionUser.getId());
        model.addAttribute("interests", mypageTagResponseDto);

        for (Common c : mypageTagResponseDto.getCommonInterests()){
        System.out.println("mypageTagResponseDto = " + c.toString());
        }
        for (Interest i : mypageTagResponseDto.getSavedInterests()) {
            System.out.println("i.toString() = " + i.toString());

        }

        return "km/mypage-tag";
    }

    @PatchMapping("/api/v1/mypage/interest")
    @ResponseBody
    public String mypageInterest(@RequestBody MypageInterestUpdateRequestDto requestDto,
                                 @Login2User SessionUser sessionUser,
                                 Model model){

        requestDto.setId(sessionUser.getId());

        int insertResult = mypageService.mypageInterestUpdate(requestDto);

        return String.valueOf(insertResult);
    }


    // 관리자 페이지----------------------------------------------------------------
    /**
     * 2023-05-01 조경민
     * 설명 : 관리자 페이지 회원 목록 조회, 쿼리 스트링을 이용한 검색기능 추가, Jpa Pageable 이용하여 페이징 처리(page = 0부터 시작)
     *
     * @pageableDefault : 페이지에 출력되는 데이터 사이즈 설정(default = 10, 보여주기위해 입력)
     * Pageable : Jpa에서 제공하는 페이징 인터페이스로 레포지토리 쿼리에 인수로 전해주면 Page 객체를 반환
     * */
    @GetMapping("/admin")
    public String adminMain(@RequestParam(value = "search", required = false) String search,
                            @PageableDefault(size = 10) Pageable pageable,
                            Model model){
        // 현재 페이지 담기
        model.addAttribute("currentPage", pageable.getPageNumber());

        if (search==null){
            // search 값이 null이면 전체 조회
            Page<AdminMemberResponseDto> responseDtos = memberService.adminMemberListShow(pageable);


            // 페이지 전체 갯수 담기
            model.addAttribute("totalPage", responseDtos.getTotalPages());

            // 해당 페이지 멤버 정보 담기
            model.addAttribute("members",responseDtos.getContent());
        }else {
            // search값 존재하면 검색어 조회
            Page<AdminMemberResponseDto> responseDtos = memberService.adminMemberSearchAndListShow(search, pageable);

            // 페이지 전체 갯수 담기
            model.addAttribute("totalPage", responseDtos.getTotalPages());

            // 해당 페이지 멤버정보 담기
            model.addAttribute("members", responseDtos.getContent());
        }

        return "km/admin-main";
    }


    /**
     * 2023-05-02 조경민
     * 설명 : 관리자 페이지 회원 권한 변경
     * */
    @PatchMapping("/api/v1/admin/role")
    @ResponseBody
    public Long adminRoleChange(@RequestBody AdminMemberRoleRequestDto requestDto){

        System.out.println("requestDto = " + requestDto.getRole());
        System.out.println("requestDto.getId() = " + requestDto.getId());
        // 회원 권한 변경 후 아이디 반환
        return memberService.adminMemberRoleChange(requestDto);
    }

    /**
     * 2023-05-05 조경민
     * 설명 : 관리자 페이지 회원 정보 변경창 이동
     * */
    @GetMapping("/admin/info/{id}")
    public String adminInfo(@PathVariable Long id, Model model){

        model.addAttribute("member", memberService.adminMemberInfoById(id));

        return "km/admin-info";
    }

    /**
     * 2023-05-02 조경민
     * 설명 : 관리자 회원 정보 변경
     * */
    @PatchMapping("/api/v1/admin/info")
    @ResponseBody
    public Long adminInfoChange(@RequestPart(value = "file", required = false) List<MultipartFile> file,
                                @RequestPart(value = "key") AdminMemberInfoDto requestDto) throws Exception {

        return memberService.adminMemberinfoChange(file,requestDto);
    }



}



