/**
 * Copyright (C): 恒大集团版权所有 Evergrande Group
 * FileName: HelloClient
 * Author:   huangxiaoqiu
 * Date:     2018-11-06 11:15
 * Description:
 */
/**
 * @author kate
 * @create 2018/11/6
 * @since 1.0.0
 */

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 *  netty client
 * @author huangxiaoqiu
 * @since 1.0.0
 */
public class HelloClient {
  public static void main(String[] args) {
    //worker负责读写数据
    EventLoopGroup worker = new NioEventLoopGroup();
    try {
      //辅助启动类
      Bootstrap bootstrap = new Bootstrap();
      //设置线程池
      bootstrap.group(worker);
      //设置socket工厂
      bootstrap.channel(NioSocketChannel.class);
      //设置管道
      bootstrap.handler(new ChannelInitializer<SocketChannel>() {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
          //获取管道
          ChannelPipeline pipeline = socketChannel.pipeline();
          //字符串解码器
          pipeline.addLast(new StringDecoder());
          //字符串编码器
          pipeline.addLast(new StringEncoder());
          //处理类
          pipeline.addLast(new ClientHandler4());

        }
      });
      //发起异步连接操作
      ChannelFuture future = bootstrap.connect(new InetSocketAddress("127.0.0.1", 8866));
      //等客户端链路关闭
      future.channel().closeFuture().sync();

    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
        //优雅的退出，释放NIO线程组
        worker.shutdownGracefully();
    }
  }

  /**
   *
   */
  static class ClientHandler4 extends SimpleChannelInboundHandler<String>{

    /**
     * 接受服务端发来的消息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
      System.out.print("server response : " + msg + "\n");
    }
    /**
     * 与服务器建立连接
     */

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      //给服务器发消息
      ctx.channel().writeAndFlush("i am client !");
      System.out.print("channelActive\n");
      //logger.error("channelActive");
    }

    /**
     * 与服务器断开连接
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
      System.out.print("channelInactive\n");
      //logger.error("channelInactive");
    }

    /**
     *  异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      //关闭管道
      ctx.channel().close();
      //打印异常信息
      cause.printStackTrace();
    }

  }

}