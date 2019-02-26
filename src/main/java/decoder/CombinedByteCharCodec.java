package decoder;
/**
 * @author kate
 * @create 2019/2/26
 * @since 1.0.0
 */

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * @ClassName CombinedByteCharCodec
 * @Description 编码器，解码器的容器.灵活使用编码器和解码器
 * 通过该解码器和编码器实现参数化CombinedByteCharCodec
 * @Author Huang Xiaoqiu
 * @Date 2019/2/26 15:47
 * @Version 1.0.0
 **/
public class CombinedByteCharCodec extends CombinedChannelDuplexHandler<
    ByteToCharDecoder,CharToByteEncoder> {

  /**
   * 通过该解码器和编码器实现参数化CombinedByteCharCodec
    */
  public CombinedByteCharCodec(){
    super(new ByteToCharDecoder(),new CharToByteEncoder());
  }
}