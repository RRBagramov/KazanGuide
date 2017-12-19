package ru.kpfu.kazanguide;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnPoiClickListener {
    private List<LatLng> places = new ArrayList<>();
    private int width;
    private GoogleMap mMap;
    private UiSettings uiSettings;
    private String mapsApiKey;
    private LatLng pushkinPosition = new LatLng(55.791933, 49.1240163);
    private LatLng workDestination = new LatLng(55.788528, 49.116056);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        places.add(new LatLng(55.7881218, 49.1200908));
        places.add(new LatLng(55.7889422, 49.118275));
        places.add(new LatLng(55.7902044, 49.1156035));
        places.add(new LatLng(55.7913038, 49.1133907));

        width = getResources().getDisplayMetrics().widthPixels;

        mapsApiKey = this.getResources().getString(R.string.google_maps_key);
    }

    private static String latLngToString(LatLng latLngatLng) {
        return (latLngatLng.latitude + "," + latLngatLng.longitude);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnPoiClickListener(this);
        uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMapToolbarEnabled(true);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

            mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
                    Criteria criteria = new Criteria();
                    String provider = service.getBestProvider(criteria, false);
                    if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        Location location = service.getLastKnownLocation(provider);

                        if (location != null) {
                            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(userLocation).title("My marker"));
                            CameraPosition myPosition = new CameraPosition.Builder()
                                    .target(userLocation).zoom(17).bearing(90).tilt(30).build();
                            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(myPosition));
                            return true;
                        }
                    }
                    return false;
                }
            });


        } else {
            // Show rationale and request permission.
        }

        MarkerOptions[] markers = new MarkerOptions[places.size()];
        for (int i = 0; i < places.size(); i++) {
            markers[i] = new MarkerOptions()
                    .position(new LatLng(places.get(i).latitude, places.get(i).longitude));
            googleMap.addMarker(markers[i]);
        }


        GeoApiContext geoApiContext = new GeoApiContext.Builder()
                .apiKey(mapsApiKey)
                .build();
        DirectionsResult result = null;
        try {
            result = DirectionsApi.newRequest(geoApiContext)
                    .mode(TravelMode.WALKING)
                    .origin(latLngToString(places.get(0)))
                    .destination(latLngToString(places.get(places.size() - 1)))
                    .waypoints(latLngToString(places.get(1)), latLngToString(places.get(2))).await();
        } catch (InterruptedException | com.google.maps.errors.ApiException | IOException e) {
            e.printStackTrace();
        }

        List<com.google.maps.model.LatLng> path = result.routes[0].overviewPolyline.decodePath();
        PolylineOptions line = new PolylineOptions();

        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();

        for (int i = 0; i < path.size(); i++) {
            line.add(new LatLng(path.get(i).lat, path.get(i).lng));
            latLngBuilder.include(new LatLng(path.get(i).lat, path.get(i).lng));
        }

        line.width(16f).color(R.color.colorPrimary);

        mMap.addPolyline(line);

        LatLngBounds latLngBounds = latLngBuilder.build();
        CameraUpdate track = CameraUpdateFactory.newLatLngBounds(latLngBounds, width, width, 25);
        mMap.moveCamera(track);

    }

    @Override
    public void onPoiClick(PointOfInterest poi) {
        Toast.makeText(getApplicationContext(), "Clicked: " +
                        poi.name + "\nPlace ID:" + poi.placeId +
                        "\nLatitude:" + poi.latLng.latitude +
                        " Longitude:" + poi.latLng.longitude,
                Toast.LENGTH_SHORT).show();
    }

}
