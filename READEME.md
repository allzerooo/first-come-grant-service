## 개발 환경

### 필수 요구사항
- Docker & Docker Compose
- JDK 24

### 명령어

| 명령어 | 설명 |
|--------|------|
| `make up` | 인프라 스택 기동 (Postgres, Redis, Kafka, Prometheus, Grafana, OTel) |
| `make down` | 인프라 스택 중지 |
| `make reset` | 볼륨 삭제 후 재기동 (데이터 초기화) |
| `make logs` | 전체 로그 tail |
| `make logs-kafka` | Kafka 로그만 tail |
| `make app` | 애플리케이션 실행 |
| `make clean` | Gradle 빌드 캐시 정리 |

### 접속 정보

| 서비스 | URL |
|--------|-----|
| 애플리케이션 | http://localhost:8080 |
| Prometheus | http://localhost:9090 |
| Grafana | http://localhost:3000 (admin/admin) |
