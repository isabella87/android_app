package com.banhuitong.activity;


public class MapActivity extends BaseActivity {

//	private LocationMode mCurrentMode;
//	BitmapDescriptor mCurrentMarker;
//	private static final int accuracyCircleFillColor = 0xAAFFFF88;
//	private static final int accuracyCircleStrokeColor = 0xAA00FF00;
//
//	MapView mMapView;
//	BaiduMap mBaiduMap;
//
//	// UI相关
//	Button requestLocButton;
//	boolean isFirstLoc = true; // 是否首次定位
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_location);
//		requestLocButton = (Button) findViewById(R.id.button1);
//		mCurrentMode = LocationMode.NORMAL;
//		requestLocButton.setText("普通");
//		OnClickListener btnClickListener = new OnClickListener() {
//			public void onClick(View v) {
//				switch (mCurrentMode) {
//				case NORMAL:
//					requestLocButton.setText("跟随");
//					mCurrentMode = LocationMode.FOLLOWING;
//					mBaiduMap
//							.setMyLocationConfigeration(new MyLocationConfiguration(
//									mCurrentMode, true, mCurrentMarker));
//					break;
//				case COMPASS:
//					requestLocButton.setText("普通");
//					mCurrentMode = LocationMode.NORMAL;
//					mBaiduMap
//							.setMyLocationConfigeration(new MyLocationConfiguration(
//									mCurrentMode, true, mCurrentMarker));
//					break;
//				case FOLLOWING:
//					requestLocButton.setText("罗盘");
//					mCurrentMode = LocationMode.COMPASS;
//					mBaiduMap
//							.setMyLocationConfigeration(new MyLocationConfiguration(
//									mCurrentMode, true, mCurrentMarker));
//					break;
//				default:
//					break;
//				}
//			}
//		};
//		requestLocButton.setOnClickListener(btnClickListener);
//
//		// 地图初始化
//		mMapView = (MapView) findViewById(R.id.bmapView);
//		mBaiduMap = mMapView.getMap();
//
//		mCurrentMarker = BitmapDescriptorFactory
//				.fromResource(R.drawable.icon_gcoding);
//		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
//				mCurrentMode, true, mCurrentMarker, accuracyCircleFillColor,
//				accuracyCircleStrokeColor));
//		
//		// 开启定位图层
//		mBaiduMap.setMyLocationEnabled(true);
//
//		BDLocation location = new BDLocation();
//		location.setLatitude(Constants.const_latitude);
//		location.setLongitude(Constants.const_longitude);
//
//		MyLocationData locData = new MyLocationData.Builder()
//				.accuracy(location.getRadius())
//				// 此处设置开发者获取到的方向信息，顺时针0-360
//				.direction(0).latitude(location.getLatitude())
//				.longitude(location.getLongitude()).build();
//		mBaiduMap.setMyLocationData(locData);
//
//		if (isFirstLoc) {
//			isFirstLoc = false;
//			LatLng ll = new LatLng(location.getLatitude(),
//					location.getLongitude());
//			MapStatus.Builder builder = new MapStatus.Builder();
//			builder.target(ll).zoom(18.0f);
//			mBaiduMap.animateMapStatus(MapStatusUpdateFactory
//					.newMapStatus(builder.build()));
//		}
//	}
//
//	@Override
//	protected void onPause() {
//		mMapView.onPause();
//		super.onPause();
//	}
//
//	@Override
//	protected void onResume() {
//		mMapView.onResume();
//		super.onResume();
//	}
//
//	@Override
//	protected void onDestroy() {
//		// 关闭定位图层
//		mBaiduMap.setMyLocationEnabled(false);
//		mMapView.onDestroy();
//		mMapView = null;
//		super.onDestroy();
//	}
}
