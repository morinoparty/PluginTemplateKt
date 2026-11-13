# 推奨される書き方

## DI
- DIを使う際には、コンストラクタインジェクションではなく、Koinの`by inject()`を使うことが推奨されます。

## 依存ライブラリ
- 依存ライブラリは `gradle/libs.versions.toml` に追加してください。
- paper モジュールの外部依存は JAR に同梱せず、`PluginNameLoader` が実行時に Paper に取得させます (同梱されるのは common / api と SNAPSHOT 版のみ)。そのため、追加するライブラリは Paper が参照する Maven Central のミラーから取得できるものにしてください。

## Test
- テストの数については、あまり大量にせず、メソッドの主要な分岐点をカバーする程度に留めることが推奨されます。
- また、@DisplayNameアノテーションを使用して、テストケースの説明を明確にすることが推奨されます。これは、英語で短く記述してください。
- MockBukkit で再現できない挙動 (実際のクライアントとのやり取りなど) は、fukurou の in-game テストで確認してください。

## Minecraft
- player | senderにメッセージを送る際には、sender.sendMessageではなく、sender.sendRichMessage(minimessage : string)を使うことが推奨されます。
