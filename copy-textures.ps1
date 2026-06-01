<#
.SYNOPSIS
    TinkersConstruct 贴图覆盖工具 - 从 1.20.1 复制贴图到附属模组
.DESCRIPTION
    这个脚本帮助你从 TinkersConstruct-1.20.1 复制贴图到 Unofficial-TinkersConstruct-3to2
    以实现贴图覆盖功能。
.NOTES
    运行方式: .\copy-textures.ps1
#>

$ErrorActionPreference = "Stop"

$SourceDir = "E:\Git\TinkersConstruct-1.20.1\src\main\resources\assets\tconstruct"
$TargetDir = "E:\Git\Unofficial-TinkersConstruct-3to2\src\main\resources\assets\tconstruct"

function Show-Menu {
    Clear-Host
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host "  TinkersConstruct 贴图覆盖工具" -ForegroundColor Cyan
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "源目录: $SourceDir" -ForegroundColor Yellow
    Write-Host "目标目录: $TargetDir" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "请选择操作:" -ForegroundColor Green
    Write-Host "  1. 列出所有可用的贴图" -ForegroundColor White
    Write-Host "  2. 复制指定贴图" -ForegroundColor White
    Write-Host "  3. 复制整个类别的贴图" -ForegroundColor White
    Write-Host "  4. 查看已复制的贴图" -ForegroundColor White
    Write-Host "  5. 退出" -ForegroundColor White
    Write-Host ""
}

function List-Textures {
    param(
        [string]$Category
    )
    
    $searchPath = Join-Path $SourceDir "textures\$Category"
    
    if (Test-Path $searchPath) {
        Write-Host "`n可用的贴图 ($Category):" -ForegroundColor Cyan
        Write-Host "----------------------------------------" -ForegroundColor DarkGray
        
        $textures = Get-ChildItem -Path $searchPath -Recurse -Filter "*.png"
        $i = 1
        foreach ($texture in $textures) {
            $relativePath = $texture.FullName.Substring($SourceDir.Length)
            Write-Host "  $i. $relativePath" -ForegroundColor White
            $i++
        }
        
        Write-Host "`n共找到 $($textures.Count) 个贴图文件" -ForegroundColor Yellow
    }
    else {
        Write-Host "`n未找到类别: $Category" -ForegroundColor Red
    }
}

function Copy-Texture {
    param(
        [string]$SourcePath,
        [string]$TargetPath
    )
    
    $fullSource = Join-Path $SourceDir $SourcePath
    $fullTarget = Join-Path $TargetDir $TargetPath
    
    if (Test-Path $fullSource) {
        $targetDir = Split-Path $fullTarget -Parent
        if (-not (Test-Path $targetDir)) {
            New-Item -ItemType Directory -Path $targetDir -Force | Out-Null
        }
        
        Copy-Item -Path $fullSource -Destination $fullTarget -Force
        Write-Host "成功复制: $SourcePath -> $TargetPath" -ForegroundColor Green
    }
    else {
        Write-Host "源文件不存在: $fullSource" -ForegroundColor Red
    }
}

function Batch-Copy {
    param(
        [string]$Category,
        [string]$TargetCategory
    )
    
    $searchPath = Join-Path $SourceDir "textures\$Category"
    $targetPath = Join-Path $TargetDir "textures\$TargetCategory"
    
    if (Test-Path $searchPath) {
        Write-Host "`n正在复制整个 $Category 类别..." -ForegroundColor Cyan
        
        $textures = Get-ChildItem -Path $searchPath -Recurse -Filter "*.png"
        foreach ($texture in $textures) {
            $relativePath = $texture.FullName.Substring((Join-Path $SourceDir "textures\$Category").Length).TrimStart('\')
            $targetFile = Join-Path $targetPath $relativePath
            
            $targetFileDir = Split-Path $targetFile -Parent
            if (-not (Test-Path $targetFileDir)) {
                New-Item -ItemType Directory -Path $targetFileDir -Force | Out-Null
            }
            
            Copy-Item -Path $texture.FullName -Destination $targetFile -Force
            Write-Host "  复制: $relativePath" -ForegroundColor Gray
        }
        
        Write-Host "`n成功复制 $($textures.Count) 个文件" -ForegroundColor Green
    }
    else {
        Write-Host "源类别不存在: $Category" -ForegroundColor Red
    }
}

function List-Copied {
    if (Test-Path $TargetDir) {
        $textures = Get-ChildItem -Path $TargetDir -Recurse -Filter "*.png"
        Write-Host "`n已复制的贴图:" -ForegroundColor Cyan
        Write-Host "----------------------------------------" -ForegroundColor DarkGray
        
        foreach ($texture in $textures) {
            $relativePath = $texture.FullName.Substring($TargetDir.Length)
            Write-Host "  $relativePath" -ForegroundColor White
        }
        
        Write-Host "`n共 $($textures.Count) 个贴图文件" -ForegroundColor Yellow
    }
    else {
        Write-Host "`n目标目录不存在，尚未复制任何贴图" -ForegroundColor Yellow
    }
}

# 主循环
while ($true) {
    Show-Menu
    $choice = Read-Host "请输入选项 (1-5)"
    
    switch ($choice) {
        "1" {
            Clear-Host
            Write-Host "可用的贴图类别:" -ForegroundColor Cyan
            Write-Host "  block - 方块贴图"
            Write-Host "  entity - 实体贴图"
            Write-Host "  fluid - 流体贴图"
            Write-Host "  gui - GUI 贴图"
            Write-Host "  item - 物品贴图"
            Write-Host "  particle - 粒子贴图"
            Write-Host ""
            $category = Read-Host "输入类别名称"
            List-Textures -Category $category
            Write-Host "`n按回车键继续..."
            $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
        }
        "2" {
            Clear-Host
            Write-Host "复制指定贴图示例:" -ForegroundColor Cyan
            Write-Host "源路径格式: textures/item/tool/pickaxe/head.png"
            Write-Host "目标路径格式: textures/items/pickaxe/head.png"
            Write-Host ""
            $source = Read-Host "输入源路径（相对于 1.20.1 的 tconstruct 目录）"
            $target = Read-Host "输入目标路径（相对于附属模组的 tconstruct 目录）"
            Copy-Texture -SourcePath $source -TargetPath $target
            Write-Host "`n按回车键继续..."
            $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
        }
        "3" {
            Clear-Host
            Write-Host "批量复制类别:" -ForegroundColor Cyan
            Write-Host "  block -> block"
            Write-Host "  item -> items"
            Write-Host "  entity -> entity"
            Write-Host "  fluid -> fluid"
            Write-Host ""
            $sourceCat = Read-Host "输入源类别"
            $targetCat = Read-Host "输入目标类别"
            Batch-Copy -Category $sourceCat -TargetCategory $targetCat
            Write-Host "`n按回车键继续..."
            $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
        }
        "4" {
            List-Copied
            Write-Host "`n按回车键继续..."
            $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
        }
        "5" {
            Write-Host "退出贴图覆盖工具" -ForegroundColor Yellow
            exit
        }
        default {
            Write-Host "无效的选项，请重新输入" -ForegroundColor Red
            Start-Sleep -Seconds 1
        }
    }
}
