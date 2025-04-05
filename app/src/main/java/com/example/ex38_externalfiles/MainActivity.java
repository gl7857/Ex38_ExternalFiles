package com.example.ex38_externalfiles;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * MainActivity handles reading and writing files to external storage.
 * It allows saving, resetting, and reading text from an external file,
 * as well as requesting necessary permissions.
 *
 *
 *  * @author Gali Lavi <gl7857@bs.amalnet.k12.il>
 *  * @version 1.0
 *  * @since 03/04/2025
 */

public class MainActivity extends AppCompatActivity {
    /**
     * Request code for external storage permission request.
     */
    private static final int REQUEST_CODE_PERMISSION = 1;

    /**
     * EditText field where the user enters text.
     */
    private EditText etTextInput;

    /**
     * TextView to display the contents of the saved file.
     */
    private TextView tV;

    /**
     * Name of the file to store data in external storage.
     */
    private final String FILENAME = "exttest.txt";

    /**
     * Called when the activity is created.
     * Sets up the UI and requests necessary permissions.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTextInput = findViewById(R.id.etTextInput);
        tV = findViewById(R.id.tvOutput);

        // Check permissions and storage availability before reading file contents.
        if (!checkPermission() || !isExternalStorageAvailable()) {
            requestPermission();
            return;
        }
        tV.setText(getFileContent());
    }

    /**
     * Checks if external storage is available for read and write operations.
     *
     * @return True if external storage is available, false otherwise.
     */
    public boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * Checks if the application has permission to write to external storage.
     *
     * @return True if the permission is granted, false otherwise.
     */
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Requests the necessary permission to write to external storage.
     */
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
    }

    /**
     * Handles the result of the permission request.
     * Displays a toast message based on whether the permission was granted or denied.
     *
     * @param requestCode  The request code passed in requestPermissions().
     * @param permissions  The requested permissions.
     * @param grantResults The grant results for the corresponding permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission to access external storage granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Permission to access external storage NOT granted", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Saves the content from EditText to the external file.
     *
     * @param view The view that was clicked.
     */
    public void onSaveClick(View view) {
        if (!isExternalStorageAvailable() || !checkPermission()) {
            Toast.makeText(this, "External memory or permission problem", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            File externalDir = Environment.getExternalStorageDirectory();
            File file = new File(externalDir, FILENAME);
            file.getParentFile().mkdirs();

            FileWriter writer = new FileWriter(file, true);
            writer.write(etTextInput.getText().toString());
            writer.close();
            tV.setText(getFileContent());
            Toast.makeText(this, "Text file saved successfully!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save text file", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Clears the content of the external file and resets the EditText and TextView.
     *
     * @param view The view that was clicked.
     */
    public void onResetClick(View view) {
        if (!isExternalStorageAvailable() || !checkPermission()) {
            Toast.makeText(this, "External memory or permission problem", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            File externalDir = Environment.getExternalStorageDirectory();
            File file = new File(externalDir, FILENAME);
            file.getParentFile().mkdirs();

            FileWriter writer = new FileWriter(file);
            writer.write("");
            writer.close();

            etTextInput.setText("");
            tV.setText("");

            Toast.makeText(this, "Text file cleared successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to clear text file", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Saves the content and exits the application.
     *
     * @param view The view that was clicked.
     */
    public void onExitClick(View view) {
        onSaveClick(view);
        finish();
    }

    /**
     * Reads and returns the content of the external file.
     *
     * @return The content of the file as a String.
     */
    public String getFileContent() {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            File externalDir = Environment.getExternalStorageDirectory();
            File file = new File(externalDir, FILENAME);
            file.getParentFile().mkdirs();
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferReader.readLine()) != null) {
                contentBuilder.append(line).append('\n');
            }
            bufferReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    /**
     * Creates the options menu on the screen.
     *
     * @param menu The menu to create.
     * @return true to indicate that the menu was created successfully.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.creditsmenu, menu);
        return true;
    }

    /**
     * Handles item selection from the options menu.
     * Navigates to the credits screen if the corresponding item is selected.
     *
     * @param item The selected menu item.
     * @return true if the item is handled.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuCredits) {
            Intent si = new Intent(this, Credits.class);
            startActivity(si);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
