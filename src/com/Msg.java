package com;

import java.io.DataInputStream;
import java.net.DatagramSocket;

public interface Msg {
     int TANK_NEW_MSG=1;
     int TANK_MOVE_MSG=2;

    /**
     * 发送消息
     * @param datagramSocket
     * @param ip
     * @param port
     */
     void send(DatagramSocket datagramSocket, String ip, int port);

    /**
     * 解析
     * @param inputStream
     */
     void parse(DataInputStream inputStream);
}
