package org.example;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketHandler {
    public String ReadMessageFromSocket(SocketChannel socketChannel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        StringBuilder response = new StringBuilder();

        int bytesRead;
        while ((bytesRead = socketChannel.read(buffer)) > 0) {
            buffer.flip(); // переключение буфера в режим чтения
            while (buffer.hasRemaining()) {
                response.append((char) buffer.get());
            }
            buffer.clear();
        }

        if (bytesRead == -1) {
            socketChannel.close();
            throw new IOException("Connection refused(sad)");
        }


        String result = response.toString();
        return result;
    }

    public void WriteToSocket(SocketChannel socketChannel, String message) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());

        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
    }
}
