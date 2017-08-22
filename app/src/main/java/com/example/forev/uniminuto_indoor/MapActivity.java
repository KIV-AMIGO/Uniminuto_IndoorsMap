package com.example.forev.uniminuto_indoor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.customlbs.coordinates.GeoCoordinate;
import com.customlbs.library.IndoorsException;
import com.customlbs.library.IndoorsFactory;
import com.customlbs.library.IndoorsLocationListener;
import com.customlbs.library.LocalizationParameters;
import com.customlbs.library.callbacks.LoadingBuildingStatus;
import com.customlbs.library.callbacks.RoutingCallback;
import com.customlbs.library.callbacks.ZoneCallback;
import com.customlbs.library.model.Building;
import com.customlbs.library.model.Zone;
import com.customlbs.shared.Coordinate;
import com.customlbs.surface.library.IndoorsSurface;
import com.customlbs.surface.library.IndoorsSurfaceFactory;
import com.customlbs.surface.library.IndoorsSurfaceFragment;
import com.customlbs.surface.library.IndoorsSurfaceQuickAction;

import java.util.ArrayList;
import java.util.List;

import static com.example.forev.uniminuto_indoor.R.id.btn_credit;

public class MapActivity extends AppCompatActivity implements IndoorsLocationListener {

    public static final int REQUEST_CODE_LOCATION = 58774;
    private IndoorsSurfaceFragment indoorsSurfaceFragment;
    private Toast progressToast;
    private static int lastProgress = 0;
    String APIKEY = "91c2793f-993f-454f-8802-96a3fe8cdb3c";
    long BuildingID = 795523136;
    private TextView infoTxt;
    private TextView destinationTxt;
    private ImageView img_cancle ;
    private static ArrayList<Zone> zones = new ArrayList<>();
    private ArrayList<String> zoneList = new ArrayList<>();
    private Coordinate destinationCoor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        img_cancle =(ImageView)findViewById(R.id.img_cancle);
        Toast.makeText(MapActivity.this, "Espere hasta que el mapa aparezca", Toast.LENGTH_LONG).show();
        infoTxt = (TextView) findViewById(R.id.txt_gpsInfo);
        destinationTxt = (TextView) findViewById(R.id.destinationText);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            //1. PermissionListener ->  Listner sobre la permisión
            checkLocationIsEnabled();

        }
        else{ //Si la versión es inferior al mashmallow
            continueLoading();
        }

        //Botón de regreso
        Button btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),HomeActivity.class));
                MapActivity.this.finish();
            }
        });

        //Botón de menú
        Button btn_map = (Button) findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),MapActivity.class));
                MapActivity.this.finish();
            }
        });

        Button btn_home = (Button) findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), HomeActivity.class));
                MapActivity.this.finish();
            }
        });
        Button btn_setting = (Button) findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),SettingActivity.class));
                MapActivity.this.finish();
            }
        });

        Button btn_menual = (Button) findViewById(R.id.btn_menual);
        btn_menual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplication(),MenualActivity.class));
                MapActivity.this.finish();
            }
        });
        //Fin de botón de menú

        // Al hacer click el botón de click, Con ListView se mira el listado de  Zonas
        Button btn_search = (Button) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IndoorsFactory.getInstance().getZones(indoorsSurfaceFragment.getBuilding(), new ZoneCallback() {
                    @Override
                    public void setZones(ArrayList<Zone> arrayList) {
                        for(int i = 0; i < arrayList.size(); i++) {
                            zoneList.add(arrayList.get(i).getName());
                            zones.add(arrayList.get(i));
                        }
                        Intent it = new Intent(getApplication(), ZoneListActivity.class);
                        it.putExtra("zoneList", zoneList);
                        startActivity(it);
                    }
                });
            } //end of onClick
        });

        destinationTxt = (TextView) findViewById(R.id.destinationText);
        final String destination = getIntent().getStringExtra("destination");

        if(destination != null) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MapActivity.this);
            alertDialogBuilder.setTitle("Configuración de ruta");
            alertDialogBuilder
                    .setMessage("¿Va configurar la ruta?")
                    .setCancelable(false)
                    .setPositiveButton("Sí",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(MapActivity.this, "Destination : " + destination, Toast.LENGTH_LONG).show();
                                    destinationTxt.setText("Destino : " + destination);
                                    findRoute(destination);
                                }
                            })
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.cancel();
                                }
                            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } //Fin de botón de búsqueda
 // Botón de cancelación de búsqueda de camino
        img_cancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                indoorsSurfaceFragment.getSurfaceState().setRoutingPath(null);
                indoorsSurfaceFragment.updateSurface();
                destinationTxt.setText("Destino : ");
                img_cancle.setVisibility(View.INVISIBLE);
            }
        });
