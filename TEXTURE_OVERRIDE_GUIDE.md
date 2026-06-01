# TinkersConstruct 贴图覆盖指南

## 概述

本文档说明如何使用 TinkersConstruct-1.20.1 的贴图覆盖 TinkersConstruct-1.12 的同名物品材质。

## 原理

Minecraft 1.12 的资源加载机制会自动处理同名资源的覆盖。当附属模组中存在与主模组相同路径的资源文件时，附属模组的文件会优先加载。

## 目录结构

```
Unofficial-TinkersConstruct-3to2/
├── src/main/resources/
│   ├── assets/
│   │   ├── tconstruct/          ← 1.20.1 贴图覆盖目录
│   │   │   └── textures/
│   │   │       ├── items/       ← 物品贴图（对应 1.12 的 items）
│   │   │       ├── blocks/      ← 方块贴图（对应 1.12 的 blocks）
│   │   │       └── ...
│   │   └── tconfuture/          ← 附属模组原有内容
│   └── pack.mcmeta              ← 资源包配置（已存在）
├── copy-textures.ps1            ← 贴图复制工具
└── texture_mappings.json        ← 贴图路径映射配置
```

## 使用方法

### 方法 1：使用复制脚本（推荐）

1. 打开 PowerShell
2. 切换到项目目录：
   ```powershell
   cd E:\Git\Unofficial-TinkersConstruct-3to2
   ```

3. 运行脚本：
   ```powershell
   .\copy-textures.ps1
   ```

4. 根据菜单选择操作：
   - 1: 列出所有可用的贴图
   - 2: 复制指定贴图
   - 3: 复制整个类别的贴图
   - 4: 查看已复制的贴图
   - 5: 退出

### 方法 2：手动复制

1. 找到 1.20.1 中的贴图文件：
   ```
   E:\Git\TinkersConstruct-1.20.1\src\main\resources\assets\tconstruct\textures\
   ```

2. 复制到附属模组的对应位置：
   ```
   E:\Git\Unofficial-TinkersConstruct-3to2\src\main\resources\assets\tconstruct\textures\
   ```

3. 确保路径正确，例如：
   - 1.20.1: `textures/item/materials/cobalt_ingot.png`
   - 1.12: `textures/items/materials/cobalt_ingot.png`

### 方法 3：编辑映射配置文件

编辑 `texture_mappings.json`，添加你想要复制的贴图映射：

```json
[
  {
    "1.20.1_source": "textures/item/materials/cobalt_ingot.png",
    "1.12_target": "textures/items/materials/cobalt_ingot.png"
  }
]
```

## 路径映射规则

### 物品贴图

| 1.20.1 路径 | 1.12 路径 |
|-------------|----------|
| `textures/item/materials/*.png` | `textures/items/materials/*.png` |
| `textures/item/tool/pickaxe/*.png` | `textures/items/pickaxe/*.png` |
| `textures/item/tool/sword/*.png` | `textures/items/sword/*.png` |

### 方块贴图

| 1.20.1 路径 | 1.12 路径 |
|-------------|----------|
| `textures/block/storage/*.png` | `textures/blocks/storage/*.png` |
| `textures/block/ore/*.png` | `textures/blocks/ore/*.png` |

### 工具贴图

工具贴图的路径变化较大，需要根据具体工具类型映射：

- 1.20.1 使用：`textures/item/tool/{tool_type}/{part}.png`
- 1.12 使用：`textures/items/{tool_type}/{part}.png`

## 已完成的示例

- `cobalt_ingot.png` 已从 1.20.1 复制到附属模组
- 位置：`textures/items/materials/cobalt_ingot.png`

## 验证

构建并运行模组后，检查游戏中的贴图是否已替换为 1.20.1 的样式。

## 注意事项

1. **pack.mcmeta 配置**：已配置 `pack_format: 3`（适用于 Minecraft 1.12）
2. **命名空间**：使用 `tconstruct` 命名空间以确保覆盖主模组的贴图
3. **贴图尺寸**：1.20.1 的贴图可能尺寸不同，可能需要调整
4. **构建**：复制贴图后需要重新构建模组
