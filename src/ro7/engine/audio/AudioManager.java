package ro7.engine.audio;

import java.io.File;
import java.io.IOException;

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

	// Specialized Thread for streaming the background music
	public class MusicThread extends Thread {

		private SourceDataLine line;
		private AudioInputStream encoded;
		private AudioInputStream stream;

		public MusicThread(String fileName)
				throws UnsupportedAudioFileException, IOException,
				LineUnavailableException {
			encoded = AudioSystem.getAudioInputStream(new File(fileName)); // Original
																			// file,
																			// not
																			// yet
																			// decoded
																			// (decode
																			// to
																			// OGG)
			AudioFormat encodedFormat = encoded.getFormat();
			AudioFormat decodedFormat = getDecodedFormat(encodedFormat);
			stream = AudioSystem.getAudioInputStream(decodedFormat, encoded); // New
																				// Stream,
																				// decoded
																				// with
																				// the
																				// 3rd
																				// Party
																				// SPI
																				// (jorbis)

			line = AudioSystem.getSourceDataLine(decodedFormat);
			line.open(decodedFormat);
			line.start();
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			byte[] data = new byte[4096];
			int nRead = 0;
			while (true) {
				// se desse pra tirar esse try catch seria lindo
				try {
					nRead = stream.read(data, 0, data.length);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (nRead == -1)
					break;
				line.write(data, 0, nRead);
			}
			line.drain();
			line.close();
			try {
				stream.close();
				encoded.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// Specialized Thread for streaming the sound effects
	public class SongThread extends Thread {

		private AudioInputStream audioStream;

		public SongThread(String fileName)
				throws UnsupportedAudioFileException, IOException,
				LineUnavailableException {
			audioStream = AudioSystem.getAudioInputStream(new File(fileName));
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
	public void playMusic(String fileName)
			throws UnsupportedAudioFileException, IOException,
			LineUnavailableException, InterruptedException {

		// Start a thread to open and play the file
		MusicThread mt = new MusicThread(fileName);
		Thread thread = new Thread(mt);
		thread.start();
	}

	// Method for playing SOUNDS - WAV format
	public void playSound(String fileName)
			throws UnsupportedAudioFileException, IOException,
			LineUnavailableException {
		SongThread st = new SongThread(fileName);
		Thread thread = new Thread(st);
		thread.start();
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
		//System.out.println(event.toString());
		if (event.getType().equals(LineEvent.Type.STOP)) // Acho que isso limpa
															// os resources
															// usados... not
															// sure
		{
			event.getLine().close();
		}
	}
}
