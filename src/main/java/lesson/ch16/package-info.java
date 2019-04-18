/**
 * 实战：客户端互聊原理与实现
 *
 * + 多个用户登录
 * + 登录成功，将(username, channel)缓存
 * + 登出，将(username, channel)移除
 * + 消息互发，根据 to_username 查询 channel，然后发送数据
 */
package lesson.ch16;