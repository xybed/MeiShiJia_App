package lib.baidu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.model.LocationModel;

import java.net.URISyntaxException;

import lib.utils.MyLogUtil;
import lib.utils.SystemUtil;
import lib.utils.ToastUtil;

/**
 * Created by 7mu on 2016/4/26.
 * 百度定位
 */
public class MyLocation {
    private static MyLocation myLocation;

    private LocationClient locationClient;
    private BDLocationListener bdLocationListener;
    private LocationModel locationData;
    public static final String COORTYPE = "bd09ll";//坐标系类型，有gcj02，wgs84，bd09mc

    private Context context;

    private MyLocation(Context context){
        this.context = context;
        initLocation();
    }

    public static MyLocation getInstance(){
        if(myLocation == null)
            myLocation = new MyLocation(MyApplication.getInstance());
        return myLocation;
    }

    public LocationModel getLocationData(){
        if("".equals(this.locationData.getDataProvide())){
            this.requestLocation();
        }
        return this.locationData;
    }

    private void initLocation(){
        locationData = new LocationModel();
        locationClient = new LocationClient(context);
        bdLocationListener = new MyLocationListener();
        locationClient.registerLocationListener(bdLocationListener);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        //option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        //option.setLocationNoti5onPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(true);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(true);//可选，默认false，设置是否收集CRASH信息，默认收集,不收集,由统计组件收集
        //option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        locationClient.setLocOption(option);

//        mClient.start();
        //requestLocation();
    }

    public void requestLocation(){
//        if(!mClient.isStarted())
//            mClient.start();
        locationClient.start();
        Log.d("baidulbs","start");
    }

    public  void stopLocation(){
//		if (mClient!=null&&mClient.isStarted()) {
//			mClient.stop();
//		}
        Log.d("baidulbs","stop");
        locationClient.stop();
    }

    class MyLocationListener implements BDLocationListener {

        public void onReceivePoi(BDLocation arg0) {

        }
        @Override
        public void onReceiveLocation(BDLocation arg0) {
            Log.d("baidulbs","receive");
            if (arg0 == null) {
                return;
            } else {
                stopLocation();
            }
            int type = arg0.getLocType();
            Log.d("baidulbs",type+"");
            switch (type) {
                case BDLocation.TypeCacheLocation:		//缓存定位结果
                case BDLocation.TypeNetWorkLocation:		//网络定位结果
                case BDLocation.TypeOffLineLocation:	//离线定位结果
                case BDLocation.TypeGpsLocation:		//GPS定位结果
                    transBaiduData(arg0);
                    break;
                case BDLocation.TypeCriteriaException:	//扫描整合定位依据失败。此时定位结果无效。
                    show("扫描整合定位依据失败", type);
                    break;
                case BDLocation.TypeNetWorkException:	//网络异常，没有成功向服务器发起请求。此时定位结果无效。
                    break;
                case BDLocation.TypeNone:
                    break;
                case BDLocation.TypeOffLineLocationFail://离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果
                    show("离线定位失败", type);
                    break;
                case BDLocation.TypeOffLineLocationNetworkFail:	//网络连接失败时，查找本地离线定位时对应的返回结果
                    break;
                case BDLocation.TypeServerError:	//服务端定位失败
                    ToastUtil.setContext(context);
                    ToastUtil.show("请确认您是否打开了定位权限");
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    /**
     * 将百度的数据转化为本地数据
     * @param bd
     */
    private void transBaiduData(BDLocation bd){
        locationData.setDataProvide("BD");
        MyLogUtil.e("baidu", bd.getCity());
        locationData.setCity(bd.getCity());
        locationData.setLat(bd.getLatitude());
        locationData.setLng(bd.getLongitude());
        locationData.setProvince(bd.getProvince());
        locationData.setRadius(bd.getRadius());
        locationData.setDirection(bd.getDirection());
        locationData.setAddrStr(bd.getAddrStr());
        locationData.setBuildingName(bd.getBuildingName());
        locationData.setCountry(bd.getCountry());
        locationData.setDistrict(bd.getDistrict());
        locationData.setFloor(bd.getFloor());
        locationData.setStreet(bd.getStreet());
        locationData.setStreetNumber(bd.getStreetNumber());
    }

    /**
     * 处理通过网络IP数据,如果百度有数据的话,则不做处理
     * @param eping
     */
    public void transIpLocationData(LocationModel eping){
        if("BD".equals(this.locationData.getDataProvide())){
            //百度地图数据不做处理
            return;
        }
        this.locationData = eping;
        this.locationData.setDataProvide("ep");

    }
    /**Toast提示*/
    private void show(String content, int type){
        StringBuffer sb = new StringBuffer();
        sb.append(content);
        sb.append("，无法获取所处城市信息，错误代码：");
        sb.append(type);
        Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();
        Log.e("Location", sb.toString());
    }

    public  void NavigationTo(Context context, String lat, String lng, String address){
        try {
            //百度地图初始化
//			SDKInitializer.initialize(MyApplication.getInstance().getApplicationContext());
            if (!"".equals(locationData.getDataProvide())) {
//            NaviParaOption para = new NaviParaOption();
//            LatLng pt1 = new LatLng(NumberUtil.parseDouble(locationData.getLat(), 0), NumberUtil.parseDouble(locationData.getLng(),0));
//            LatLng pt2 = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
//            para.startPoint(pt1).startName(context.getResources().getString(R.string.toast_gps_from)).endPoint(pt2).endName(context.getResources().getString(R.string.toast_gps_to));
//            try {
//
//                BaiduMapNavigation.o
// penBaiduMapNavi(para,
//                        context);
//
//            } catch (BaiduMapAppNotSupportNaviException e) {
//                e.printStackTrace();
//            }

                //如果已安装,调用百度地图客户端，未安装则调用浏览器客户端
                if (SystemUtil.isInstalled(context, "com.baidu.BaiduMap")) {//传入指定应用包名
                    Intent intent = null;
                    try {
                        intent = Intent.parseUri("intent://map/direction?" +
                                "origin=latlng:"
                                + locationData.getLat()
                                + ","
                                + locationData.getLng()
                                + "|name:I'm here" +
                                "&destination=latlng:"
                                + lat
                                + ","
                                + lng
                                + "|name:"
                                + "&mode=driving&region=" + locationData.getCity()
                                + "&src=医评心声"
                                + "#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end", 0);
                        context.startActivity(intent); //启动调用
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                } else {
                    String baiduMapUrl = "http://api.map.baidu.com/direction?" +
                            "origin=latlng:"
                            + locationData.getLat()
                            + ","
                            + locationData.getLng()
                            + "|name:I'm here" +
                            "&destination=latlng:"
                            + lat
                            + ","
                            + lng
                            + "|name:"
                            + "&mode=driving&region=" + locationData.getCity()
                            + "&output=html&src=医评心声";
                    Uri uri = Uri.parse(baiduMapUrl);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }
            } else {
                ToastUtil.setContext(this.context);
                ToastUtil.show("暂无定位数据,请打开定位功能,再试一下");
            }
        }catch(Exception e){
            ToastUtil.show("出错啦，请再试一下");
        }
    }
}
