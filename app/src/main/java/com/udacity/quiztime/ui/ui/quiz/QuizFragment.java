package com.udacity.quiztime.ui.ui.quiz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.udacity.quiztime.R;
import com.udacity.quiztime.adapters.SimpleFragmentPagerAdapter;
import com.udacity.quiztime.data.DataRepository;
import com.udacity.quiztime.data.database.AppDatabase;
import com.udacity.quiztime.data.network.QuizDataSource;
import com.udacity.quiztime.ui.list.SavedFragmentViewModelFactory;
import com.udacity.quiztime.utils.AppExecutors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import static com.udacity.quiztime.ui.QuizActivity.QUIZ_ID;


public class QuizFragment extends Fragment {

    private View rootView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private boolean initOnce = false;
    private int finalSize;
    private ImageView progressIV;


    public static QuizFragment newInstance(String quizID) {


        Bundle args = new Bundle();

        args.putString(QUIZ_ID, quizID);
        QuizFragment fragment = new QuizFragment();
        fragment.setArguments(args);
        return fragment;


    }

    private static QuizViewModelFactory provideQuizActivityViewModelFactory(Context context, String quizID) {
        DataRepository repository = provideRepository(context.getApplicationContext());
        return new QuizViewModelFactory(repository, quizID);
    }

    public static SavedFragmentViewModelFactory provideMainActivityViewModelFactory(Context context, String quizID) {
        DataRepository repository = provideRepository(context.getApplicationContext());
        return new SavedFragmentViewModelFactory(repository, quizID);
    }

    private static DataRepository provideRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        QuizDataSource networkDataSource =
                QuizDataSource.getInstance(context.getApplicationContext(), executors);
        return DataRepository.getInstance(database.quizDao(), database.quizListDao(), networkDataSource, executors);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.quiz_fragment, container, false);

        return rootView;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = rootView.findViewById(R.id.pager);
        tabLayout = rootView.findViewById(R.id.sliding_tabs);
        progressIV = rootView.findViewById(R.id.progressIV);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        String quizID = null;
        if (getArguments() != null) {
            quizID = getArguments().getString(QUIZ_ID);
        }

        QuizViewModelFactory factory = provideQuizActivityViewModelFactory(requireActivity().getApplicationContext(), quizID);


        QuizViewModel mViewModel = ViewModelProviders.of(this, factory).get(QuizViewModel.class);

        mViewModel.getThisQuiz().observe(this, quizQuestions -> {
            SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(requireFragmentManager(),
                    requireActivity(), quizQuestions);
            if (!initOnce && quizQuestions.size() > 0) {
                progressIV.setVisibility(View.GONE);
                viewPager.setAdapter(adapter);
                initOnce = true;
                setupTabIcons(quizQuestions.size());
            } else if (quizQuestions.size() > 0) {
                progressIV.setVisibility(View.GONE);
            } else {
                progressIV.setVisibility(View.VISIBLE);
            }

        });

    }

    private void setupTabIcons(int size) {
        finalSize = size + 1;
        for (int i = 0; i < finalSize; i++) {
            @SuppressLint("InflateParams")
            TextView tabOne = (TextView) LayoutInflater.from(requireActivity()).inflate(R.layout.tab_single_item, null);
            if (finalSize - 1 == i) {
                tabOne.setText(R.string.results);
                tabOne.setBackground(null);
            } else if (i == 0) {
                tabOne.setText(String.valueOf(i + 1));
                tabOne.setBackground(getResources().getDrawable(R.drawable.circle_design_orange));
            } else {
                tabOne.setText(String.valueOf(i + 1));
            }
            tabLayout.addTab(tabLayout.newTab().setCustomView(tabOne).setTag(String.valueOf(i)));
        }


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position != finalSize - 1) {
                    tabLayout.getTabAt(position).getCustomView().setBackground(getResources().getDrawable(R.drawable.circle_design_orange));
                    tabLayout.getTabAt(position).select();
                } else {
                    TextView tv = (TextView) tabLayout.getTabAt(position).getCustomView();
                    tv.setTextColor(getResources().getColor(R.color.orange));
                    tabLayout.getTabAt(position).select();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setCurrentItem(0);

        tabLayout.getTabAt(0).select();
    }

    public void changeTabColor(int color) {
        int tabPosition = viewPager.getCurrentItem();
        Drawable d;
        if (color == 1) { // Green
            d = getResources().getDrawable(R.drawable.circle_design_green);
        } else if (color == 2) { // Red
            d = getResources().getDrawable(R.drawable.circle_design_red);
        } else {
            d = getResources().getDrawable(R.drawable.circle_design_orange);

        }
        tabLayout.getTabAt(tabPosition).getCustomView().setBackground(d);
        tabLayout.getTabAt(tabPosition).select();
    }

}
