# PluginTemplateKt

morinoparty の Minecraft プラグインテンプレートです。Paper / Velocity 両対応のマルチモジュール構成になっています。

## モジュール構成

| モジュール | 説明 |
|-----------|------|
| `common` | Paper/Velocity 共通コード |
| `paper` | Paper (Bukkit) プラグイン |
| `velocity` | Velocity プロキシプラグイン |
| `api` | 外部プラグイン向け公開 API |

## 技術スタック

バージョンは執筆時点のものです。最新の値は `gradle/libs.versions.toml` と `docs/package.json` を参照してください (Dependabot が更新します)。

### プラグイン

- **Kotlin** 2.4 / **Java** 25 - 言語・ツールチェーン
- **Paper API** 26.3 - Minecraft サーバー API (`paper-plugin.yml` の `apiVersion` と開発サーバーは 26.2)
- **Velocity API** 4.3 - Minecraft プロキシ API
- **Cloud** - コマンドフレームワーク (Incendo)
- **Koin** - 依存性注入
- **MCCoroutine** - Kotlin Coroutines の Minecraft 統合
- **kotlinx.serialization** - シリアライズ
- **Shadow** / **run-paper** / **resource-factory** - JAR 生成・開発サーバー・`paper-plugin.yml` / `velocity-plugin.json` の生成

### テスト・品質管理

