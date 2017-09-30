package com.adsale.HEATEC.view;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.database.ExhibitorDBHelper;
import com.adsale.HEATEC.database.model.clsExhibitor;
import com.adsale.HEATEC.fragment.ExhibitorDetailFragment;
import com.adsale.HEATEC.util.SystemMethod;

import sanvio.libs.util.DialogUtils;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class NoteView extends RelativeLayout {

	public static final String TAG = "NoteView";

	private Context mContext;
	private Button btnSave, btnClear;
	private EditText editNote;

	private clsExhibitor mClsExhibitor;

	public NoteView(Context context) {
		super(context);
		setupView();
	}

	public NoteView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupView();
	}

	public NoteView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setupView();
	}

	private void setupView() {
		mContext = getContext();
		LayoutInflater.from(mContext).inflate(R.layout.view_note, this);
		findView();
	}

	private void findView() {
		btnSave = (Button) findViewById(R.id.btnSave);
		btnClear = (Button) findViewById(R.id.btnClear);
		editNote = (EditText) findViewById(R.id.editNote);
	}

	public void setData(clsExhibitor exhibitor) {
		mClsExhibitor = exhibitor;
		editNote.setText(mClsExhibitor.getNote());
		btnSave.setOnClickListener(savaNoteListener);
		btnClear.setOnClickListener(savaNoteListener);
	}

	private OnClickListener savaNoteListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			SystemMethod.hideSoftInput(mContext);
			final ExhibitorDBHelper oExhibitorDBHelper = new ExhibitorDBHelper(mContext);
			if (v.getId() == R.id.btnSave) {
				String strNote = editNote.getText().toString().trim();
				if (strNote.isEmpty())
					return;
				mClsExhibitor.setNote(strNote);
				mClsExhibitor.setIsFavourite(1);
				oExhibitorDBHelper.Update(mClsExhibitor);
				
				FragmentManager fm = ((FragmentActivity) mContext).getSupportFragmentManager();
				ExhibitorDetailFragment oFragment = (ExhibitorDetailFragment) fm.findFragmentByTag(ExhibitorDetailFragment.TAG);
				if (oFragment != null) {
					oFragment.refCollection() ;
				}
				
				DialogUtils.showAlertDialog(mContext, R.string.note_successful, null);
			} else {
				DialogUtils.showAskDialog(mContext, R.string.ask_clear_note, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mClsExhibitor.setNote("");
						editNote.setText("");
						oExhibitorDBHelper.Update(mClsExhibitor);
					}
				}, null);

			}
		}
	};

}
