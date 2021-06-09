# Celebrity Quiz
- 강의명 : 2021학년도 1학기 모바일 앱 프로그래밍
- 과제 내용 : 기존 Celebrity Quiz 게임에서의 기능 추가
- 팀명 : Three Amigos
  - [이주형](https://github.com/yamiblack)
  - [정보균](https://github.com/jeongbogyun)
  - [푼치얀](https://github.com/CHEEYAN) 
</br>

## 기능 추가 내용
### 1. 'Main Page' 구현

<p align="center"><img width="300" src="https://user-images.githubusercontent.com/50551349/121319336-12311680-c947-11eb-9b57-d56e97a9bedb.jpg"/> </p>

- Main Page에는 ‘게임 시작’ 버튼, ‘글로벌 랭킹’ 버튼, ‘마이 페이지’ 버튼, ‘오답노트’ 버튼, 그리고 ‘게임 설정’ 버튼이 존재한다. 해당 버튼들을 터치하게 되면 해당 페이지로 이동한다.
</br>

### 2. 'Setting' 구현

<p align="center"><img width="300" src="https://user-images.githubusercontent.com/50551349/121319609-5b816600-c947-11eb-86c3-ccb4546e5059.jpg"/> </p>

- Setting에서는 게임의 난이도, 시간, 그리고 유형을 선택할 수 있다. 또한, 배경음악을 키고 끄는 기능과 로그아웃을 할 수 있도록 설정했다. 
</br>

### 3. '다양한 문제 추가' 구현

<p align="center"><img src="https://user-images.githubusercontent.com/50551349/121319878-98e5f380-c947-11eb-9498-54e946309ff0.png" /> <img src="https://user-images.githubusercontent.com/50551349/121320028-c03cc080-c947-11eb-8935-a15adf5f12a4.png"/> </p>

- 위 그림과 같이 인터넷에서 다양한 이미지를 찾아 저장했다. 이러한 이미지들을 활용하여 [그림 3.8]과 같이 [JSONBIN.io](https://jsonbin.io/)에서 2개의 JSON을 생성했다.
  - https://api.jsonbin.io/b/607805a35b165e19f62090f3/4
  - https://api.jsonbin.io/b/607864715b165e19f620c32b

</br>

### 4. '점수 기록 화면 및 기능' 구현

<p align="center"> <img src="https://user-images.githubusercontent.com/50551349/121320584-422ce980-c948-11eb-9846-8b580c322496.jpg" width="300"/>
 <img src="https://user-images.githubusercontent.com/50551349/121320599-4527da00-c948-11eb-91df-45c52159acbf.jpg" width="300"/> </p>

- 점수 기록 화면은 위 그림과 같이 ‘글로벌 랭킹’과 ‘마이페이지’에서 확인할 수 있다. 게임이 종료될 때 마다 게임 기록은 Database에 저장된다. 저장된 기록을 읽어 ‘글로벌 랭킹’과 ‘마이페이지’에 표시된다.
</br>

### 5. 'Firebase 활용 회원가입 및 로그인' 구현

<p align="center"> <img src="https://user-images.githubusercontent.com/50551349/121321058-bbc4d780-c948-11eb-8546-540f05b992e0.jpg" width="300"/>
 <img src="https://user-images.githubusercontent.com/50551349/121321073-be273180-c948-11eb-89a6-b6a750c2805f.jpg" width="300"/> </p>

- Firebase 활용 회원가입 및 로그인 화면은 위 그림과 같다.

<p align="center"> <img src="https://user-images.githubusercontent.com/50551349/121321577-2fff7b00-c949-11eb-9c97-f23e9b856cf6.jpg" width="300"/>
 <img src="https://user-images.githubusercontent.com/50551349/121321592-33930200-c949-11eb-9b5a-ed15f60bfee7.jpg" width="300"/> </p>

- 좌측은 로그인 오류 화면을, 우측은 로그인 성공 화면을 나타낸다. 

<p align="center"> 
 <img src="https://user-images.githubusercontent.com/50551349/121321895-794fca80-c949-11eb-8ae7-4ad9a1aae700.jpg" width="300"/>
 <img src="https://user-images.githubusercontent.com/50551349/121321911-7c4abb00-c949-11eb-990a-37a532a3fddd.jpg" width="300"/>  </p>
 
- 좌측은 회원가입 오류 화면을, 우측은 회원가입 성공 화면을 나타낸다. 
</br>

### 6. 'Firebase 활용 Global Ranking' 구현

<p align="center"> 
 <img src="https://user-images.githubusercontent.com/50551349/121322392-e2374280-c949-11eb-945c-3c03f7543dd4.jpg" width="300"/>
 <img src="https://user-images.githubusercontent.com/50551349/121322407-e5323300-c949-11eb-984b-8049225828cd.jpg" width="300"/>  </p>

- 각 게임이 종료되면 점수와 시간은 Database에 저장된다. 이에 대한 데이터를 바탕으로 위 그림과 같이 글로벌 랭킹을 나타낼 수 있다. 
- Database는 Firebase의 Firestore Database를 활용했다. 
</br>

### 7. '낱말 퀴즈' 구현

<p align="center"> 
 <img src="https://user-images.githubusercontent.com/50551349/121322817-435f1600-c94a-11eb-903f-6fd3d51e3e60.jpg" width="300"/> </p>

- 기존의 객관식 Quiz 유형에서 위 그림과 같이 낱말 퀴즈의 유형도 추가했다.

### 8. '틀린 문제 DB 저장 및 조회' 구현

<p align="center"> 
 <img src="https://user-images.githubusercontent.com/50551349/121323223-9cc74500-c94a-11eb-8dec-9dc283338b51.jpg" width="300"/> </p>

- 각 게임이 종료되면 틀린 문제들은 Database에 저장된다. 이에 대한 데이터를 바탕으로 위 그림과 같이 오답노트를 나타낼 수 있다. 
</br>

### 9. '목숨 추가' 구현

<p align="center"> 
 <img src="https://user-images.githubusercontent.com/50551349/121323511-d7c97880-c94a-11eb-9e6d-129451d2f1c2.jpg" width="300"/> </p>

- 게임을 진행하는데 위 그림과 같이 총 3개의 목숨을 하트로 표시하고 선택한 답이 틀릴 경우, 하트가 빈 하트로 표시가 된다.
- 하트가 차감 될 때 마다 움직이는 모션을 주기 위해 anim 폴더를 생성한 후에 0.5초 안에 3번 양쪽으로 움직이게 하는 애니메이션을 생성했다.
</br>

### 10. 'Service 활용 배경음악' 구현

<p align="center"><img width="300" src="https://user-images.githubusercontent.com/50551349/121319609-5b816600-c947-11eb-86c3-ccb4546e5059.jpg"/> </p>

- 배경음악은 '게임 설정'에서 크고 끌 수 있다. 
</br>

### 11. 'My Page' 구현

<p align="center"><img width="300" src="https://user-images.githubusercontent.com/50551349/121324272-88d01300-c94b-11eb-895c-11f00fb9bf7c.jpg"/> </p>

- ‘My Page’에서는 위 그림과 같이 각 난이도 별로 최고 점수와 평균 점수를 나타낸다. 글로벌 랭킹과 마찬가지로 난이도별 점수를 나타내기 위해서 TabLayout과 ViewPager를 활용했다. 각각에 대한 정보를 나타내기 위해 글로벌 랭킹에서 사용된 Ranking의 Database를 활용했다. 사용자의 이메일과 게임 난이도를 비교하여 올바른 정보를 나타냈다. 
</br>

### 12. '효과음' 구현
- 게임에서 버튼을 누르면 각 상황에 맞는 효과음이 발생한다. 
- SoundPool을 활용했다.
</br>

## 추후 보완 내용(예정)
- 새로운 오류 및 버그 수정
- 새롭고 다양한 문제 세트 추가
- 실시간 대결 방식과 같은 새로운 게임 유형 추가
</br> 


## 사용 기술 스택
- Android(Java)
- Firebase
