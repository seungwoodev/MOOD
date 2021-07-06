# MOOD
**KAIST MADCAMP CS496 Project1**

**+) apk 파일 위치 : app/releases/app-release.apk**




## 팀원 : 금나연, 추승우




## Development Environment

### *Android Studio 4.1.2*

  * compileSDKversion : 30
  * buildToolsversion : 30.0.3
  * SDK 16(API Level 29) 기준 호환






## **App Logo**
<img src="https://user-images.githubusercontent.com/68985625/124529645-fe91a680-de45-11eb-9b88-8490d8b4d77e.jpg"/>


# 
## **Introduction**


* 컴팩트한 앱
- 연락처 및 갤러리 연동
- 위치, 날씨 기반 음악 추천 및 유튜브 연동





## **실행 화면**

### 
### - 시작 화면
<img src="https://user-images.githubusercontent.com/68985625/124556474-47ac1f80-de73-11eb-8aae-cb06a952454c.jpg"/>
- splash로 로고를 띄운다.

### 
### - 연락처
<img width=30% alt="contact" src="https://user-images.githubusercontent.com/68985625/124556328-1fbcbc00-de73-11eb-9f1c-450ed4f8e754.png">   <img width=30% alt="contact_add" src="https://user-images.githubusercontent.com/68985625/124556335-221f1600-de73-11eb-9b72-0b53f9a988d7.png">
- 이름순으로 정렬된 연락처를 recyclerview를 통해 보여준다.
- 우측 하단의 floatingbutton을 클릭하면 새로운 연락처의 이름, 전화번호, 이메일을 추가하여 local의 연락처 추가로 연결한다.


<img width=30% alt="contact_info" src="https://user-images.githubusercontent.com/68985625/124556345-24817000-de73-11eb-889c-85c146bfda6d.png">   <img width=30% alt="contact_call" src="https://user-images.githubusercontent.com/68985625/124556342-23504300-de73-11eb-8dee-7f489a9e5ccf.png">   <img width=30% alt="contact_text" src="https://user-images.githubusercontent.com/68985625/124556352-251a0680-de73-11eb-9dd8-517e0f3ea019.png">
- 연락처 클릭 시 intent를 이용하여 세부 정보가 있는 activity로 들어온다.
- 전화 버튼을 누르면 전화로, 문자 버튼을 누르면 문자로 연결된다.


### 
### - 갤러리
#### 기능 - 구글포토에서 사진 선택, 저장된 사진 확대
#### recycler_view & grid_manager 사용


<img width=30% alt="gallery" src="https://user-images.githubusercontent.com/68985625/124556353-25b29d00-de73-11eb-8496-351ff22615ee.png">   <img width=30% alt="gallery_in" src="https://user-images.githubusercontent.com/68985625/124556379-2d724180-de73-11eb-9eec-49ff9b08bb6e.png">   <img width=30% alt="gallery_in2" src="https://user-images.githubusercontent.com/68985625/124556410-3400b900-de73-11eb-861b-b011fd787f8e.png">
- 갤러리 탭으로 들어오면 하단의 이미지 선택 버튼을 통해 local gallery에 접근한다.



<img width=30% alt="gallery_select" src="https://user-images.githubusercontent.com/68985625/124556412-3531e600-de73-11eb-9d0e-ea2f9df97d20.png">   <img width=30% alt="gallery_show" src="https://user-images.githubusercontent.com/68985625/124556433-3a8f3080-de73-11eb-90b7-1fafb47dded9.png">   <img width=30% alt="gallery_click" src="https://user-images.githubusercontent.com/68985625/124556354-264b3380-de73-11eb-9ee7-46dd527ec48f.png">
- 원하는 이미지를 다중 선택한 뒤, 상단의 Done 버튼을 클릭한다.
- 이후 Toast와 함께 선택한 이미지들이 GridView로 보여진다.


### 
### - 음악
#### 기능 - 현재 위치 및 날씨 표시, 유튜브 음악 추천 및 썸네일 표시, 해당 화면 실행
#### search keyword = weather_description + "weather music"
<img width=30% alt="music" src="https://user-images.githubusercontent.com/68985625/124564584-2c91dd80-de7c-11eb-91a5-2b738855c9c7.png">  <img width=30% alt="music_show" src="https://user-images.githubusercontent.com/68985625/124564594-2ef43780-de7c-11eb-92bb-46bd1621daf7.png">  <img width=30% alt="music_connect" src="https://user-images.githubusercontent.com/68985625/124564589-2dc30a80-de7c-11eb-9e62-e9d2f1543ab7.png">
- 하단의 음악 추천 버튼을 클릭하면 GPS tracker & OpenWeather API, action_view를 사용하여 현재 위치와 날씨가 나타나고, 날씨에 해당하는 이미지 또한 나타난다.
- 버튼의 위에는 날씨에 맞는 유투브 썸네일이 보여지며, 하단의 유투브로 이동 버튼을 클릭하면 intent를 통해 Youtube에서 해당 동영상이 열린다.


## 
## **실행 gif**

./시연영상.mp4
  
