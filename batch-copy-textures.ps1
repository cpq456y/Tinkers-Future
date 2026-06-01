<#
.SYNOPSIS
    根据 texture_mappings.json 批量复制贴图
.DESCRIPTION
    从 texture_mappings.json 中读取映射配置，自动将 1.20.1 的贴图复制到附属模组
.NOTES
    运行方式: .\batch-copy-textures.ps1
#>

$ErrorActionPreference = "Stop"

$SourceDir = "E:\Git\TinkersConstruct-1.20.1\src\main\resources\assets\tconstruct"
$TargetDir = "E:\Git\Unofficial-TinkersConstruct-3to2\src\main\resources\assets\tconstruct"
$MappingsFile = "E:\Git\Unofficial-TinkersConstruct-3to2\texture_mappings.json"

# 读取映射配置
if (-not (Test-Path $MappingsFile)) {
    Write-Host "错误: 找不到映射配置文件 $MappingsFile" -ForegroundColor Red
    exit 1
}

$mappings = Get-Content $MappingsFile | ConvertFrom-Json

$successCount = 0
$failCount = 0
$skipCount = 0

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  批量复制贴图工具" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

foreach ($category in $mappings) {
    Write-Host "处理类别: $($category.category) - $($category.description)" -ForegroundColor Yellow
    Write-Host "----------------------------------------" -ForegroundColor DarkGray
    
    foreach ($mapping in $category.mappings) {
        $sourcePath = Join-Path $SourceDir $mapping.'1.20.1_source'
        $targetPath = Join-Path $TargetDir $mapping.'1.12_target'
        
        if (Test-Path $sourcePath) {
            $targetDirPath = Split-Path $targetPath -Parent
            if (-not (Test-Path $targetDirPath)) {
                New-Item -ItemType Directory -Path $targetDirPath -Force | Out-Null
            }
            
            Copy-Item -Path $sourcePath -Destination $targetPath -Force
            Write-Host "  [成功] $($mapping.note): $($mapping.'1.20.1_source') -> $($mapping.'1.12_target')" -ForegroundColor Green
            $successCount++
        }
        else {
            Write-Host "  [跳过] $($mapping.note): 源文件不存在 $($mapping.'1.20.1_source')" -ForegroundColor Yellow
            $skipCount++
        }
    }
    Write-Host ""
}

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "复制完成!" -ForegroundColor Cyan
Write-Host "  成功: $successCount" -ForegroundColor Green
Write-Host "  跳过: $skipCount" -ForegroundColor Yellow
Write-Host "  失败: $failCount" -ForegroundColor Red
Write-Host "========================================" -ForegroundColor Cyan
