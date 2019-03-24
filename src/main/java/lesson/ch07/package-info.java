/**
 * 数据传输载体 ByteBuf 介绍
 *
 * ByteBuf:
 *  2个index:
 *      readIndex: 读指针，指针之前的数据已经被读取了，之后的没有被读取
 *      writeIndex: 写指针，指针之前是写过数据的，之后是未写的地址
 *  2个capacity:
 *      capacity: 已经被读取了的字节 + 已经写了但是未读取的字节 + 可写字节
 *      maxCapacity: capacity + 可扩容字节
 *
 * 可以通过 操作两个指针的位置，来实现重复读
 */
package lesson.ch07;