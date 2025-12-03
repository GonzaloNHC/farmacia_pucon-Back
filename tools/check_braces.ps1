Get-ChildItem -Path . -Recurse -Filter *.java | ForEach-Object {
    $text = Get-Content -Raw -Path $_.FullName
    $opens = ([regex]::Matches($text,'\{')).Count
    $closes = ([regex]::Matches($text,'\}')).Count
    if ($opens -ne $closes) {
        Write-Output "$($_.FullName): opens=$opens closes=$closes"
    }
}