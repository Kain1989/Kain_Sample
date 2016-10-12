package com.kain.guitarhero;

public class Note {
	private final boolean needKey1;
	private final boolean needKey2;
	private final boolean needKey3;
	private final boolean needKey4;
	private final boolean needKey5;
	private final long time; //millian seconds

	public Note(final boolean needKey1, final boolean needKey2, final boolean needKey3,//
	        final boolean needKey4, final boolean needKey5, final long time) {
		this.needKey1 = needKey1;
		this.needKey2 = needKey2;
		this.needKey3 = needKey3;
		this.needKey4 = needKey4;
		this.needKey5 = needKey5;
		this.time = time;
	}

	public boolean isSimilarWithNote(final Note other, final long timeOffset) {
		return (this.needKey1 == other.isNeedKey1()) && //
		        (this.needKey2 == other.isNeedKey2()) && //
		        (this.needKey3 == other.isNeedKey3()) && //
		        (this.needKey4 == other.isNeedKey4()) && //
		        (this.needKey5 == other.isNeedKey5()) && //
		        (isTimeSimilar(other.getTime(), timeOffset));
	}

	private boolean isTimeSimilar(final long otherTime, final long timeOffset) {
		return Math.abs(getTime() - otherTime) < timeOffset;
	}

	@Override
	public String toString() {
		return "Note time:" + getTime() + " " + isNeedKey1() + " " + isNeedKey2() + " " + isNeedKey3() + " " + isNeedKey4() + " " + isNeedKey5();
	}

	//getters
	public boolean isNeedKey1() {
		return this.needKey1;
	}

	public boolean isNeedKey2() {
		return this.needKey2;
	}

	public boolean isNeedKey3() {
		return this.needKey3;
	}

	public boolean isNeedKey4() {
		return this.needKey4;
	}

	public boolean isNeedKey5() {
		return this.needKey5;
	}

	public long getTime() {
		return this.time;
	}
}
