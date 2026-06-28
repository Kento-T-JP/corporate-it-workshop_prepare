# Corporate IT Workshop Prepare

任天堂のコーポレートIT（社内SE）ワークショップ・面接準備を目的に、Java / Spring Boot を中心として Web アプリケーション開発の基礎を実践した学習用プロジェクトです。

単にコードを書くのではなく、以下を理解することを重視しています。

- なぜ Spring Boot を使うのか
- なぜ Controller / Service / Repository に分けるのか
- なぜ DTO や Validation が必要なのか
- なぜ Docker / CI / IaC を使うのか
- AWS に載せる場合、どのような構成と課金ポイントになるのか

## 現在の到達点

このリポジトリでは、Spring Boot の基本的な REST API を実装し、ローカル開発・テスト・CI・コンテナ化・IaC の入口までを扱っています。

```text
Spring Boot
├── REST API
├── Controller / Service / Repository
├── Entity / DTO
├── Validation
├── Global Exception Handling
├── H2 / PostgreSQL
├── Docker
├── Docker Compose
├── GitHub Actions CI
└── Terraform
```

## 技術スタック

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Bean Validation
- H2 Database
- PostgreSQL
- Gradle
- Docker
- Docker Compose
- GitHub Actions
- Terraform

## 実装済みAPI

### Health Check

```http
GET /health
```

### Employee API

```http
GET    /employees
GET    /employees/{id}
POST   /employees
PUT    /employees/{id}
DELETE /employees/{id}
```

この API では、社員情報を題材にして CRUD、DTO、Validation、例外処理、JPA Repository の基本を学習しています。

## プロジェクト構成

```text
src/main/java/com/kento/corporateitworkshop
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
└── service
```

各層の役割は以下です。

| 層 | 役割 |
|---|---|
| controller | HTTPリクエストを受け取り、レスポンスを返す |
| service | 業務ロジックを担当する |
| repository | DBアクセスを担当する |
| entity | DBテーブルに対応するオブジェクト |
| dto | APIの入出力用オブジェクト |
| config | アプリケーション設定や初期データ投入 |
| exception | 例外処理を一元管理する |

## 学習したこと

### Java / Spring Boot

- Spring Framework と Spring Boot の違い
- DI / Bean / DIコンテナの考え方
- REST API の基本
- Controller / Service / Repository の責務分離
- JPA による DB 操作
- Entity と DTO を分ける理由
- Validation による入力チェック
- Global Exception Handler による例外処理の一元化
- H2 と PostgreSQL の使い分け
- 物理削除と論理削除の違い

### Docker / Docker Compose

- Docker image と container の違い
- Dockerfile による Spring Boot アプリのコンテナ化
- Docker Compose によるアプリ + PostgreSQL のローカル起動
- 環境変数による DB 接続設定の切り替え
- ローカル開発環境を再現可能にする考え方

### GitHub Actions / CI

- CI とは何か
- テストとビルドの違い
- push / pull request を契機にした自動実行
- Gradle test の自動実行
- Docker build の自動確認
- CI によって変更品質を継続的に確認する考え方

### AWS / Terraform

- AWS に Spring Boot アプリを載せる場合の構成イメージ
- ECR / ECS Fargate / RDS / ALB / CloudWatch の役割
- AWS の課金ポイント
- Terraform による IaC の考え方
- `terraform plan` と `terraform apply` の違い
- `.terraform.lock.hcl` を Git 管理する理由
- `.terraform/` や `tfstate` を Git 管理しない理由

## ローカルでの起動方法

### Spring Boot 単体起動

```bash
./gradlew bootRun
```

### テスト実行

```bash
./gradlew test
```

### Docker image build

```bash
docker build -t corporate-it-workshop .
```

### Docker Compose 起動

```bash
docker compose up --build
```

アプリは以下で確認できます。

```text
http://localhost:18080/health
```

終了する場合は以下です。

```bash
docker compose down
```

## CI

GitHub Actions では、push / pull request 時に以下を自動実行します。

```text
checkout
↓
Java 21 setup
↓
Gradle test
↓
Docker build
```

CI の目的は、「変更後もテストが通り、アプリケーションをビルドできること」を継続的に確認することです。

## Terraform

Terraform 設定は以下にあります。

```text
infra/terraform
```

現在は AWS ECR リポジトリ定義までを作成しています。

検証済みコマンド:

```bash
terraform -chdir=infra/terraform init
terraform -chdir=infra/terraform fmt -check
terraform -chdir=infra/terraform validate
```

まだ AWS 上にはリソースを作成していません。

```bash
terraform apply
```

は実行していません。

## AWSにデプロイする場合の想定構成

将来的に AWS へローンチする場合は、以下のような構成を想定しています。

```text
GitHub
  ↓
GitHub Actions
  ↓ test / build / docker build
Amazon ECR
  ↓ Docker image
Amazon ECS Fargate
  ↓
Spring Boot App
  ↓
Amazon RDS PostgreSQL

User
  ↓
Application Load Balancer
  ↓
ECS Fargate
```

主な課金ポイントは以下です。

| サービス | 課金ポイント |
|---|---|
| ECR | Docker image の保存容量 |
| ECS Fargate | vCPU / メモリ / 実行時間 |
| ALB | 起動時間 / 処理量 |
| RDS | DBインスタンス / ストレージ / バックアップ |
| CloudWatch Logs | ログ取り込み量 / 保存量 |
| NAT Gateway | 起動時間 / 通信量 |

学習用途では、特に RDS、ALB、NAT Gateway の放置に注意します。

## これからやるべきこと

### AWSに進む前の安全設定

- rootユーザーに MFA を設定する
- AWS Budgets で低額の予算アラートを設定する
- Free Tier / Credits の状態を確認する
- rootユーザーを普段使いしない
- IAM Identity Center または IAMユーザーを用意する
- AWS CLI を設定する
- `aws sts get-caller-identity` で接続先アカウントを確認する

### Spring Boot の次の学習

- Spring Security
- 認証・認可
- トランザクション
- JPA の N+1 問題
- Flyway / Liquibase による DB migration
- OpenAPI / Swagger
- テスト設計の強化

### CI/CD の次の学習

- GitHub Actions から ECR へ Docker image を push
- GitHub Actions OIDC による AWS 認証
- Terraform plan を PR で実行
- レビュー後に Terraform apply を実行
- 本番環境では GitHub Environments による承認を挟む

### AWS の次の学習

- IAM / MFA / 権限管理
- ECR
- ECS Fargate
- RDS PostgreSQL
- ALB
- CloudWatch Logs
- Terraform remote state
- S3 backend + DynamoDB lock

## 面接で説明するなら

このプロジェクトは、以下のように説明できます。

> Java / Spring Boot を使って、Controller / Service / Repository / Entity / DTO に責務を分けた REST API を実装しました。JPA による DB アクセス、Validation、例外処理の一元化も導入しています。ローカルでは Docker Compose により Spring Boot と PostgreSQL を起動できるようにし、GitHub Actions でテストと Docker build を自動実行する CI を構築しました。AWS については、ECR / ECS Fargate / RDS / ALB を使ったデプロイ構成を想定し、Terraform で ECR 定義を作成してローカル検証まで行いました。課金や誤操作を避けるため、AWS 認証・予算設定・plan の確認前に apply は実行していません。
