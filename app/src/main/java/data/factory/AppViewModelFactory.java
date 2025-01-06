package data.factory;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import viewmodel.BookmarksViewModel;
import viewmodel.RatingsViewModel;

public class AppViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;

    public AppViewModelFactory(Application application) {
        this.application = application;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BookmarksViewModel.class)) {
            return (T) new BookmarksViewModel(application);
        } else if (modelClass.isAssignableFrom(RatingsViewModel.class)) {
            return (T) new RatingsViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
