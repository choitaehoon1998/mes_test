package com.broanex.mes.exceptionHandler;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
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

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

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
				"http://7839-175-119-149-98.ngrok.io/log",
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
