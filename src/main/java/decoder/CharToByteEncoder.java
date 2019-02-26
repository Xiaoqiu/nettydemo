package decoder;
/**
 * @author kate
 * @create 2019/2/26
 * @since 1.0.0
 */

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @ClassName CharToByteEncoder
 * @Description 字符到字节编码器
 * @Author Huang Xiaoqiu
 * @Date 2019/2/26 16:06
 * @Version 1.0.0
 **/
public class CharToByteEncoder extends MessageToByteEncoder<Character> {

  //将Character解码为char，并将其写入到出站ByteBuf中
  @Override
  protected void encode(ChannelHandlerContext ctx, Character msg, ByteBuf out) throws Exception {
    out.writeChar(msg);
  }

}