package com;


import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class NetClient {
    private Main main;
    private int UDP_PORT;

    public int getUDP_PORT() {
        return UDP_PORT;
    }

    public void setUDP_PORT(int UDP_PORT) {
        this.UDP_PORT = UDP_PORT;
    }

    private DatagramSocket datagramSocket;
    public NetClient(Main main) {

        this.main = main;
    }

    public void connect(String ip, int port){
        Socket socket=null;
        try {
            datagramSocket=new DatagramSocket(UDP_PORT);
            socket= new Socket(ip,port);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeInt(UDP_PORT);
            DataInputStream inputStream=new DataInputStream(socket.getInputStream());
            int id = inputStream.readInt();
            main.myTank.setId(id);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        TankMessae tankMessae = new TankMessae(main.myTank);
        send(tankMessae);
        new Thread(new UDPReceiveThread()).start();
    }
    public void send(Msg tankMessage){
        tankMessage.send(datagramSocket,"127.0.0.1",TankServer.UDP_pORT);
    }
    private class UDPReceiveThread implements Runnable{

        @Override
        public void run() {
            byte[] buff=new byte[1024];
            while (true){
                try {
                    DatagramPacket db = new DatagramPacket(buff,buff.length);
                    datagramSocket.receive(db);
                    parse(db);
                    System.out.println("收到消息---");
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void parse(DatagramPacket db) {
            ByteArrayInputStream bis=new ByteArrayInputStream(db.getData());
            DataInputStream inputStream=new DataInputStream(bis);
            try {
                int i = inputStream.readInt();
                Msg msg=null;
                switch (i){
                    case Msg.TANK_NEW_MSG:
                        msg = new TankMessae(NetClient.this.main);
                        msg.parse(inputStream);
                        break;
                    case Msg.TANK_MOVE_MSG:
                         msg=  new TankMoveMsg(NetClient.this.main);
                         msg.parse(inputStream);
                         break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
