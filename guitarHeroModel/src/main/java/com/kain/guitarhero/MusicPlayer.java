package com.kain.guitarhero;

public interface MusicPlayer {
	public void play();

	public void stop();

	public void setVolumn(int volumn);

	public long getCurrentPosition();
}
