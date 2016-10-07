package in.blogspot.shiftcodes.map2;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String url = "http://programmingwizard.16mb.com/myjson/place";
    String data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {

                        try{

                            JSONArray ja = response.getJSONArray("Place");

                            for(int i=0; i < ja.length(); i++){

                                JSONObject jsonObject = ja.getJSONObject(i);

                                // int id = Integer.parseInt(jsonObject.optString("id").toString());
                                String name = jsonObject.getString("City");
                                String loc = jsonObject.getString("Location");
                                int pin = Integer.parseInt(jsonObject.optString("Pin code").toString());
                                Double lat = Double.parseDouble(jsonObject.optString("Lattitude").toString());
                                Double lon = Double.parseDouble(jsonObject.optString("Longitude").toString());
                                /*here map marker add by getting value of parsed latlong*/
                                LatLng sydney = new LatLng(lat, lon);
                                mMap.addMarker(new MarkerOptions().position(sydney).title("" + loc + ""));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,13));
                                mMap.getUiSettings().setZoomControlsEnabled(true);
                            }

                        }catch(JSONException e){e.printStackTrace();}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley","Error");

                    }
                }
        );
        requestQueue.add(jor);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
