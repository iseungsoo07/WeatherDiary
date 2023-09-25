# WeatherDiary
제로베이스 백엔드스쿨 날씨 일기 프로젝트

# 구현 API
- POST /create/diary
  - Parameter : yyyy-MM-dd 형식의 작성할 날짜 date, 작성한 일기 text
  - openweathermap API에서 받아온 날씨 데이터와 함께 DB에 저장

- GET /read/diary
  - Parameter : yyyy-MM-dd 형식의 조회할 날짜 date
  - 해당 날자의 일기를 List 형태로 반환
 
- GET /read/diaries
  - Parameter : yyyy-MM-dd 형식의 시작일 startDate, yyyy-MM-dd 형식의 종료일 endDate
  - startDate부터 endDate까지의 일기를 List 형태로 반환
 
- PUT /update/diary
  - Parameter : yyyy-MM-dd 형식의 수정할 날짜 date, 수정할 새 일기 글 text
  - 해당 날짜의 첫 번째 일기 글을 새로 받아온 일기글로 수정
 
- DELETE /delete/diary
  - Parameter : yyyy-MM-dd 형식의 삭제할 날짜 date
  - 해당 날짜의 모든 일기를 삭제
