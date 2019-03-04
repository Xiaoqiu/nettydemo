# ByteBuf
- 网络数据的基本单位总是字节
- Java NIO 提供ByteBuffer作为字节的容器
- Netty提供ByteBuf替代ByteBuffer
## ByteBuff的API
- 被用户自定义的缓冲区类型扩展
- 通过内置的复合缓冲区实现了透明的零拷贝
- 容量可以按需增长（类似JDK的StringBuilder）
- 在读和写两种模式之间切换不用调用ByteBuffer的flip()方法
- 读和写使用了不同的索引
- 支持链式调用
- 支持引用计数
- 支持池化

## API的详细信息
## 用例
## 内存分配

## 5.2 ByteBuf----Netty的数据容器
- 网络通信涉及字节的序列的移动，需要高效的数据结构
- 不同索引简化数据访问
### 5.2.1 如何工作
- ByteBuff维护两个索引，一个读取，一个写入
- 写入：writeIndex增加
- read或write开头的ByteBuf方法，将会推进其对应的索引
- 以set或get开头的方法不会改变索引
- 可以指定ByteBuff的最大容量，试图移动写索引超过这个值会触发一个异常 ,默认为Integer.MAX_VALUE

### 5.2.2 ByteBuf的使用模式（想象为一个读写数组，由索引控制）
#### 1 堆缓冲区
- 支持数组：将数据存储在JVM的堆空间中。
- 提供快速的分配和释放
- 例子：
```java
ByteBuf = heapBuf = ... ;
//todo
```

## 5.3 字节级操作
### 5.3.1 随机访问索引
- 如同普通数组
- 【注意】：需要一个索引值的方法访问数据，不会改为readerIndex或writeIndex的值
- 改为索引可以用：readIndex(index), writeIndex(index)
- 访问数据
ByteBuf buffer = ...
for (int i = 0; i < buffer.capacity(); i++) {
    byte b = buffer.getByte(i);
    System.out.println((char)b);
}
### 5.3.2 顺序访问索引
- ByteBuff具有读索引，写索引划分为三个区域
    - 可丢弃字节（已读字节）
    - 可读字节
    - 可写字节
- JDK的ByteBuffer只有一个索引，必须调用flip()方法在读取和写入模式之前切换。
### 5.3.3 可丢弃字节

### 5.3.4 
### 5.3.5


