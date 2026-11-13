## このアプリケーションの概要
「PluginName」という、Minecraftのプラグインです。Paper / Velocity 両対応のマルチモジュール構成です。

## 主な技術スタック

### プラグイン
- Kotlin (言語) / Java 25 (ツールチェーン)
- PaperMC (MinecraftプラグインAPI)
- Velocity (MinecraftプロキシAPI)
- Koin (依存性注入)
- Cloud (コマンドフレームワーク)
- MCCoroutine (非同期処理)
- kotlinx.serialization (シリアライズ)
- Gradle (Kotlin DSL, バージョンは `gradle/libs.versions.toml` で管理) / Shadow (JAR 生成) / run-paper (開発サーバー)

### テスト
- JUnit Jupiter / MockK / MockBukkit / koin-test (ユニットテスト, `paper/src/test/`)
- [fukurou](https://github.com/morinoparty/fukurou) (実際の Paper サーバーとクライアントで動かす in-game テスト, `game-test/`。`.github/workflows/preview.yml` で PR ごとに実行される)

### 品質管理
- ktlint (フォーマット, `task check` で `ktlintFormat` を実行)
- detekt (静的解析, `detekt-baseline.xml` をベースラインとし、ビルドは失敗させない)
- Spotless (ライセンスヘッダーの付与・検証, ヘッダーは `config/spotless/license-header.kt`。`task license` / `task license:check`)
- Dokka (API ドキュメント生成)

### ドキュメント (`docs/`)
- Fumadocs (ドキュメントフレームワーク)
- TanStack Start (React フレームワーク)
- Vite+ (`vp` によるビルド・フォーマット・リント)
- Chlorophyll (`@morinoparty/chlorophyll-react`, morinoparty のデザインシステム。トークンは Panda CSS のプリセットから利用する)

## モジュール構成
- `common` - Paper/Velocity共通コード
- `paper` - Paper (Bukkit) プラグイン
- `velocity` - Velocity プロキシプラグイン
- `api` - 外部プラグイン向け公開API
