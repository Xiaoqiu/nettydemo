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
 * @ClassName ShortToByteEncoder
 * @Description 解码器
 * @Author Huang Xiaoqiu
 * @Date 2019/2/26 08:04
 * @Version 1.0.0
 **/
public class ShortToByteEncoder extends MessageToByteEncoder<Short> {

  @Override
  public void encode(ChannelHandlerContext ctx, Short msg, ByteBuf out) throws Exception {
    out.writeShort(msg);
  }
}