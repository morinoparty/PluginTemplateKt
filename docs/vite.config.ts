import pandacss from "@pandacss/dev/postcss";
import tailwindcss from "@tailwindcss/vite";
import { tanstackStart } from "@tanstack/react-start/plugin/vite";
import react from "@vitejs/plugin-react";
import { fumadocsMdx } from "fumadocs-mdx/vite";
import { defineConfig, lazyPlugins } from "vite-plus";

// プレビュー環境では /PluginName/<sha>/docs のようなサブパスに配置される (CI が BASE_PATH を渡す)
// Vite の base から TanStack Router の basepath も導出される
const basePath = (process.env.BASE_PATH ?? "").replace(/\/+$/, "");

// 整形・lint の対象外 (依存・生成物)
const ignorePatterns = [
    "**/node_modules/**",
    "**/out/**",
    "**/dist/**",
    "**/public/**",
    "**/.source/**",
    "**/.tanstack/**",
    "**/styled-system/**",
    "**/routeTree.gen.ts",
];

export default defineConfig({
    base: `${basePath}/`,
    fmt: {
        tabWidth: 4,
        printWidth: 120,
        // package.json のキー順は手で管理する
        sortPackageJson: false,
        sortImports: {},
        // content/ (ドキュメント本文と meta.json) は整形しない
        ignorePatterns: [...ignorePatterns, "content/**", "**/*.md", "**/*.mdx", "**/*.yml", "**/*.yaml"],
    },
    lint: {
        plugins: ["eslint", "typescript", "unicorn", "oxc", "react"],
        categories: { correctness: "error" },
        options: { reportUnusedDisableDirectives: "warn" },
        ignorePatterns,
    },
    css: {
        postcss: {
            // chlorophyll のデザイントークンを Panda CSS で生成する (src/styles/tokens.css の @layer 宣言に展開される)
            plugins: [pandacss()],
        },
    },
    resolve: {
        tsconfigPaths: true,
    },
    build: {
        // サーバー側の成果物 (プリレンダー用)。公開するのは client の出力のみ
        outDir: "dist",
    },
    environments: {
        // 静的サイトとして公開するディレクトリ (CI は docs/out をアップロードする)
        client: { build: { outDir: "out" } },
    },
    server: {
        port: 3000,
    },
    // lint / fmt など設定値だけを読むコマンドではプラグインを初期化しない
    plugins: lazyPlugins(() => [
        fumadocsMdx(),
        tailwindcss(),
        tanstackStart({
            prerender: {
                enabled: true,
                // サイドバーなどのリンクをたどってすべてのページを書き出す
                crawlLinks: true,
                // トップページは public/index.html (相対パスで docs/ へ転送する静的ページ) を使う
                // PR コメントは .../docs/index.html を開くため、アプリのルートとしてハイドレーションさせない
                // クロールで見つかるパスには base が付く (404 ページのホームへのリンクなど) ため、base 付きのトップも除外する
                filter: (page) => page.path !== "/" && page.path !== `${basePath}/`,
            },
            // リンクからたどれないルートは明示する
            pages: [
                { path: "/docs" },
                { path: "/api/search.json" },
                { path: "/llms.txt" },
                { path: "/llms-full.txt" },
                // 静的ホスティングが存在しない URL で返すページ (404/index.html ではなく 404.html として出力する)
                { path: "/404", prerender: { autoSubfolderIndex: false } },
            ],
        }),
        react(),
    ]),
});
