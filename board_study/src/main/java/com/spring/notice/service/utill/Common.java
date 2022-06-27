package com.spring.notice.service.utill;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

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
	
	/**	
	 * WhiteList : 보안에서 화이트리스트란 기본 정책이 모두 차단인 상황에서 예외적으로 접근이 가능한 대상을 지정하는 방식 또는 그 지정된 대상들을 말한다.
	 * BlackList : 블랙리스트는 반대로 기본 정책이 모두 허용인 상황에서 예외적으로 차단하는 대상을 지정하는 것으로, 화이트리스트의 반대말이다.
       화이트리스트가 블랙리스트에 비해 보안성이 우수하지만 가용성이나 편의성은 블랙리스트 방식이 더 우수하다.
	**/
		
	/*** XSS 필터 ***/
	/*** html 인코딩 태그 변환 ***/

	public String htmlEn(String str) {

		str = str.replaceAll("<()(?!####)([^<|>]+)?>", "&lt;$1$2&gt;");
		str = str.replaceAll("\"", "&quot;");

		return str;
	}

	/** 화이트 리스트 **/
	public String whiteList(String str) {
		String htmlContent = str;
		//compile() : String 값으로 들어온 정규식을 Pattern 객체로 바꿔줌. -> 1. 패턴 정의 
		Pattern pattern = Pattern.compile("&lt;(/)?\\s*\\/?\\s*(div|h1|h2|h3|h4|h5|h6|p|ul|ol|li|strong)\\b.*?&gt;(/)?");
		Matcher m = pattern.matcher(htmlContent); //matcher() : 정의된 패턴에 매치되는 값을 저장 -> 2
		
		while (m.find()) { //find() : 매치된 값이 있으면 true, 없으면 false를 반환 -> 3
			String strOrg = m.group(); //group() : 매치된 값 반환 -> 4
			System.out.println("strOrg>>" + strOrg);
			String strRep = m.group().replace("&lt;", "<").replace("&gt;", ">"); //변환 후 다시 변수에 넣음
			System.out.println("strRep>>" + strRep);
			str = str.replace(strOrg, strRep); //original에서 변환해준 replace로 값을 변환해줌 
		}
		
		//공백처리
		str = str.replace("&nbsp;", " ");
		
		//str반환
		return str;
	}
	
	/*
	 **** Matcher 클래스의 메소드 ****
	 
	 1. find() : 주어진 텍스트에서 pattern에 일치하는 텍스트들이 발견되면 true를 반환
	 반복문을 계속 호출하여 주어진 텍스트의 두번째 이상의 패턴과 일치하는 부분도 찾을 수 있다.
	 
	 2. group : 주어진 텍스트에서 주어진 패턴과 일치하는 텍스트를 반환해준다.
	 
	 3. match() : 주어진 텍스트 전체와 pattern이 일치하는 경우 true를 반환해준다.
	 
	 
	 **** replace와 replaceAll 차이 ****
	 
	 replace : String.replace([찾을 문자열], [변경 문자열]);
	 replaceAll : String.replaceAll([정규표현식], [변경 문자열]);
	 
	 String replace(CharSequnce target, CharSequence replacement)
	 String replaceAll(String regex, String replacement)
	 
	 replace는 첫번째 인자값에 문자열이 들어가며, 문자열만 변환가능
	 replaceAll은 첫번째 인자값에 정규식이 들어간다.
	 
	 ex) String str = "abcabdd";
	     String result1 = str.replace("abc", "힛");
         String result2 = str.replaceAll("[abc]", "힛");
         
         replace result-> 힛abdd
         replaceAll result->힛힛힛힛힛dd
         
    **** [abc]는 정규식 표현이다. [abc]는 []안에 있는 문자들을 전부 힛으로 변환시켜줍니다. 
         a or b or c 3가지의 문자를 만나면 전부 치환대상이 됨.
    **** abc는 문자열 "abc"가 치환 대상이다.      

	*/
	
	/*** 시간 처리 ***/
	public void date() {
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
