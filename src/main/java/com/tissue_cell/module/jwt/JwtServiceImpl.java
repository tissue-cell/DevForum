package com.tissue_cell.module.jwt;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.tissue_cell.dto.UserDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtServiceImpl {

	private String secretKey = "myKey"; // 서명에 사용할 secretKey
	private final long ACCESS_TOKEN_EXPIRE = 1000L * 60 * 60; // 토큰 사용가능 시간, 1시간
	private final long REFRESH_TOKEN_EXPIRE = 1000L * 60 * 60 * 24 * 14; // 토큰 사용가능 시간, 2주
	private final String ACCESS_TOKEN = "ACCESS_TOKEN";
	private final String REFRESH_TOKEN = "REFRESH_TOKEN";

	// 액세스토큰 생성
	public String createAccessToken(String userId) { // 토큰에 담고싶은 값 파라미터로 가져오기
		return Jwts.builder().setHeaderParam("typ", "JWT") // 토큰 타입
				.setSubject(ACCESS_TOKEN) // 토큰 제목
				.setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE)) // 토큰 유효시간
				.claim("userid", userId) // 토큰에 담을 데이터 선택사항
				.signWith(SignatureAlgorithm.HS256, secretKey) // secretKey를 사용하여 해싱 암호화 알고리즘 처리
				.compact(); // 직렬화, 문자열로 변경
	}
	// 리프레시토큰 생성	
	public String createRefreshToken(String userId) { // 토큰에 담고싶은 값 파라미터로 가져오기
		return Jwts.builder().setHeaderParam("typ", "JWT") // 토큰 타입
				.setSubject(REFRESH_TOKEN) // 토큰 제목
				.setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE)) // 토큰 유효시간
				.claim("userid", userId) // 토큰에 담을 데이터 선택사항
				.signWith(SignatureAlgorithm.HS256, secretKey) // secretKey를 사용하여 해싱 암호화 알고리즘 처리
				.compact(); // 직렬화, 문자열로 변경
	}

	// 토큰에서 값 추출
	public String getSubject(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
	
	// 토큰에 담긴 정보를 가져오기 메서드
	public Map<String, Object> getInfo(String token) throws Exception {
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token); // secretKey를 사용하여 복호화
		} catch(Exception e) {
			throw new Exception();
		}
		
		return claims.getBody();
	}

	// 유효한 토큰인지 확인
	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			if (claims.getBody().getExpiration().before(new Date())) {
				return false;
			}
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}
}