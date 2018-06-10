package com;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.LinkedList;
import java.util.List;

public class TankServer {
    public static final int TCP_PORT=8787;
    public static final int UDP_pORT=6666;
    private int id=100;
    private List<Client> clientList=new LinkedList<>();
    public void start(){
        new Thread(new UDPThread()).start();
        Socket accept=null;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(TCP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true){
            try {
                if (serverSocket!=null){
                    accept= serverSocket.accept();
                }
                if (accept==null) {
                    return;
                }
                DataInputStream dataInputStream = new DataInputStream(accept.getInputStream());
                int port = dataInputStream.readInt();
                String ip = accept.getInetAddress().getHostAddress();
                Client client = new Client();
                client.setIP(ip);
                client.setPort(port);
                clientList.add(client);
                DataOutputStream outputStream=new DataOutputStream(accept.getOutputStream());
                outputStream.writeInt(id++);
                System.out.println(ip);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (accept!=null){
                    try {
                        accept.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        TankServer tankServer = new TankServer();
        tankServer.start();
    }
    private class Client{
        private String IP;
        private int port;

        public String getIP() {
            return IP;
        }

        public void setIP(String IP) {
            this.IP = IP;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }
    private class UDPThread implements Runnable{

        @Override
        public void run() {
            byte[] buff=new byte[1024];
            DatagramSocket datagramSocket = null;
            try {
                datagramSocket = new DatagramSocket(UDP_pORT);
            } catch (SocketException e) {
                e.printStackTrace();
            }
            while (true){
                try {

                    DatagramPacket db = new DatagramPacket(buff,buff.length);
                    datagramSocket.receive(db);
                    //接受到消息 转发到其他客户端
                    for (int i = 0; i < clientList.size(); i++) {
                        db.setSocketAddress(new InetSocketAddress
                                (clientList.get(i).getIP(),clientList.get(i).getPort()));
                        datagramSocket.send(db);
                    }
                    System.out.println("收到消息");
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
