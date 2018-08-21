package pasigqueueportal.com.pqpapp.ui.location;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.util.List;

import io.realm.Realm;

import pasigqueueportal.com.pqpapp.model.data.TaxCompany;
import pasigqueueportal.com.pqpapp.model.data.User;
import pasigqueueportal.com.pqpapp.util.DistanceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MapPresenter extends MvpNullObjectBasePresenter<MapView> {
    private Realm realm;
    private User user;

    void onStart() {
        realm = Realm.getDefaultInstance();
        user = realm.where(User.class).findFirst();
      loadDealerList(user.getUserId());


    }


        public void loadDealerList(int userID) {
           getView().updateMap();
        }


    public void onStop() {
        realm.close();
    }


    void getNearest(double latitude, double longitude) {
        final Realm realm = Realm.getDefaultInstance();
        getView().startLoading();
       // List<Dealer> companys = realm.where(Dealer.class).findAll();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(TaxCompany.class);
            }
        });
       // for (Dealer company : companys) {
            Double distance = DistanceUtil.distanceBetween(latitude, longitude,14.12121212, 121.2121212);
            final TaxCompany nearest = new TaxCompany();
            nearest.setDealerId(1);
            nearest.setDealerName("name");
            nearest.setDealerAddress("sasas");
            nearest.setDealerClosing("sasasas");
            nearest.setDealerOpening("2121212");
            nearest.setDealerLat("14.12121212");
            nearest.setDealerLong("121.2121212");
            nearest.setDealerContact("212121212");
            nearest.setDealerImage("image");
            nearest.setDistance(distance);

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(nearest);
                }
            });
       // }
        getView().stopLoading();
        realm.close();
    }




}
