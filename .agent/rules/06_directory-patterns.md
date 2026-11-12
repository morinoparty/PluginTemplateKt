## ディレクトリ配置規則

- common/src/main/kotlin/party/morino/pluginname/common/ に共通コードを配置します。
- paper/src/main/kotlin/party/morino/pluginname/paper/ にPaperプラグインのコードを配置します。
- paper/src/main/java/party/morino/pluginname/paper/ には、Kotlin 標準ライブラリより先に読み込まれる Java のクラス (PluginLoader など) のみを配置します。
- paper/src/test/kotlin/party/morino/pluginname/paper/ にPaperプラグインのユニットテストを配置します。
- velocity/src/main/kotlin/party/morino/pluginname/velocity/ にVelocityプラグインのコードを配置します。
- api/src/main/kotlin/party/morino/pluginname/api/ にAPIのインターフェースを配置します。
- data classはそれぞれのmodelに配置します。
- game-test/ に fukurou の in-game テストを配置します。
  - `game-test/fukurou.yml` - テストスイート (シナリオの場所と参加するプレイヤー)
  - `game-test/scenarios/*.json` - 1 ファイルが 1 テスト。ファイル名がテスト id になる
- config/spotless/license-header.kt にライセンスヘッダーのテンプレートを配置します。
- gradle/libs.versions.toml で依存ライブラリとプラグインのバージョンを管理します。

## ドキュメント (docs/)

- docs/content/docs/ にドキュメント (mdx) を配置します。
- docs/ は単独の pnpm プロジェクトで、Vite+ (`vite.config.ts`) と TanStack Start でビルドします。Vite+ のバージョンは `docs/pnpm-workspace.yaml` の catalog で管理します。
- docs/src/routes/ に TanStack Start のルート、docs/src/lib/ にサイト設定 (`shared.ts`) と Fumadocs のソース定義 (`source.ts`)、docs/src/styles/ に CSS を配置します。
- docs/src/routeTree.gen.ts は自動生成されるファイルのため、手で編集しません。
- Dokka の API ドキュメントは docs/public/dokka に出力されます (生成物のため git には含めません)。
