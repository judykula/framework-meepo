
# 概要

整套架构的命名规则以中国古代神话人物拼音生成，全部小写

## meepo

ID Generator ，id发号器。这个是一个server 服务，最好搭配client使用

## 1.0.0

暂时定义meepo为一个"轻量级"的服务，而除了源生SnowFlake的实现之外，都需要额外的支持，比如数据库

- 排除掉baidu的uid-generator，因其基本上与使用SnowFlake实现无区别，且未有维护
- 排除掉美团leaf，在高QPS场景可以使用
- 排除掉滴滴Tinyid，与美团leaf相似，我认为还是美团leaf好点

需要关心两个配置：

```
- sys.snowflake.workid： 机器的编号，默认为1。！如果有多个节点部署的话，必须要"手动配置"这个参数，不能重复
- sys.snowflake.datacenterId：数据中心的编号，默认为1。一般情况下不需要配置，有必要的话也手动配置。
```

### 如何处理时钟回拨问题？

- 记录最近一次生成的ID
- 每次生成新的都要比较，如果不是递增顺序的话，则返回最近的id+1
  - 每次生成的ID步长为"10"
  - 不适用于"平均" qps > 1000的场景

### 升级路线

- 自研实现分段式数据库id分发
- 采用美团leaf

