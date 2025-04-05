package com.example.ex38_externalfiles;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
/**
 * Activity for displaying credits.
 * This activity shows the credits screen and allows the user to navigate back to the main activity.
 *
 * @author      Gali Lavi <gl7857@bs.amalnet.k12.il>
 * @version     1.0
 * @since       03/04/2025
 *
 * short description:
 *        This activity enables edge-to-edge display and shows a credits screen.
 *        It provides a simple interface with a menu that allows the user to navigate back to
 *        the main screen.
 */
public class Credits extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     * Enables edge-to-edge display and sets the content view to the credits screen layout.
     *
     * @param savedInstanceState a Bundle containing the activity's previously saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_credits);
    }

    /**
     * Creates the options menu for this activity.
     * Inflates the menu resource to display the options menu items.
     *
     * @param menu the menu in which the items should be added
     * @return true if the menu was successfully created, false otherwise
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.creditsmenu, menu);
        return true;
    }
    /**
     * Handles item selection from the options menu.
     * If the "menuMain" item is selected, the activity finishes and returns to the main activity.
     *
     * @param item the menu item that was selected
     * @return true if the item selection was handled, false otherwise
     */
    @Override
    public boolean onOptionsItemSelected(@Nullable MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuMain) {
            finish();
        }
        return true;
    }
}
