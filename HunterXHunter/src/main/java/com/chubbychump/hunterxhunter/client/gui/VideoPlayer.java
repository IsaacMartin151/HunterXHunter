package com.chubbychump.hunterxhunter.client.gui;

import com.chubbychump.hunterxhunter.HunterXHunter;
import javafx.scene.Scene;
import javafx.scene.media.MediaView;
import java.io.File;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.canvas.Canvas;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import net.minecraft.client.Minecraft;

import static javafx.application.Application.launch;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

import net.minecraft.util.ResourceLocation;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;

public class VideoPlayer {
    public static void play(String path) throws IOException, JCodecException {
        FFmpegFrameGrabber.createDefault("departure.mp4");
        File f = new File(path);
        long time = System.currentTimeMillis();
        FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(f));
        Picture picture;
        while (null != (picture = grab.getNativeFrame())) {
            Path asdf = Paths.get(".");
            //HunterXHunter.LOGGER.info("Current directory is "+asdf.toAbsolutePath());
            //AWTUtil.writePNG(picture, new File("./../src/main/resources/assets/hunterxhunter/textures/bruh.png"));
            AWTUtil.writePNG(picture, new File("resourcepacks/bruh.png"));
        }

    }
}
/*
public class VideoPlayer extends Application {
    public static String path = "file:///"+ Minecraft.getInstance().getFileResourcePacks().getAbsolutePath()+"/departure.mp4";

    public void start(Stage primaryStage) {
        // TODO Auto-generated method stub
        //Initialising path of the media file, replace this with your file path
        //ResourceLocation movie = new ResourceLocation(HunterXHunter.MOD_ID, "textures/gui/title/background/movie.mp4");
        //Instantiating Media class
        Media media = new Media(path);

        //Instantiating MediaPlayer class
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        //by setting this property to true, the Video will be played
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.getOnPlaying();
        Canvas eh = new Canvas();
    }
} */