//Fin de botón de cancelación de búsqueda de camino
        indoorsSurfaceFragment.registerOnSurfaceLongClickListener(new IndoorsSurface.OnSurfaceLongClickListener() {
            @Override
            public void onLongClick(final Coordinate coordinate) {
                Context context;
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MapActivity.this);
                alertDialogBuilder.setTitle("Configuración de ruta");
                alertDialogBuilder
                        .setMessage("¿Va configurar la ruta?")
                        .setCancelable(false)
                        .setPositiveButton("Sí",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                            routing(coordinate);
                                    }
                                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    } //end of onCreate(
// Función de búsqueda de camino (ListView)
    public void findRoute(String destination) {
        for(int i = 0; i < zones.size(); i++) {
            if(zones.get(i).getName().equals(destination)) {
//                Toast.makeText(MapActivity.this, "Success", Toast.LENGTH_LONG).show();
                destinationCoor = new Coordinate(zones.get(i).getZonePoints().get(0).x, zones.get(i).getZonePoints().get(0).y, zones.get(i).getZonePoints().get(0).z);
                routing(destinationCoor);
                break;
            } else {
//                Toast.makeText(MapActivity.this, "Fail", Toast.LENGTH_LONG).show();
            }
        }
    }

    // Función de búsqueda de camino
    public void routing(Coordinate end){
        int currentFloor = 8;
        Coordinate start =  indoorsSurfaceFragment.getCurrentUserPosition();
        //indoorsSurfaceFragment.setFloor(currentFloor);
                // indoorsSurfaceFragment.getCurrentUserPosition();;
                // new Coordinate(93808,45276,8);
        //
        //double latitude = geoCoordinate.getLatitude();
        indoorsSurfaceFragment.getIndoors().getRouteAToB(start, end, new RoutingCallback() {
            @Override
            public void onError(IndoorsException arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(MapActivity.this, "Error", Toast.LENGTH_LONG).show();
            }

            @Override
            public void setRoute(ArrayList<Coordinate> route) {
                indoorsSurfaceFragment.getSurfaceState().setRoutingPath(route);
                // this is how to enable route snapping starting with version 3.8
                IndoorsFactory.getInstance().enableRouteSnapping(route);
                indoorsSurfaceFragment.updateSurface();
                img_cancle.setVisibility(View.VISIBLE);
            }
        });
    }

    private void checkLocationIsEnabled() {
        // On android Marshmallow we also need to have active Location Services (GPS or Network based)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            LocationManager locationManager =
                    (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean isNetworkLocationProviderEnabled =
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            boolean isGPSLocationProviderEnabled =
                    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (!isGPSLocationProviderEnabled &&
                    !isNetworkLocationProviderEnabled) {
                // Only if both providers are disabled
                // we need to ask the user to do something
                Toast.makeText(this,
                        "La ubicación está apagada, enciéndalo en ajustes.", //La ubicación está apagada, enciéndalo en ajustes
                        Toast.LENGTH_LONG).show(); //세팅에서 열어라
                Intent locationInSettingsIntent =
                        new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                this.startActivityForResult(
                        locationInSettingsIntent, REQUEST_CODE_LOCATION);
            } else {
                usingKalmanStabilisationFilter();
                continueLoading();
            }
        } else {
            usingKalmanStabilisationFilter();
            continueLoading();
        }
    }

    //Utilizar el filtro para la ubicación exacta
    public void usingKalmanStabilisationFilter() {
        LocalizationParameters parameters = new LocalizationParameters();
        parameters.setUseKalmanStrategy(false);
        parameters.setUseStabilizationFilter(true);
        parameters.setStabilizationFilterTime(4000); // 4000 milliseconds 4s
        IndoorsFactory.Builder indoorsBuilder = new IndoorsFactory.Builder();
        indoorsBuilder.setLocalizationParameters(parameters);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            // Check if the user has really enabled Location services.
            checkLocationIsEnabled();
    }

    // At this point we can continue to load
// the Indoo.rs SDK as we did with previous
// android versions
    private void continueLoading() {
        IndoorsFactory.Builder indoorsBuilder = initializeIndoorsLibrary();
        indoorsSurfaceFragment = initializeIndoorsSurface(indoorsBuilder);
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
        indoorsBuilder.setApiKey(APIKEY); //Número de llave
        /**
         * TODO: replace 12345 with the id of the building you uploaded to our cloud using the MMT
         * This is the ID of the Building as shown in the desktop Measurement Tool (MMT)
         */
        indoorsBuilder.setBuildingId(BuildingID); //El nombre del edificio
        // callback for indoo.rs-events
        indoorsBuilder.setUserInteractionListener(this);
        return indoorsBuilder;
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

    private void setSurfaceFragment(final IndoorsSurfaceFragment indoorsFragment) {//Función que dibuja el mapa
        /**
         * This will add the IndoorsSurfaceFragment to the current layout
         */
        // http://stackoverflow.com/questions/33264031/calling-dialogfragments-show-from-within-onrequestpermissionsresult-causes/34204394#34204394
        // http://stackoverflow.com/questions/17184653/commitallowingstateloss-in-fragment-activities
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                transaction.add(R.id.mapView, indoorsFragment, "indoors");
                transaction.commit();
            }
        });
    }

    @Override
    public void positionUpdated(Coordinate userPosition, int accuracy) {//Función que muestra la ubicación actual del usuario
        /**
         * Is called each time the Indoors Library calculated a new position for the user
         * If Lat/Lon/Rotation of your building are set correctly you can calculate a
         * GeoCoordinate for your users current location in the building.
         */
        GeoCoordinate geoCoordinate = indoorsSurfaceFragment.getCurrentUserGpsPosition();

        if (geoCoordinate != null) {

            infoTxt.setText("User Locate :  "+indoorsSurfaceFragment.getCurrentFloor()+"\n Latitude "+geoCoordinate.getLatitude() + "Longitude "+geoCoordinate.getLongitude());
            //  Toast.makeText(
            //       this,
            //      "User is located at " + geoCoordinate.getLatitude() + "," //위도
            //              + geoCoordinate.getLongitude()+ "   "+indoorsSurfaceFragment.getCurrentFloor(), Toast.LENGTH_SHORT).show(); //경도
        }
    }

    @Override
    public void loadingBuilding(LoadingBuildingStatus loadingBuildingStatus) {
        // indoo.rs is still downloading or parsing the requested building
        // Inform the User of Progress
        showDownloadProgressToUser(loadingBuildingStatus.getProgress()); //Download el edificio
    }

    @Override
    public void buildingLoaded(Building building) {
        // Fake a 100% progress to your UI when you receive info that the download is finished.
        showDownloadProgressToUser(100);
        // indoo.rs SDK successfully loaded the building you requested and
        // calculates a position now
        // Toast.makeText(
        //      this,
        //      "Building is located at " + building.getLatOrigin() / 1E6 + ","
        //              + building.getLonOrigin() / 1E6, Toast.LENGTH_SHORT).show();
    }

    private void showDownloadProgressToUser(int progress) { //Muestra del porcentaje del download del edificio
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
