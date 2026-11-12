import { createFileRoute, redirect } from "@tanstack/react-router";

// 開発サーバー用。ビルドでは public/index.html がトップページになる
export const Route = createFileRoute("/")({
    beforeLoad: () => {
        throw redirect({ to: "/docs/$/", params: { _splat: "" } });
    },
});
