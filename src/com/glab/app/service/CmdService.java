package com.glab.app.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class CmdService {
	private Process process;
	private BufferedReader bufferedReader;
	private StringBuffer readBuffer;
	
	ArrayList<String> p = null;
	ArrayList<String> dumpP = null;;
	
	/**
	 * cmd 명령어를 입력받아 buffer에 시스템 명령어 입력
	 * @param cmd
	 * @return
	 */
	public String inputCommand(String cmd) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("cmd.exe ");
		buffer.append("/c ");
		buffer.append(cmd);
		return buffer.toString();
	}
	
	/**
	 * 한글모드 변경 후 cmd 명령어 입력
	 * @param cmd
	 * @return
	 */
	public String inputCommandKo(String cmd) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("cmd.exe ");
		buffer.append("/c ");
		buffer.append("chcp 437& ");				//한글지원모드로 변경
		buffer.append(cmd);
		return buffer.toString();
	}
	

	/**
	 * cmd에서 command 명령을 수행하여 결과 값을 리턴
	 * @params command
	 * @return result
	 */
	public String execCommand(String command) {
		System.out.println(command);//sysout
		process = null;
		bufferedReader = null;
		readBuffer = null;
		try {
			process = Runtime.getRuntime().exec(command);
			
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("UTF-8")));

			String line = null;
			readBuffer = new StringBuffer();

			while ((line = bufferedReader.readLine()) != null) {
				readBuffer.append(line);
				readBuffer.append("\n");
			}

			return readBuffer.toString();

		} catch (Exception e) {
			e.printStackTrace();
			//System.exit(1);			//stream closed되면 exit 처리
		} finally {
            try {
                process.destroy();
                if (bufferedReader != null) bufferedReader.close();
                if (readBuffer != null) bufferedReader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
		return null;
	}
	
	public String onlyExecCommand(String command) {
		System.out.println(command);//sysout
		process = null;
		bufferedReader = null;
		readBuffer = null;
		try {
			process = Runtime.getRuntime().exec(command);
		} catch (Exception e) {
			e.printStackTrace();
			//System.exit(1);			//stream closed되면 exit 처리
		}
		return null;
	}
	
	public String execCmdUtter(String command) {
		System.out.println(command);//sysout
		process = null;
		bufferedReader = null;
		readBuffer = null;
		try {
			process = Runtime.getRuntime().exec(command);
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("UTF-8")));

			String line = null;
			readBuffer = new StringBuffer();
			
			while ((line = bufferedReader.readLine()) != null) {
				if(line.contains("Active")) {		//Active code mode 결과 pass
					continue;
				}

				readBuffer.append(line);
			}

			return readBuffer.toString();

		} catch (Exception e) {
			e.printStackTrace();
			//System.exit(1);
		} finally {
            try {
                process.destroy();
                if (bufferedReader != null) bufferedReader.close();
                if (readBuffer != null) bufferedReader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
		return null;
	}
	
	/**
	 * cmd에서 readPackage 명령을 수행하여 결과 값을 리턴
	 * @params command
	 * @return p
	 */
	@SuppressWarnings("null")
	public ArrayList<String> readPackage(String command) {
		process = null;
		bufferedReader = null;
		readBuffer = null;
		try {
			process = Runtime.getRuntime().exec(command);
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("UTF-8")));

			String line = null;
			readBuffer = new StringBuffer();
			p = new ArrayList<String>();
			while ((line = bufferedReader.readLine()) != null) {
				if(!line.contains("pack"))		//공백줄 pass
					continue;
			
				//AdbPackage temp = new AdbPackage();
				readBuffer.append(line);
				String[] array = line.split(":");
				String packname = array[1];
				//temp.setPackname(packname);
				p.add(packname);

				readBuffer.append("\n");
			}
			return p;

		} catch (Exception e) {
			e.printStackTrace();
			//
			//System.exit(1);
		} finally {
            try {
                process.destroy();
                if (bufferedReader != null) bufferedReader.close();
                if (readBuffer != null) bufferedReader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

		return null;
	}
	
	/**
	 * cmd에서 adb dumpsys package 명령을 수행하여 결과 값을 리턴
	 * @params command
	 * @return p
	 */
	@SuppressWarnings("null")
	public ArrayList<String> dumpSys(String command) {
		process = null;
		bufferedReader = null;
		readBuffer = null;
		try {
			process = Runtime.getRuntime().exec(command);
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("UTF-8")));

			String line = null;
			readBuffer = new StringBuffer();
			dumpP = new ArrayList<String>();
			while ((line = bufferedReader.readLine()) != null) {
				line = line.trim();	//공백제거
				//AdbPackage temp = new AdbPackage();
				//temp.setPackname(line);
				dumpP.add(line);
			}
			return dumpP;

		} catch (Exception e) {
			e.printStackTrace();
			//System.exit(1);
		} finally {
            try {
                process.destroy();
                if (bufferedReader != null) bufferedReader.close();
                if (readBuffer != null) bufferedReader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

		return null;
	}
	
	
	/**
	 * cmd에서 command 명령을 수행하여 결과 값을 리턴
	 * @params command
	 * @return result
	 */
	public String openCommand() {
		process = null;
		bufferedReader = null;
		readBuffer = null;
		try {
	             String [] command = {"cmd.exe", "/c" ,"start" , "cmd.exe" , "/k" , "C:\\GAUS\\buddy_log.bat"}	;     
	             Runtime.getRuntime( ).exec(command);

		} catch (Exception e) {
			e.printStackTrace();
			//System.exit(1);			//stream closed되면 exit 처리
		} finally {
            try {
              //  process.destroy();
                if (bufferedReader != null) bufferedReader.close();
                if (readBuffer != null) bufferedReader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
			System.out.println("finally");
        }
		return null;
	}
	
	public String appCommand() {
		process = null;
		bufferedReader = null;
		readBuffer = null;
		try {
	             String [] command = {"cmd.exe", "/c" ,"start" , "cmd.exe" , "/k" , "C:\\GAUS\\bash\\goApp.sh"}	;     
	             Runtime.getRuntime( ).exec(command);

		} catch (Exception e) {
			e.printStackTrace();
			//System.exit(1);			//stream closed되면 exit 처리
		} finally {
            try {
              //  process.destroy();
                if (bufferedReader != null) bufferedReader.close();
                if (readBuffer != null) bufferedReader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
			System.out.println("finally");
        }
		return null;
	}
	
}

