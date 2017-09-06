package monica.files.socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;
import monica.registry.context.RegistryContext;

/**
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */
public class FileClientInitializer extends ChannelInitializer<SocketChannel> {
	private final String SERVER_IP_CACHE = "server_ip";
	private final String SERVER_PORT_CACHE = "server_port";
	private String ip = (String) RegistryContext.clientCache.get(SERVER_IP_CACHE);
	private int port = Integer.valueOf((String) RegistryContext.clientCache.get(SERVER_PORT_CACHE));

	// private static final ObjectDecoder DECODER = new
	// ObjectDecoder(1024*1024,ClassResolvers.weakCachingResolver());
	// private static final ObjectEncoder ENCODER = new ObjectEncoder();

	private static final StringDecoder DECODER = new StringDecoder();
	private static final StringEncoder ENCODER = new StringEncoder();

	private static final FileClientHandler CLIENT_HANDLER = new FileClientHandler();

	private final SslContext sslCtx;

	public FileClientInitializer(SslContext sslCtx) {
		this.sslCtx = sslCtx;
	}

	@Override
	public void initChannel(SocketChannel ch) {
		ChannelPipeline pipeline = ch.pipeline();

		if (sslCtx != null) {
			pipeline.addLast(sslCtx.newHandler(ch.alloc(), ip, port));
		}

		// Add the text line codec combination first,
		pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		// pipeline.addLast(new
		// ObjectDecoder(1024*1024,ClassResolvers.weakCachingResolver(this.getClass().getClassLoader())));
		// pipeline.addLast(ENCODER);

		pipeline.addLast(DECODER);
		pipeline.addLast(ENCODER);

		// and then business logic.
		pipeline.addLast(CLIENT_HANDLER);
	}
}