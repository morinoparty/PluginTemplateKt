import { createRouter as createTanStackRouter } from "@tanstack/react-router";

import { NotFound } from "@/components/not-found";

import { routeTree } from "./routeTree.gen";

export function getRouter() {
    return createTanStackRouter({
        routeTree,
        defaultPreload: "intent",
        scrollRestoration: true,
        defaultNotFoundComponent: NotFound,
        // 静的ホスティングで dir/index.html に解決されるよう、URL は末尾スラッシュ付きにそろえる
        trailingSlash: "always",
    });
}
