package br.com.bemypet.bemypet;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.clustering.ClusterManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import br.com.bemypet.bemypet.api.StringUtils;
import br.com.bemypet.bemypet.controller.Constants;
import br.com.bemypet.bemypet.model.Usuario;
import br.com.bemypet.bemypet.model.maps.Retorno;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisualizarRotaPetActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private ClusterManager mClusterManager;
    private String origem, destino;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_rota_pet);
        getBundle();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




    }

    private void getBundle() {



        if(getIntent() != null && getIntent().getExtras() != null){

            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                Set<String> keys = bundle.keySet();
                Iterator<String> it = keys.iterator();
                Log.e("getExtras","Dumping Intent start");
                while (it.hasNext()) {
                    String key = it.next();
                    Log.e("getExtras","[" + key + "=" + bundle.get(key)+"]");
                }
                Log.e("getExtras","Dumping Intent end");
            }


            if (getIntent().getExtras().getString("origem") != null) {
                origem = getIntent().getExtras().getString("origem");
            }

            if (getIntent().getExtras().getString("destino") != null) {
                destino = getIntent().getExtras().getString("destino");
            }
        }
        Log.i("origem", "origem: "+ origem);
        Log.i("destino", "destino: "+ destino);
        if(!(StringUtils.isNullOrEmpty(origem) && StringUtils.isNullOrEmpty(destino))) {
            showRota(origem, destino);
        }
    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public void showRota(String origem, String destino){

        Log.i("origem", origem);
        Log.i("destino", destino);
        String key = Constants.KEY_MAP;
        Call<Retorno> call = ((BeMyPetApplication) getApplication()).service.searchPositions(origem, destino, key);

        call.enqueue(new Callback<Retorno>() {
            @Override
            public void onResponse(Call<Retorno> call, Response<Retorno> response) {
                String points = response.body().routes.get(0).overview_polyline.points;
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.addAll(PolyUtil.decode(points));
                polylineOptions.color(Color.BLUE);
                mMap.addPolyline(polylineOptions);

            }

            @Override
            public void onFailure(Call<Retorno> call, Throwable t) {
            }
        });

        mMap.setOnCameraChangeListener(mClusterManager);
    }
}
