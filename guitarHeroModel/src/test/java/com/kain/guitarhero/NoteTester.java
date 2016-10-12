package com.kain.guitarhero;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class NoteTester {
	private Note note1;
	private long offset;

	@Before
	public void setup() {
		this.note1 = new Note(true, true, false, false, false, 34123);
		this.offset = 100;
	}

	@Test
	public void testNoteSimliar() {
		final Note note2 = new Note(true, true, false, false, false, 34157);
		final Note note3 = new Note(true, true, false, false, false, 34223);
		assertTrue(this.note1.isSimilarWithNote(this.note1, this.offset));
		assertTrue(this.note1.isSimilarWithNote(note2, this.offset));
		assertTrue(!this.note1.isSimilarWithNote(note3, this.offset));
	}

}
