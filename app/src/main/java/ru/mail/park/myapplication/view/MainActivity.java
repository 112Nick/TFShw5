package ru.mail.park.myapplication.view;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ru.mail.park.myapplication.R;
import ru.mail.park.myapplication.model.CoinInfo;
import ru.mail.park.myapplication.viewmodel.MainViewModel;
import timber.log.Timber;

import java.util.List;

/*
 * TODO:
 * 1) Подключить ViewModel и LiveData из Android Architecture components //
 * 2) Разделить классы по пакетам //
 * 3) Внедрить в проект архитектуру MVVM, вынести бизнес-логику во вьюмодель. //
 * В идеале вьюмодель не должна содержать в себе андроид-компонентов (таких как Context)
 * 4) Сделать так, чтобы при повороте экрана данные не перезапрашивались заново,
 * а использовались полученные ранее
 * 5) Don't repeat yourself - если найдете в коде одинаковые куски кода, выносите в утилитные классы //
 */

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private CoinsAdapter adapter;
    private View errorView;
    private View contentView;
    private View loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> getData());
        errorView = findViewById(R.id.error_layout);
        contentView = findViewById(R.id.main_recycler_view);
        loadingView = findViewById(R.id.loading_layout);
        initRecyclerView();

        mainViewModel = new MainViewModel();
        mainViewModel.getLoadingData().observe(this, aBoolean -> {
            if (aBoolean) {
                loadingView.setVisibility(View.VISIBLE);
                contentView.setVisibility(View.GONE);
            }

        });

        mainViewModel.getIsError().observe(this, aBoolean -> {
            if (aBoolean) {
                loadingView.setVisibility(View.GONE);
                errorView.setVisibility(View.VISIBLE);
            } else {
                errorView.setVisibility(View.GONE);
            }
        });

        mainViewModel.getCoinsList().observe(this, coinsInfo -> {
            adapter.setData(coinsInfo);
            contentView.setVisibility(View.VISIBLE);
            loadingView.setVisibility(View.GONE);
            errorView.setVisibility(View.GONE);
        });

        getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            getData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void getData() {
        mainViewModel.onRefreshButtonClicked();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CoinsAdapter(coinInfo -> DetailsActivity.start(MainActivity.this, coinInfo));
        recyclerView.setAdapter(adapter);
    }
}
