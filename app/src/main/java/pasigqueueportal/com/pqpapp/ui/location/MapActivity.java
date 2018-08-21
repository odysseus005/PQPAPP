package pasigqueueportal.com.pqpapp.ui.location;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import pasigqueueportal.com.pqpapp.R;
import pasigqueueportal.com.pqpapp.databinding.ActivityMapBinding;
import pasigqueueportal.com.pqpapp.model.data.TaxCompany;
import pasigqueueportal.com.pqpapp.model.data.User;
import pasigqueueportal.com.pqpapp.util.BitmapUtils;
import pasigqueueportal.com.pqpapp.util.FunctionUtils;
import pasigqueueportal.com.pqpapp.util.FusedLocation;
import pasigqueueportal.com.pqpapp.util.SimpleLocation;


public class MapActivity extends MvpActivity<MapView, MapPresenter> implements MapView, OnMapReadyCallback, GoogleMap.OnMarkerClickListener,DirectionCallback {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private GoogleMap mMap;
    private ProgressDialog progressDialog;
    private Realm realm;
    private LatLngBounds bounds;
    private View markerRestIcon, markerUserIcon;
    private String TAG = MapActivity.class.getSimpleName();
    private PlaceAutocompleteFragment autocompleteFragment;
    private Marker myMarker = null;
    private ActivityMapBinding binding;
    private FusedLocation fusedLocation;
    private SimpleLocation location;
    private TaxCompany nearDealer;
    LocationManager locationManager;
    private User user;
    static final Integer CALL = 0x2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map);
        binding.setView(getMvpView());
        realm = Realm.getDefaultInstance();


        user = realm.where(User.class).findFirst();
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        realm = Realm.getDefaultInstance();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        initializeMap();

        location = new SimpleLocation(this);

        fusedLocation = new FusedLocation(this, new FusedLocation.Callback() {
            @Override
            public void onLocationResult(Location location) {
                Log.e(TAG, "Location Triggered\n" + location.getLongitude() + "," + location.getLatitude());
                stopLoading();
                setMyMarker(new LatLng(location.getLatitude(), location.getLongitude()));
            }
        });
        ;
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getCurrentLocation();

            }
        });



    }

    private void getCurrentLocation()
    {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            startLoading();
            location = new SimpleLocation(MapActivity.this);
            stopLoading();
            setMyMarker(new LatLng(location.getLatitude(), location.getLongitude()));
        }else
        {
            showAlert("Can't Access Location Turn on Gps");
        }
    }
    private void initializeMap() {
        if (!isGooglePlayServicesAvailable()) {
            finish();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        markerUserIcon = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_user, null);
        markerRestIcon = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_dealer, null);

        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(Place.TYPE_COUNTRY)
                .setCountry("PH")
                .build();
        autocompleteFragment.setFilter(typeFilter);
        autocompleteFragment.setHint("Search Place");
        autocompleteFragment.setBoundsBias(new LatLngBounds(new LatLng(14.503863, 120.859556), new LatLng(14.767616, 121.088896)));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());//get place details here
                //my marker
                setMyMarker(place.getLatLng());
                Log.i(TAG, "Place Coordinates: " + place.getLatLng());//get place details here
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

    }

    private void setMyMarker(LatLng latLng) {
        if (myMarker != null) {
            myMarker.remove();
        }
        myMarker = mMap.addMarker(new MarkerOptions().position(latLng)
                .snippet("-1")
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapUtils.createDrawableFromView(MapActivity.this, markerUserIcon))));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));



        presenter.getNearest(myMarker.getPosition().latitude, myMarker.getPosition().longitude);
    }

    @NonNull
    @Override
    public MapPresenter createPresenter() {
        return new MapPresenter();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(14.599512, 120.984222), 15));


        presenter.onStart();
        getCurrentLocation();
    }





    @Override
    public void startLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(MapActivity.this);
            progressDialog.setCanceledOnTouchOutside(true);
        }
        progressDialog.show();
    }

    @Override
    public void stopLoading() {
        if (progressDialog != null) progressDialog.dismiss();
    }

    @Override
    public void showAlert(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateMap() {
        final Realm realm = Realm.getDefaultInstance();
        mMap.clear();
        List<TaxCompany> companies = realm.where(TaxCompany.class).findAll();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        MarkerOptions markerOptions = new MarkerOptions();
        if (!companies.isEmpty()) {
            for (TaxCompany company : companies) {
                markerOptions.position(new LatLng(Double.parseDouble(company.getDealerLat()), Double.parseDouble(company.getDealerLong())));
                markerOptions.title(company.getDealerName());
                markerOptions.snippet(company.getDealerId() + "");
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapUtils.createDrawableFromView(this, markerRestIcon)));
                builder.include(markerOptions.getPosition());
                mMap.addMarker(markerOptions);

            }
          /*  bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
            mMap.animateCamera(cu);*/

        }else
        {
            showAlert("Can't Load Company");
        }

        realm.close();

    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        try {
         if(!(marker.getSnippet().equalsIgnoreCase("-1"))) {
             mMap.clear();
             updateMap();


             int nearID = Integer.parseInt(marker.getSnippet());


             nearDealer= realm.where(TaxCompany.class).equalTo("companyId", nearID).findFirst();


             if (nearDealer.isLoaded() || nearDealer.isValid())
                 showDealerDetail(nearDealer);


             LatLng latLng = new LatLng(Double.parseDouble(nearDealer.getDealerLat()), Double.parseDouble(nearDealer.getDealerLong()));

             mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

             setMyMarker(myMarker.getPosition());




             GoogleDirection.withServerKey("AIzaSyCi6ViLY_YfMCyFFg5FyfjuVLACPNRNYY0")
                     .from(myMarker.getPosition())
                     .to(marker.getPosition())
                     .transportMode(TransportMode.DRIVING)
                     .execute(this);


         }

        }catch (Exception e)
        {
                showAlert("Can't Access User Location");
                Log.e(">>>>>",e+"");
        }



        return true;
    }







    @Override
    protected void onResume() {
        super.onResume();

        if (checkLocationPermission()) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                location = new SimpleLocation(this);
                location.beginUpdates();
//                if (!fusedLocation.isGPSEnabled()) {
//                    fusedLocation.showSettingsAlert();
//                } else {
//                    fusedLocation.getCurrentLocation(1);
//                    startLoading();
//                    Log.e(TAG, "Getting Location");
//                }
            }
        }

    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Permission")
                        .setMessage("Location Request")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {


                    }

                } else {
                    showAlert("Can't use this feature without location permission");
                    MapActivity.this.finish();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }



        }
    }




    public void showDealerDetail(final TaxCompany dealer)
    {


       final String contact = dealer.getDealerContact();
       final double lat = Double.parseDouble(dealer.getDealerLat());
       final double lng = Double.parseDouble(dealer.getDealerLong());
        binding.cardView2.setVisibility(View.VISIBLE);




        binding.dealerName.setText(dealer.getDealerName());
        binding.dealerAddress.setText(dealer.getDealerAddress());
        binding.dealerContact.setText("Contact Number: "+dealer.getDealerContact());
        binding.dealerOpening.setText("Opening: "+ FunctionUtils.hour24to12hour(dealer.getDealerOpening()));
        binding.dealerClosing.setText("Closing: "+FunctionUtils.hour24to12hour(dealer.getDealerClosing()));
        binding.dealerDistance.setText("Total Distance: "+dealer.getDistance()+" KM");
        binding.dealerEta.setVisibility(View.GONE);




        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              binding.cardView2.setVisibility(View.GONE);

            }
        });

        binding.googlemaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", lat,lng);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);

            }
        });




        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.cardView2.setVisibility(View.GONE);


            }
        });


        binding.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contact));
                startActivity(intent);



//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:"+contact));
//                if (ActivityCompat.checkSelfPermission(MapActivity.this,
//                        android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(MapActivity.this, new String[]{android.Manifest.permission.CALL_PHONE},CALL);
//                    return;
//                }
//                startActivity(intent);

            }
        });


    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
        realm.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_refresh:
                mMap.clear();
                presenter.loadDealerList(user.getUserId());
                return true;



            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
       // Snackbar.make(btnRequestDirection, "Success with status : " + direction.getStatus(), Snackbar.LENGTH_SHORT).show();
        if (direction.isOK()) {
           // mMap.addMarker(new MarkerOptions().position(origin));
           // googleMap.addMarker(new MarkerOptions().position(destination));

        ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
        mMap.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 5, Color.parseColor("#f3bc00")));


    }
    }

    @Override
    public void onDirectionFailure(Throwable t) {
        showAlert("Error on getting Route");
    }



}
