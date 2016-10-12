package com.kain.guitarhero;

import java.util.concurrent.LinkedBlockingQueue;

public class MuteTask implements Runnable {
	private final LinkedBlockingQueue<Note> notesQuery;
	private final MusicPlayer musicPlayer;
	private final long offset;

	public MuteTask(final LinkedBlockingQueue<Note> notesQuery, final MusicPlayer musicPlayer, final long offset) {
		this.notesQuery = notesQuery;
		this.musicPlayer = musicPlayer;
		this.offset = offset;
	}

	@Override
	public void run() {
		while (true) {
			final long currentPosition = this.musicPlayer.getCurrentPosition();
			//System.out.println("currentPosititon:" + currentPosition);
			final Note note = this.notesQuery.peek();
			if ((note != null) && (currentPosition > (note.getTime() + this.offset))) {
				this.notesQuery.poll();
				//System.out.println("pull out node Time:" + currentPosition);
				this.musicPlayer.setVolumn(0);
			}
		}
	}

}
