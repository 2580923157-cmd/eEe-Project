/*
*为了能够在打包之后依然正确运行和读取文件，使用了这个文件读取
package utils;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ResourceProcess {
    */
/** 从类路径加载 ImageIcon *//*

    public static ImageIcon loadIcon(String path) {
        URL url = ResourceProcess.class.getClassLoader().getResource(path);
        if (url == null) {
            System.err.println("资源未找到: " + path);
            return null;
        }
        return new ImageIcon(url);
    }

    */
/** 从类路径加载 Image *//*

    public static Image loadImage(String path) {
        ImageIcon icon = loadIcon(path);
        if (icon == null) return null;
        return icon.getImage();
    }
}
*/

package utils;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
/**
 *为了能够在打包之后依然正确运行和读取文件，使用了这个文件读取</br>
 * 注意！打包之后，这个根目录就是resource，后边加上下面的子目录即可*/
public class ResourceProcess {

    /** 从类路径加载 ImageIcon
     * @param path 文件路径*/
    public static ImageIcon loadIcon(String path) {
        URL url = ResourceProcess.class.getClassLoader().getResource(path);
        if (url == null) {
            System.err.println("资源未找到于: " + path);
            return null;
        }
        return new ImageIcon(url);
    }

    /** 从类路径加载 Image
     * @param path 文件路径*/
    public static Image loadImage(String path) {
        ImageIcon icon = loadIcon(path);
        return icon != null ? icon.getImage() : null;
    }

    /**
     * 从类路径加载音频，返回 AudioInputStream。
     * @param path 音频资源路径，例如 "audio\bgm.wav"
     * @return AudioInputStream，若失败返回 null
     */
    public static AudioInputStream loadAudio(String path) {
        try{
            InputStream stream = ResourceProcess.class.getClassLoader().getResourceAsStream(path);
            if (stream == null) {
                System.err.println("音频资源未找到于: " + path);
                return null;
            }
            // 加缓冲支持 mark/reset，某些音频解码器需要
            BufferedInputStream bis = new BufferedInputStream(stream);
            return AudioSystem.getAudioInputStream(bis);
        } catch (UnsupportedAudioFileException e) {
            System.err.println("不支持的音频格式: " + path + " → " + e.getMessage());
        } catch (IOException e) {
            System.err.println("音频读取错误: " + path + " → " + e.getMessage());
        }

        return null;
    }
}