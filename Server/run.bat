@echo off
@title Running Feather
:start
java -XX:-OmitStackTraceInFastThrow -server -Xms2048m -Xmx4096m -cp bin;data/libs/netty-3.5.2.Final.jar;data/libs/FileStore.jar com.feather.Launcher false false true
) 
taskkill /f /im java.exe
cls
goto start