package com.travelAlone.s20230404.service.si;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.travelAlone.s20230404.dao.si.SiDao;
import com.travelAlone.s20230404.model.Board;
import com.travelAlone.s20230404.model.House;
import com.travelAlone.s20230404.model.Res;
import com.travelAlone.s20230404.model.Travel;
import com.travelAlone.s20230404.model.si.ResultList;
import com.travelAlone.s20230404.model.si.Search;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiServiceImpl implements SiService {
	
	private final Logger logger = LoggerFactory.getLogger(SiService.class);
	private final SiDao siDao;
	
	//검색 시 선택한 카테고리에 따라 DB 데이터에  검색 키워드가 존재하는지 조회
	@Override
	public ResultList search(String keyword, String category) {
		
		logger.info("siServiceImpl search start");
		ResultList resultList = new ResultList();
		
		  //카테고리가 전체인 경우
		  if(category.equals("category_total")) {
			  resultList.setTravelList(siDao.travelSearch(keyword));
			  resultList.setHouseList(siDao.houseSearch(keyword));
			  resultList.setRestaurantList(siDao.resSearch(keyword));
			  resultList.setBoardList(siDao.boardSearch(keyword));
		  
		  //카테고리가 여행지인 경우	  
		  } else if(category.equals("category_travel")) {
			  resultList.setTravelList(siDao.travelSearch(keyword));
		  
		  //카테고리가 숙소인 경우			  
		  } else if(category.equals("category_house")) {
			  resultList.setHouseList(siDao.houseSearch(keyword));
		  
		  //카테고리가 맛집인 경우	  
		  } else if(category.equals("category_res")) {
			  resultList.setRestaurantList(siDao.resSearch(keyword));
		  
		  //카테고리가 커뮤니티인 경우		  
		  } else if(category.equals("category_comm")) {
			  logger.info("siServiceImpl에서 category_comm인 경우 search");
			  resultList.setBoardList(siDao.boardSearch(keyword));
	      }
		  
		 return resultList;
		 
	}
	
	
	//검색 키워드가 Search 테이블에 있는지 조회한 뒤 있으면 update, 없으면 insert
	@Override
	public void upsertSearch(String keyword) {
		logger.info("siServiceImpl upsertSearch Start");
		siDao.upsertSearch(keyword);
		
	}

	//일간 인기 검색어 구하기
	@Override
	public List<String> getDailyPopularSearches() {
		logger.info("siServiceImpl getDailyPopularSearches Start");
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime start = now.with(LocalTime.MIN); //2023-04-27T00:00
		LocalDateTime end = now.with(LocalTime.MAX);   //2023-04-27T23:59:59.99999999
		
		ZonedDateTime utcStart = start.atZone(ZoneOffset.UTC);
		ZonedDateTime utcEnd = end.atZone(ZoneOffset.UTC);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String startOfToday = utcStart.format(formatter);   //2023-04-27 00:00:00
		String endOfToday = utcEnd.format(formatter);       //2023-04-27 23:59:59
		 
		
		List<Search> dailySearches = siDao.getDailyPopularSearches(startOfToday, endOfToday);
		
		return dailySearches.stream().map(Search::getS_keyword).collect(Collectors.toList());		
	}


	//주간 인기 검색어 구하기
	@Override
	public List<String> getWeeklyPopularSearches() {
		logger.info("siServiceImpl getWeeklyPopularSearches Start");
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime start = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).with(LocalTime.MIN);   //2023-04-24T00:00
		LocalDateTime end = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).with(LocalTime.MAX);         //2023-04-30T23:59:59.999999999
		
		ZonedDateTime utcStart = start.atZone(ZoneOffset.UTC);
		ZonedDateTime utcEnd = end.atZone(ZoneOffset.UTC);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String startOfWeek = utcStart.format(formatter);   //2023-04-24 00:00:00
		String endOfWeek = utcEnd.format(formatter);       //2023-04-30 23:59:59
		
		List<Search> weeklySearches = siDao.getWeeklyPopularSearches(startOfWeek, endOfWeek);
		
		return weeklySearches.stream().map(Search::getS_keyword).collect(Collectors.toList());
	}


	//월간 인기 검색어 구하기
	@Override
	public List<String> getMonthlyPopularSearches() {
		logger.info("siServiceImpl getMonthlyPopularSearches Start");
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime start = now.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);   //2023-04-01T00:00
		LocalDateTime end = now.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);      //2023-04-30T23:59:59.999999999
		
		ZonedDateTime utcStart = start.atZone(ZoneOffset.UTC);
		ZonedDateTime utcEnd = end.atZone(ZoneOffset.UTC);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String startOfMonth = utcStart.format(formatter);   //2023-04-01 00:00:00
		String endOfMonth = utcEnd.format(formatter);       //2023-04-30 23:59:59
		
		List<Search> monthlySearches = siDao.getMonthlyPopularSearches(startOfMonth, endOfMonth);
		
		return monthlySearches.stream().map(Search::getS_keyword).collect(Collectors.toList());
		
	}


	


	
		
	
	

}