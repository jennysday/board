package com.spring.notice.service.utill;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tika.Tika;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class Common {
		
		/*** 클라이언트 아이피 조회 ***/
		public String getUserIp() throws Exception {
			
	        String ip = null;
	        HttpServletRequest request = 
	        ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();

	        ip = request.getHeader("X-Forwarded-For");
	        
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("Proxy-Client-IP"); 
	        } 
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("WL-Proxy-Client-IP"); 
	        } 
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("HTTP_CLIENT_IP"); 
	        } 
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("X-Real-IP"); 
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("X-RealIP"); 
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("REMOTE_ADDR");
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getRemoteAddr(); 
	        }
			
			return ip;
		}
		
		
		/** MimeType 확인 **/
		public boolean isPermisionFileMimeType(String filePath) throws Exception {
			
			//아래의 배열의 해당하는 확장자에게만 true값을 반환
			final String[] PERMISSION_FILE_MIME_TYPE = {"image/gif", "image/jpeg", "image/png", "image/bmp", "application/pdf", "video/mp4"};
			
			//hasText는 파라미터가 문자열인지 확인해서 true 또는 false를 반환하는 메서드이다.
			//hasText는 null이 아니어야 하고, 길이가 0보다 커야하고, 공백이 아닌 문자가 포함되어있어야 한다
			//filePath는 이미지 경로를 의미 -> StringUtils.hasText(filePath) -> filepath의 경로가 없으면 거짓!
			if(!StringUtils.hasText(filePath)) { //파일 경로가 문자열로 있는지 확인
				System.out.println("filePath에 경로(문자열 경로)가 없음");
				return false;
			}
			
			//주어진 문자열 경로를 갖는 File 객체를 생성
			File file = new File(filePath);
			
			//file.isFile() : 파일인지를 검사하는 함수. 파일이 존재하지 않거나 디렉토리이면 false를 반환, 파일이면 true 반환
			if(!file.isFile()) {
				System.out.println("파일 없음");
				return false;
			}
			
			String mimeType = new Tika().detect(file);
			boolean isPermisionFileMimeType = false;
			
			for(int i = 0; i < PERMISSION_FILE_MIME_TYPE.length; i++) {
				if(PERMISSION_FILE_MIME_TYPE[i].equals(mimeType)) {
					isPermisionFileMimeType = true;
					break; //for문을 빠져나감
				}
			}
			
			System.out.println("isPermisionFileMimeType : " + isPermisionFileMimeType);
			
			return isPermisionFileMimeType;
	}
		
	/*** alert 메세지 ***/
	public void alertAndGo(HttpServletResponse response, String msg, String url) {
	    try {
	        response.setContentType("text/html; charset=utf-8");
	        PrintWriter w = response.getWriter();
	        w.write("<script>alert('"+msg+"');location.href='"+url+"';</script>");
	        w.flush();
	        w.close();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}
	
	/*** 시간 처리 ***/
	public void date() throws Exception{
		//시간 처리
		SimpleDateFormat format = new SimpleDateFormat ("yyyy년 MM월dd일 HH:mm:ss");
		Calendar time = Calendar.getInstance();
		String format_time = format.format(time.getTime());
		System.out.println("등록 시간>>"+format_time);
		
		// 현재 날짜 구하기        
		LocalDate now = LocalDate.now();        
		// 포맷 정의        
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");        
		// 포맷 적용        
		String formatedNow = now.format(formatter);        
		// 결과 출력        
		System.out.println("등록시간22>>>>>>>"+formatedNow); 
		
	}
	
}
