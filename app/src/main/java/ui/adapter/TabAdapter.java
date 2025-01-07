package ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import ui.fragment.BookmarksFragment;
import ui.fragment.MostPopularFragment;
import ui.fragment.MovieSearchFragment;

public class TabAdapter extends FragmentStateAdapter {
    public TabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new MostPopularFragment();
            case 1: return new MovieSearchFragment();
            case 2: return new BookmarksFragment();
            default: return new MostPopularFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
