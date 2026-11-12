import { createPreset, stone } from "@morinoparty/chlorophyll-react/preset";
import { defineConfig } from "@pandacss/dev";
import type { Preset } from "@pandacss/types";

// chlorophyll のプリセットからデザイントークン (CSS 変数) だけを使う
// コンポーネントは fumadocs-ui のものを使うため、レシピは出力しない
const chlorophyll = createPreset({ brandColor: "mori", grayColor: stone, radius: "md" });
const preset: Preset = {
    ...chlorophyll,
    theme: {
        ...chlorophyll.theme,
        extend: Object.fromEntries(
            Object.entries(chlorophyll.theme?.extend ?? {}).filter(
                ([key]) => key !== "recipes" && key !== "slotRecipes",
            ),
        ),
    },
};

// chlorophyll の 12 段階スケールはライトモード固定のため、.dark (fumadocs のダークモード) でダークランプに切り替える
const steps = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
const lightDarkScale = (name: string) =>
    Object.fromEntries(
        steps.flatMap((step) => [
            [step, { value: { base: `{colors.${name}.light.${step}}`, _dark: `{colors.${name}.dark.${step}}` } }],
            [
                `a${step}`,
                { value: { base: `{colors.${name}.light.a${step}}`, _dark: `{colors.${name}.dark.a${step}}` } },
            ],
        ]),
    );

export default defineConfig({
    // リセット CSS は Tailwind (fumadocs-ui) 側のものを使う
    preflight: false,
    // ユーティリティは使わないため、ソースを走査しない
    include: [],
    // CSS 変数名を --mpc-colors-mori-* にする
    prefix: "mpc",
    presets: [preset],
    theme: {
        extend: {
            semanticTokens: {
                colors: {
                    mori: lightDarkScale("mori"),
                    gray: lightDarkScale("gray"),
                },
            },
        },
    },
    outdir: "styled-system",
});
