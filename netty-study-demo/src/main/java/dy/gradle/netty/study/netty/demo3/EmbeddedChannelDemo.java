package dy.gradle.netty.study.netty.demo3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * 类的描述
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/7/29
 */
public class EmbeddedChannelDemo {

    public static void main(String[] args) {

        testFixedLengthFrameDecoder();
    }

    /* ---------------------------------------------------------------------------------------------------- */
    public static void testAbsIntegerEncoder() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 1; i < 10; i++) {
            buf.writeInt(i * -1);
        }

        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());

        channel.writeOutbound(buf);
        channel.finish();

        // read bytes
        for (int i = 1; i < 10; i++) {
            System.out.println((Integer) channel.readOutbound());
        }
    }

    static class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {

        @Override
        protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {

            while (msg.readableBytes() >= 4) {
                int value = Math.abs(msg.readInt());
                out.add(value);
            }
        }
    }

    /* ---------------------------------------------------------------------------------------------------- */

    public static void testFixedLengthFrameDecoder () {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder());

        try {
            System.out.println(channel.writeInbound(input.readBytes(2)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(channel.writeInbound(input.readBytes(7)));

        channel.finish();

        // read
        ByteBuf read = (ByteBuf) channel.readInbound();
        System.out.println(buf.readSlice(3).equals(read));
        read.release();

        read = (ByteBuf) channel.readInbound();
        System.out.println(buf.readSlice(3).equals(read));
        read.release();

        read = (ByteBuf) channel.readInbound();
        System.out.println(buf.readSlice(3).equals(read));
        read.release();

        System.out.println(null == channel.readInbound());
        buf.release();


    }

    /**
     * 入站处理器
     *
     * 检测入站ByteBuf够不够frameLength bytes的数据，够的话，就每frameLength个bytes封装成一个整体进行解码
     */
    static class FixedLengthFrameDecoder extends ByteToMessageDecoder {

        final int frameLength = 3;

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            while (in.readableBytes() >= frameLength) {
                ByteBuf buf = in.readBytes(frameLength);
                out.add(buf);
            }
        }
    }
}
