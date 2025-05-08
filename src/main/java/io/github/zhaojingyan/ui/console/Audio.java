package io.github.zhaojingyan.ui.console;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

public class Audio {
    public Audio() {
        // 构造函数可留空或初始化资源
    }

public void playSound(String name) {
    try (InputStream audioStream = Audio.class
            .getClassLoader()
            .getResourceAsStream("audio/" + name + ".wav")) {
        
        if (audioStream == null) {
            throw new IllegalStateException("音频文件未找到: audio/bomb.wav");
        }

        // 使用 BufferedInputStream 包裹原始流
        try (BufferedInputStream bufferedStream = new BufferedInputStream(audioStream);
             AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedStream)) {
            
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
            
            clip.start();
            clip.drain(); // 等待播放完成
        }
    } catch (Exception e) {
        System.err.println("播放失败: " + e.getMessage());
    }
}
}