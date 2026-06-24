# Kill ports on Windows
# Usage:
#   .\kill-ports.ps1
#   .\kill-ports.ps1 8080 8082 9000
#   .\kill-ports.ps1 -Force
#   .\kill-ports.ps1 -Help

param(
    [Parameter(ValueFromRemainingArguments = $true)]
    [int[]]$Ports = @(8848,9848,4001,3000,9999,9090),
    [switch]$Force,
    [switch]$Help
)

$ErrorActionPreference = "Stop"

$PortHints = @{
    8082 = "core"
    9000 = "gateway"
    8848 = "Nacos"
    3306 = "MySQL"
}

function Show-Help {
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host "  kill-ports" -ForegroundColor Cyan
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Usage:" -ForegroundColor Yellow
    Write-Host "  .\kill-ports.ps1              default ports 8082,9000,8848"
    Write-Host "  .\kill-ports.ps1 8080 8082    custom ports"
    Write-Host "  .\kill-ports.ps1 -Force       kill without confirm"
    Write-Host "  .\kill-ports.ps1 -Help        show help"
    Write-Host ""
    Write-Host "Default ports:" -ForegroundColor Yellow
    foreach ($entry in $PortHints.GetEnumerator() | Sort-Object Name) {
        Write-Host ("  {0,-6} -> {1}" -f $entry.Key, $entry.Value)
    }
    Write-Host ""
    Write-Host "Tips:" -ForegroundColor Yellow
    Write-Host "  - Fix 'Address already in use' on core/gateway"
    Write-Host "  - Run PowerShell as Administrator if access denied"
    Write-Host ""
}

function Get-PortHint {
    param([int]$Port)
    if ($PortHints.ContainsKey($Port)) { return $PortHints[$Port] }
    return "-"
}

function Get-PortOwners {
    param([int]$Port)

    $connections = @()
    try {
        $connections = Get-NetTCPConnection -LocalPort $Port -ErrorAction SilentlyContinue |
            Where-Object { $_.State -in @("Listen", "Bound", "Established") }
    }
    catch { }

    if (-not $connections -or $connections.Count -eq 0) {
        $connections = netstat -ano | Select-String ":$Port\s" | ForEach-Object {
            $parts = ($_ -replace '\s+', ' ').Trim().Split(' ')
            if ($parts.Length -ge 5 -and $parts[-1] -match '^\d+$') {
                [PSCustomObject]@{
                    LocalPort     = $Port
                    State         = $parts[3]
                    OwningProcess = [int]$parts[-1]
                }
            }
        }
    }

    $connections | Select-Object OwningProcess, State -Unique
}

function Get-ProcessDetail {
    param([int]$ProcessId, [int]$Port, [string]$State)

    $name = "(unknown)"
    $path = "-"
    $startTime = "-"
    $commandLine = "-"

    try {
        $proc = Get-Process -Id $ProcessId -ErrorAction Stop
        $name = $proc.ProcessName
        $path = if ($proc.Path) { $proc.Path } else { "-" }
        $startTime = if ($proc.StartTime) { $proc.StartTime.ToString("yyyy-MM-dd HH:mm:ss") } else { "-" }
    }
    catch { }

    try {
        $wmi = Get-CimInstance Win32_Process -Filter "ProcessId=$ProcessId" -ErrorAction SilentlyContinue
        if ($wmi -and $wmi.CommandLine) {
            $commandLine = $wmi.CommandLine
            if ($commandLine.Length -gt 120) {
                $commandLine = $commandLine.Substring(0, 117) + "..."
            }
        }
    }
    catch { }

    [PSCustomObject]@{
        Port        = $Port
        Hint        = Get-PortHint -Port $Port
        State       = $State
        Pid         = $ProcessId
        Name        = $name
        Path        = $path
        StartTime   = $startTime
        CommandLine = $commandLine
    }
}

function Test-PortFree {
    param([int]$Port)
    $owners = Get-PortOwners -Port $Port
    return (-not $owners -or @($owners).Count -eq 0)
}

function Show-PortStatus {
    param(
        [int[]]$PortList,
        [string]$Title,
        [ConsoleColor]$Color = "White"
    )

    Write-Host ""
    Write-Host $Title -ForegroundColor $Color
    foreach ($port in ($PortList | Sort-Object -Unique)) {
        $hint = Get-PortHint -Port $port
        if (Test-PortFree -Port $port) {
            Write-Host ("  [FREE] port {0} ({1})" -f $port, $hint) -ForegroundColor Green
        }
        else {
            Write-Host ("  [BUSY] port {0} ({1})" -f $port, $hint) -ForegroundColor Red
        }
    }
}

