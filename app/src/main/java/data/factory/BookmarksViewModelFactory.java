package data.factory;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import data.model.BookmarksViewModel;
import data.model.FavoritesViewModel;

public class BookmarksViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;

    public BookmarksViewModelFactory(Application application) {
        this.application = application;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BookmarksViewModel.class)) {
            return (T) new BookmarksViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
