package com.sinbal.spring;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {
	public static void main(String[] args) {
		BCryptPasswordEncoder e=new BCryptPasswordEncoder();
		String result=e.encode("1234");
		System.out.println(result);
	}
}
