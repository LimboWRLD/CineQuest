package ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.cinequest.BookmarksFragment;
import com.example.cinequest.MostPopular;
import com.example.cinequest.MovieSearchFragment;

public class TabAdapter extends FragmentStateAdapter {
    public TabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new MostPopular();
            case 1: return new MovieSearchFragment();
            case 2: return new BookmarksFragment();
            default: return new MostPopular();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
