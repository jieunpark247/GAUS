package com.glab.app.controller;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import com.glab.app.service.CmdService;
import com.glab.app.service.FileService;
import com.glab.app.service.ParsingService;

public class AdbController {
	private String result = null;
	private String cdPath = "";
	private String selectedIP = "";
	CmdService cs = new CmdService();
	FileService fwa = new FileService();
	ParsingService ps = new ParsingService();
	
	/**
	 * adb경로를 입력받아 전역변수 path 설정
	 * @param adbPath
	 * @return
	 */
	public String setAdbPath(String adbPath) {
		result = null;
		cdPath = "cd " + adbPath + "/ && ";
		return "수정된 cd path " + cdPath;
	}
	
	/**
	 * 특정 ip 선택해서 실행 
	 * @param oneIP
	 * @return
	 */
	public String setOneIP(String oneIP) {
		result = null;
		selectedIP = oneIP;
		System.out.println("selected IP  " + selectedIP);
		return selectedIP;
	}

	
	/**
	 * ip를 입력받아 adb connect 수행
	 * @param input : path,ip
	 * @return connected to <host>[:<port>]
	 */
	public String adbConnect(String input) {
		result = null;
		String[] array = input.split(",");
		String path = array[0]+"/";
		String ip = array[1];

		if (path.length() > 0) { // path경로가 입력된 경우
			cdPath = "cd " + path + " && ";
		} else {
			//로컬 사용
		}
		String command = cs.inputCommand(cdPath + " adb connect " + ip);
		result = cs.execCommand(command);
		return result;
	}

	
	/**
	 * adb device 수행
	 * @return List if devices attached [<ip> device]*
	 */
	public String adbDevices() {
		result = null;
		String command = cs.inputCommand(cdPath + "adb devices");
		result = cs.execCommand(command);
		return result;
	}

	
	/**
	 * adb에 연결된 기기의 모델명 반환
	 * @return 모델명
	 */
	public String modelInfo(String deviceIP) {
		result = null;
		String command = cs.inputCommand(cdPath +   "adb " + deviceIP+ " shell getprop ro.product.model");
		result = cs.execCommand(command);
		return result;
	}
		
	
	/**
	 * adb 연결 해제
	 * @return disconnected <ip> || disconnected erverything
	 */
	public String disConnect() {
		result = null;
		String command = cs.inputCommand(cdPath + "adb disconnect");
		result = cs.execCommand(command);
		return result;
	}
	

	public String disConnectOneDevice(String deviceIP) {
		result = null;
		String command = cs.inputCommand(cdPath + "adb disconnect " + deviceIP);
		result = cs.execCommand(command);
		return result;
	}

	
	/**
	 * 개발자 모드 실행
	 * @param temp : 사용안함
	 * @return Broadcasting .......
	 */
	public String devMode() {
		result = null;
		String command = cs.inputCommand(cdPath
				+ "adb" + selectedIP + " shell am broadcast -a \"kt.action.container.devmode.req\" --ei \"devmodeState\" 1 --ei \"pwrState\" 0 --es \"userKey\" \"UNKNOWN\" --es \"uword\" \"개발자모드\"");
		result = cs.execCommand(command);
		return result;
	}
	
	/**
	 * 개발자 모드 해제
	 * @param temp : 사용안함
	 * @return Broadcasting .......
	 */
	public String devModeOff() {
			result = null;
			String command = cs
					.inputCommand(cdPath + "adb" + selectedIP+ " shell am broadcast -a \"kt.action.voicecommand.asr\" --es \"kwsText\" \""
							+ "개발자모드\\ 해제" + "\" --es \"gender\" \"0\" --ei \"resultCode\" 0");
			result = cs.execCommand(command);
			return result;

	}

	
	/**
	 * 앱아이디와 인텐트 정보를 받아와 기가지니 음성으로 해당 동작 수행
	 * @param input : appId,intent 정보
	 * @return
	 */
	public String callIntent(String input) {
		result = null;
		String[] array = input.split(",");
		String appId = array[0];
		String intent = array[1];
		String command = cs.inputCommand(cdPath
				+ "adb" + selectedIP+ " shell am broadcast -a \"kt.action.container.service.req\" --ei \"pwrState\" 0 --es \"userKey\" \"UNKNOWN\" --es \"appId\" \""
				+ appId + "\" --es \"actionCode\" \"" + intent + "\"");
		result = cs.execCommand(command);
		return result;
	}

	
	public String cmsInput(String input) {
		result = null;
		String command = cs
				.inputCommand(cdPath + "adb" + selectedIP+ " shell input keyevent KEYCODE_DPAD_RIGHT && adb" + selectedIP+ " shell input text " + input);
		result = cs.execCommand(command);
		return result;
	}

	
	public String cmsCall() {
		result = null;
		String command = cs
				.inputCommand(cdPath + "adb" + selectedIP+ " shell input keyevent KEYCODE_DPAD_DOWN && adb" + selectedIP+ " shell input press");
		result = cs.execCommand(command);
		return result;
	}
	
