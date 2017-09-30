package sanvio.libs.view;

import sanvio.libs.util.ImageCache;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public abstract class SanvioScrollLayout extends ViewGroup {

	private static final String TAG = "SanvioScrollLayout";

	private Scroller mScroller;

	private VelocityTracker mVelocityTracker;

	protected static final int SHOWING_VIEW = 1;

	private int mWidth;

	private static final int TOUCH_STATE_REST = 0;

	private static final int TOUCH_STATE_SCROLLING = 1;

	private static final int SNAP_VELOCITY = 600;

	private int mTouchState = TOUCH_STATE_REST;

	private int mTouchSlop;

	private float mLastMotionX, mLastMotionY;

	public static final int IMGTAG = 1;

	// private ScrollLayoutTest mContext;

	protected int mViewsQty = -1;

	protected int mIndex = 0;
	
	protected ImageCache imageCache;
	
	protected OnViewChangeListener mOnViewChangeListener=null;
	
	public void SetOnViewChangeListener(OnViewChangeListener listener)
	{
		mOnViewChangeListener = listener;
	}
		
	public SanvioScrollLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);		 
		System.out.println(getClass().getName());
	}
	
	public SanvioScrollLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		imageCache = new ImageCache();
		// mContext = (ScrollLayoutTest) context;
		mScroller = new Scroller(context);
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
	}
	
	@Override
	protected void attachViewToParent(View child, int index, LayoutParams params) {
		super.attachViewToParent(child, index, params);
	}

	@Override
	public void addView(View child) {
		super.addView(child);
	}

	@Override
	public void requestChildFocus(View child, View focused) {
		Log.d("requestChildFocus", "child = " + child);
		super.requestChildFocus(child, focused);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		int childLeft = 0;

		final int childCount = getChildCount();

		for (int i = 0; i < childCount; i++) {

			final View childView = getChildAt(i);

			if (childView.getVisibility() != View.GONE) {

				final int childWidth = childView.getMeasuredWidth();

				childView.layout(childLeft, 0,

				childLeft + childWidth, childView.getMeasuredHeight());

				childLeft += childWidth;

			}

		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		final int width = MeasureSpec.getSize(widthMeasureSpec);

		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		Log.v(TAG, "onMeasure width = " + width);

		if (widthMode != MeasureSpec.EXACTLY) {

			throw new IllegalStateException(
					"ScrollLayout only canmCurScreen run at EXACTLY mode!");

		}

		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		if (heightMode != MeasureSpec.EXACTLY) {

			throw new IllegalStateException(
					"ScrollLayout only can run at EXACTLY mode!");

		}

		// The children are given the same width and height as the scrollLayout

		final int count = getChildCount();

		for (int i = 0; i < count; i++) {

			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);

		}

		// Log.e(TAG, "moving to screen "+mCurScreen);

		if (count > 1)
			scrollTo(SHOWING_VIEW * width, 0);
		else
			scrollTo(0, 0);

	}

	/**
	 * 
	 * 
	 * scroll to the destination page.
	 */

	public void snapToDestination() {

		setMWidth();

		final int destScreen = (getScrollX() + mWidth / 2) / mWidth;

		snapToScreen(destScreen);

	}

	private void setMWidth() {
		if (mWidth == 0) {
			mWidth = getWidth();
		}
	}

	public void snapToScreen(int whichScreen) {

		// get the valid layout page

		// whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() -
		// 1));
		setMWidth();

		int scrollX = getScrollX();
		int startWidth = whichScreen * mWidth;

		if (scrollX != startWidth) {

			int delta = 0;
			int startX = 0;

			if (whichScreen > SHOWING_VIEW) {
				// if (mIndex<mArticlePhotoQty-1) {
				// mIndex = mIndex + 1;
				setPre();
				delta = startWidth - scrollX;
				startX = mWidth - startWidth + scrollX;
				mIndex = Math
						.max(0, Math.min(mIndex + 1, mViewsQty - 1));
				// mScroller
				// .startScroll(startX, 0, delta, 0, Math.abs(delta) * 10);
				// setViews();
				// }

			} else if (whichScreen < SHOWING_VIEW) {

				// if (mIndex > 0) {
				// mIndex = mIndex - 1;
				setNext();
				delta = -scrollX;
				startX = scrollX + mWidth;
				mIndex = Math
						.min(mViewsQty - 1, Math.max(mIndex - 1, 0));

				// }

			} else {
				startX = scrollX;
				delta = startWidth - scrollX;
				// mScroller
				// .startScroll(startX, 0, delta, 0, Math.abs(delta) * 10);
				// setViews();
			}

			mScroller.startScroll(startX, 0, delta, 0, Math.abs(delta) * 5);
			LoadViewsData();
			invalidate(); // Redraw the layout
			if (mOnViewChangeListener != null) {
				mOnViewChangeListener.OnViewChange(mIndex);
			}
		}

		// startCurrentView(whichScreen);
	}

	protected void LoadViewsData() {
		loadData(-1);
		loadData(0);
		loadData(1);
	}
	
	protected abstract void loadData(int position);

	public void setToScreen(int whichScreen) {

		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		scrollTo(whichScreen * mWidth, 0);
		if (whichScreen > SHOWING_VIEW) {
			setPre();
		} else if (whichScreen < SHOWING_VIEW) {
			setNext();
		}
		mIndex = whichScreen;
		System.out.println("set to screen:" + mIndex);
	}

	public View getCurScreen() {

		return this.getChildAt(SHOWING_VIEW);

	}

	@Override
	public void computeScroll() {

		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}

	}

	private void setNext() {
		int count = this.getChildCount();
		if (count > 0) {
			View view = getChildAt(count - 1);
			removeViewAt(count - 1);
			if (view instanceof IPageView) {
				((IPageView) view).DestoryView();
			}
			view = null;
			addView(CreateNewView(-2), 0);
		}
	}

	private void setPre() {
		int count = this.getChildCount();
		if (count > 0) {
			View view = getChildAt(0);
			removeViewAt(0);
			if (view instanceof IPageView) {
				((IPageView)view).DestoryView();
			}
			view = null;
			addView(CreateNewView(2), count - 1);
		}
	}
	
	protected abstract View CreateNewView(int index);

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		System.out.println(getClass().getName());
		if (mVelocityTracker == null) {

			mVelocityTracker = VelocityTracker.obtain();

		}

		mVelocityTracker.addMovement(event);

		final int action = event.getAction();
		

		final float x = event.getX();

		switch (action) {

		case MotionEvent.ACTION_DOWN:

			Log.d(TAG, "event down!");

			if (!mScroller.isFinished()) {

				mScroller.abortAnimation();

			}

			mLastMotionX = x;

			break;

		case MotionEvent.ACTION_MOVE:

			int deltaX = (int) (mLastMotionX - x);

			if (!((mIndex == 0 && deltaX < 0) || (mIndex == mViewsQty - 1 && deltaX > 0))) {
				mLastMotionX = x;
				scrollBy(deltaX, 0);
			}

			break;

		case MotionEvent.ACTION_UP:

			// if (mTouchState == TOUCH_STATE_SCROLLING) {

			final VelocityTracker velocityTracker = mVelocityTracker;

			velocityTracker.computeCurrentVelocity(1000);

			int velocityX = (int) velocityTracker.getXVelocity();

			Log.d(TAG, "velocityX:" + velocityX + "; event : up");

			if (velocityX > SNAP_VELOCITY && mIndex - 1 >= 0) {

				// Fling enough to move left

				Log.d(TAG, "snap left");

				snapToScreen(SHOWING_VIEW - 1);

			} else if (velocityX < -SNAP_VELOCITY
					&& mIndex + 1 <= mViewsQty - 1) {

				// Fling enough to move right

				Log.d(TAG, "snap right");

				snapToScreen(SHOWING_VIEW + 1);

			} else {

				snapToDestination();

			}

			if (mVelocityTracker != null) {

				mVelocityTracker.recycle();

				mVelocityTracker = null;

			}

			// }

			mTouchState = TOUCH_STATE_REST;
			break;

		case MotionEvent.ACTION_CANCEL:

			mTouchState = TOUCH_STATE_REST;

			break;

		}

		return true;

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		Log.d(TAG, "onInterceptTouchEvent-slop:" + mTouchSlop);

		final int action = ev.getAction();

		if ((action == MotionEvent.ACTION_MOVE) &&

		(mTouchState != TOUCH_STATE_REST)) {

			return true;

		}

		final float x = ev.getX();

		final float y = ev.getY();

		switch (action) {

		case MotionEvent.ACTION_MOVE:
			// if (mIndex > 0 && mIndex < listFilePath.size() - 1) {
			final int xDiff = (int) Math.abs(mLastMotionX - x);
			final int yDiff = (int) Math.abs(mLastMotionY - y);
			if (xDiff > mTouchSlop) {
				if (yDiff == 0 || yDiff / xDiff <= 0.5)
					mTouchState = TOUCH_STATE_SCROLLING;

			}
			// } else
			// mTouchState = TOUCH_STATE_REST;
			break;

		case MotionEvent.ACTION_DOWN:

			mLastMotionX = x;
			mLastMotionY = y;

			mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST
					: TOUCH_STATE_SCROLLING;

			break;

		case MotionEvent.ACTION_CANCEL:

		case MotionEvent.ACTION_UP:

			mTouchState = TOUCH_STATE_REST;

			break;

		}

		return mTouchState != TOUCH_STATE_REST;

	}

	@Override
	protected void onAttachedToWindow() {

		// mIndex = 0;
		// System.out.println("index:" + mIndex);
		// setToScreen(mIndex);
		// setViews();
		super.onAttachedToWindow();
	}

	@Override
	public void dispatchWindowFocusChanged(boolean hasFocus) {
		super.dispatchWindowFocusChanged(hasFocus);
	}

	@Override
	public void dispatchWindowVisibilityChanged(int visibility) {

		super.dispatchWindowVisibilityChanged(visibility);
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {

		super.onWindowFocusChanged(hasWindowFocus);
	}

	@Override
	protected void onWindowVisibilityChanged(int visibility) {
		super.onWindowVisibilityChanged(visibility);
	}

	@Override
	protected void onDetachedFromWindow() {

		super.onDetachedFromWindow();
	}

	public void viewsDestroy() {
		for (int i = 0; i < this.getChildCount(); i++) {
			if (this.getChildAt(i) instanceof IPageView) {
				((IPageView) this.getChildAt(i)).DestoryView();
			}
		}
	}
	
	public void refreshView(int position,String pRefreshMode) {
		IPageView oitemview=(IPageView) this.getChildAt(position);
		if (oitemview!=null) {
			oitemview.DestoryView();	
			oitemview.RefreshView(pRefreshMode);
		}
	}

	protected void InitViews() {
		viewsDestroy();
		removeAllViews();
//		int count = Math.min(3, mArticlePhotoQty);
		int count =3;
		for (int i = 0; i < count; i++) {
			View oItemView =CreateNewView(i-1);
			if(oItemView!=null)
				addView(oItemView);
		}
	}
	
}
