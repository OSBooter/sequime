package pc.javier.seguime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Javier on 16 feb 2018.
 */

public class ActividadAyudaRegresiva extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ayudas);
        ((TextView) findViewById(R.id.ayudas_texto)).setText(R.string.txt_ayuda_opciones);
    }

}