	public String micttsCall(String input) {
		result = null;
		String command = cs
				.inputCommand(cdPath + "adb" + selectedIP+ " shell am broadcast -a \"kt.action.remocon.voice.ready\" ");
		result = cs.execCommand(command);
		
		
		String command2 = cs
				.inputCommand(cdPath + "adb" + selectedIP+ " shell am broadcast -a \"kt.action.voicecommand.asr\" --es \"kwsText\" \""
						+ input + "\" --es \"gender\" \"0\" --ei \"resultCode\" 0");
		result = cs.execCommand(command2);
		return result;
	}

	
	/**
	 * input데이터를 받아 기가지니 음성명령어로 실행
	 * @param input
	 * @return
	 */
	public String tts(String input) {
		result = null;
		String command = cs
				.inputCommand(cdPath + "adb" + selectedIP+ " shell am broadcast -a \"kt.action.voicecommand.asr\" --es \"kwsText\" \""
						+ input + "\" --es \"gender\" \"0\" --ei \"resultCode\" 0");
		result = cs.execCommand(command);
		return result;
	}

	
	/**
	 * 연결된 디바이스 화면에 input을 텍스트로 입력
	 * @param input
	 * @return
	 */
	public String textInput(String input) {
		result = null;
		String command = cs.inputCommand(cdPath + "adb" + selectedIP+ " shell input text " + input);
		result = cs.execCommand(command);
		return result;
	}

	
	/**
	 * 연결돤 디바이스 화면의 텍스트 입력값 삭제
	 * @param temp
	 * @return
	 */
	public String textClear() {
		result = null;
		String command = cs.inputCommand(cdPath
				+ "adb" + selectedIP+ " shell input keyevent KEYCODE_DPAD_DOWN && adb" + selectedIP+ " shell input keyevent KEYCODE_DPAD_UP && adb" + selectedIP+ " shell input keyevent KEYCODE_DEL");
		result = cs.execCommand(command);
		return result;
	}

	
	/**
	 * 연결된 디바이스의 package 목록 로드하여 반환
	 * @return
	 */
	public ArrayList<String> loadPackage() {
		ArrayList<String> p = null;
		String command = cs.inputCommand(cdPath + "adb" + selectedIP + " shell pm list packages");
		System.out.println(command);
		p = cs.readPackage(command);
		return p;
	}

	
	/**
	 * package 이름을 받아 =가 들어가는 정보 반환
	 * @param packName
	 * @return
	 */
	public ArrayList<String> dumpSysPackage(String packName) {
		ArrayList<String> info = null;
		String command = cs.inputCommand(cdPath + "adb" + selectedIP+ " shell dumpsys package " + packName + " | find \"=\"");
		System.out.println(command);
		info = cs.dumpSys(command);

		return info;
	}

