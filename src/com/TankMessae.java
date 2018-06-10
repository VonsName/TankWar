package com;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class TankMessae implements Msg{
    private int msgType=Msg.TANK_NEW_MSG;
    private Tanks tanks;
    private Main main;
    public TankMessae(Tanks tanks) {
        this.tanks = tanks;
    }

    public TankMessae(Main main) {
        this.main = main;
    }

    public TankMessae() {
    }

    @Override
    public void send(DatagramSocket datagramSocket, String ip, int port){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream outputStream=new DataOutputStream(bos);
        try {
            outputStream.writeInt(msgType);
            outputStream.writeInt(tanks.getX());
            outputStream.writeInt(tanks.getId());
            outputStream.writeInt(tanks.getY());
            outputStream.writeInt(tanks.getDir().ordinal());
            outputStream.writeBoolean(tanks.isGood());
            outputStream.writeBoolean(tanks.isLive());
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
            int x = inputStream.readInt();
            int id = inputStream.readInt();
            if (id==main.myTank.getId()){
                return;
            }
            int y = inputStream.readInt();
            int dir = inputStream.readInt();
            boolean good = inputStream.readBoolean();
            boolean live= inputStream.readBoolean();

            boolean exist=false;
            for (int i=0;i<main.tanksList.size();i++){
                Tanks tanks1 = main.tanksList.get(i);
                if (tanks1.getId()==id){
                    exist=true;
                    break;
                }
            }
            if (!exist){
                Tanks tanks = new Tanks(good, main, Tanks.Direction.values()[dir], x, y);
                TankMessae tankMessae = new TankMessae(main.myTank);
                main.netClient.send(tankMessae);
                tanks.setLive(live);
                tanks.setId(id);
                main.tanksList.add(tanks);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
