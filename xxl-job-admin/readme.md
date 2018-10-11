## 一、修改说明

xxl-job-admin项目：

​	新增

- 接口：/jobgroup/getAll 获取全部任务分组
- 接口：/jobgroup/getByTitle 根据title（名称）获取某一任务分组
- 接口：/jobinfo/trigger/desc 根据任务描述执行某一任务
- RPC接口实现implements AdminBiz：triggerJob(String jobDesc) 根据任务描述执行某一任务
- 校验：保存（增改）任务分组时，任务分组名称唯一性校验
- 校验：保存任务时，任务描述唯一性校验
- 拦截：不对前3个接口进行登录拦截

xxl-job-core项目：

新增

- RPC	接口AdminBiz triggerJob(String jobDesc) 