本系统使用mysql


请先配置mysql环境变量

Windows 操作系统下:

1. 在“系统变量”下点击“新建”  新建一个名为“MYSQL_HOME”的系统环境变量。其变量的值为MySQL安装根目录，
例: 我的MySQL的路径为 D:\Program Files (x86)\MySQL\MySQL Server 5.7

2. 在“系统变量”的选择框中，找一个名为“path”的环境变量，则在变量值的最后面追加“;%MYSQL_HOME%\bin;”。
这个变量是为了能在dos中，能使用mysql的命令。

3. 验证是否安装成功
打开dos命令，输入mysql，如果提示“ERROR 1045（28000）：....”提示，则说明配置正确


4. 当 用户管理员为root , 密码为root, 连接数据库为posmall时,可以直接双击使用 init_db.cmd 脚本；其他情况时，请使用init_db-h.cmd脚本。

