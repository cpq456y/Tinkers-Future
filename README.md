# Tinkers' Construct Future

TIC2 附属模组，移植 TIC3 特性。

## ⚠️ CustomMeltingRecipe 温度和时间设置警告

使用 `CustomMeltingRecipe` 时，温度和时间的设置需要遵循以下规则：

### 计算公式

```
usableTemp = temperature - 300
progressPerTick = ceil(usableTemp * 8 / fixedTime)
实际熔融时间 = (usableTemp * 8) / progressPerTick
```

### 安全范围

要保持低偏差（<10%），需要满足：

```
fixedTime <= usableTemp * 0.8
```

| 温度 | usableTemp | 安全最大时间 |
|------|-----------|-------------|
| 600  | 300       | 240 ticks   |
| 1000 | 700       | 560 ticks   |
| 1500 | 1200      | 960 ticks   |
| 2000 | 1700      | 1360 ticks  |

### 示例

**正确**：
```java
// 温度1750，usableTemp=1450，安全最大时间=1160 ticks
new CustomMeltingRecipe(input, fluid, 1750, 380);  // ✓ 偏差约2%
```

**错误**：
```java
// 温度500，usableTemp=200，安全最大时间=160 ticks
new CustomMeltingRecipe(input, fluid, 500, 1000);  // ✗ 偏差爆炸！
```

### 注意事项

1. 温度最低为 301（usableTemp >= 1）
2. 时间不能超过 `usableTemp * 8`，否则无法达到预期
3. 温度和时间是近似独立的，偏差在 1-2% 以内
