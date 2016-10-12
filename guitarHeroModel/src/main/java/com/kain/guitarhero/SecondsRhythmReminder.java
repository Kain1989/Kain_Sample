package com.kain.guitarhero;

public class SecondsRhythmReminder implements Runnable {
	private final MusicPlayer musicPlayer;

	public SecondsRhythmReminder(final MusicPlayer musicPlayer) {
		this.musicPlayer = musicPlayer;
	}

	@Override
	public void run() {
		while (true) {
			if ((this.musicPlayer.getCurrentPosition() % 1000) == 0) {
				System.out.println(this.musicPlayer.getCurrentPosition());
				try {
					Thread.sleep(100);
				}
				catch (final InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
