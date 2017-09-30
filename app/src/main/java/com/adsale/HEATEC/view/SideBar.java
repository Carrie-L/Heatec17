package com.adsale.HEATEC.view;

import java.util.List;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.database.model.clsSection;

import sanvio.libs.util.DisplayUtils;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class SideBar extends View {
	private List<clsSection> arrIndexList;
	private SectionIndexer sectionIndexter = null;
	private ListView list;
	private TextView mDialogText;
	private int color = 0xff858c94;
	private Paint paint;

	private int mFontSize;

	public SideBar(Context context) {
		super(context);
		init();
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {

		// l = new char[] {'#','A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
		// 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
		// 'W', 'X', 'Y', 'Z'};
		mFontSize = DisplayUtils.dip2px(getContext(), 12);
		paint = new Paint();
		paint.setColor(color);
		paint.setTextSize(mFontSize);
		paint.setStyle(Style.FILL);
		paint.setTextAlign(Paint.Align.CENTER);
	}

	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public void setListView(ListView _list, SectionIndexer pSectionIndexer, List<clsSection> pIndexList) {
		list = _list;
		sectionIndexter = pSectionIndexer;
		arrIndexList = pIndexList;
	}

	public void setIndexArray(List<clsSection> pIndexList) {
		arrIndexList = pIndexList;
		requestLayout();
	}

	public void setTextView(TextView mDialogText) {
		this.mDialogText = mDialogText;
	}

	@SuppressWarnings("deprecation")
	public boolean onTouchEvent(MotionEvent event) {

		super.onTouchEvent(event);
		int i = (int) event.getY();
		int intSize = arrIndexList.size();
		int idx = i / (getMeasuredHeight() / intSize);
		if (idx >= intSize) {
			idx = intSize - 1;
		} else if (idx < 0) {
			idx = 0;
		}
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			clsSection section = arrIndexList.get(idx);

			setBackgroundResource(R.drawable.scrollbar_bg);
			mDialogText.setVisibility(View.VISIBLE);
			mDialogText.setText(section.getLable());

			if (sectionIndexter == null) {
				sectionIndexter = (SectionIndexer) list.getAdapter();
			}

			int position = sectionIndexter.getPositionForSection(arrIndexList.indexOf(section));

			if (position == -1) {
				return true;
			}
			list.setSelection(position);
		} else {
			mDialogText.setVisibility(View.INVISIBLE);

		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			setBackgroundDrawable(new ColorDrawable(0x00000000));
		}
		return true;
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		float widthCenter = getMeasuredWidth() / 2;
		if (arrIndexList != null) {
			if (arrIndexList.size() > 1) {
				int intSize = arrIndexList.size();
				int y;
				int height = getMeasuredHeight() / (intSize);
				for (int i = 0; i < intSize; i++) {
					y = (i + 1) * height;
					canvas.drawText(arrIndexList.get(i).getLable(), widthCenter, y, paint);
				}
			} else if (arrIndexList.size() == 1) {
				int height = getMeasuredHeight();
				canvas.drawText(arrIndexList.get(0).getLable(), widthCenter, height / 2, paint);
			}

		}
		this.invalidate();

	}
}
