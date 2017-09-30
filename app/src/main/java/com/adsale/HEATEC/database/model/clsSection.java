package com.adsale.HEATEC.database.model;

public class clsSection {
	private String mLable;
	private int mSection;

	/**
	 * @return the mLable
	 */
	public String getLable() {
		return mLable;
	}

	public clsSection(String pLable, int pSection) {
		super();
		this.mLable = pLable;
		this.mSection = pSection;
	}

	/**
	 * @param mLable
	 *            the mLable to set
	 */
	public void setLable(String pLable) {
		this.mLable = pLable;
	}

	/**
	 * @return the mSection
	 */
	public int getSection() {
		return mSection;
	}

	/**
	 * @param mSection
	 *            the mSection to set
	 */
	public void setSection(int pSection) {
		this.mSection = pSection;
	}

	@Override
	public String toString() {
		return "clsSection [mLable=" + mLable + ", mSection=" + mSection + "]";
	}
}