- **JUnit** / **MockK** / **MockBukkit** / **koin-test** - ユニットテスト
- **[fukurou](https://github.com/morinoparty/fukurou)** - 実際の Paper サーバーとクライアントで動かす in-game テスト (`game-test/`)
- **ktlint** / **detekt** / **Spotless** (ライセンスヘッダー) / **Dokka** (API ドキュメント)

### ドキュメントサイト (`docs/`)

- **[Fumadocs](https://fumadocs.dev/)** - ドキュメントフレームワーク
- **[TanStack Start](https://tanstack.com/start)** - React フレームワーク (静的サイトとしてプリレンダー)
- **[Vite+](https://viteplus.dev/)** (`vp`) - 開発サーバー・ビルド・フォーマット・リント
- **[Chlorophyll](https://www.npmjs.com/package/@morinoparty/chlorophyll-react)** (`@morinoparty/chlorophyll-react`) - morinoparty のデザインシステム。デザイントークンを Panda CSS のプリセットから生成します

## 必要環境

- **Java** 25 (Temurin 推奨)
- **Gradle** 9.x (Wrapper 同梱)
- **Node.js** 26 / **pnpm** 10.26.1 (ドキュメントビルド用。pnpm の版は `docs/package.json` の `packageManager` で固定)
- **[Task](https://taskfile.dev/)** (タスクランナー、任意)

## セットアップ

### 1. テンプレートからリポジトリを作成

GitHub の "Use this template" ボタンからリポジトリを作成するか、クローンしてください。

### 2. プロジェクト名の変更

テンプレートのプレースホルダーを自分のプラグイン名に一括置換します。

```bash
# 例: "PluginName" を "MyPlugin" に変更する場合

# 1. settings.gradle.kts のルートプロジェクト名
#    rootProject.name = "MyPlugin"

# 2. パッケージ名の変更
#    party.morino.pluginname → party.morino.myplugin
#    ディレクトリ名も合わせてリネーム
#    common/build.gradle.kts の basePackage も合わせて変更 (BuildConstants の生成先)

# 3. クラス名の変更
#    PluginName → MyPlugin
#    PluginNameLoader / PluginNameBootstrap → MyPluginLoader / MyPluginBootstrap
#    PluginNameAPI → MyPluginAPI
#    PluginNameCommon → MyPluginCommon

# 4. 設定ファイルの更新
#    - paper/build.gradle.kts: paperPluginYaml (main / bootstrapper / loader のクラスパス、website)
#    - velocity/build.gradle.kts: velocityPluginJson (id / main / description / authors)
#    - .agent/rules/: プロジェクト名、リポジトリ URL (CLAUDE.md / AGENTS.md は task agent で生成)
#    - docs/src/lib/shared.ts: サイト名、GitHub リポジトリ、Modrinth URL
#    - docs/src/routes/__root.tsx: <title> と description
#    - docs/public/index.html: トップページの <title>
#    - docs/content/docs/meta.json: title (llms.txt の見出し)
#    - .github/workflows/preview.yml: PROJECT_NAME
#    - .github/workflows/upload.yml: JAR ファイル名
#    - game-test/scenarios/hello.json: コマンド名と期待するメッセージ
```

### 3. AI エージェント向け指示ファイルの生成

`CLAUDE.md` / `AGENTS.md` は `.agent/rules/` から生成されるファイルで、git には含めていません (`.gitignore` 済み)。
クローン直後や新しい worktree を作成した直後、`.agent/rules/` を編集したあとに生成してください。
人格や汎用的なルールは各自のマシンのグローバル設定で管理し、`.agent/rules/` にはこのリポジトリ固有のルールだけを置きます。

```bash
bash .agent/build.sh

# または Task を使用
task agent
```

[Orca](https://github.com/stablyai/orca) で worktree を作成する場合は、リポジトリ設定の Setup Script でこの生成を自動化できます。
詳しくはドキュメントの「Orca のセットアップ」(`docs/content/docs/development/orca-setup.mdx`) を参照してください。

### 4. ビルド

```bash
# Gradle ビルド (ユニットテストも実行される)
./gradlew build

# テストを省略する場合
./gradlew build -x test

# または Task を使用 (clean build)
task build
```

Paper 向けの JAR は `paper/build/libs/*-all.jar`、Velocity 向けの JAR は `velocity/build/libs/*-all.jar` に出力されます。

### 5. 開発サーバー起動

```bash
# Paper テストサーバー
./gradlew :paper:runServer

# または Task を使用
task run
```

### 6. ドキュメント開発

`docs/` は単独の pnpm プロジェクトです (Fumadocs + TanStack Start + Vite+)。

```bash
cd docs
pnpm install
pnpm dev          # 開発サーバー (http://localhost:3000/docs/)

pnpm check        # フォーマット・リント (vp check --fix)
pnpm check:ci     # フォーマット・リントの検証のみ (vp check)
pnpm typecheck    # 型チェック (tsc --noEmit)
pnpm build        # 静的サイトを docs/out に出力 (vp build)

# または Task を使用
task docs         # pnpm install + pnpm dev
task docs:build   # pnpm install + pnpm build
```

サブパスに配置する場合は `BASE_PATH` を指定してビルドします (例: `BASE_PATH=/PluginName/abc1234/docs pnpm build`)。
Dokka の API ドキュメントは `./gradlew dokkaGenerate` で `docs/public/dokka` に出力され、ビルド時に `docs/out/dokka` にコピーされます。

## 初期化チェックリスト

テンプレートから新しいプラグインを作成する際に行うべき作業の一覧です。

- [ ] リポジトリ名を変更
- [ ] `settings.gradle.kts` の `rootProject.name` を変更
- [ ] パッケージ名 `party.morino.pluginname` を変更
- [ ] `common/build.gradle.kts` の `basePackage` をパッケージ名に合わせて変更
- [ ] ソースディレクトリ名をパッケージ名に合わせてリネーム
- [ ] クラス名 (`PluginName`, `PluginNameLoader`, `PluginNameBootstrap`, `PluginNameAPI`, `PluginNameCommon`) を変更
- [ ] `paper/build.gradle.kts` の `paperPluginYaml` (main / bootstrapper / loader のクラスパス、website) を変更
- [ ] `velocity/build.gradle.kts` の `velocityPluginJson` (id / main / description / authors) を変更
- [ ] `.agent/rules/` のプロジェクト説明とリポジトリ URL を変更し、`task agent` で `CLAUDE.md` / `AGENTS.md` を生成
- [ ] `docs/src/lib/shared.ts` のサイト名 (`appName`)、GitHub リポジトリ (`gitConfig`)、`modrinthUrl` を変更
- [ ] `docs/src/routes/__root.tsx` の `title` / `description` を変更
- [ ] `docs/public/index.html` の `<title>` を変更
- [ ] `docs/content/docs/meta.json` の `title` と `docs/content/docs/` の本文を変更
- [ ] `docs/package.json` の `name` を変更
- [ ] `.github/workflows/preview.yml` の `PROJECT_NAME` を変更
- [ ] `.github/workflows/upload.yml` の JAR ファイル名を変更
- [ ] `game-test/scenarios/hello.json` をプラグインのコマンドに合わせて変更
- [ ] GitHub リポジトリの Settings で GitHub Pages を有効化 (Source は GitHub Actions。公開パスは `deploy_docs.yml` が Pages の設定から取得するため、カスタムドメインの有無で設定を変える必要はない)
- [ ] GitHub リポジトリの Secrets に S3 認証情報を設定 (プレビュー機能を使う場合)
- [ ] GitHub リポジトリの Secrets に `GRADLE_ENCRYPTION_KEY` を設定 (CI で configuration cache を使う場合、任意)
- [ ] 不要な初期サンプルコード (`ExampleCommand`) を削除
- [ ] `config/spotless/license-header.kt` の著者名を変更し、`task license` でヘッダーを付け直す
- [ ] Orca を使う場合は、リポジトリ設定の Setup Script を設定 (ドキュメントの「Orca のセットアップ」を参照)

## Task コマンド一覧

| コマンド | 説明 |
|---------|------|
| `task build` | 全モジュールをビルド (`./gradlew clean build`、テストを含む) |
| `task run` | Paper 開発サーバーを起動 |
| `task docs` | ドキュメント開発サーバーを起動 (`pnpm install` + `pnpm dev`) |
| `task docs:build` | ドキュメントを静的サイトとしてビルド (`docs/out`) |
| `task check` | フォーマット (`ktlintFormat`) + ビルド |
| `task clear` | session.lock ファイルを削除 |
| `task license` | ライセンスヘッダーを付与・更新 (`spotlessApply`) |
| `task license:check` | ライセンスヘッダーを検証 (`spotlessCheck`) |
| `task agent` | `.agent/rules/` から `CLAUDE.md` / `AGENTS.md` を生成 |

## ビルド時定数 (BuildConstants)

`common/build.gradle.kts` の `generateBuildConstants` タスクが、ビルドのたびに `BuildConstants.kt` を生成します。

| 定数 | 値の由来 | 主な用途 |
|------|---------|---------|
| `BuildConstants.VERSION` | `gradle.properties` の `version` | ログ出力やバージョン表示など |

`const val` なので、アノテーション引数のようにコンパイル時定数が必要な場所でもそのまま使えます。
なお、Velocity の `velocity-plugin.json` は kapt (`@Plugin` アノテーション) ではなく、`velocity/build.gradle.kts` の `velocityPluginJson` (resource-factory) で生成しています。

## 実行時ライブラリ

Paper 向けの JAR には Kotlin 標準ライブラリや Koin などの依存を同梱せず、`PluginNameLoader` がサーバー起動時に Maven Central のミラーから取得します (JAR は約 9MB → 約 1MB)。
取得するライブラリの一覧 (`paper-libraries.txt`) はビルド時に `runtimeClasspath` から自動生成されます。
`party.morino` のモジュールと `-SNAPSHOT` 版 (cloud など) のみ JAR に同梱します。条件は `paper/build.gradle.kts` の `isBundled` で変更できます。

## GitHub Actions

| ワークフロー | トリガー | 説明 |
|-------------|---------|------|
| `check_pull_request.yml` | Pull Request / Push to main | ビルドチェック・実行時ライブラリがミラーに存在するかの確認・docs の lint / 整形 / 型チェック (`pnpm check:ci` / `pnpm typecheck`) |
| `preview.yml` | Pull Request | プレビュービルド・fukurou の in-game テスト・S3 アップロード・PR コメント |
| `upload.yml` | Release published | GitHub Release に JAR をアップロード |
| `release.yml` | Push to main | Release Drafter でドラフトリリース作成 |
| `deploy_docs.yml` | Push to main (docs/) | GitHub Pages にドキュメントデプロイ |
| `dependabot_auto_merge.yml` | Dependabot PR | 自動マージ |
| `sync-label.yml` | labels.json 変更 | GitHub ラベル同期 |

## ライセンス

CC0-1.0

### ライセンスヘッダー

Kotlin / Java ソースと `*.gradle.kts` には [Spotless](https://github.com/diffplug/spotless) でライセンスヘッダーを付与します。
ヘッダー本文は `config/spotless/license-header.kt` に一元管理されており、`$YEAR` が年に置き換わります (既存ファイルは `開始年-現在年` に更新されます)。

```bash
# ヘッダーを付与・更新
task license        # = ./gradlew spotlessApply

# ヘッダーの検証のみ
task license:check  # = ./gradlew spotlessCheck
```

`build` / `check` は失敗しない設定 (`isEnforceCheck = false`) なので、任意のタイミングで実行してください。
