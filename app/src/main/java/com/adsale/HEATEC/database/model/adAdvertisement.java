package com.adsale.HEATEC.database.model;

/**
 * 广告
 * 检测对应的广告位置是否需要更新
 * @author orz
 *
 */
public class adAdvertisement {
	public boolean m1;
	public boolean m2;
	public boolean m3;
	public boolean m4;
	public boolean m5;
	public boolean m6;
	public boolean m7;

	public String m1_startDate;
	public String m2_startDate;
	public String m3_startDate;
	public String m4_startDate;
	public String m5_startDate;
	public String m6_startDate;
	public String m7_startDate;

	public String m1_endDate;
	public String m2_endDate;
	public String m3_endDate;
	public String m4_endDate;
	public String m5_endDate;
	public String m6_endDate;
	public String m7_endDate;

	public adAdvertisement(boolean m1, boolean m2, boolean m3, boolean m4,
			boolean m5, boolean m6, boolean m7, String m1_startDate,
			String m2_startDate, String m3_startDate, String m4_startDate,
			String m5_startDate, String m6_startDate, String m7_startDate,
			String m1_endDate, String m2_endDate, String m3_endDate,
			String m4_endDate, String m5_endDate, String m6_endDate,
			String m7_endDate) {
		this.m1 = m1;
		this.m2 = m2;
		this.m3 = m3;
		this.m4 = m4;
		this.m5 = m5;
		this.m6 = m6;
		this.m7 = m7;
		
		this.m1_startDate = m1_startDate;
		this.m2_startDate = m2_startDate;
		this.m3_startDate = m3_startDate;
		this.m4_startDate = m4_startDate;
		this.m5_startDate = m5_startDate;
		this.m6_startDate = m6_startDate;
		this.m7_startDate = m7_startDate;
	
		this.m1_endDate = m1_endDate;
		this.m2_endDate = m2_endDate;
		this.m3_endDate = m3_endDate;
		this.m4_endDate = m4_endDate;
		this.m5_endDate = m5_endDate;
		this.m6_endDate = m6_endDate;
		this.m7_endDate = m7_endDate;		
	}

}
