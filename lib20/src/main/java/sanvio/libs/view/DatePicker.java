package sanvio.libs.view;


/**
 * DatePicker
 * 
 * @author Wang_Yuliang
 */
//public class DatePicker extends LinearLayout {

//	private Calendar calendar = Calendar.getInstance(); // 日历类
//	private WheelView years, months, days; // Wheel picker
//	private OnChangeListener onChangeListener; // onChangeListener
//
//	// Constructors
//	public DatePicker(Context context) {
//		super(context);
//		init(context);
//	}
//
//	public DatePicker(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		init(context);
//	}
//
//	/**
//	 * 初始化组件
//	 * 
//	 * @param context
//	 */
//	private void init(Context context) {
//		years = new WheelView(context);
//		LayoutParams lparams_hours = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//		lparams_hours.setMargins(0, 0, 16, 0);
//		years.setLayoutParams(lparams_hours);
//		years.setViewAdapter(new NumericWheelAdapter(context, 1900, 2100));
//
//		years.addChangingListener(onYearsChangedListener);
//		initWheelView(years);
//		addView(years);
//
//		months = new WheelView(context);
//		LayoutParams lparams_month = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//		lparams_month.setMargins(0, 0, 16, 0);
//		months.setLayoutParams(lparams_month);
//		months.setViewAdapter(new NumericWheelAdapter(context, 1, 12, "%02d"));
//		months.addChangingListener(onMonthsChangedListener);
//		initWheelView(months);
//		addView(months);
//
//		days = new WheelView(context);
//		days.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//		int maxday_of_month = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//		days.setViewAdapter(new NumericWheelAdapter(context, 1, maxday_of_month, "%02d"));
//		days.addChangingListener(onDaysChangedListener);
//		initWheelView(days);
//		addView(days);
//	}
//
//	private void initWheelView(WheelView oWheelView) {
//		oWheelView.setVisibleItems(5);
//		oWheelView.setCyclic(true);
//
//		oWheelView.setWheelBackground(R.drawable.wheel_bg);
//		oWheelView.setWheelForeground(R.drawable.wheel_val);
//	}
//
//	// listeners
//	private OnWheelChangedListener onYearsChangedListener = new OnWheelChangedListener() {
//		@Override
//		public void onChanged(WheelView hours, int oldValue, int newValue) {
//			calendar.set(Calendar.YEAR, 1900 + newValue);
//			if (onChangeListener != null)
//				onChangeListener.onChange(getYear(), getMonth(), getDay(), getDayOfWeek());
//			setMonth(getMonth());
//			setDay(getDay());
//		}
//	};
//	private OnWheelChangedListener onMonthsChangedListener = new OnWheelChangedListener() {
//		@Override
//		public void onChanged(WheelView mins, int oldValue, int newValue) {
//			calendar.set(Calendar.MONTH, 1 + newValue - 1);
//			if (onChangeListener != null)
//				onChangeListener.onChange(getYear(), getMonth(), getDay(), getDayOfWeek());
//			setMonth(getMonth());
//			setDay(getDay());
//		}
//	};
//	private OnWheelChangedListener onDaysChangedListener = new OnWheelChangedListener() {
//		@Override
//		public void onChanged(WheelView mins, int oldValue, int newValue) {
//			calendar.set(Calendar.DAY_OF_MONTH, newValue + 1);
//			if (onChangeListener != null)
//				onChangeListener.onChange(getYear(), getMonth(), getDay(), getDayOfWeek());
//			days.setViewAdapter(new NumericWheelAdapter(getContext(), 1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), "%02d"));
//		}
//	};
//
//	/**
//	 * 定义了监听时间改变的监听器借口
//	 * 
//	 * @author Wang_Yuliang
//	 * 
//	 */
//	public interface OnChangeListener {
//		void onChange(int year, int month, int day, int day_of_week);
//	}
//
//	/**
//	 * 设置监听器的方法
//	 * 
//	 * @param onChangeListener
//	 */
//	public void setOnChangeListener(OnChangeListener onChangeListener) {
//		this.onChangeListener = onChangeListener;
//	}
//
//	/**
//	 * 设置年
//	 * 
//	 * @param year
//	 */
//	public void setYear(int year) {
//		years.setCurrentItem(year - 1900);
//	}
//
//	/**
//	 * 获得年
//	 * 
//	 * @return
//	 */
//	public int getYear() {
//		return calendar.get(Calendar.YEAR);
//	}
//
//	/**
//	 * 设置月
//	 */
//	public void setMonth(int month) {
//		months.setCurrentItem(month - 1);
//	}
//
//	/**
//	 * 获得月
//	 * 
//	 * @return
//	 */
//	public int getMonth() {
//		return calendar.get(Calendar.MONTH) + 1;
//	}
//
//	/**
//	 * 设置日
//	 * 
//	 * @param day
//	 */
//	public void setDay(int day) {
//		days.setCurrentItem(day - 1);
//	}
//
//	/**
//	 * 获得日
//	 * 
//	 * @return
//	 */
//	public int getDay() {
//		return calendar.get(Calendar.DAY_OF_MONTH);
//	}
//
//	/**
//	 * 获得星期
//	 * 
//	 * @return
//	 */
//	public int getDayOfWeek() {
//		return calendar.get(Calendar.DAY_OF_WEEK);
//	}
//
//	/**
//	 * 获得星期
//	 * 
//	 * @return
//	 */
//	public static String getDayOfWeekCN(int day_of_week) {
//		String result = null;
//		switch (day_of_week) {
//		case 1:
//			result = "日";
//			break;
//		case 2:
//			result = "一";
//			break;
//		case 3:
//			result = "二";
//			break;
//		case 4:
//			result = "三";
//			break;
//		case 5:
//			result = "四";
//			break;
//		case 6:
//			result = "五";
//			break;
//		case 7:
//			result = "六";
//			break;
//		default:
//			break;
//		}
//		return result;
//	}
//
//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//		// 默认设置为系统时间
//		setYear(getYear());
//		setMonth(getMonth());
//		setDay(getDay());
//	}
//
//	@SuppressLint("SimpleDateFormat")
//	public void setCalendar(String date) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		try {
//			calendar.setTime(sdf.parse(date));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//	}
//}
