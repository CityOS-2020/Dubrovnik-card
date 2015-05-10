package dubrovnik.card.hardcode.dubrovnikcard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * Created by Ivan on 09/05/15.
 */
public class LandmarkDialog extends DialogFragment{

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_minor, null);
        Button dismiss = (Button) rootView.findViewById(R.id.dismissDialog);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LandmarkDialog.this.dismiss();
            }
        });
        builder.setView(rootView);

        return builder.create();
    }
}
