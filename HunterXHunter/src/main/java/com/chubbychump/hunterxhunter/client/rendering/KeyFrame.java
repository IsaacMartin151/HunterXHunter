package com.chubbychump.hunterxhunter.client.rendering;

public class KeyFrame {
    public double time;
    public double x;
    public double y;
    public double z;
    public double rotPitch;
    public double rotYaw;
    public double rotYawHead;

    public KeyFrame(double t, double x1, double y1, double z1, double rotP, double rotYawBody, double rotYawHead1) {
        time = t;
        x = x1;
        y = y1;
        z = z1;
        rotPitch = rotP;
        rotYaw = rotYawBody;
        rotYawHead = rotYawHead1;
    }
}
