package decoder;
/**
 * @author kate
 * @create 2019/2/25
 * @since 1.0.0
 */

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * @ClassName SafeByteToMessageDecoder
 * @Description 安全的解码器, 字节解码
 * @Author Huang Xiaoqiu
 * @Date 2019/2/25 16:16
 * @Version 1.0.0
 **/
public class SafeByteToMessageDecoder extends ByteToMessageDecoder {
  private static final int MAX_FRAME_SIZE = 1024;

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    int readable = in.readableBytes();
    //检查缓冲区是否有超过MAX_FRAME_SIZE个字节
    //帧大小溢出，可变帧大小的协议，这种保护措施特别重要。
    if (readable > MAX_FRAME_SIZE) {
      //挑过所有的可读字节，抛出TooLongFrameException，并通知ChnnalHandler
      in.skipBytes(readable);
      throw new TooLongFrameException("Frame too big");
    }
  }
}