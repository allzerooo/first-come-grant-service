.PHONY: up down reset logs app clean

# 전체 스택 기동
up:
	docker compose up -d

# 전체 스택 중지
down:
	docker compose down

# 볼륨 삭제 후 재기동 (데이터 초기화)
reset:
	docker compose down -v
	docker compose up -d

# 주요 컨테이너 로그 tail
logs:
	docker compose logs -f --tail=100

# 특정 서비스 로그 (예: make logs-kafka)
logs-%:
	docker compose logs -f --tail=100 $*

# 애플리케이션 실행
app:
	./gradlew bootRun

# Gradle 빌드 캐시 정리
clean:
	./gradlew clean

# 전체 환경 기동 (인프라 + 앱)
all: up app
