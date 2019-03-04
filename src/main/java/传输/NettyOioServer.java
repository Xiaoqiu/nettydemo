package 传输;
/**
 * @author kate
 * @create 2019/3/3
 * @since 1.0.0
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @ClassName NettyOioServer
 * @Description 使用Netty的阻塞网络处理
 * @Author Huang Xiaoqiu
 * @Date 2019/3/3 22:58
 * @Version 1.0.0
 **/
public class NettyOioServer {
  public void server(int port) throws Exception {
    // 创建要写数据的ByteBuf
    final ByteBuf byteBuf = Unpooled.unreleasableBuffer(
        Unpooled.copiedBuffer("hi\r\n", Charset.forName("UTF-8"))
    );
    EventLoopGroup group = new OioEventLoopGroup();
    //创建ServerBootstrap
    ServerBootstrap serverBootstrap = new ServerBootstrap();

    try {
      //使用OioEventLoopGroup以允许阻塞模式（旧的IO）
      serverBootstrap
          .group(group)
          .channel(OioServerSocketChannel.class)
          .localAddress(new InetSocketAddress(port))
          // 指定ChannelInitializer，对于每个已接受的连接都调用他
          .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
              ch.pipeline().addLast(
                  //添加一个ChannelInboundHandlerAdapter以来拦截和处理事件
                  new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                      // 写数据，并冲刷到底层传输
                      // 将消息写到客户端，并添加ChannelFutureListener，以便消息被写完就关闭连接
                      ctx.writeAndFlush(byteBuf.duplicate()).addListener(ChannelFutureListener.CLOSE);
                    }
                  }); // pipeline加入handler
            } //初始化完Channel

          }); //serverBootstrap 加入到childHandler

      // 绑定服务器以接受连接
      ChannelFuture future = serverBootstrap.bind().sync();
      future.channel().closeFuture().sync();

    } finally {
      // 释放所有的资源
      group.shutdownGracefully().sync();
    }
  }
}