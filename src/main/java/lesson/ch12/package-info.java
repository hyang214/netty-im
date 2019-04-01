/**
 * 实战：构建客户端与服务端 pipeline
 *
 * ByteToMessageDecoder: 用于将byte转换为对象
 * MessageToByteEncoder: 用于将对象转换为byte
 * SimpleChannelInboundHandler: 用于链式调用不同message的处理逻辑
 *      链太长了怎么办，为何不用事务总线，或者map之类的
 *
 * author's opinion: channel->byte->ByteToMessageDecoder->handler->handler2->...handlerN->result->MessageToByteEncoder->byte->channel
 *      handler, handler2, ..., handlerN all are sub class of SimpleChannelInboundHandler
 *          use the design pattern of duty chain
 * my opinion: channel->byte->ByteToMessageDecoder->handleRoute->handle->result->MessageToByteEncoder->byte->channel
 */
package lesson.ch12;