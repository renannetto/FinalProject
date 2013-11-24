package ro7.engine.audio;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioManager implements LineListener {

	private static AudioManager instance;
	
	private Map<String, MusicThread> audioThreads = new HashMap<String, MusicThread>();

	// Specialized Thread for streaming the background music
	public class MusicThread extends Thread {

		private String fileName;
		private boolean repeat;
		private boolean stop;

		public MusicThread(String fileName, boolean repeat) {
			this.fileName = fileName;
			this.repeat = repeat;
			this.stop = false;
		}

		@Override
		public void run() {
			if (repeat) {
				while (repeat) {
					playMusic();
				}
			} else {
				playMusic();
			}
		}

		private void playMusic() {
			try {
				AudioInputStream encoded = AudioSystem
						.getAudioInputStream(new File(fileName));
				AudioFormat encodedFormat = encoded.getFormat();
				AudioFormat decodedFormat = getDecodedFormat(encodedFormat);
				AudioInputStream stream = AudioSystem.getAudioInputStream(
						decodedFormat, encoded);

				SourceDataLine line = AudioSystem
						.getSourceDataLine(decodedFormat);
				line.open(decodedFormat);
				line.start();
				byte[] data = new byte[4096];
				int nRead = 0;
				while (!stop) {
					nRead = stream.read(data, 0, data.length);
					if (nRead == -1)
						break;
					line.write(data, 0, nRead);
				}
				line.drain();
				line.close();
				stream.close();
				encoded.close();
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				System.out.println("Unsupported audio file");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Failed to read audio file");
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				System.out.println("Line unavailable");
			}
		}

		public void stopMusic() {
			stop = true;
			repeat = false;
		}
	
	}

	// Specialized Thread for streaming the sound effects
	public class SongThread extends Thread {

		private AudioInputStream audioStream;

		public SongThread(String fileName) {
			try {
				audioStream = AudioSystem
						.getAudioInputStream(new File(fileName));
			} catch (UnsupportedAudioFileException e) {
				System.out.println("Audio file not supported");
			} catch (IOException e) {
				System.out.println("Could not open audio file");
			} // Original
		}

		@Override
		public void run() {
			try {
				Clip clip = AudioSystem.getClip();
				clip.open(audioStream);
				clip.start();
				clip.addLineListener(AudioManager.this);
				audioStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Method for playing MUSIC ONLY - Music should be on OGG format.
	public void playMusic(String fileName, boolean repeat) {

		// Start a thread to open and play the file
		MusicThread mt = new MusicThread(fileName, repeat);
		Thread thread = new Thread(mt);
		audioThreads.put(fileName, mt);
		thread.start();
	}

	// Method for playing SOUNDS - WAV format
	public void playSound(String fileName) {
		SongThread st = new SongThread(fileName);
		Thread thread = new Thread(st);
		thread.start();
	}
	
	public void stopMusic(String fileName) {
		audioThreads.get(fileName).stopMusic();
		audioThreads.remove(fileName);
	}

	private static AudioFormat getDecodedFormat(AudioFormat format) {
		AudioFormat decodedFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED, // Encoding
				format.getSampleRate(), // sample rate
				16, // sample size in bits
				format.getChannels(), // Number of Channels
				format.getChannels() * 2, // Frame Size
				format.getSampleRate(), // Frame Rate
				false);
		return decodedFormat;
	}

	public static AudioManager getInstance() {
		if (instance == null)
			instance = new AudioManager();
		return instance;
	}

	@Override
	public void update(LineEvent event) {
		// TODO Auto-generated method stub
		// System.out.println(event.toString());
		if (event.getType().equals(LineEvent.Type.STOP)) {
			event.getLine().close();
		}
	}
}
