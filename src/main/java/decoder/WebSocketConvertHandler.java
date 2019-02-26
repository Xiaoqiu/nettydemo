package decoder;
/**
 * @author kate
 * @create 2019/2/26
 * @since 1.0.0
 */

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.*;

import java.util.List;

/**
 * @ClassName WebSocketConvertHandler
 * @Description
 * @Author Huang Xiaoqiu
 * @Date 2019/2/26 10:11
 * @Version 1.0.0
 **/
public class WebSocketConvertHandler extends MessageToMessageCodec<WebSocketFrame,
    WebSocketConvertHandler.MyWebSocketFrame> {

  //将MyWebSocketFrame转为指定的WebSocketFrame子类类型
  @Override
  protected void encode(ChannelHandlerContext ctx, MyWebSocketFrame msg, List<Object> out) throws Exception {
    ByteBuf payload = msg.getData().duplicate().retain();
    //实例化为WebSocketFrame的子类
    switch (msg.getType()) {
      case BINARY:
        out.add(new BinaryWebSocketFrame(payload));
        break;
      case TEXT:
        out.add(new TextWebSocketFrame(payload));
        break;
      case CLOASE:
        out.add(new CloseWebSocketFrame(true,0,payload));
        break;
      case CONTINUATION:
        out.add(new ContinuationWebSocketFrame(payload));
        break;
      case PONG:
        out.add(new PongWebSocketFrame(payload));
        break;
      case PING:
        out.add(new PingWebSocketFrame(payload));
        break;
      default:
        throw new IllegalAccessException("Unsuppoted websocket msg" + msg);

    }
  }

  // 将WebSocketFrame解码为MyWebSocketFrame，被设置FrameType
  @Override
  protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
    //保证引用不会被release
    ByteBuf payload = msg.content().duplicate().retain();
    Boolean flag = false;
    if (msg instanceof BinaryWebSocketFrame) {
      flag = true;
      out.add(new MyWebSocketFrame (MyWebSocketFrame.FrameType.BINARY,payload));
    }
    if (msg instanceof CloseWebSocketFrame) {
      flag = true;
      out.add(new MyWebSocketFrame (MyWebSocketFrame.FrameType.CLOASE,payload));
    }
    if (msg instanceof PingWebSocketFrame) {
      flag = true;
      out.add(new MyWebSocketFrame (MyWebSocketFrame.FrameType.PING,payload));
    }
    if (msg instanceof PongWebSocketFrame) {
      flag = true;
      out.add(new MyWebSocketFrame (MyWebSocketFrame.FrameType.PONG,payload));
    }
    if (msg instanceof TextWebSocketFrame) {
      flag = true;
      out.add(new MyWebSocketFrame (MyWebSocketFrame.FrameType.TEXT,payload));
    }
    if (msg instanceof ContinuationWebSocketFrame) {
      flag = true;
      out.add(new MyWebSocketFrame (MyWebSocketFrame.FrameType.CONTINUATION,payload));
    }
    if (flag == false) {
      throw new IllegalStateException("Unsupported web socket msg" + msg);
    }

  }

  public static final class MyWebSocketFrame {
    public enum FrameType {
      BINARY,
      CLOASE,
      PING,
      PONG,
      TEXT,
      CONTINUATION
    }
    private final FrameType type;
    private final ByteBuf data;

    public MyWebSocketFrame(FrameType type, ByteBuf data){
      this.type = type;
      this.data = data;
    }

    public FrameType getType() {
      return type;
    }

    public ByteBuf getData() {
      return data;
    }
  }
}