package com.chubbychump.hunterxhunter.client.gui;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.jcodec.codecs.png.PNGEncoder;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.ColorSpace;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.ColorUtil;
import org.jcodec.scale.RgbToBgr;
import org.jcodec.scale.Transform;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.jcodec.common.model.ColorSpace.RGB;

public class AWTUtil {
    private static final int alphaR = 0xff;
    private static final int alphaG = 0xff;
    private static final int alphaB = 0xff;

    public static void toBufferedImage2(Picture src, BufferedImage dst) {
        byte[] data = ((DataBufferByte) dst.getRaster().getDataBuffer()).getData();
        byte[] srcData = src.getPlaneData(0);
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (srcData[i] + 128);
        }
    }

    public static File writePNG(Picture picture, File pngFile) throws IOException {
        Picture rgb = picture.getColor().equals(RGB) ? picture : convertColorSpace(picture, RGB);
        PNGEncoder encoder = new PNGEncoder();
        ByteBuffer tmpBuf = ByteBuffer.allocate(encoder.estimateBufferSize(rgb));
        ByteBuffer encoded = encoder.encodeFrame(rgb, tmpBuf).getData();
        NIOUtils.writeTo(encoded, pngFile);
        return pngFile;
    }

    public static Picture convertColorSpace(Picture pic, ColorSpace tgtColor) {
        Transform tr = ColorUtil.getTransform(pic.getColor(), tgtColor);
        Picture res = Picture.create(pic.getWidth(), pic.getHeight(), tgtColor);
        tr.transform(pic, res);
        return res;
    }

    public static BufferedImage toBufferedImage(Picture src) {
        if (src.getColor() != ColorSpace.BGR) {
            Picture bgr = Picture.createCropped(src.getWidth(), src.getHeight(), ColorSpace.BGR, src.getCrop());
            if (src.getColor() == RGB) {
                new RgbToBgr().transform(src, bgr);
            } else {
                Transform transform = ColorUtil.getTransform(src.getColor(), RGB);
                transform.transform(src, bgr);
                new RgbToBgr().transform(bgr, bgr);
            }
            src = bgr;
        }
        BufferedImage dst = new BufferedImage(src.getCroppedWidth(), src.getCroppedHeight(),
                BufferedImage.TYPE_3BYTE_BGR);

        if (src.getCrop() == null)
            toBufferedImage2(src, dst);


        return dst;
    }
}
