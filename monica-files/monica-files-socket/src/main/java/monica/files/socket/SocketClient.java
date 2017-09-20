package monica.files.socket;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Properties;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import monica.configuration.context.ConfigurationContext;
import monica.framework.Client;
import monica.framework.tools.Utils;
import monica.framework.transport.TransportFile;

/**
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */
public class SocketClient implements Client {
	public final static String NAME = "socket";
	private final String SOCKET_CHANNEL = "channel";
	private volatile boolean isStarted = false;
	static final boolean SSL = System.getProperty("ssl") != null;

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
			Channel ch = b.connect(ip, port).sync().channel();
			ConfigurationContext.propMap.putIfAbsent(SOCKET_CHANNEL, ch);			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}

	public boolean isStarted() {
		return this.isStarted;
	}

}
