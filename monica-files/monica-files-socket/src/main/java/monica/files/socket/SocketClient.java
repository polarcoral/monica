package monica.files.socket;

import java.io.File;
import java.io.RandomAccessFile;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import monica.framework.Client;

/**
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */
public class SocketClient implements Client {
	public final static String NAME = "socket";

	static final boolean SSL = System.getProperty("ssl") != null;
	static final String HOST = System.getProperty("host", "127.0.0.1");
	static final int PORT = Integer.parseInt(System.getProperty("port", SSL ? "8992" : "8023"));

	public void start(String ip, int port) throws Exception {
		// Configure SSL.
		final SslContext sslCtx;
		if (SSL) {
			sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
		} else {
			sslCtx = null;
		}

		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).handler(new FileClientInitializer(sslCtx));

			// Start the connection attempt.
			Channel ch = b.connect(HOST, PORT).sync().channel();

			// Read commands from the stdin.
			ChannelFuture lastWriteFuture = null;

			File file = new File("F:\\aa.txt");
			RandomAccessFile raf = null;

			long length = -1;
			try {
				raf = new RandomAccessFile(file, "r");
				length = raf.length();
				System.out.println("file.length--------  " + file.length());
				lastWriteFuture = ch.writeAndFlush("rrrrrrrrrrrrrr");

				// ch.writeAndFlush(new Student());
			} catch (Exception e) {
				ch.writeAndFlush("ERR: " + e.getClass().getSimpleName() + ": " + e.getMessage() + '\n');
				return;
			} finally {
				if (length < 0 && raf != null) {
					raf.close();
				}
			}

			// Wait until all messages are flushed before closing the channel.
			if (lastWriteFuture != null) {
				lastWriteFuture.sync();
			}

			Thread.sleep(10000000);
		} finally {
			group.shutdownGracefully();
		}
	}

}
