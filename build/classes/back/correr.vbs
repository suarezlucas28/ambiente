Set WshShell = CreateObject("WScript.Shell") 
WshShell.Run chr(34) & "C:\ambiente\src\back\respaldo.bat" & Chr(34), 0 
Set WshShell = Nothing