package org.asdtm.fas;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.asdtm.fas.util.AppUtils;
import org.asdtm.fas.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DonationDialog extends DialogFragment {

    @BindView(R.id.bitcoin_address) TextView bitcoinAddress;

    public static DonationDialog newInstance() {
        return new DonationDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_donation, null, false);
        ButterKnife.bind(this, v);

        bitcoinAddress.setText(Constants.BITCOIN_ADDRESS);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Bitcoin");
        alertDialog.setView(v);
        alertDialog.setNegativeButton(android.R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface,
                                        int i) {
                        dialogInterface.cancel();
                    }
                });
        return alertDialog.create();
    }

    @OnClick(R.id.copy_bitcoin_address_button)
    void copyBitcoinAddress() {
        AppUtils.copyToClipboard(getActivity(), Constants.BITCOIN_ADDRESS);
        Toast.makeText(getActivity(),
                R.string.donation_bitcoin_copy_message,
                Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.open_bitcoin_app_button)
    void openBitcoinApp() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("bitcoin:" + Constants.BITCOIN_ADDRESS));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(),
                    R.string.donation_bitcoin_copy_message,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
