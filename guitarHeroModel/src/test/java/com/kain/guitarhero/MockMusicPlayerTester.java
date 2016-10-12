package com.kain.guitarhero;

import org.junit.Before;
import org.junit.Test;

public class MockMusicPlayerTester {
	private MockMusicPlayer player;

	@Before
	public void setup() {
		this.player = new MockMusicPlayer();
	}

	@Test
	public void testGetCurrentPosition() throws InterruptedException {
		this.player.play();
		Thread.sleep(3123);
		final long currentPosition = this.player.getCurrentPosition();

		System.out.println("current position: " + currentPosition);
	}

}
