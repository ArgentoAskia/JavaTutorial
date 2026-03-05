@echo off
chcp 65001 >nul
title Java exec()测试工具
color 0A

:start
cls
echo ========================================
echo     Java exec() 方法测试工具 v1.0
echo ========================================
echo.
echo 当前时间: %date% %time%
echo 当前目录: %cd%
echo.
echo 可用命令:
echo  [1]显示系统信息
echo  [2]列出当前目录文件
echo  [3]测试输入参数
echo  [4]模拟长时间运行(测试超时)
echo  [5]故意返回错误码
echo  [6]interactive input
echo  [7]查看环境变量
echo  [8]执行自定义命令
echo  [0]exit

set /p choice="请输入选项 [0-8]: "

if "%choice%"=="1" goto sysinfo
if "%choice%"=="2" goto listfiles
if "%choice%"=="3" goto testargs
if "%choice%"=="4" goto longrun
if "%choice%"=="5" goto errorcode
if "%choice%"=="6" goto interactive
if "%choice%"=="7" goto envvars
if "%choice%"=="8" goto custom
if "%choice%"=="0" goto end

echo 无效选项，请重新输入
pause
goto start

:sysinfo
cls
echo === 系统信息 ===
systeminfo | findstr /B /C:"OS Name" /C:"OS Version" /C:"System Type"
echo.
echo === Java信息 ===
java -version 2>&1
pause
goto start

:listfiles
cls
echo === 当前目录文件列表 ===
dir /B
echo.
dir | find "个文件"
pause
goto start

:testargs
cls
echo === 参数测试 ===
echo 总共收到 %* 个参数
echo.
set count=1
for %%i in (%*) do (
    echo 参数!count!: [%%i]
    set /a count+=1
)
if "%*"=="" echo 没有收到任何参数
echo.
echo 尝试输入一些参数（如：hello world "带空格的参数"）
pause
goto start

:longrun
cls
echo === 模拟长时间运行 ===
echo 进程将持续运行60秒...
echo 可以用来测试waitFor()和超时控制
echo.
for /l %%i in (1,1,60) do (
    echo 已运行 %%i 秒...
    timeout /t 1 /nobreak >nul
)
echo 运行完成！
pause
goto start

:errorcode
cls
echo === 错误码测试 ===
echo 这个命令会返回错误码 5
echo.
echo 请观察Java程序能否捕获到错误码
echo.
exit /b 5
:: 注意：exit /b 5 会退出当前脚本并返回错误码5
:: 实际不会执行到下面的pause
pause
goto start

:interactive
cls
echo === 交互模式 ===
echo 你现在可以直接输入任何命令
echo 输入 'exit' 返回主菜单
echo.
:interactive_loop
set /p cmd=">> "
if "%cmd%"=="exit" goto start
if "%cmd%"=="" goto interactive_loop
echo 执行: %cmd%
echo --------------------
%cmd%
echo --------------------
echo 命令执行完成，返回码: %errorlevel%
goto interactive_loop

:envvars
cls
echo === 环境变量测试 ===
echo.
echo PATH 变量前几项:
echo %PATH% | findstr /B /C:"C:"
echo.
echo JAVA_HOME: %JAVA_HOME%
echo.
echo 所有环境变量:
set
pause
goto start

:custom
cls
echo === 执行自定义命令 ===
echo 请输入要执行的命令（如：ping 127.0.0.1 -n 3）
set /p custom_cmd="命令: "
echo.
echo 执行: %custom_cmd%
echo --------------------
%custom_cmd%
echo --------------------
echo 返回码: %errorlevel%
pause
goto start

:end
cls
echo 感谢使用，再见！
timeout /t 2 >nul
exit /b 0

REM web聊天室
REM Tool接口的设计
REM 轻量级HTTPCaller

REM 补款结算地址选择

REM 补款超时的订单后台更新
