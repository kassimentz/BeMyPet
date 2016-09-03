package br.com.bemypet.bemypet;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.clustering.ClusterManager;

import br.com.bemypet.bemypet.api.StringUtils;
import br.com.bemypet.bemypet.controller.Constants;
import br.com.bemypet.bemypet.model.maps.Bounds;
import br.com.bemypet.bemypet.model.maps.Retorno;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisualizarRotaPetActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private ClusterManager mClusterManager;
    private String origem, destino, mensagem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_rota_pet);


        //setOrigem("DIOMARIO MOOJEN,150/101 - CRISTAL. POA, RS / BR");
        //setDestino("GABRIEL FRANCO DA LUZ,560/206 - SARANDI. POA, RS / BR");
        getBundle();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void getBundle() {

        if(getIntent() != null && getIntent().getExtras() != null){

            Bundle bundle = getIntent().getExtras();

            if (getIntent().getExtras().getString("origem") != null) {
                setOrigem(getIntent().getExtras().getString("origem"));
            }

            if (getIntent().getExtras().getString("destino") != null) {
                setDestino(getIntent().getExtras().getString("destino"));
            }

            if (getIntent().getExtras().getString("message") != null) {
                setMensagem(getIntent().getExtras().getString("message"));
            }
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mClusterManager = new ClusterManager<>(this, mMap);
        mMap.setOnCameraChangeListener(mClusterManager);
        showRota(getOrigem(), getDestino());

        if(!StringUtils.isNullOrEmpty(getMensagem())){
            new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert).setTitle("Adoção Aprovada")
                    .setMessage(getMensagem()).setPositiveButton("OK", null).show();
        }
    }

    public void showRota(String localOrigem, String localDestino){


        String key = Constants.KEY_MAP_SERVER;
        Call<Retorno> call = ((BeMyPetApplication) getApplication()).service.searchPositions(localOrigem, localDestino, key);

        call.enqueue(new Callback<Retorno>() {
            @Override
            public void onResponse(Call<Retorno> call, Response<Retorno> response) {
                String points = response.body().routes.get(0).overview_polyline.points;
                Bounds bounds = response.body().routes.get(0).bounds;
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.addAll(PolyUtil.decode(points));
                polylineOptions.color(Color.BLUE);
                mMap.addPolyline(polylineOptions);
                mMap.setLatLngBoundsForCameraTarget(new LatLngBounds(new LatLng(bounds.southwest.lat, bounds.southwest.lng),
                        new LatLng(bounds.northeast.lat, bounds.northeast.lng)));

                mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));

            }

            @Override
            public void onFailure(Call<Retorno> call, Throwable t) {
            }
        });


        mMap.setOnCameraChangeListener(mClusterManager);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
