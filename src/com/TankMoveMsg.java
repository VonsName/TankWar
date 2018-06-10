package com;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * @author ASUS
 */
public class TankMoveMsg implements Msg {
    private int msgType=Msg.TANK_MOVE_MSG;
    private Tanks.Direction dir;
    private int id;
    private Main main;
    private int x;
    private int y;

    public TankMoveMsg(Tanks.Direction dir, int id, Main main) {
        this.dir = dir;
        this.id = id;
        this.main = main;
    }

    public TankMoveMsg(Tanks.Direction dir, int x,int y,int id) {
        this.dir = dir;
        this.id = id;
        this.x=x;
        this.y=y;
    }

    public TankMoveMsg(Main main) {
        this.main = main;
    }

    @Override
    public void send(DatagramSocket datagramSocket, String ip, int port) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream outputStream=new DataOutputStream(bos);
        try {
            outputStream.writeInt(msgType);
            outputStream.writeInt(id);
            outputStream.writeInt(x);
            outputStream.writeInt(y);
            outputStream.writeInt(dir.ordinal());
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = bos.toByteArray();
        DatagramPacket datagramPacket = new DatagramPacket(bytes,bytes.length,new InetSocketAddress(ip,port));
        try {
            datagramSocket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parse(DataInputStream inputStream) {
        try {
            int id = inputStream.readInt();
            if (id==main.myTank.getId()){
                return;
            }
            int x=inputStream.readInt();
            int y=inputStream.readInt();
            int dir = inputStream.readInt();
            Tanks.Direction direction=Tanks.Direction.values()[dir];
            boolean exist=false;
            for (int i=0;i<main.tanksList.size();i++){
                Tanks tanks = main.tanksList.get(i);
                if (id==tanks.getId()){
                    tanks.setX(x);
                    tanks.setY(y);
                    tanks.setDir(direction);
                    exist=true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
