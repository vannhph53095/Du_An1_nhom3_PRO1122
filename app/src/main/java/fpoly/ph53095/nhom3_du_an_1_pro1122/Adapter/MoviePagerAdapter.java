package fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import fpoly.ph53095.nhom3_du_an_1_pro1122.activity.MovieFragment;

public class MoviePagerAdapter extends FragmentPagerAdapter {

    public MoviePagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MovieFragment.newInstance("Sci-fi");
            case 1:
                return MovieFragment.newInstance("Romance");
            case 2:
                return MovieFragment.newInstance("Action");
            case 3:
                return MovieFragment.newInstance("Horror");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Sci-fi";
            case 1:
                return "Romance";
            case 2:
                return "Action";
            case 3:
                return "Horror";
            default:
                return null;
        }
    }
}

