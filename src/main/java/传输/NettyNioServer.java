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
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @ClassName NettyNioServer
 * @Description 使用netty的非阻塞异步网络处理
 *
 * @Author Huang Xiaoqiu
 * @Date 2019/3/3 23:20
 * @Version 1.0.0
 **/
public class NettyNioServer {
  public void server (int port) throws Exception {
    final ByteBuf byteBuf = Unpooled.copiedBuffer("hi\r\n", Charset.forName("UTF-8"));
    //为非阻塞模式使用
    EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    try {
      //创建serverBootstrap
      ServerBootstrap serverBootstrap = new ServerBootstrap();
      serverBootstrap.group(eventLoopGroup).channel(NioServerSocketChannel.class)
          .localAddress(new InetSocketAddress(port))
          // 指定ChannelInitializer， 对于每个已接受的连接都调用它
          .childHandler(new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
              // 添加ChannelInboundHandlerAdapter以接收处理事件
              ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                @Override
                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                  //将消息写到客户端，并添加ChannelFutureListener，以便消息一被写完就关闭连接
                  ctx.writeAndFlush(byteBuf.duplicate())
                      .addListener(ChannelFutureListener.CLOSE);
                }
              }); // end addLast
            } // end initChannel
          }); // end childHandler

      //绑定服务器以接手连接
      ChannelFuture channelFuture = serverBootstrap.bind().sync();
      channelFuture.channel().closeFuture().sync();
    } finally {
      //释放所有资源
      eventLoopGroup.shutdownGracefully().sync();
    }
  }
}

















