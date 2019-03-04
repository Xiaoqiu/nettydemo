package websocket;
/**
 * @author kate
 * @create 2019/2/28
 * @since 1.0.0
 */

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * @ClassName WebSocketServerInitializer
 * @Description webSocket协议
 * @Author Huang Xiaoqiu
 * @Date 2019/2/28 10:39
 * @Version 1.0.0
 **/
public class WebSocketServerInitializer extends ChannelInitializer<Channel> {

  @Override
  protected void initChannel(Channel ch) throws Exception {
    //为握手提供聚合的HttpRequest
    ch.pipeline().addLast(new HttpServerCodec(), new HttpObjectAggregator(655536),
        //如果请求的端点是"/websocket",则处理该升级握手
        new WebSocketServerProtocolHandler("/websocket"),
        //处理TextWebSocketFrame
        new TextFrameHandler(),
        //处理BinaryWebSocketFrame
        new BinaryFrameHandler(),
        //处理ContinuationWebSocketFrame
        new ContinuationFrameHandler());
  }

  /**
   *
   */
  public static final class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
      //Handler text frame
    }
  }

  /**
   *
   */
  public static final class BinaryFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {
      //Handler Binary frame
    }
  }


  /**
   *
   */
  public static final class ContinuationFrameHandler extends SimpleChannelInboundHandler<ContinuationWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ContinuationWebSocketFrame msg) throws Exception {
      //Handle continuation frame
    }
  }
}




















