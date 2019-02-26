package decoder;
/**
 * @author kate
 * @create 2019/2/25
 * @since 1.0.0
 */

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.ReferenceCountUtil;

import java.util.List;

/**
 * @ClassName ToIntegerDecoder
 * @Description 将字节解码为特定的格式，int字节流，从入站ByteBuf中读取每个int
 * @Author Huang Xiaoqiu
 * @Date 2019/2/25 15:28
 * @Version 1.0.0
 **/
public class ToIntegerDecoder extends ByteToMessageDecoder {

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    //检测是否至少4字节（一个int的字节长度）刻度，
    if (in.readableBytes() >= 4) {
      //ByteBuf中读取一个int,并添加到解码消息List中
        out.add(in.readInt());
    }
  }
}