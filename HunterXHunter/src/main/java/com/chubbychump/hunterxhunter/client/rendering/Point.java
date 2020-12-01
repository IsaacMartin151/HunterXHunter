package com.chubbychump.hunterxhunter.client.rendering;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Point {
    public float nx = 0;
    public float ny = 0;
    public float nz = 0;
    public float s = 0;
    public float t = 0;
    public float x = 0;
    public float y = 0;
    public float z = 0;

    public Point() { }

    public void setTheNumbers(float nx, float ny, float nz, float s, float t, float x, float y, float z) {
        this.nx = nx;
        this.ny = ny;
        this.nz = nz;
        this.s = s;
        this.t = t;
        this.x = x;
        this.y = y;
        this.z = z;
    }
}