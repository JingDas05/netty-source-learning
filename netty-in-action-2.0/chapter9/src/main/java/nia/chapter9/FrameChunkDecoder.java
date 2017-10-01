package nia.chapter9;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * Listing 9.5 FrameChunkDecoder
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
public class FrameChunkDecoder extends ByteToMessageDecoder {
    private final int maxFrameSize;

    public FrameChunkDecoder(int maxFrameSize) {
        this.maxFrameSize = maxFrameSize;
    }

    // 如果可读长度大于最大允许长度，抛出异常
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in,
        List<Object> out)
        throws Exception {
        int readableBytes = in.readableBytes();
        if (readableBytes > maxFrameSize) {
            // discard the bytes
            in.clear();
            throw new TooLongFrameException();
        }
        // 全部读取，添加到解码列表中
        ByteBuf buf = in.readBytes(readableBytes);
        out.add(buf);
    }
}
