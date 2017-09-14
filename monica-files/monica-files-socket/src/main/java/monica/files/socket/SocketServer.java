package monica.files.socket;

import java.net.InetAddress;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;
import monica.framework.Server;

/**
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */
public class SocketServer implements Server {
	public final static String NAME = "socket";
	private volatile boolean isStarted = false;

	static final boolean SSL = System.getProperty("ssl") != null;
	// Use the same default port with the telnet example so that we can use the
	// telnet client example to access it.
	static final int PORT = Integer.parseInt(System.getProperty("port", SSL ? "8992" : "8023"));
	static String HOST = "";

	private String getHostAddress() throws Exception {
		HOST = InetAddress.getLocalHost().getHostAddress();
		return HOST;
	}

	public void start() throws Exception {
		// Configure SSL.
		final SslContext sslCtx;
		if (SSL) {
			SelfSignedCertificate ssc = new SelfSignedCertificate();
			sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
		} else {
			sslCtx = null;
		}

		// Configure the server.
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 100)
					.handler(new LoggingHandler(LogLevel.INFO)).childHandler(new FileServerHandlerInitializer());

			// Start the server.
			ChannelFuture f = b.bind(getHostAddress(), PORT).sync();
			// System.out.println("server is started "+f.isSuccess());
			setStarted(true);
			// Wait until the server socket is closed.
			f.channel().closeFuture().sync();
		} finally {
			// Shut down all event loops to terminate all threads.
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}

	}

	public static void main(String args[]) {
		try {
			new SocketServer().start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int getPort() {
		return PORT;
	}

	public String getHostIp() throws Exception {
		return getHostAddress();
	}

	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}

	public boolean isStarted() {
		return this.isStarted;
	}

}