if ($Help) {
    Show-Help
    exit 0
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  kill-ports - scanning..." -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ("Time   : {0}" -f (Get-Date -Format "yyyy-MM-dd HH:mm:ss"))
Write-Host ("Ports  : {0}" -f ($Ports -join ", "))
Write-Host ("Mode   : {0}" -f ($(if ($Force) { "Force" } else { "Confirm" })))
Write-Host ""

$targets = @()
$freePorts = @()

foreach ($port in ($Ports | Sort-Object -Unique)) {
    $hint = Get-PortHint -Port $port
    Write-Host (">> Check port {0} ({1}) ..." -f $port, $hint) -ForegroundColor DarkGray

    $owners = @(Get-PortOwners -Port $port)
    if ($owners.Count -eq 0) {
        Write-Host ("   OK: port {0} is free" -f $port) -ForegroundColor Green
        $freePorts += $port
        continue
    }

    foreach ($owner in $owners) {
        $processId = [int]$owner.OwningProcess
        if ($processId -le 0) { continue }

        $state = if ($owner.State) { $owner.State } else { "Listen" }
        $detail = Get-ProcessDetail -ProcessId $processId -Port $port -State $state
        $targets += $detail

        Write-Host ("   BUSY: port {0} -> PID {1}, name {2}, state {3}" -f `
            $port, $detail.Pid, $detail.Name, $detail.State) -ForegroundColor Yellow
    }
}

Write-Host ""
Write-Host "----------------------------------------" -ForegroundColor DarkGray
Write-Host ("Scan done: {0} ports checked, {1} free, {2} busy records" -f `
    ($Ports | Sort-Object -Unique).Count, $freePorts.Count, $targets.Count)

if ($targets.Count -eq 0) {
    Show-PortStatus -PortList $Ports -Title "Final status:" -Color Green
    Write-Host ""
    Write-Host "All ports are free. You can start services now." -ForegroundColor Green
    Write-Host "  core     -> 8082"
    Write-Host "  gateway  -> 9000"
    Write-Host "  nacos    -> 8848"
    exit 0
}

Write-Host ""
Write-Host "Process details:" -ForegroundColor Yellow
$targets | Sort-Object Port, Pid | Format-Table Port, Hint, State, Pid, Name, StartTime, Path -AutoSize

$hasCommandLine = $targets | Where-Object { $_.CommandLine -ne "-" }
if ($hasCommandLine) {
    Write-Host "Command line:" -ForegroundColor Yellow
    $hasCommandLine | ForEach-Object {
        Write-Host ("  PID {0}: {1}" -f $_.Pid, $_.CommandLine) -ForegroundColor DarkGray
    }
}

Write-Host ""
Write-Host "Tips:" -ForegroundColor Cyan
Write-Host "  - Restart core/gateway after killing Java processes"
Write-Host "  - Run as Administrator if Access denied"
Write-Host "  - Manual check: netstat -ano | findstr :8082"
Write-Host "  - Manual kill  : taskkill /PID <pid> /F"
Write-Host ""

if (-not $Force) {
    $answer = Read-Host "Kill above processes? [y/N]"
    if ($answer -notin @("y", "Y", "yes", "YES")) {
        Write-Host ""
        Write-Host "Cancelled. No process was killed." -ForegroundColor Cyan
        exit 0
    }
}

Write-Host ""
Write-Host "Killing processes ..." -ForegroundColor Yellow

$killed = 0
$failed = @()

foreach ($item in ($targets | Sort-Object Pid -Unique)) {
    try {
        Stop-Process -Id $item.Pid -Force -ErrorAction Stop
        Write-Host ("  [OK] killed PID {0} ({1}), freed port {2} ({3})" -f `
            $item.Pid, $item.Name, $item.Port, $item.Hint) -ForegroundColor Green
        $killed++
    }
    catch {
        Write-Host ("  [FAIL] PID {0} ({1}) port {2}: {3}" -f `
            $item.Pid, $item.Name, $item.Port, $_.Exception.Message) -ForegroundColor Red
        $failed += $item
    }
}

Write-Host ""
Write-Host "----------------------------------------" -ForegroundColor DarkGray
Write-Host ("Result: {0} killed, {1} failed" -f $killed, $failed.Count)

Show-PortStatus -PortList $Ports -Title "Final port status:"

if ($failed.Count -gt 0) {
    Write-Host ""
    Write-Host "Manual kill commands:" -ForegroundColor Red
    $failed | ForEach-Object {
        Write-Host ("  taskkill /PID {0} /F    # port {1} ({2}), {3}" -f $_.Pid, $_.Port, $_.Hint, $_.Name)
    }
    Write-Host ""
    Write-Host "If still failing, run PowerShell as Administrator." -ForegroundColor Yellow
    exit 1
}

Write-Host ""
Write-Host "Ports released. You can restart services." -ForegroundColor Green
exit 0