	public ArrayList<String> searchPackage(String packName) {
		ArrayList<String> info = null;
		String command = cs.inputCommand(cdPath + "adb" + selectedIP+ " shell pm list packages | find \""+ packName+ "\"");
		System.out.println(command);
		info = cs.readPackage(command);
		return info;
	}

	
	/**
	 * 연결된 디바이스의 화면을 캡쳐
	 * @param input : 사용안함
	 * @return
	 * @throws UnknownHostException
	 */
	public String screenShot(String input) throws UnknownHostException {
		result = null;
		String filename = "screenshot_" + getCurrentDate("yyyyMMddHHmmss") + ".png"; // 현재 시간으로 파일명 설정

		String dir = "C:/Users/" + System.getProperty("user.name") + "/Downloads/";
		if (input.length() > 2) {
			dir = input+"/";
		}
		System.out.println("screenShot() - " + dir);
		String command = cs.inputCommand(cdPath + "adb" + selectedIP+ " shell screencap /sdcard/" + filename);
		result = cs.execCommand(command); // 디바이스 화면 캡쳐
		String command2 = cs.inputCommand(cdPath + "adb" + selectedIP+ " pull /sdcard/" + filename + " " + dir + filename);
		result += cs.execCommand(command2); // 디바이스 캡쳐 파일 로컬로 가져오기
		String command3 = cs.inputCommand(cdPath + "adb" + selectedIP+ " shell rm /sdcard/" + filename);
		result += cs.execCommand(command3); // 디바이스에 저장된 캡쳐파일 삭제
		System.out.println(dir + filename);
		return dir + filename;
	}

	
	/**
	 * adb 키 이벤트 기능수행
	 * @param key
	 * @return
	 * @throws UnknownHostException
	 */
	public String keyEvent(String key) throws UnknownHostException {
		String keycode = "";
		if (key.equals("upBtn")) {
			keycode = "KEYCODE_DPAD_UP";
		} else if (key.equals("leftBtn")) {
			keycode = "KEYCODE_DPAD_LEFT";
		} else if (key.equals("rightBtn")) {
			keycode = "KEYCODE_DPAD_RIGHT";
		} else if (key.equals("downBtn")) {
			keycode = "KEYCODE_DPAD_DOWN";
		} else if (key.equals("backBtn")) {
			keycode = "	KEYCODE_BACK";
		} else if (key.equals("pressBtn")) {
			String command = cs.inputCommand(cdPath + "adb" + selectedIP+ " shell input press");
			result = cs.execCommand(command);
			System.out.println(result + key);
			return result;
		}
		String command = cs.inputCommand(cdPath + "adb" + selectedIP+ " shell input keyevent " + keycode);
		result = cs.execCommand(command);
		return result;
	}

	
	/**
	 * 연결된 디바이스의 logcat 출력
	 * @param logPath
	 * @return
	 */
	public String logPrint(String logPath) {
		result = null;
		System.out.println("logPrint() - " + cdPath + " logpath 는" + logPath);

		String command = cs.inputCommand(cdPath + "adb" + selectedIP+ " logcat -d -v time > " + logPath +"/"+ "genie_log_write.txt");
		/**
		 * logcat 명령어 -d : 로그를 화면에 출력하고 종료 -v <format> : 특정 메타데이터 필드를 선택하면 로그 메시지 출력 가능
		 * ㄴ time : 로그 메시지의 날짜, 호출시간, 우선순위/태그와 메시지가 발생한 PID 출력
		 */
		result = cs.execCommand(command);

		return result;
	}

	
	/**
	 * 연결된 디바이스에 저장된 log 기록 clear
	 * @param type
	 * @return
	 */
	public String logDel() {
		result = null;
		System.out.println("logDel() - " + cdPath);
		String command = cs.inputCommand(cdPath + "adb" + selectedIP+ " logcat -c");
		/**
		 * logcat 명령어 -c : 전체 로그를 삭제
		 */
		result = cs.execCommand(command);
		return result;
	}

	
	/**
	 * log.txt 파일 오픈
	 * @param Path : log.txt가 저장된 경로
	 * @return
	 */
	public String logOpen(String Path ) {
		result = null;
		System.out.println("logOpen() - " + Path);
		String command = cs.inputCommand("cd " + Path + " && copy \""+Path+"/genie_log_write.txt\" \""+Path+"/genie_log_read.txt\"" +" && "+ ""+Path+"/genie_log_read.txt");
		result = cs.onlyExecCommand(command);
		return result;
	}

	
	/**
	 * 발화데이터를 받아서 음성결과값(answer)를 반환
	 * @param input
	 * @return
	 */
	public String utter(String input) {
		result = null;
		String command = cs.inputCommand(
				cdPath + "adb" + selectedIP+ " logcat -c && adb" + selectedIP+ " shell am broadcast -a \"kt.action.voicecommand.asr\" --es \"kwsText\" \""
						+ input + "\" --es \"gender\" \"0\" --ei \"resultCode\" 0");
		result = cs.execCmdUtter(command);
		String command2 = cs.inputCommandKo(cdPath + "adb" + selectedIP+ " logcat -d -v time | find \"DSS response:\"");
		result = cs.execCmdUtter(command2);
		System.out.println("==== > " +result);
		String data = null;
		if(result.equals("")) {
			System.out.println("---------------------->");
			return result;
		}else {
			try {
				String[] arr = result.split("DSS response: ");
				data = arr[1]; // 'TTS Data '이후 결과만 반환
			} catch (Exception e) {
				/////// 수정 	
				e.printStackTrace();
			} finally {
				fwa.writerAnswer("answer.csv", data); // 파일에 기록
				System.out.println(data);
				
			}
			
			return data;
		}
		
	}

	
	/**
	 * 답변에 관련된 로그만 출력
	 * @param input
	 * @return
	 */
	public String utterLog(String input) {
		result = null;
		String command2 = cs.inputCommandKo(cdPath + "adb" + selectedIP + " logcat -d -v time | find \"DSS response:\"");
		result = cs.execCmdUtter(command2);
		return result;
	}

	
	/**
	 * keyword를 parsing한 결과 반환
	 * @param packName
	 * @return
	 */
	public ArrayList<String> parsingKeyword(String wordTime) {
		ArrayList<String> word = null;
		try {
			word = ps.parsingKeyword(wordTime);
		} catch (Exception e) {
			e.printStackTrace();
			word.add("검색어 파싱 실패");
		}
		return word;
	}

