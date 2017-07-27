package com.example.forev.uniminuto_indoor;

import android.Manifest;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.customlbs.coordinates.GeoCoordinate;
import com.customlbs.library.IndoorsException;
import com.customlbs.library.IndoorsFactory;
import com.customlbs.library.IndoorsLocationListener;
import com.customlbs.library.callbacks.LoadingBuildingStatus;
import com.customlbs.library.model.Building;
import com.customlbs.library.model.Zone;
import com.customlbs.shared.Coordinate;
import com.customlbs.surface.library.IndoorsSurfaceFactory;
import com.customlbs.surface.library.IndoorsSurfaceFragment;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IndoorsLocationListener {
    public static final int REQUEST_CODE_PERMISSIONS = 34168; //Random request code, use your own
    public static final int REQUEST_CODE_LOCATION = 58774; //Random request code, use your own
    private IndoorsSurfaceFragment indoorsSurfaceFragment;
    private Toast progressToast;
    private static int lastProgress = 0;
    String APIKEY = "91c2793f-993f-454f-8802-96a3fe8cdb3c";
    long BuildingID = 795523136;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            //1. PermissionListener -> 권한이 허가 or 거부시 결과를 리턴해주는 리스너 생성.
            checkLocationIsEnabled();

        }
        else{ //버전이 마시멜로 이하인 경우
            continueLoading();
        }
    }

    private void checkLocationIsEnabled() { //장소가 연결되었는지 체크
        // On android Marshmallow we also need to have active Location Services (GPS or Network based)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //마시멜로버전 이상인 경우
            LocationManager locationManager =
                    (LocationManager) getSystemService(LOCATION_SERVICE); //장소관리 매니저 생성.
            boolean isNetworkLocationProviderEnabled =
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER); // 네트워크장소제공이 되는지 확인
            boolean isGPSLocationProviderEnabled =
                    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER); // GPS 장소 제공이 되는지 확인.

            if (!isGPSLocationProviderEnabled &&
                    !isNetworkLocationProviderEnabled) { //만약 둘다 연결되지 않았다면
                // Only if both providers are disabled
                // we need to ask the user to do something
                Toast.makeText(this,
                        "Location is off, enable it in system settings.",
                        Toast.LENGTH_LONG).show(); //세팅에서 열어라
                Intent locationInSettingsIntent =
                        new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS); //세팅창에대한 인텐트 설정.
                this.startActivityForResult(
                        locationInSettingsIntent, REQUEST_CODE_LOCATION); //그화면으로 보내버린다.
            } else {
                continueLoading(); //둘 다 연결되면 로딩 진행.
            }
        } else {
            continueLoading();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //결과화면 생성??
            // Check if the user has really enabled Location services.
            checkLocationIsEnabled();
    }
    // At this point we can continue to load
