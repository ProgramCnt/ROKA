package com.trainingBot.api.util;

import java.io.IOException;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

public class MusicConvUtil {
	
	public static void main(String[] args) {
		musicConvert();
	}
	
	public static void musicConvert() {
		try {
			FFmpeg ffmpeg = new FFmpeg("C:\\Users\\MVPC10225\\git\\ROKA\\src\\ffmpeg\\ffmpeg-5.1.2-essentials_build\\bin\\ffmpeg.exe");
			FFprobe ffprobe = new FFprobe("C:\\Users\\MVPC10225\\git\\ROKA\\src\\ffmpeg\\ffmpeg-5.1.2-essentials_build\\bin\\ffprobe.exe");
			FFmpegBuilder builder = new FFmpegBuilder()
				.setInput("input.mp4")
				.overrideOutputFiles(true)
				.addOutput("C:\\Users\\MVPC10225\\git\\ROKA\\src\\ffmpeg\\ffmpeg-5.1.2-essentials_build\\bin\\music\\music.mp4")
			    .setFormat("mp4")
			    .setTargetSize(250_000)
			    .disableSubtitle()
			    .setAudioChannels(1)
			    .setAudioCodec("aac")
			    .setAudioSampleRate(48_000)
			    .setAudioBitRate(32768)
			    .setVideoCodec("libx264")
			    .setVideoFrameRate(24, 1)
			    .setVideoResolution(640, 480)
			    .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
			    .done();
			
			FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

			executor.createJob(builder).run();
			executor.createTwoPassJob(builder).run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
