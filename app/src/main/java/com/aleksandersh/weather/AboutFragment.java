package com.aleksandersh.weather;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Фрагмент, содержащий информацию о приложении.
 */
public class AboutFragment extends Fragment {
    /**
     * Создает новый экземпляр фрагмента {@link AboutFragment} и задает аргументы.
     *
     * @return Новый экземпляр
     */
    public static AboutFragment newInstance() {
        Bundle args = new Bundle();

        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public AboutFragment() {
        // Пустой констурктор
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }
}
