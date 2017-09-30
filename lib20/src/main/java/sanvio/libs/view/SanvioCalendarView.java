package sanvio.libs.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sanvio.libs.util.DateUtils;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 日历控件 功能：获得点选的日期区间
 * 
 */
public abstract class SanvioCalendarView extends View implements View.OnTouchListener {

	public class CalendarDate {
		public int mDate; // 日历显示数字
		public String mLocaleString; // 日历显示数字
		public int mMark; // 圆点的颜色
		public boolean isEvent;

		public CalendarDate() {
			mDate = 0;
			mLocaleString = "";
			mMark = Color.TRANSPARENT;
			isEvent = false;
		}

		public String toString() {
			return "\r\n mDate:" + mDate + "   mLocaleString:" + mLocaleString + "  mMark:" + mMark;
		}
	}

	private final static String TAG = "anCalendar";
	protected final static String PATTERN = "yyyy-MM-dd";

	protected Date selectedStartDate;
	protected Date selectedEndDate;
	protected Date curDate; // 当前日历显示的月
	protected Date today; // 今天的日期文字显示红色
	protected Date downDate; // 手指按下状态时临时日期
	protected Date showFirstDate, showLastDate; // 日历显示的第一个日期和最后一个日期

	private int downIndex; // 按下的格子索引
	private Calendar calendar;
	private Surface surface;

	protected CalendarDate[] date = new CalendarDate[42];
	protected int mReserveDayCount = 0;

	private int curStartIndex, curEndIndex; // 当前显示的日历起始的索引
	// private boolean completed = false; //
	// 为false表示只选择了开始日期，true表示结束日期也选择了
	// 给控件设置监听事件
	private OnItemClickListener onItemClickListener;

	private OnFirstDrawListener mFirstDrawListener;

	public SanvioCalendarView(Context context) {
		super(context);
		init();
	}

