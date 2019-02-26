package decoder;
/**
 * @author kate
 * @create 2019/2/26
 * @since 1.0.0
 */

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @ClassName ByteToCharDecoder
 * @Description 字节到字符解码器
 * @Author Huang Xiaoqiu
 * @Date 2019/2/26 16:05
 * @Version 1.0.0
 **/
public class ByteToCharDecoder extends ByteToMessageDecoder {

  //将一个或多个Character对象添加到传出的List中
  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
      out.add(in.readChar());
  }
}