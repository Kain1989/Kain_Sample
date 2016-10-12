package com.kain.guitarhero;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class GameModel {
	private final MusicPlayer musicPlayer;
	private final LinkedBlockingQueue<Note> notesQueue;
	private final ExecutorService executorService;
	private final long offset;

	public GameModel(final MusicPlayer musicPlayer, final LinkedBlockingQueue<Note> notesQueue, final long offset) {
		this.musicPlayer = musicPlayer;
		this.notesQueue = notesQueue;
		this.executorService = Executors.newCachedThreadPool();
		this.offset = offset;
	}

	public GameModel(final MusicPlayer musicPlayer, final LinkedBlockingQueue<Note> notesQueue) {
		this(musicPlayer, notesQueue, 200);
	}

	public void start() {
		this.musicPlayer.play();
		this.musicPlayer.setVolumn(0);
		final MuteTask muteTask = new MuteTask(this.notesQueue, this.musicPlayer, this.offset);
		final SecondsRhythmReminder secondsRhythmReminder = new SecondsRhythmReminder(this.musicPlayer);
		this.executorService.execute(muteTask);
		this.executorService.execute(secondsRhythmReminder);
	}

	public void handleInput(final Note userNote) {
		System.out.println("User press note:" + userNote);

		final Note headNote = this.notesQueue.peek();

		if (headNote == null) {
			return;
		}

		if (headNote.isSimilarWithNote(userNote, this.offset)) {
			final Note notePoll = this.notesQueue.poll();
			System.out.println("User Hit Note: noteTime" + notePoll.getTime());
			this.musicPlayer.setVolumn(100);
		}
		else {
			//do somthing if user doesn't hit right
			System.out.println("miss");
		}
	}

	//getter
	public MusicPlayer getMusicPlayer() {
		return this.musicPlayer;
	}

	public LinkedBlockingQueue<Note> getNotesQueue() {
		return this.notesQueue;
	}

}
