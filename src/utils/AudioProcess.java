package utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/*
 音频处理类，负责背景音乐和音效的播放与停止。
 音乐文件位于 resource/audio/ 目录下，格式定为 WAV！
 BGM循环，音效每次播放均创建新的 Clip
 */
public class AudioProcess {

    //音频存放目录
    private static final String AUDIO_DIR = "resource\\audio\\";
    //BGM：带循环
    private static Clip bgmClip;
    private static boolean bgmPlaying = false;

    // ---------- 背景音乐控制 ----------

    /**
     * 播放背景音乐（无限循环）。如果已在播放，会先停止再从头播放。
     */
    public static void playBgm() {
        stopBgm(); // 确保停止当前播放并重置
        bgmClip = loadClip("bgm.wav");
        if (bgmClip != null) {
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmPlaying = true;
        }
    }

    /**
     * 停止背景音乐，并将播放位置重置到开头（下次播放从头开始）。
     */
    public static void stopBgm() {
        if (bgmClip != null) {
            if (bgmClip.isRunning()) {
                bgmClip.stop();
            }
            bgmClip.setFramePosition(0);
            bgmPlaying = false;
        }
    }

    //音效

    /** 播放消除音效 */
    public static void playClear() {
        playSoundEffect("clear.wav");
    }

    /** 播放切换/操作音效 */
    public static void playChange() {
        playSoundEffect("audio_change.wav");
    }

    // ---------- 内部工具方法 ----------

    /**
     * 播放指定音效文件。该方法会创建一个新的 Clip 并播放一次，
     * 播放完毕后自动关闭 Clip 释放资源，因此多个音效可以同时播放。
     */
    private static void playSoundEffect(String fileName) {
        Clip clip = loadClip(fileName);
        if (clip != null) {
            clip.start();
            // 播放完成后自动关闭 Clip，避免资源泄漏
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
        }
    }

    /**
     * 从 resource/audio/ 目录加载指定文件名的音频，返回一个已经打开的 Clip。
     * 如果文件不存在或格式不支持，返回 null。
     */
    private static Clip loadClip(String fileName) {
        try {
            File file = new File(AUDIO_DIR + fileName);
            if (!file.exists()) {
                System.err.println("文件不存在于: " + file.getPath());
                return null;
            }
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            return clip;

        } catch (UnsupportedAudioFileException e) {
            System.err.println("不支持的音频格式：" + fileName);
        } catch (IOException e) {
            System.err.println("读取错误：" + fileName);
        } catch (LineUnavailableException e) {
            System.err.println("线路问题：" + fileName);
        }
        return null;
    }

    /**
     * 释放所有音频资源（程序退出时调用）。
     */
    public static void release() {
        stopBgm();
        if (bgmClip != null && bgmClip.isOpen()) {
            bgmClip.close();
        }
    }
}