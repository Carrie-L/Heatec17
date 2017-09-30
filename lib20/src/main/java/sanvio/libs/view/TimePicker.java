package sanvio.libs.view;


/**
 * TimePicker
 * 
 * @author Wang_Yuliang
 */
//public class TimePicker extends LinearLayout {
//
//	private Calendar calendar = Calendar.getInstance(); // 日历类
//	private WheelView hours, mins; // Wheel picker
//	private OnChangeListener onChangeListener; // onChangeListener
//
//	// Constructors
//	public TimePicker(Context context) {
//		super(context);
//		init(context);
//	}
//
//	public TimePicker(Context context, AttributeSet attrs) {
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
//		hours = new WheelView(context);
//		LayoutParams lparams_hours = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//		lparams_hours.setMargins(0, 0, 20, 0);
//		hours.setLayoutParams(lparams_hours);
//		hours.setViewAdapter(new NumericWheelAdapter(context, 0, 23));
//		hours.addChangingListener(onHoursChangedListener);
//		initWheelView(hours);
//		addView(hours);
//
//		mins = new WheelView(context);
//		mins.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//		mins.setViewAdapter(new NumericWheelAdapter(context, 0, 59, "%02d"));
//		mins.addChangingListener(onMinsChangedListener);
//		initWheelView(mins);
//		addView(mins);
//	}
//
//	private void initWheelView(WheelView oWheelView) {
//		oWheelView.setVisibleItems(3);
//		oWheelView.setCyclic(true);
//		oWheelView.setBackgroundResource(R.drawable.wheel_bg);
//		oWheelView.setWheelForeground(R.drawable.wheel_val);
//	}
//
//	// listeners
//	private OnWheelChangedListener onHoursChangedListener = new OnWheelChangedListener() {
//		@Override
//		public void onChanged(WheelView hours, int oldValue, int newValue) {
//			calendar.set(Calendar.HOUR_OF_DAY, newValue);
//			if (onChangeListener != null)
//				onChangeListener.onChange(getHourOfDay(), getMinute());
//		}
//	};
//	private OnWheelChangedListener onMinsChangedListener = new OnWheelChangedListener() {
//		@Override
//		public void onChanged(WheelView mins, int oldValue, int newValue) {
//			calendar.set(Calendar.MINUTE, newValue);
//			if (onChangeListener != null)
//				onChangeListener.onChange(getHourOfDay(), getMinute());
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
//		void onChange(int hour, int munite);
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
//	 * 设置小时
//	 * 
//	 * @param hour
//	 */
//	public void setHourOfDay(int hour) {
//		hours.setCurrentItem(hour);
//	}
//
//	/**
//	 * 获得24小时制小时
//	 * 
//	 * @return
//	 */
//	public int getHourOfDay() {
//		return calendar.get(Calendar.HOUR_OF_DAY);
//	}
//
//	/**
//	 * 设置分钟
//	 */
//	public void setMinute(int minute) {
//		mins.setCurrentItem(minute);
//	}
//
//	/**
//	 * 获得分钟
//	 * 
//	 * @return
//	 */
//	public int getMinute() {
//		return calendar.get(Calendar.MINUTE);
//	}
//
//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//		// 默认设置为系统时间
//		setHourOfDay(getHourOfDay());
//		setMinute(getMinute());
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
