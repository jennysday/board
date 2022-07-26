package com.spring.notice.service.utill;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SecurityUtill {
	/*** 작성자 양방향 암호화 ***/
	public static String alg = "AES/CBC/PKCS5Padding";
    private final String key = "01234567890123456789012345678901";
    private final String iv = key.substring(0, 16); // 16byte

    /*** AES256 암호화 ***/
    public String encrypt(String text) throws Exception {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /*** AES256 복호화 ***/
    public String decrypt(String cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decrypted = cipher.doFinal(decodedBytes);
        return new String(decrypted, "UTF-8");
    }
    
    /** 비밀번호 단방향 암호화 **/
    public String SHA256Encrypt(String pwd){
    	try{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(pwd.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			//출력
			return hexString.toString();
		} catch(Exception ex) {
			throw new RuntimeException(ex);
		}
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
	
}
