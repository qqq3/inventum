package org.asdtm.fas.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.asdtm.fas.model.FilterData;
import org.asdtm.fas.R;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DiscoverFragment extends Fragment implements AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener{

    private static final String TAG = DiscoverFragment.class.getSimpleName();

    private Unbinder unbinder;
    @BindView(R.id.discover_toggle) RadioGroup toggleRadioGroup;
    @BindView(R.id.radio_movies) RadioButton moviesRadioButton;
    @BindView(R.id.radio_tv) RadioButton tvRadioButton;
    @BindView(R.id.discover_discover) TextView discoverButton;
    @BindView(R.id.discover_genres) TextView genresView;
    @BindView(R.id.discover_sort) Spinner sortSpinner;
    @BindView(R.id.discover_rating) TextView ratingView;
    @BindView(R.id.discover_rating_bar) AppCompatSeekBar ratingSeekBar;

    OnDiscoverClickListener mCallback;

    private int mType;
    private String mSortValue;
    private String mGenresValues;
    private String mMinRating;

    private HashSet<String> mGenresList;
    private HashSet<String> mGenresValuesList;
    private String mGenres;
    private boolean[] checkedGenres;

    public interface OnDiscoverClickListener {
        public void onDiscoverClick(FilterData data);
    }

    public DiscoverFragment newInstance() {
        return new DiscoverFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_discover, container, false);
        unbinder = ButterKnife.bind(this, v);
        mType = DiscoverResultFragment.TYPE_MOVIES;

        mGenresList = new HashSet<>();
        mGenresValuesList = new HashSet<>();
        checkedGenres = new boolean[] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
        };
        ratingSeekBar.setOnSeekBarChangeListener(this);

        final List<String> sort = Arrays.asList(getResources().getStringArray(R.array.sort));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, sort);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);
        sortSpinner.setOnItemSelectedListener(this);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnDiscoverClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnDiscoverClickListener");

        }
    }

    @OnCheckedChanged(R.id.radio_movies)
    void onCheckedMovies(boolean isChecked) {
        mType = isChecked ? DiscoverResultFragment.TYPE_MOVIES : DiscoverResultFragment.TYPE_TV;
        int textColorLight = ContextCompat.getColor(getActivity(), R.color.textColorLight);
        int textColorDark = ContextCompat.getColor(getActivity(), R.color.textColorDark);
        moviesRadioButton.setTextColor(isChecked ? textColorLight : textColorDark);
        tvRadioButton.setTextColor(!isChecked ? textColorLight : textColorDark);
    }

    @OnClick(R.id.genres_root)
    void getGenres() {
        final List<String> genresValues = Arrays.asList(getResources().getStringArray(R.array.genresValues));
        final String[] genres = getResources().getStringArray(R.array.genres);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle(R.string.genres_dialog_title);
        builder.setMultiChoiceItems(genres, checkedGenres, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                checkedGenres[i] = isChecked;
                if (isChecked) {
                    mGenresValuesList.add(genresValues.get(i));
                    mGenresList.add(genres[i]);
                } else {
                    mGenresValuesList.remove(genresValues.get(i));
                    mGenresList.remove(genres[i]);
                }
            }
        });
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mGenresValues = TextUtils.join(",", mGenresValuesList);
                mGenres = TextUtils.join(", ", mGenresList);
                genresView.setText(mGenres);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @OnClick(R.id.discover_discover)
    void discover() {
        FilterData data = new FilterData();
        data.setType(mType);
        data.setGenres(mGenresValues);
        data.setSortType(mSortValue);
        data.setMinRating(mMinRating);

        mCallback.onDiscoverClick(data);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String[] sortValues;
        if (mType == DiscoverResultFragment.TYPE_MOVIES) {
            sortValues = getResources().getStringArray(R.array.sortValuesMovie);
        } else {
            sortValues = getResources().getStringArray(R.array.sortValuesTv);
        }
        mSortValue = sortValues[adapterView.getSelectedItemPosition()];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        mMinRating = String.valueOf(i);
        ratingView.setText(String.valueOf(i));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