	/**
	 * 현재 시간을 반환
	 * @param format
	 * @return
	 */
	public static String getCurrentDate(String format) {
		String dtStr = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date dt1 = new Date();
		dtStr = sdf.format(dt1);
		return dtStr;
	}
	
	/*
	 * buddy log 돌리기
	 */
	public String logBuddy(String logPath) {
		result = null;
		System.out.println("logPrint() - " + cdPath + " logpath 는" + logPath);
		result = cs.openCommand(); //.bat 실행
		System.out.println(".bat 파일 실행");
		return result;
	}

	/*
	 * buddy log txt파일로 열기
	 */
	public String logBuddyOpen(String Path) {
		result = null;
		String command = cs.inputCommand(cdPath
				+ "cd " + Path + " && copy \""+Path+"/buddy_log_write.txt\" \""+Path+"/buddy_log_read.txt\"" +" && "+ ""+Path+"/buddy_log_read.txt");
		result = cs.execCommand(command);
		return result;
	}

	
	/**
	 * log.txt 파일 오픈
	 * @param Path : log.txt가 저장된 경로
	 * @return
	 */
	public String devKill() {
		result = null;
		System.out.println("devKill");
		String command = cs.inputCommand(cdPath + "adb kill-server");
		result = cs.execCommand(command);
		return result;
	}

	/*
	 * h2e0l3l9omA 파일 push
	 */
	public String adbPush(String path) {
		result = null;
		System.out.println("adbPush file");
		String command = cs.inputCommand("cd " + path + "/ && type NUL > h2e0l3l9omA && adb push h2e0l3l9omA /tmp");
		result = cs.execCommand(command);
		return result;
	}
	
	/*
	 * adb명령어 입력
	 */
	public String inputAdb(String input) {
		result = null;
		String command = cs.inputCommand(input);
		result = cs.execCommand(command);
		return result;
	}
	
	public String mcConfig() {
		result = null;
		String command = cs.inputCommand(cdPath + "adb" + selectedIP + " shell am broadcast -a kt.action.mc.config ");
		result = cs.execCommand(command);
		return result;
	}
	public String exit() {
		result = null;
		String command = cs
				.inputCommand(cdPath + "adb" + selectedIP+ " shell am broadcast -a \"kt.action.voicecommand.asr\" --es \"kwsText\" \""
						+ "나가기" + "\" --es \"gender\" \"0\" --ei \"resultCode\" 0");
		result = cs.execCommand(command);
		return result;
	}
	
	public String goApp(String logPath) {
		result = null;
		System.out.println("logPrint() - " + cdPath + " logpath 는" + logPath);
		result = cs.appCommand(); //.sh 실행
		System.out.println(".sh 파일 실행");
		return result;
	}
	

}
