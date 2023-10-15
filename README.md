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

# 피드백
- DiaryException 클래스의 errorCode, errorMessage는 생성자에서 초기화된 이후 변경사항이 없기 때문에, final로 처리

- DiaryService 클래스의 로그가 MyWeatherApplication 로 되어 있음. 이부분 DiaryService 로 수정 <br>
private static final Logger logger = LoggerFactory.getLogger(MyWeatherApplication.class); <br>
==> <br>
private static final Logger logger = LoggerFactory.getLogger(DiaryService.class);

- GlobalExceptionHandler 에서 System.out.println로 화면에 로그를 출력하기 보다는 로거를 이용해서 화면에 출력하도록 하는것이 좋다.

- 예외처리 핸들러 구현에서 예외처리에 있어서, 그냥 Exception을 다시 발생시키기 보다는, 이곳에서 예외에 대한 처리가 마무리 되는 형태로 작성하는 것이 좋다.

- 사용하지 않는 import문, 주석, 함수, 변수, 클래스등은 제거 !!

- openapi를 통해서 날씨를 가져오는 부분은 DiaryService에 있기 보다는, 따로 서비스 클래스를 만들어서 처리하는 것이 더 좋다.

- 서비스 부분은 인터페이스와 구현체를 따로 작성해 주는 것이 더 좋다.

- 스케쥴 관련 처리에 있어도, 스케쥴이 호출되는 부분은 따로 클래스로 작성하고, 이곳에서 관련 기능들에 대한 클래스나 함수를 호출하도록 처리하는 것이 더 좋다.

- DiaryService 클래스의 parseWeather 함수 구현에 있어서, get으로 특정 값을 가져오기 전에 키가 존재하는지를 체크하던지, 아니면 가져온 값이 널이 아닌지를 체크하는 방어 로직이 있어야 할 것 같다.
