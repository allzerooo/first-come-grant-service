package com.toy.first_come_grant_service

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class FirstComeGrantServiceApplicationTests {

	@Test
	fun contextLoads() {
	}

	@Test
	fun `의도적 실패`() {
		throw AssertionError("CI 실패 테스트")
	}

}
