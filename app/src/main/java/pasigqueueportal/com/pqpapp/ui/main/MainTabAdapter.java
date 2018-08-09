package pasigqueueportal.com.pqpapp.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import pasigqueueportal.com.pqpapp.ui.main.currentAppointment.AppointmentActivity;
import pasigqueueportal.com.pqpapp.ui.main.pastAppointment.PastAppointmentActivity;


class MainTabAdapter extends FragmentStatePagerAdapter {

    private static final String[] TITLES = {"CURRENT", "PAST"};

    public MainTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                 return new AppointmentActivity();
            case 1:
                return new PastAppointmentActivity();


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
