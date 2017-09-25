/*
 * Copyright 2017 The Monica Project
 *
 * The Monica Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package monica.examples;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Properties;
import java.util.concurrent.Executors;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import monica.framework.tools.Utils;
import monica.framework.transport.TransportFile;
import monica.starter.client.ClientStarter;

/**
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */
public class MonicaClient {

	public static void main(String args[]) throws FileNotFoundException, IOException {
		Properties pps = new Properties();
		File file = Utils.getPropertiesPath("path.properties");
		pps.load(new FileInputStream(file));
		String filePath = pps.getProperty("client.path");
		ClientStarter clientContainer = new ClientStarter();
		Channel ch;
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@Override
			public void run() {
				try {
					clientContainer.start();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		for (;;) {
			ch = clientContainer.getSocketChannel();
			if (null != ch) {
				break;
			}
		}
		try {

			File[] fileList = new File(filePath).listFiles();
			for (int i = 0; i < fileList.length; i++) {
				FileChannel channel = (new FileInputStream(fileList[i])).getChannel();
				ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
				while (channel.read(byteBuffer) > 0)
					;
				TransportFile transportFile = new TransportFile();
				transportFile.setContent(byteBuffer.array());
				transportFile.setFileName(fileList[i].getName());
				ChannelFuture f = ch.writeAndFlush(transportFile);
				// Wait until the connection is closed.
				f.sync();
				// Thread.sleep(10000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
