package ru.mail.park.myapplication.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import ru.mail.park.myapplication.R;
import ru.mail.park.myapplication.model.CoinInfo;

public class DetailsActivity extends AppCompatActivity {

    private static final String EXTRA_INFO = "extra_info";

    public static void start(Activity activity, CoinInfo coinInfo) {
        Intent intent = new Intent(activity, DetailsActivity.class);
        intent.putExtra(EXTRA_INFO, coinInfo);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CoinInfo info = (CoinInfo) getIntent().getSerializableExtra(EXTRA_INFO);
        getSupportActionBar().setTitle(info.getSymbol());
        ImageView logo = findViewById(R.id.coin_logo);
        String logoUrl = getString(R.string.coin_logo_url, info.getSymbol().toLowerCase());
        Glide.with(this)
                .load(logoUrl)
                .into(logo);

        TextView title = findViewById(R.id.coin_title);
        title.setText(info.getName());
        TextView price = findViewById(R.id.price_value);
        price.setText(getString(R.string.price_format, info.getPriceUsd()));
        TextView change7d = findViewById(R.id.change_value_7d);
        change7d.setText(getString(R.string.percent_format, info.getPercentChange7d()));
        if (info.getPercentChange7d() > 0) {
            change7d.setTextColor(ContextCompat.getColor(this, R.color.green700));
        } else {
            change7d.setTextColor(ContextCompat.getColor(this, R.color.red700));
        }
        TextView change24h = findViewById(R.id.change_value_24h);
        change24h.setText(getString(R.string.percent_format, info.getPercentChange24h()));
        if (info.getPercentChange24h() > 0) {
            change24h.setTextColor(ContextCompat.getColor(this, R.color.green700));
        } else {
            change24h.setTextColor(ContextCompat.getColor(this, R.color.red700));
        }
        TextView change1h = findViewById(R.id.change_value_1h);
        change1h.setText(getString(R.string.percent_format, info.getPercentChange1h()));
        if (info.getPercentChange1h() > 0) {
            change1h.setTextColor(ContextCompat.getColor(this, R.color.green700));
        } else {
            change1h.setTextColor(ContextCompat.getColor(this, R.color.red700));
        }
        TextView marketCap = findViewById(R.id.market_cap_value);
        marketCap.setText(getString(R.string.price_format, info.getMarketCapUsd()));
        TextView lastUpdate = findViewById(R.id.last_update_value);
        lastUpdate.setText(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.getDefault())
                .format(new Date(info.getLastUpdated() * 1000)));
    }
}
