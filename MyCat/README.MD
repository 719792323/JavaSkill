# 资料
1. [官方文档](https://www.yuque.com/ccazhw/ml3nkf/bef923fb8acc57e0f805d45ef7782670)

# 安装
1. 下载mycat的install和jar包
```shell
# 下载源 http://dl.mycat.org.cn/2.0/
# 下载1.20版本
wget http://dl.mycat.org.cn/2.0/install-template/mycat2-install-template-1.20.zip
wget http://dl.mycat.org.cn/2.0/1.20-release/mycat2-1.20-jar-with-dependencies.jar
```
2. 解压后把jar包复制到install包对于的lib文件夹下
```shell
unzip mycat2-install-template-1.20.zip
cp mycat2-1.20-jar-with-dependencies.jar mycat/lib
```
3. 修改bin目录文件权限
```shell
chmod 777 -R mycat/bin
```

# 运行
## docker部署mysql
1. 拉取镜像
注意：mycat2建议使用mysql8以上的版本
```shell
docker pull mysql:8.0.18
```
2. 创建挂载目录(不需要挂载可以不设置)
```shell
mkdir -p /home/docker/mysql8/log
mkdir -p /home/docker/mysql8/data
mkdir -p /home/docker/mysql8/conf.d
```
3. 自定义mysql配置（不需要可以跳过）
```shell
vi /home/docker/mysql8/conf.d/my.cnf
###### [mysql]配置模块 ######
[client]
default-character-set=utf8mb4
socket=/var/lib/mysql/mysql.sock

###### [mysql]配置模块 ######
[mysql]
# 设置MySQL客户端默认字符集
default-character-set=utf8mb4
socket=/var/lib/mysql/mysql.sock

###### [mysqld]配置模块 ######
[mysqld]
port=3306
user=mysql
# 设置sql模式 sql_mode模式引起的分组查询出现*this is incompatible with sql_mode=only_full_group_by，这里最好剔除ONLY_FULL_GROUP_BY
sql_mode=STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION
datadir=/var/lib/mysql
socket=/var/lib/mysql/mysql.sock
server-id = 1

# MySQL8 的密码认证插件 如果不设置低版本navicat无法连接
default_authentication_plugin=mysql_native_password

# 禁用符号链接以防止各种安全风险
symbolic-links=0

# 允许最大连接数
max_connections=1000

# 服务端使用的字符集默认为8比特编码的latin1字符集
character-set-server=utf8mb4

# 创建新表时将使用的默认存储引擎
default-storage-engine=INNODB

# 表名存储在磁盘是小写的，但是比较的时候是不区分大小写
lower_case_table_names=0
max_allowed_packet=16M 

# 设置时区
default-time_zone='+8:00'


```
4. 运行容器（不需要挂载可以不设置-v）
```shell
docker run -d \
-p 3306:3306 \
--name mysql8 \
--restart always \
--privileged=true \
-v /home/docker/mysql8/log:/var/log/mysql \
-v /home/docker/mysql8/data:/var/lib/mysql \
-v /home/docker/mysql8/conf.d:/etc/mysql/conf.d \
-v /etc/localtime:/etc/localtime:ro \
-e MYSQL_ROOT_PASSWORD=123456 \
mysql:8.0.18

```

## 在mycat连接的mysql数据库中添加用户（如果下面的配置文件直接用root，该步骤可以跳过）
```shell
CREATE USER 'mycat'@'%' IDENTIFIED BY '123456';
--必須要複的權限 mysql8才有的
GRANT XA_RECOVER_ADMIN ON *.* TO 'root'@'%';
---視情況賦值權限
GRANT ALL PRIVILEGES ON *.* TO 'mycat'@'%' ;
flush privileges;
```
## 修改mycat的prototype配置
修改user与password，url（ip地址与指定所用的库，库需要先创建好）
```shell
cat conf/datasources/prototypeDs.datasource.json
 
{
    "dbType":"mysql",
    "idleTimeout":60000,
    "initSqls":[],
    "initSqlsGetConnection":true,
    "instanceType":"READ_WRITE",
    "maxCon":1000,
    "maxConnectTimeout":3000,
    "maxRetryCount":5,
    "minCon":1,
    "name":"prototypeDs",
    "password":"123456",
    "type":"JDBC",
    "url":"jdbc:mysql://localhost:3306/mydb1?useUnicode=true&serverTimezone=Asia/Shanghai&characterEncoding=UTF-8",
    "user":"mycat",
    "weight":0
}
```
## mycat命令
```shell
cd mycat/bin
./mycat start
./mycat status
./mycat start 启动
./mycat stop 停止
./mycat console 前台运行
./mycat install 添加到系统自动启动（暂未实现）
./mycat remove 取消随系统自动启动（暂未实现）
./mycat restart 重启服务
./mycat pause 暂停
./mycat status 查看启动状态
```

## 测试用mysql客户端连接启动的mycat
u和p的参数通过conf/users下的文件查看，-h填写mycat部署的ip地址
```shell
mysql -uroot -p123456 -P8066 -h192.168.0.242
```

# 搭建mycat读写分离
## 搭建mysql一主一从（主只负责读从只负责写）
### 主mysql配置的my.cnf
```shell
[mysqld]
# 主服务唯一ID
server-id=1
# 启用二进制日志
log-bin=mysql-bin
# 设置不要复制的数据库(可设置多个)
binlog-ignore-db=information_schema
binlog-ignore-db=mysql
binlog-ignore-db=performance_schema
binlog-ignore-db=sys
# 设置需要复制的数据库(可设置多个)
binlog-do-db=mydb1
# 设置logbin路径，可以绝对路径也可以相对路径
log-bin=mysql-bin
# logbin格式
binlog_format=STATEMENT

# 在docker中部署如果报错尝试添加如下
#pid-file        = /var/run/mysqld/mysqld.pid
#socket          = /var/run/mysqld/mysqld.sock
#datadir         = /var/lib/mysql
#secure-file-priv= NULL

```
```shell
docker run -d \
-p 1000:3306 \
--name mysql1 \
--restart always \
--privileged=true \
-v /home/mycat/onemaster-oneslaver/master/log:/var/log/mysql \
-v /home/mycat/onemaster-oneslaver/master/data:/var/lib/mysql \
-v /home/mycat/onemaster-oneslaver/master/conf.d:/etc/mysql \
-e MYSQL_ROOT_PASSWORD=123456 \
mysql:8.0.18
```
### 从mysql的my.cnf
```shell
[mysqld]
# 从服务唯一ID
server-id=2
# 启用中级日志
relay-log=mysql-relay

# 在docker中部署如果报错尝试添加如下
#pid-file        = /var/run/mysqld/mysqld.pid
#socket          = /var/run/mysqld/mysqld.sock
#datadir         = /var/lib/mysql
#secure-file-priv= NULL

```
```shell
docker run -d \
-p 2000:3306 \
--name mysql2 \
--restart always \
--privileged=true \
-v /home/mycat/onemaster-oneslaver/slaver/log:/var/log/mysql \
-v /home/mycat/onemaster-oneslaver/slaver/data:/var/lib/mysql \
-v /home/mycat/onemaster-oneslaver/slaver/conf.d:/etc/mysql \
-e MYSQL_ROOT_PASSWORD=123456 \
mysql:8.0.18
```
### 检查主mysql的log-bin是否启动
```sql
show variables like 'log_bin';
```
### 主节点为从节点创建访问账户(创建一个slaver1)
```sql
-- 创建
CREATE USER 'slaver1'@'%' IDENTIFIED by '123123';
-- 修改密码
ALTER USER 'slaver1'@'%' IDENTIFIED WITH mysql_native_password by '123123';
-- 设置该账户拥有SLAVE权限
GRANT REPLICATION SLAVE ON *.* to 'slaver1'@'%';
FLUSH PRIVILEGES;
```
### 查看主机当前接入点情况，在从机上执行复制命令
主机上执行
```sql
SHOW MASTER status;
```
从机上执行(其中MASTER_LOG_FILE和MASTER_LOG_POS从SHOW MASTER status获得)
```sql
CHANGE MASTER to MASTER_HOST='192.168.0.243',MASTER_USER='slaver1',MASTER_PASSWORD='123123',MASTER_LOG_FILE='binlog.000002',MASTER_LOG_POS=1454,MASTER_PORT=1000;
start slave;
-- 查看Slave lO Running和Slave SQL Running是否为yes
show slave status;
```
