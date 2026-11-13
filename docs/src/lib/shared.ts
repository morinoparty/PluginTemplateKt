export const appName = "PluginName";
// ドキュメントはサイトのルートに置く (トップページがドキュメントの index になる)
export const docsRoute = "/";

export const gitConfig = {
    user: "morinoparty",
    repo: "PluginName",
    branch: "main",
};

export const modrinthUrl = "https://modrinth.com/plugin/pluginname";

// BASE_URL は Vite の base (例: /PluginName/<sha>/docs/、GitHub Pages のカスタムドメインなら /)。静的ファイルの URL には base を付ける
export const searchIndexUrl = `${import.meta.env.BASE_URL}api/search.json`;
