package com.adsale.HEATEC.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.dao.MainIcon;
import com.adsale.HEATEC.database.model.clsMainIcon;
import com.facebook.imagepipeline.request.BaseRepeatedPostProcessor;

/**
 * 在Icon上添加文字标题
 *
 * @author Carrie
 * @version 创建时间：2016年9月30日 下午1:24:30
 * @Description: TODO
 */
public class TextMeshPostProcessor extends BaseRepeatedPostProcessor {
	private static final String TAG = "TextMeshPostProcessor";
	private static final float TEXT_SIZE = 36;
	private TextPaint paint;
	private MainIcon mEntity;
	private int width;
	private int height;
	private Canvas canvas;
	private int mLanguage;
	private Context mContext;
	/**
	 * 判断是否是封面大图，大图与小图的文字位置不一样
	 */
	private boolean cover = false;
	private StaticLayout slayout;

	public TextMeshPostProcessor(Context context, MainIcon entity, boolean large) {
		mEntity=entity;
		mContext=context;
		
		paint = new TextPaint();
		paint.setAntiAlias(true);

		if(large){
			paint.setTextSize(20);
		}else
			paint.setTextSize(TEXT_SIZE);
		
		LogUtil.i(TAG, "entity.getGoogle_TJ()="+entity.getGoogle_TJ());

		if(SystemMethod.isPadDevice(context)){
			setTextColor(mEntity.getGoogle_TJ().split("\\|")[1]);
		}else{
			setTextColor(mEntity.getGoogle_TJ().split("\\|")[0]);
		}

		paint.setFakeBoldText(true);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		
		mLanguage=SystemMethod.getCurLanguage(context);

	}
	
	private void setTextColor(String googleTJ){
		if (googleTJ.contains("C0")) {
			paint.setColor(Color.WHITE);
		} else if (googleTJ.contains("C1")) {
			paint.setColor(mContext.getResources().getColor(R.color.text_black));
		}
	}

	@Override
	public void process(Bitmap bitmap) {
		canvas = new Canvas(bitmap);

		width = bitmap.getWidth();
		height = bitmap.getHeight();

		LogUtil.d(TAG, "width=" + width + ",height=" + height + ",cover=" + cover);

		slayout = new StaticLayout(mEntity.getTitle(mLanguage), paint, width - 16,
				Alignment.ALIGN_CENTER, 1, 0, true);

		canvas.translate(0 + 8, height * 5 / 8);

		slayout.draw(canvas);

		super.process(bitmap);
	}

}
