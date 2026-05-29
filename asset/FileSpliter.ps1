# Split-Maps.ps1
$easyFile = "C:\Users\MDG\Desktop\maps\easy_all.txt"
$hardFile = "C:\Users\MDG\Desktop\maps\hard_all.txt"

$rootDir = "E:\eEe-Project\resource\maps"
$easyDir = Join-Path $rootDir "easy"
$hardDir = Join-Path $rootDir "hard"

function Split-MapFile {
    param([string]$inputFile, [string]$outputPrefix, [string]$outputDir)
    
    if (-not (Test-Path $inputFile)) {
        Write-Host "File not found: $inputFile" -ForegroundColor Red
        return
    }
    
    New-Item -ItemType Directory -Force -Path $outputDir | Out-Null
    
    $content = Get-Content -Path $inputFile -Raw
    $maps = $content -split "\r?\n\s*\r?\n"
    $count = 0
    foreach ($map in $maps) {
        if ($map.Trim() -eq "") { continue }
        $count++
        $fileName = "{0}{1}.txt" -f $outputPrefix, $count   # no leading zero
        $outFile = Join-Path $outputDir $fileName
        $map | Out-File -FilePath $outFile -Encoding ascii -NoNewline
        Add-Content -Path $outFile -Value "`r`n" -NoNewline
        Write-Host "Generated: $outFile"
    }
    Write-Host "Done! Generated $count files in $outputDir" -ForegroundColor Green
}

Split-MapFile -inputFile $easyFile -outputPrefix "easy_map_" -outputDir $easyDir
Split-MapFile -inputFile $hardFile -outputPrefix "hard_map_" -outputDir $hardDir

Write-Host "All processing completed." -ForegroundColor Cyan