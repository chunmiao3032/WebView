package com.example.webview;

import java.io.Serializable;
import java.util.List;

import com.basic.CardResult;
import com.common.CommonMethord;
import com.droid.Activity01;
import com.droid.DatabaseHelper;
import com.common.CustomDialog; 

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

	private DatabaseHelper helper;
	private List<CardResult> items;
	private LayoutInflater inflater;
	CustomDialog dialog;

	public ListViewAdapter(Context context, List<CardResult> items,DatabaseHelper helper) {
		this.items = items;
		this.helper = helper;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if (view == null) {
			view = inflater.inflate(R.layout.card_list_item, null);
		}
		final CardResult card = items.get(position);
		// ivImg tvApplyUserCount tvCardName tvTitleDesc ivApply
		ImageView ivImg = (ImageView) view.findViewById(R.id.ivImg);
		TextView tvApplyUserCount = (TextView) view
				.findViewById(R.id.tvApplyUserCount);// 申请人数
		TextView tvCardName = (TextView) view.findViewById(R.id.tvCardName);// 卡名称
		TextView tvCardName1 = (TextView) view.findViewById(R.id.tvCardName1);// 卡名称1
		// TextView tvTitleDesc1 = (TextView)
		// view.findViewById(R.id.tvTitleDesc1);//标题描述1
		// TextView tvTitleDesc2 = (TextView)
		// view.findViewById(R.id.tvTitleDesc2);//标题描述2
		// TextView tvTitleDesc3 = (TextView)
		// view.findViewById(R.id.tvTitleDesc3);//标题描述3
		TextView tvBankName = (TextView) view.findViewById(R.id.tvBankName);// 银行名

		TextView tvCardDesc1 = (TextView) view.findViewById(R.id.tvCardDesc1);// 免息期
		TextView tvCardDesc3 = (TextView) view.findViewById(R.id.tvCardDesc3);// 积分有效期
		TextView tvCardDesc2 = (TextView) view.findViewById(R.id.tvCardDesc2);// 取现额度
		TextView tvCardDesc4 = (TextView) view.findViewById(R.id.tvCardDesc4);// 卡分类

		Button ivApply = (Button) view.findViewById(R.id.ivApply);
		Button btCardDesc7 = (Button) view.findViewById(R.id.btCardDesc7);

		Bitmap bitmap = CommonMethord.base64ToBitmap(card.CARD_IMG_DATA);
		ivImg.setImageBitmap(bitmap);
		tvApplyUserCount.setText(card.APPLY_USER_COUNT);
		tvCardName.setText(card.CARD_NAME);
		tvCardName1.setText(card.CARD_NAME);
		// tvTitleDesc1.setText(card.CARD_TITLE_DESC1);
		// tvTitleDesc2.setText(card.CARD_TITLE_DESC2);
		// tvTitleDesc3.setText(card.CARD_TITLE_DESC3);
		tvBankName.setText(card.BANK_ID);
		tvCardDesc1.setText(card.CARD_DESC1);
		tvCardDesc2.setText(card.CARD_DESC2);
		tvCardDesc3.setText(card.CARD_DESC3);
		tvCardDesc4.setText(card.CARD_DESC4);

		ivApply.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				CommonMethord.InsertCard(card,helper);

				Intent intent = new Intent(v.getContext(),
						WebViewActivity.class);
				intent.putExtra("Url", card.CARD_URL);
				v.getContext().startActivity(intent);
				
				

			}
		});

		btCardDesc7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

//				new AlertDialog.Builder(v.getContext()).setTitle("注意事项")
//						.setMessage(card.CARD_DESC7)
//						.setPositiveButton("知道了", null).show();

				String strMsg = 
						card.CARD_TITLE_DESC1 + "\n" +
						card.CARD_TITLE_DESC2 + "\n" +
						card.CARD_TITLE_DESC3 + "\n" +
						card.CARD_DESC7 + "\n";
				strMsg = strMsg.replace("anyType{}", "");
				CustomDialog.Builder customBuilder = new CustomDialog.Builder(v.getContext());
				customBuilder
						.setTitle("注意事项")
						.setMessage(strMsg)
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
		                    public void onClick(DialogInterface dialog, int which) {
		                    	dialog.dismiss();
		                    }
		                });
				
				dialog = customBuilder.create();
				dialog.show();

			}
		});

		return view;
	}

	/**
	 * 添加列表项
	 * 
	 * @param item
	 */
	public void addItem(CardResult item) {
		items.add(item);
	}
}
