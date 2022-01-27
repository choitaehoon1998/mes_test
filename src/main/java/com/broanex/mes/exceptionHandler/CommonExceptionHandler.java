package com.broanex.mes.exceptionHandler;

/*
 * 코드작성자 : 최태훈
 * 소스설명 : MES에서 Excpetion 이 발생했을경우, 해당내용을 LogServer에 전달하는 역활을 한다.
 * 관련 DB 테이블 : 없음
 * */

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * 동작방식 (R: RETURN TYPE, P: PARAMETER TYPE)
 * 1. paramMapToString R:[String] P:[Map<String, String[]>]                                              : 요청 받은 모든 파라미터를 String으로 변경한다.
 * 2. SendToLogServer R:[X] P:[HashMap<String, String>]                                                  : 내용을 LogServer에 전달한다.
 * 3. requestAndExceptionToBody R:[HashMap<String, String>] P:[HttpServletRequest request, Exception e]  : 요청과 exception에 대한 내용을 logserver에 전달할 body 로 변경한다.
 * 4. BasicExceptionHandling R:[ResponseEntity<Void> ] P:[HttpServletRequest request, Exception e]       : 모든 Exception이 발생할경우, 이 메소드를 타게되고 logServer로 post 요청을 보내고, 요청받은 클라이언트에게 status500을 준다
 */

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

	@Value("${spring.log.server.uri}")
	private String logServerUri;

	private String paramMapToString(Map<String, String[]> paramMap) {
		return paramMap.entrySet().stream()
				.map(entry -> String.format("%s -> (%s)",
						entry.getKey(), Joiner.on(",").join(entry.getValue())))
				.collect(Collectors.joining(", "));
	}

	private void SendToLogServer(HashMap<String, String> body) {
		HttpEntity<HashMap<String, String>> request = new HttpEntity<>(body);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(
				logServerUri,
				HttpMethod.POST,
				request,
				String.class
		);
	}

	private HashMap<String, String> requestAndExceptionToBody(HttpServletRequest request, Exception e) {
		Map<String, String[]> paramMap = request.getParameterMap();

		String params = "";
		if (paramMap.isEmpty() == false) {
			params = " [" + paramMapToString(paramMap) + "]";
		}

		return new HashMap<String, String>() {{
			put("userName", "testUserName");
			put("companyCode", "testCompanyCode");
			put("exceptionClass", e.getClass().toString());
			put("exceptionMessage", e.getMessage());
			put("method", request.getMethod());
			put("requestUri", request.getRequestURI());
			put("remoteHost", request.getRemoteHost());
		}};
	}

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<Void> BasicExceptionHandling(HttpServletRequest request, Exception e) {
		HashMap<String, String> hashMap = requestAndExceptionToBody(request, e);
		SendToLogServer(hashMap);
		return ResponseEntity.status(500).build();
	}
}
