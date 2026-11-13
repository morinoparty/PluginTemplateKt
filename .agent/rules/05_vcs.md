# Version Control

- 別の作業があったとしても、できるだけすべてのファイルをステージングの対象とすること

## Repository
- [PluginName](https://github.com/morinoparty/PluginName)

## 作業完了後の流れ

- 作業が終わったら、ユーザーに確認を求めずに、以下を続けて行うこと
  1. `task build` でビルドが通ることを確認する (`docs/` を変更した場合は、docs のチェックとビルドも通ることを確認する)
  2. すべての変更をコミットする
  3. ブランチを push する
  4. `gh pr create` で main ブランチに対してプルリクエストを作成する
- 作業が途中で止まった場合や、ユーザーが明示的に「push しない」「PR を作らない」と言った場合はこの限りではない

## PRについて

- プルリクエストの本文は英語で、変更内容と背景を簡潔に書くこと

## Issueについて

- 使えるラベルは `.github/labels.json` で管理している (`sync-label.yml` ワークフローで GitHub のラベルに同期される)
