package com.kain.guitarhero;

public class MockMusicPlayer implements MusicPlayer {

	private long startTimeMillis;

	public MockMusicPlayer() {
		this.startTimeMillis = 0;
	}

	@Override
	public void play() {
		System.out.println("start playing music");
		this.startTimeMillis = System.currentTimeMillis();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVolumn(final int volumn) {
		System.out.println("set volumn to " + volumn);
	}

	@Override
	public synchronized long getCurrentPosition() {
		return System.currentTimeMillis() - this.startTimeMillis;
	}

}
