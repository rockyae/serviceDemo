@echo off
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0kill-ports.ps1" %*
exit /b %ERRORLEVEL%
