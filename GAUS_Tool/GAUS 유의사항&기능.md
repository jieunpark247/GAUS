## 설치 순서

- jdk 1.8.0 설치 (jre 1.8.0)

- adb 경로 설정 및 환경 변수 설정

- GAUS실행



## GAUS 실행 유의사항

1. **jre 1.8.0** 버전이 있어야하고 / jdk,jre 환경변수가 설정되어있어야 함
2. C:\ 경로에 **GAUS폴더** 형태로 풀기(C:\GAUS\....)
3. GAUS폴더에 있는 platform-tools-latest-windows폴더(adb)로 **기본설정** 되어있는 경로로 설정 
    <u>or</u>  **path setting**(버튼)에서 경로설정 수정
4. 앱을 실행하려면 adb 환경변수 설정이 되어 있어야함 
5. RPA 실행시 image 캡쳐, answer.csv 저장될 위치 등 수정 



## GAUS 기능 설명

![기능 설명](C:\GAUS\_logo\기능 설명.png)



#### Buddy log 설정 설명 

1. 버디 연결후 개발자모드로 진입 

2. step2 버튼 클릭시 서버 기동

3. 버튼클릭 후 뜬 cmd 창에 start 버튼 직접 입력 

4. cmd가 실행되고 있으면 step4 버튼 눌러서 txt파일 형태로 로그 확인  ( cmd 창 종료 x )

   *** ctrl s : stop  / ctrl q  : restart