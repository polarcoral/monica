package monica.files.socket;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class FileServerHandlerInitializer  extends ChannelInitializer<SocketChannel>{
	private final ObjectDecoder DECODER = new ObjectDecoder(4096*4096,ClassResolvers.weakCachingResolver(this.getClass().getClassLoader()));
	private final ObjectEncoder ENCODER = new ObjectEncoder();
	private final FileServerHandler SERVER_HANDLER = new FileServerHandler();
	
	

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

	
		pipeline.addLast(DECODER);
		pipeline.addLast(ENCODER);	
		
		// and then business logic.
		pipeline.addLast(SERVER_HANDLER);

	}
	
	
	 @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
	        cause.printStackTrace();

	        if (ctx.channel().isActive()) {
	            ctx.writeAndFlush("ERR: " +
	                    cause.getClass().getSimpleName() + ": " +
	                    cause.getMessage() + '\n').addListener(ChannelFutureListener.CLOSE);
	        }
	    }
}
