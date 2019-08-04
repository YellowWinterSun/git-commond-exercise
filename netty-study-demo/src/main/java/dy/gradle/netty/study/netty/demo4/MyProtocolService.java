package dy.gradle.netty.study.netty.demo4;

import io.netty.channel.ChannelHandlerContext;

/**
 * 消费MyProtocol协议内容的服务接口
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/4
 */
public interface MyProtocolService {

    void process(ChannelHandlerContext ctx, MyProtocol msg);
}
