# CUB Backend 作業 - Coindesk 轉接服務

此專案依照台中 CUB/國泰世華 JAVA engineer 線上作業需求實作：
- 幣別 DB 維護 CRUD（H2 / Spring Data JPA）
- 呼叫 Coindesk API，失敗時套用題目提供的 mock JSON
- 新 API 進行資料轉換：時間格式 `yyyy/MM/dd HH:mm:ss`、幣別代碼 + 中文名 + 格式化匯率，並依代碼排序
- 排程每小時同步（記錄於 log）
- 單元測試（MockMvc）
- AOP 請求/錯誤 log
- swagger-ui：啟動後瀏覽 `http://localhost:8080/swagger-ui/index.html`
- i18n 驗證訊息（中/英）
- 設計模式：
  - **Adapter**：`CoindeskDTO -> RateView` 的轉換
  - **Strategy**：`RateFormatStrategy` 匯率格式化策略
- Docker 支援

> 規格來源：見附件 PDF。

## 快速啟動

```bash
# 1. JDK 17 + Maven
mvn spring-boot:run

# 2. Swagger
open http://localhost:8080/swagger-ui/index.html

# 3. H2 Console
open http://localhost:8080/h2-console  (JDBC URL: jdbc:h2:mem:cubdb)
```

## 主要 API

- `GET /api/currencies` 查詢（依 code 排序）
- `POST /api/currencies` 新增 `{"code":"TWD","nameZh":"新台幣"}`
- `PUT /api/currencies/{id}` 修改
- `DELETE /api/currencies/{id}` 刪除

- `GET /api/coindesk/raw` 直接回傳 Coindesk（或 mock）
- `GET /api/coindesk/transform` 轉換後回傳：
```json
{
  "updatedTime": "1990/01/01 00:00:00",
  "items": [{"code":"USD","nameZh":"美元","rate":"23,342.0112"}]
}
```

## 測試

```bash
mvn -q test
```

## Docker

```bash
docker build -t cub-coindesk .
docker run -p 8080:8080 cub-coindesk
```

## 加分作法備註
- AOP：`com.cub.coindesk.aop.LoggingAspect`
- Error handling：`GlobalExceptionHandler`
- swagger-ui：`springdoc-openapi`
- i18n：`src/main/resources/messages*.properties`
- 2 個設計模式：Adapter & Strategy
- Dockerfile：已提供

