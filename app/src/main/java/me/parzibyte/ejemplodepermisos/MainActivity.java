package me.parzibyte.ejemplodepermisos;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Código de permiso, defínelo tú mismo
    private static final int PERMISSION_REQUEST_CAMERA = 10;
    private static final int PERMISSION_REQUEST_EXTERNAL_STORAGE = 20;

    private void checkPermissionCamera() {
        if (!(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
            // Se solicita y se espera la respuesta del usuario en el método onRequestPermissionResult
            requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        } else {
            showMessagePermissionGrant("El permiso CAMERA ya estaba concedido");
        }
    }

    private void checkPermissionExternalStorage() {
        if(!(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)) {
            // Se solicita el permiso y se espera la respuesta del usuario en el método onRequestPermissionResult
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_EXTERNAL_STORAGE);
        } else {
            showMessagePermissionGrant("El permiso EXTERNAL_STORAGE ya estaba concedido");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showMessagePermissionGrant("Se ha concedido el permiso de la cámara");
                } else {
                    // Google dice que hay que mostrar otro cuadro de diálogo al usuario para explicar el motivo del permiso
                    //shouldShowRequestPermissionRationale();
                    //permisoDeCamaraDenegado();
                    showMessagePermissionDeny("No se ha concedido el permiso de la cámara");
                }
                break;

            case PERMISSION_REQUEST_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showMessagePermissionGrant("Se ha concedido el permiso de almacenamiento");
                } else {
                    showMessagePermissionDeny("No se ha concedido el permiso de almacenamiento");
                }
                break;

            // Aquí más casos dependiendo de los permisos
            // case OTRO_CODIGO_DE_PERMISOS...

        }
    }

    /**
     * Cuando el usuario deniega el permiso se muestra un mensaje
     * @param message
     */
    private void showMessagePermissionDeny(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Cuando el usuario acepta un permiso se realiza la acción correspondiente
     */
    private void showMessagePermissionGrant(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Algunos botones que, al ser tocados, van a pedir los permisos
        Button btnPermisoCamara = findViewById(R.id.btnPermisoCamara),
                btnPermisoAlmacenamiento = findViewById(R.id.btnPermisoAlmacenamiento);
        btnPermisoCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionCamera();
            }
        });

        btnPermisoAlmacenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionExternalStorage();
            }
        });
    }
}