// the Indoo.rs SDK as we did with previous
// android versions
    private void continueLoading() { //로딩 진행
        IndoorsFactory.Builder indoorsBuilder = initializeIndoorsLibrary(); //인도어 빌더 객체 생성.
        indoorsSurfaceFragment = initializeIndoorsSurface(indoorsBuilder); //표면작업??
        setSurfaceFragment(indoorsSurfaceFragment);
    }
    private IndoorsFactory.Builder initializeIndoorsLibrary() {
        /**
         * This will initialize the builder for the Indoo.rs object
         */
        IndoorsFactory.Builder indoorsBuilder = new IndoorsFactory.Builder();
        indoorsBuilder.setContext(this);
        /**
         * TODO: replace this with your API-key
         * This is your API key as set on https://api.indoo.rs
         */
        indoorsBuilder.setApiKey(APIKEY);
        /**
         * TODO: replace 12345 with the id of the building you uploaded to our cloud using the MMT
         * This is the ID of the Building as shown in the desktop Measurement Tool (MMT)
         */
        indoorsBuilder.setBuildingId(BuildingID);
        // callback for indoo.rs-events
        indoorsBuilder.setUserInteractionListener(this); //유저에 대해 세팅이 완료됨.
        return indoorsBuilder; //빌더를 리턴.
    }

    private IndoorsSurfaceFragment initializeIndoorsSurface(IndoorsFactory.Builder indoorsBuilder) {
        /**
         * This will initialize the UI from Indoo.rs which is called IndoorsSurface.
         * The implementation is the IndoorsSurfaceFragment
         *
         * If you use your own map view implementation you don't need the Surface.
         * https://indoors.readme.io/docs/localisation-without-ui
         *
         */
        IndoorsSurfaceFactory.Builder surfaceBuilder = new IndoorsSurfaceFactory.Builder();
        surfaceBuilder.setIndoorsBuilder(indoorsBuilder);
        return surfaceBuilder.build();
    }

    private void setSurfaceFragment(final IndoorsSurfaceFragment indoorsFragment) {
        /**
         * This will add the IndoorsSurfaceFragment to the current layout
         */
        // http://stackoverflow.com/questions/33264031/calling-dialogfragments-show-from-within-onrequestpermissionsresult-causes/34204394#34204394
        // http://stackoverflow.com/questions/17184653/commitallowingstateloss-in-fragment-activities
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(android.R.id.content, indoorsFragment, "indoors");
                transaction.commit();
            }
        });
    }

    @Override
    public void positionUpdated(Coordinate userPosition, int accuracy) {
        /**
         * Is called each time the Indoors Library calculated a new position for the user
         * If Lat/Lon/Rotation of your building are set correctly you can calculate a
         * GeoCoordinate for your users current location in the building.
         */
        GeoCoordinate geoCoordinate = indoorsSurfaceFragment.getCurrentUserGpsPosition(); //유저 위치 얻어오는 함수.

        if (geoCoordinate != null) {
            Toast.makeText(
                    this,
                    "User is located at " + geoCoordinate.getLatitude() + "," //위도
                            + geoCoordinate.getLongitude(), Toast.LENGTH_SHORT).show(); //경도
        }
    }

    @Override
    public void loadingBuilding(LoadingBuildingStatus loadingBuildingStatus) {
        // indoo.rs is still downloading or parsing the requested building
        // Inform the User of Progress
        showDownloadProgressToUser(loadingBuildingStatus.getProgress()); //빌딩 다운로드
    }

    @Override
    public void buildingLoaded(Building building) {
        // Fake a 100% progress to your UI when you receive info that the download is finished.
        showDownloadProgressToUser(100);
        // indoo.rs SDK successfully loaded the building you requested and
        // calculates a position now
        Toast.makeText(
                this,
                "Building is located at " + building.getLatOrigin() / 1E6 + ","
                        + building.getLonOrigin() / 1E6, Toast.LENGTH_SHORT).show();
    }

    private void showDownloadProgressToUser(int progress) { //다운로드에 대한 프로그레스바 생성.
        if (progress % 2 == 0) { // Avoid showing too many values.
            if (progress > lastProgress) {
                lastProgress = progress; // Avoid showing same value multiple times.

                if (progressToast != null) {
                    progressToast.cancel();
                }

                progressToast = Toast.makeText(this, "Building downloading : "+progress+"%", Toast.LENGTH_SHORT);
                progressToast.show();
            }
        }
    }


    @Override
    public void onError(IndoorsException indoorsException) {
        Toast.makeText(this, indoorsException.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void changedFloor(int floorLevel, String name) { //층의 변화
        // user changed the floor
    }

    @Override
    public void leftBuilding(Building building) { //왼쪽 건물
        // Deprecated
    }

    @Override
    public void buildingReleased(Building building) {
        // Another building was loaded, you can release any resources related to linked building
    }

    @Override
    public void orientationUpdated(float orientation) {
        // user changed the direction he's heading to
    }

    @Override
    public void enteredZones(List<Zone> zones) {
        // user entered one or more zones
    }

    @Override
    public void buildingLoadingCanceled() {
        // Loading of building was cancelled
    }
}
