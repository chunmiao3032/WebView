package com.example.webview;

import java.util.ArrayList;
import java.util.List;

import com.basic.BankResult;
import com.basic.BankResultOK;
import com.basic.OtherLinkResult;
import com.common.CommonMethord;
import com.common.Global;
import com.common.MyGridView;
import com.droid.Activity01;
import com.example.webview.MainActivity.GridViewAdapter;
import com.webservice.WebServiceManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class CardMainActivity extends Activity {

	ImageButton ibCardShow, ibSxcx, ibApplyQuery, ibSysLink1, ibSysLink2,
			ibSysLink3,ibYJTE;

	TextView headTitle;
	ImageView ivBack;

	// --------------------------
	List<BankResultOK> _Banks;
	RelativeLayout itmel;
	MyGridView gridView;
	ScrollView svMain;
	// --------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 去掉窗口标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cardmain);

		initHeader("申卡");

		Handler = new Handler();
		svMain = (ScrollView)findViewById(R.id.svMain);
		ibCardShow = (ImageButton) findViewById(R.id.ibCardShow);
		ibSxcx = (ImageButton) findViewById(R.id.ibSxcx);
		ibApplyQuery = (ImageButton) findViewById(R.id.ibApplyQuery);
		ibSysLink1 = (ImageButton) findViewById(R.id.ibSysLink1);
		ibSysLink2 = (ImageButton) findViewById(R.id.ibSysLink2);
		ibSysLink3 = (ImageButton) findViewById(R.id.ibSysLink3);
		ibYJTE = (ImageButton) findViewById(R.id.ibYJTE);

		ibCardShow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(CardMainActivity.this,
						CardShowActivity.class);
				startActivity(intent);
			}
		});

		ibSxcx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(CardMainActivity.this,
						CardShowActivity.class);
				startActivity(intent);
			}
		});

		ibApplyQuery.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(CardMainActivity.this,
						CardApplyQueryActivity.class);
				startActivity(intent);
			}
		});

//		ibSysLink1.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				for (OtherLinkResult link : Global.OutlinksList) {
//					if (link.LINK_NAME.equals("SysLink1")) {
//						Intent intent = new Intent(CardMainActivity.this,
//								WebViewActivity.class);
//						intent.putExtra("Url", link.LINK_URL);
//						startActivity(intent);
//					}
//				}
//
//			}
//		});

//		ibSysLink2.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				for (OtherLinkResult link : Global.OutlinksList) {
//					if (link.LINK_NAME.equals("SysLink2")) {
//						Intent intent = new Intent(CardMainActivity.this,
//								WebViewActivity.class);
//						intent.putExtra("Url", link.LINK_URL);
//						startActivity(intent);
//					}
//				}
//
//			}
//		});

//		ibSysLink3.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				for (OtherLinkResult link : Global.OutlinksList) {
//					if (link.LINK_NAME.equals("SysLink3")) {
//						Intent intent = new Intent(CardMainActivity.this,
//								WebViewActivity.class);
//						intent.putExtra("Url", link.LINK_URL);
//						startActivity(intent);
//					}
//				}
//
//			}
//		});
		
		// 提额
		ibYJTE.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CardMainActivity.this,
						AddQuotaShow1Activity.class);
				startActivity(intent);

			}
		});

		// --------------------------
		gridView = (MyGridView) findViewById(R.id.grid);
		setData();
		// --------------------------
	}

    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	svMain.fullScroll(ScrollView.FOCUS_UP);
    } 
	
	public void initHeader(String title) {
		headTitle = (TextView) findViewById(R.id.headTitle);
		ivBack = (ImageView) findViewById(R.id.ivBack);

		headTitle.setText(title);
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();
			}
		});
	}

	// ---------------------------------------------------
	private void setData() {

		new Thread() {
			public void run() {
				_Banks = WebServiceManager.getBanksOKCity();
				Global.BankList = _Banks;
				Handler.post(mUpdateResults);
			}

		}.start();

	}

	Handler Handler = null;
	final Runnable mUpdateResults = new Runnable() {
		public void run() {
			setGridView();
			//固定scrollView显示位置
			svMain.fullScroll(ScrollView.FOCUS_UP);
		}
	};

	private void setGridView() {

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		GridViewAdapter adapter = new GridViewAdapter(getApplicationContext(),
				_Banks);
		gridView.setAdapter(adapter);
	}

	public class GridViewAdapter extends BaseAdapter {
		Context context;
		List<BankResultOK> list;

		public GridViewAdapter(Context _context, List<BankResultOK> _list) {
			this.list = _list;
			this.context = _context;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			convertView = layoutInflater.inflate(R.layout.cardapplyquery_item,
					null);

			final BankResultOK bank = list.get(position);

			TextView tvTitle1 = (TextView) convertView
					.findViewById(R.id.tvTitle1);
			TextView tvDesc1 = (TextView) convertView
					.findViewById(R.id.tvDesc1);

			ImageButton ItemImage1 = (ImageButton) convertView
					.findViewById(R.id.ItemImage1);

			tvTitle1.setText(bank.BANK_NAME);
			tvDesc1.setText(bank.BANK_DESC);

			if (bank.BANK_IMG_DATA != null) {
				Bitmap bm = CommonMethord.base64ToBitmap(bank.BANK_IMG_DATA);
				ItemImage1.setImageBitmap(bm);
				
//				Bitmap bm = CommonMethord.base64ToBitmap(bank.BANK_IMG_DATA);
//				Drawable drawable = CommonMethord.bitmap2Drawable(bm);
//				
//				ItemImage1.setBackground(drawable);
			}

			ItemImage1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(CardMainActivity.this,
							CardShowActivity.class);
					intent.putExtra("bank", bank.BANK_NAME);
					startActivity(intent);
				}
			});

			return convertView;
		}
	}

	// ---------------------------------------------------

}
