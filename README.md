# Uniminuto_IndoorsMap

1Commit - 2017.07.26
1. 아이콘변경
2. CircleImageView 만들기 실패
3. 로딩화면-> nia,worldfriends,단국, 연세(단국 연세는 빼야할지고민좀)
4. 메인화면 -> Home,Map,Mypage,Setting->변경가능
5. Map-> 맵불러오기까지했음 -> 레이아웃변경요 -> intent로 인해 기존레이아웃이 사라지므로 1. 새로운액티비티를 통해 하거나
                                                                                   2. 인텐트 사용x



2Commit - 2017.07.27
1. Setting화면 프로토타입만듬
2. SQLite 사용예정-> 최근검색어
3. 그래서 검색기능구현해야함
4. nia->kiv로  이미지 변경
5. 네비게이션 내위치 보는 기능 확인



3Commmit - 2017.07.28
1. Credit화면 프로토타입만듬
2. FrontEnd 잔버그 수정




4Commit - 2017.07.31
1. MapActivity에서 전체화면에 지도가 나오는 것을 Container에 넣음



5Commit- 2017.07.31
1. Map 프론트엔드 손질
2. Back 버튼 구현 


6Commit - 2017.08.02
1. 프론트엔드 뒷배경에 Uniminuto 추가
2. 애매한 글씨 색 검정색으로 변경
3. 불투명한 이미지 투명화 (연세대 , 월드프렌즈)



7Commit - 2017.08.02
1. SettingActivity에서 GPS Setting버튼 클릭시, GPS설정화면으로 전환기능 구현



8Commit - 2017.08.03
1. SettingActivity에서 진동On/Off기능 구현
2. Kalman and Stabilisation Filter를 사용하여 Map에서 위치정확성을 높임


9Commit - 2017.08.08

1. Map 화면에서 텍스트를 통해 사용자의 현재 층수와 위치를 나타내도록 수정.

10Commit - 17.08.10
1. 로그인 화면 추가 
2. ID = Uniminuto
   PW = 1234
를 이용하여 로그인할 수 있게함.
3. Uniminuto 로고 이미지 변경.

11Commit - 17.08.10
1.  아미고 로고 이미지 생성
2. credit에 아미고 로고 추가
3. menu구성 Mypage -> setting
                 setting ->credit
으로 변경



12Commit - 17.08.16

1. 터치한 곳의 좌표를 얻어올 수 있도록 설정.
2. Map을 길게 클릭했을 시 경로를 설정할 수 잇도록 구현
-> LongClickListener사용
-> 팝업창을 통해 경로설정여부를 물어봄
3. 경로 설정했을 시 x 표시를 통해 취소할 수 잇도록 구현.
4. cancle 이미지 추가
5. 경로 구현 부분에서 벽의 위치가 잘못설정되어서 경로가 이상하게 설정됨을 확인.



13Commit - 17.08.17
(Espanol patch)
1. 50%-> 가이드라인을 따라서 구현했으나 벽의 위치를 제대로 인식하지 못하여 경로가 이상하게 지정됨 (Map에서 벽의 위치를 제대로 설정하지 않았을 거라 예상되서 물어볼예정)

2.완료 -> 원하는 아이디와 비밀번호를 요청할 예정

3.완료 -> 하단에 층수 출력하게 만듦

4. 완료 -> But gps의 부정확성으로 인해서 위치가 변경됨 (현지에서 비콘을 제공해서 정확해질 예정)

5.0% -> 시간부족할듯



14Commit - 17.08.18
1. 버튼 색상 및 이미지 수정(button_notibar_didit.xml 이용)
2. menual화면을 통해 대략적인 앱 설명 넣음
3. credit화면을 menual안으로 넣음



15Commit - 17.08.18
1. MapActivity에서 search버튼 클릭시, ZoneListActivity로 전환하여 ZoneList가 나열되도록함.



16Commit_17.08.18
1. ZoneList에서 원하는 장소 클릭 시, 그곳으로 경로가 설정됨. (그런데 잘안됨. 아직 미완)