	public SanvioCalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		creatDateList();
		curDate = selectedStartDate = selectedEndDate = today = new Date();
		calendar = Calendar.getInstance();
		calendar.setTime(curDate);
		surface = new Surface();
		surface.density = getResources().getDisplayMetrics().density;
		setBackgroundColor(surface.bgColor);
		setOnTouchListener(this);
	}

	public void setWeekAndMonth(int weekResID, int monthResID) {
		Resources res = getContext().getResources();
		surface.weekText = res.getStringArray(weekResID);
		surface.monthText = res.getStringArray(monthResID);
	}

	private void creatDateList() {
		int count = date.length;
		for (int i = 0; i < count; i++) {
			date[i] = new CalendarDate();
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		surface.width = getResources().getDisplayMetrics().widthPixels;
		surface.height = (int) (getResources().getDisplayMetrics().heightPixels * 3 / 7);
		// if (View.MeasureSpec.getMode(widthMeasureSpec) ==
		// View.MeasureSpec.EXACTLY) {
		// surface.width = View.MeasureSpec.getSize(widthMeasureSpec);
		// }
		// if (View.MeasureSpec.getMode(heightMeasureSpec) ==
		// View.MeasureSpec.EXACTLY) {
		// surface.height = View.MeasureSpec.getSize(heightMeasureSpec);
		// }
		widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(surface.width, View.MeasureSpec.EXACTLY);
		heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(surface.height, View.MeasureSpec.EXACTLY);
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		Log.d(TAG, "[onLayout] changed:" + (changed ? "new size" : "not change") + " left:" + left + " top:" + top + " right:" + right + " bottom:" + bottom);
		if (changed) {
			surface.init();
		}
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.d(TAG, "onDraw");
		// 画框
		canvas.drawPath(surface.boxPath, surface.borderPaint);
		// 年月
		// String monthText = getYearAndmonth();
		// float textWidth = surface.monthPaint.measureText(monthText);
		// canvas.drawText(monthText, (surface.width - textWidth) / 2f,
		// surface.monthHeight * 3 / 4f, surface.monthPaint);
		// 上一月/下一月
		// canvas.drawPath(surface.preMonthBtnPath,
		// surface.monthChangeBtnPaint);
		// canvas.drawPath(surface.nextMonthBtnPath,
		// surface.monthChangeBtnPaint);
		// 星期
		float weekTextY = surface.monthHeight + surface.weekHeight * 3 / 4f;
		// 星期背景
		// surface.cellBgPaint.setColor(surface.textColor);
		// canvas.drawRect(surface.weekHeight, surface.width,
		// surface.weekHeight, surface.width, surface.cellBgPaint);
		for (int i = 0; i < surface.weekText.length; i++) {
			float weekTextX = i * surface.cellWidth + (surface.cellWidth - surface.weekPaint.measureText(surface.weekText[i])) / 2f;
			canvas.drawText(surface.weekText[i], weekTextX, weekTextY, surface.weekPaint);
		}

		// 计算日期
		calculateDate();
		// 计算日期圆点
		calculateDateMark();
		// 按下状态，选择状态背景色
		drawDownOrSelectedBg(canvas);
		// write date number
		// today index
		int todayIndex = -1;
		calendar.setTime(curDate);
		String curYearAndMonth = calendar.get(Calendar.YEAR) + "" + calendar.get(Calendar.MONTH);
		calendar.setTime(today);
		String todayYearAndMonth = calendar.get(Calendar.YEAR) + "" + calendar.get(Calendar.MONTH);
		if (curYearAndMonth.equals(todayYearAndMonth)) {
			int todayNumber = calendar.get(Calendar.DAY_OF_MONTH);
			todayIndex = curStartIndex + todayNumber - 1;
		}
		for (int i = 0; i < 42; i++) {
			int color = surface.textColor;

			if (isLastMonth(i)) {
				color = surface.borderColor;
			} else if (isNextMonth(i)) {
				color = surface.borderColor;
			}
			if (todayIndex != -1 && i == todayIndex) {
				color = surface.todayNumberColor;
			}

			drawCellText(canvas, i, color);
			drawCellPoint(canvas, i);
		}

		super.onDraw(canvas);
		if (mFirstDrawListener != null) {
			mFirstDrawListener.onFinisDraw();
			mFirstDrawListener = null;
		}

	}

	@SuppressWarnings("deprecation")
	private void calculateDate() {
		Calendar tempCalendar = null;
		calendar.setTime(curDate);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		tempCalendar = (Calendar) calendar.clone();

		int dayInWeek = calendar.get(Calendar.DAY_OF_WEEK);
		Log.d(TAG, "day in week:" + dayInWeek);
		int monthStart = dayInWeek;
		// if (monthStart == 1) {
		// monthStart = 8;
		// }

		monthStart -= 1; // 以日为开头-1，以星期一为开头-2
		curStartIndex = monthStart;
		date[monthStart].mDate = 1;
		tempCalendar.set(Calendar.DAY_OF_MONTH, 1);
		date[monthStart].mLocaleString = DateUtils.FormatDate(PATTERN, tempCalendar);

		// last month
		if (monthStart > 0) {
			calendar.set(Calendar.DAY_OF_MONTH, 0);
			int dayInmonth = calendar.get(Calendar.DAY_OF_MONTH);
			tempCalendar.setTime(curDate);
			tempCalendar.add(Calendar.MONTH, -1);
			for (int i = monthStart - 1; i >= 0; i--) {
				date[i].mDate = dayInmonth;
				tempCalendar.set(Calendar.DAY_OF_MONTH, dayInmonth);
				date[i].mLocaleString = DateUtils.FormatDate(PATTERN, tempCalendar);
				dayInmonth--;
			}
			calendar.set(Calendar.DAY_OF_MONTH, date[0].mDate);
		}
		showFirstDate = calendar.getTime();
		Log.d(TAG, "[calculateDate] showFirstDate:" + showFirstDate.toLocaleString());

		// this month
		calendar.setTime(curDate);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		int monthDay = calendar.get(Calendar.DAY_OF_MONTH);
		tempCalendar.setTime(curDate);
		for (int i = 1; i < monthDay; i++) {
			date[monthStart + i].mDate = i + 1;
			tempCalendar.set(Calendar.DAY_OF_MONTH, i + 1);
			date[monthStart + i].mLocaleString = DateUtils.FormatDate(PATTERN, tempCalendar);
		}

		// next month
		curEndIndex = monthStart + monthDay;
		tempCalendar.setTime(curDate);
		tempCalendar.add(Calendar.MONTH, 1);
		for (int i = monthStart + monthDay; i < 42; i++) {
			date[i].mDate = i - (monthStart + monthDay) + 1;
			tempCalendar.set(Calendar.DAY_OF_MONTH, date[i].mDate);
			date[i].mLocaleString = DateUtils.FormatDate(PATTERN, tempCalendar);
		}

		if (curEndIndex < 42) {
			// 显示了下一月的
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}

		calendar.set(Calendar.DAY_OF_MONTH, date[41].mDate);
		showLastDate = calendar.getTime();

		Log.d(TAG, "[calculateDate] showLastDate:" + showLastDate.toLocaleString());
	}

	protected abstract void calculateDateMark();

	/**
	 * 
	 * @param canvas
	 * @param index
	 * @param text
	 */
	private void drawCellText(Canvas canvas, int index, int color) {
		int x = getXByIndex(index);
		int y = getYByIndex(index);
		String strDate = date[index].mDate + "";

		surface.datePaint.setColor(color);
		float cellY = surface.monthHeight + surface.weekHeight + (y - 1) * surface.cellHeight + surface.cellHeight * 3 / 5f;
		float cellX = (surface.cellWidth * (x - 1)) + (surface.cellWidth - surface.datePaint.measureText(strDate)) / 2f;
		canvas.drawText(strDate, cellX, cellY, surface.datePaint);
	}

	private void drawCellPoint(Canvas canvas, int index) {
		int x = getXByIndex(index);
		int y = getYByIndex(index);

		int markColor = -1;
		if ((markColor = date[index].mMark) != Color.TRANSPARENT) {
			surface.pointPaint.setColor(markColor);
			float pointX = surface.cellWidth * (x - 0.5f) + 1;
			float pointY = surface.monthHeight + surface.weekHeight + surface.cellHeight * (y - 1 + 3 / 5f + 0.2f);
			canvas.drawCircle(pointX, pointY, surface.cellWidth * 0.05f, surface.pointPaint);
		}
	}

	/**
	 * 
	 * @param canvas
	 * @param index
	 * @param color
	 */
	private void drawCellBg(Canvas canvas, int index, int color) {
		int x = getXByIndex(index);
		int y = getYByIndex(index);
		surface.cellBgPaint.setColor(color);
		float left = surface.cellWidth * (x - 1) + surface.borderWidth;
		float top = surface.monthHeight + surface.weekHeight + (y - 1) * surface.cellHeight + surface.borderWidth;
		canvas.drawRect(left, top, left + surface.cellWidth - surface.borderWidth, top + surface.cellHeight - surface.borderWidth, surface.cellBgPaint);
	}

	private void drawDownOrSelectedBg(Canvas canvas) {
		// down and not up
		if (downDate != null) {
			drawCellBg(canvas, downIndex, surface.cellDownColor);
		}
		// selected bg color
		if (!selectedEndDate.before(showFirstDate) && !selectedStartDate.after(showLastDate)) {
			int[] section = new int[] { -1, -1 };
			calendar.setTime(curDate);
			calendar.add(Calendar.MONTH, -1);
			findSelectedIndex(0, curStartIndex, calendar, section);
			if (section[1] == -1) {
				calendar.setTime(curDate);
				findSelectedIndex(curStartIndex, curEndIndex, calendar, section);
			}
			if (section[1] == -1) {
				calendar.setTime(curDate);
				calendar.add(Calendar.MONTH, 1);
				findSelectedIndex(curEndIndex, 42, calendar, section);
			}
			if (section[0] == -1) {
				section[0] = 0;
			}
			if (section[1] == -1) {
				section[1] = 41;
			}
			for (int i = section[0]; i <= section[1]; i++) {
				drawCellBg(canvas, i, surface.cellSelectedColor);
			}
		}
	}

	private void findSelectedIndex(int startIndex, int endIndex, Calendar calendar, int[] section) {
		for (int i = startIndex; i < endIndex; i++) {
			calendar.set(Calendar.DAY_OF_MONTH, date[i].mDate);
			Date temp = calendar.getTime();
			// Log.d(TAG, "temp:" + temp.toLocaleString());
			if (temp.compareTo(selectedStartDate) == 0) {
				section[0] = i;
			}
			if (temp.compareTo(selectedEndDate) == 0) {
				section[1] = i;
				return;
			}
		}
	}

	private boolean isLastMonth(int i) {
		if (i < curStartIndex) {
			return true;
		}
		return false;
	}

	private boolean isNextMonth(int i) {
		if (i >= curEndIndex) {
			return true;
		}
		return false;
	}

	private int getXByIndex(int i) {
		return i % 7 + 1; // 1 2 3 4 5 6 7
	}

	private int getYByIndex(int i) {
		return i / 7 + 1; // 1 2 3 4 5 6
	}

	// 获得当前应该显示的年月
	public String getYearAndmonth() {
		calendar.setTime(curDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		return surface.monthText[month] + "-" + year;
	}

	public String getYearAndmonth(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.ENGLISH);
		calendar.setTime(curDate);
		return formatter.format(calendar.getTime());
	}

	public int getYear() {
		calendar.setTime(curDate);
		return calendar.get(Calendar.YEAR);
	}

	public int getMonth() {
		calendar.setTime(curDate);
		return calendar.get(Calendar.MONTH) + 1;
	}

	// 上一月
	public String clickLeftMonth() {
		mReserveDayCount = 0;
		calendar.setTime(curDate);
		calendar.add(Calendar.MONTH, -1);
		curDate = calendar.getTime();
		invalidate();
		return getYearAndmonth();
	}

	// 下一月
	public String clickRightMonth() {
		mReserveDayCount = 0;
		calendar.setTime(curDate);
		calendar.add(Calendar.MONTH, 1);
		curDate = calendar.getTime();
		invalidate();
		return getYearAndmonth();
	}

	public String backToToday() {
		mReserveDayCount = 0;
		calendar.setTime(today);
		curDate = calendar.getTime();
		invalidate();
		return getYearAndmonth();
	}

	private void setSelectedDateByCoor(float x, float y) {
		// change month
		// if (y < surface.monthHeight) {
		// // pre month
		// if (x < surface.monthChangeWidth) {
		// calendar.setTime(curDate);
		// calendar.add(Calendar.MONTH, -1);
		// curDate = calendar.getTime();
		// }
		// // next month
		// else if (x > surface.width - surface.monthChangeWidth) {
		// calendar.setTime(curDate);
		// calendar.add(Calendar.MONTH, 1);
		// curDate = calendar.getTime();
		// }
		// }
		// cell click down
		/*
		 * if (y > surface.monthHeight + surface.weekHeight) { int m = (int)
		 * (Math.floor(x / surface.cellWidth) + 1); int n = (int) (Math.floor((y
		 * - (surface.monthHeight + surface.weekHeight)) /
		 * Float.valueOf(surface.cellHeight)) + 1); downIndex = (n - 1) * 7 + m
		 * - 1; Log.d(TAG, "downIndex:" + downIndex); calendar.setTime(curDate);
		 * if (isLastMonth(downIndex)) { calendar.add(Calendar.MONTH, -1); }
		 * else if (isNextMonth(downIndex)) { calendar.add(Calendar.MONTH, 1); }
		 * calendar.set(Calendar.DAY_OF_MONTH, date[downIndex]); downDate =
		 * calendar.getTime(); } invalidate();
		 */

		if (y > surface.monthHeight + surface.weekHeight) {
			int m = (int) (Math.floor(x / surface.cellWidth) + 1);
			int n = (int) (Math.floor((y - (surface.monthHeight + surface.weekHeight)) / Float.valueOf(surface.cellHeight)) + 1);
			downIndex = (n - 1) * 7 + m - 1;
			Log.d(TAG, "downIndex:" + downIndex);
			calendar.setTime(curDate);
			if (!isLastMonth(downIndex) && !isNextMonth(downIndex)) {
				calendar.set(Calendar.DAY_OF_MONTH, date[downIndex].mDate);
				downDate = calendar.getTime();
				invalidate();
			}
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			setSelectedDateByCoor(event.getX(), event.getY());
			break;
		case MotionEvent.ACTION_UP:
			if (downDate != null) {
				// if (!completed) {
				// if (downDate.before(selectedStartDate)) {
				// selectedEndDate = selectedStartDate;
				// selectedStartDate = downDate;
				// } else {
				// selectedEndDate = downDate;
				// }
				// completed = true;
				// } else {
				// selectedStartDate = selectedEndDate = downDate;
				// completed = false;
				// }
				Log.v("anCalendar", "onTouch >>>> " + date[downIndex]);
				selectedStartDate = selectedEndDate = downDate;
				// 响应监听事件
				if (onItemClickListener != null && date[downIndex].isEvent)
					onItemClickListener.OnItemClick(selectedEndDate);
				downDate = null;
				invalidate();
			}
			break;
		}
		return true;
	}

	// 给控件设置监听事件
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public void setOnFirstDrawListener(OnFirstDrawListener onFirstDrawListener) {
		this.mFirstDrawListener = onFirstDrawListener;
	}

	// 监听接口
	public interface OnItemClickListener {
		void OnItemClick(Date date);
	}

	public interface OnFirstDrawListener {
		void onFinisDraw();
	}

	/**
	 * 
	 * 1. 布局尺寸 2. 文字颜色，大小 3. 当前日期的颜色，选择的日期颜色
	 */
	public class Surface {
		public float density;
		public int width; // 整个控件的宽度
		public int height; // 整个控件的高度
		public float monthHeight; // 显示月的高度
		// public float monthChangeWidth; // 上一月、下一月按钮宽度
		public float weekHeight; // 显示星期的高度
		public float cellWidth; // 日期方框宽度
		public float cellHeight; // 日期方框高度
		public float borderWidth;

		public int bgColor = Color.parseColor("#d8d8db");
		private int textColor = Color.parseColor("#3b4958");
		// private int textColorUnimportant = Color.parseColor("#666666");
		private int btnColor = Color.parseColor("#666666");
		private int borderColor = Color.parseColor("#a0a3ab");
		public int todayNumberColor = Color.RED;
		public int cellDownColor = Color.parseColor("#CCFFFF");
		public int cellSelectedColor = Color.parseColor("#677b96");
		public Paint borderPaint;
		public Paint monthPaint;
		public Paint weekPaint;
		public Paint datePaint;
		public Paint monthChangeBtnPaint;
		public Paint pointPaint;

		public Paint cellBgPaint;
		public Path boxPath; // 边框路径
		// public Path preMonthBtnPath; // 上一月按钮三角形
		// public Path nextMonthBtnPath; // 下一月按钮三角形

		public String[] weekText;
		public String[] monthText;

		public Surface() {
			weekText = new String[] { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
			monthText = new String[] { "一月", "二月 ", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" };
		}

		public void init() {
			float temp = height / 7f;
			monthHeight = 0;// (float) ((temp + temp * 0.3f) * 0.6);
			// monthChangeWidth = monthHeight * 1.5f;

			weekHeight = (float) ((temp + temp * 0.3f) * 0.7);
			cellHeight = (height - monthHeight - weekHeight) / 6f;
			cellWidth = width / 7f;

			borderPaint = new Paint();
			borderPaint.setColor(borderColor);
			borderPaint.setStyle(Paint.Style.STROKE);
			borderWidth = (float) (0.5 * density);
			// Log.d(TAG, "borderwidth:" + borderWidth);
			borderWidth = borderWidth < 1 ? 1 : borderWidth;
			borderPaint.setStrokeWidth(borderWidth);

			monthPaint = new Paint();
			monthPaint.setColor(textColor);
			monthPaint.setAntiAlias(true);
			float textSize = cellHeight * 0.4f;
			Log.d(TAG, "text size:" + textSize);
			monthPaint.setTextSize(textSize);
			monthPaint.setTypeface(Typeface.DEFAULT_BOLD);

			weekPaint = new Paint();
			weekPaint.setColor(textColor);
			weekPaint.setAntiAlias(true);
			float weekTextSize = weekHeight * 0.5f;
			weekPaint.setTextSize(weekTextSize);
			weekPaint.setTypeface(Typeface.DEFAULT);

			datePaint = new Paint();
			datePaint.setColor(textColor);
			datePaint.setAntiAlias(true);
			float cellTextSize = cellHeight * 3 / 5f;
			datePaint.setTextSize(cellTextSize);
			datePaint.setTypeface(Typeface.DEFAULT_BOLD);

			boxPath = new Path();
			boxPath.addRect(0, 0, width, height - borderWidth, Direction.CW);

			boxPath.moveTo(0, monthHeight);
			boxPath.rLineTo(width, 0);
			boxPath.moveTo(0, monthHeight + weekHeight);
			boxPath.rLineTo(width, 0);

			// Log.v(TAG,String.format("Path moveTo (%1$.2f,%2$.2f)",0f,
			// monthHeight));
			// Log.v(TAG,String.format("Path rLineTo (%1$.2f,%2$.2f)",width*1f,
			// 0f));
			// Log.v(TAG,String.format("Path moveTo (%1$.2f,%2$.2f)",0f,
			// monthHeight + weekHeight));
			// Log.v(TAG,String.format("Path rLineTo(%1$.2f,%2$.2f)",width*1f,
			// 0f));
			for (int i = 1; i < 6; i++) {
				boxPath.moveTo(0, monthHeight + weekHeight + i * cellHeight);
				boxPath.rLineTo(width, 0);
				boxPath.moveTo(i * cellWidth, monthHeight);
				boxPath.rLineTo(0, height - monthHeight);

				// Log.v(TAG,i+String.format(" Path moveTo (%1$.2f,%2$.2f)",0f,
				// monthHeight + weekHeight + i * cellHeight));
				// Log.v(TAG,i+String.format(" Path rLineTo (%1$.2f,%2$.2f)",width*1f,
				// 0f));
				// Log.v(TAG,i+String.format(" Path moveTo (%1$.2f,%2$.2f)",i *
				// cellWidth, monthHeight));
				// Log.v(TAG,i+String.format(" Path rLineTo (%1$.2f,%2$.2f)",0f,
				// height*1f - monthHeight));
			}
			boxPath.moveTo(6 * cellWidth, monthHeight);
			boxPath.rLineTo(0, height - monthHeight);

			// Log.v(TAG,String.format(" Path moveTo (%1$.2f,%2$.2f)",6 *
			// cellWidth, monthHeight));
			// Log.v(TAG,String.format(" Path rLineTo (%1$.2f,%2$.2f)",0f,
			// height - monthHeight));

			// preMonthBtnPath = new Path();
			// int btnHeight = (int) (monthHeight * 0.6f);
			// preMonthBtnPath.moveTo(monthChangeWidth / 2f, monthHeight / 2f);
			// preMonthBtnPath.rLineTo(btnHeight / 2f, -btnHeight / 2f);
			// preMonthBtnPath.rLineTo(0, btnHeight);
			// preMonthBtnPath.close();
			// nextMonthBtnPath = new Path();
			// nextMonthBtnPath.moveTo(width - monthChangeWidth / 2f,
			// monthHeight / 2f);
			// nextMonthBtnPath.rLineTo(-btnHeight / 2f, -btnHeight / 2f);
			// nextMonthBtnPath.rLineTo(0, btnHeight);
			// nextMonthBtnPath.close();

			monthChangeBtnPaint = new Paint();
			monthChangeBtnPaint.setAntiAlias(true);
			monthChangeBtnPaint.setStyle(Paint.Style.FILL_AND_STROKE);
			monthChangeBtnPaint.setColor(btnColor);

			pointPaint = new Paint();
			pointPaint.setStyle(Paint.Style.FILL);
			pointPaint.setAntiAlias(true);
			pointPaint.setColor(Color.parseColor("#ff0000"));
			pointPaint.setTextSize(cellHeight);

			cellBgPaint = new Paint();
			cellBgPaint.setAntiAlias(true);
			cellBgPaint.setStyle(Paint.Style.FILL);
			cellBgPaint.setColor(cellSelectedColor);
		}
	}

	public void setSurfaceWeeks(String[] pWeeks) {
		surface.weekText = pWeeks;
	}

	public void setSurfaceMonths(String[] pMonths) {
		surface.monthText = pMonths;
	}

	public void setTodayColor(int todayColor) {
		surface.todayNumberColor = todayColor;
	}

}