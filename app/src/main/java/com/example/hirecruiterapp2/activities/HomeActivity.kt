package com.example.hirecruiterapp2.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hirecruiterapp2.R
import com.example.hirecruiterapp2.databinding.ActivityHomeBinding
import com.example.hirecruiterapp2.fragments.user.AddJobPreferenceFragment
import com.example.hirecruiterapp2.fragments.user.CustomerHomeFragment
import com.example.hirecruiterapp2.fragments.user.EditProfileFragment
import com.example.hirecruiterapp2.switchFragmentUsecase
import com.example.hirecruiterapp2.viewmodel.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var toolbar: Toolbar
    private lateinit var nView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var menu: Menu
    private lateinit var mAuth: FirebaseAuth
    private lateinit var userViewModel: UserViewModel
    private lateinit var toolbarName: TextView
    private lateinit var hView: View
    private lateinit var headerName: TextView
    private lateinit var headerEmail: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        mAuth = FirebaseAuth.getInstance()
        setContentView(binding.root)
        toolbar = binding.toolbar.toolbar
        setSupportActionBar(toolbar)
        drawerLayout = binding.drawerLayout
        nView = binding.navView
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        nView.bringToFront()
        nView.setNavigationItemSelectedListener(this)
        menu = nView.menu
        hView = nView.getHeaderView(0)
        headerName = hView.findViewById(R.id.nav_username)
        headerEmail = hView.findViewById(R.id.nav_userEmail)
        toolbarName = findViewById(R.id.toolbar_name)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userViewModel.getMethod()
        //Log.d("mstart", "method log started")
        userViewModel.observeMethod().observe(this) { providerId ->
            if (providerId == "google.com") {
                val googleUser: GoogleSignInAccount? =
                    GoogleSignIn.getLastSignedInAccount(applicationContext)
                if (googleUser != null)
                    userViewModel.addUser(
                        mAuth.currentUser!!.uid,
                        googleUser.displayName!!,
                        googleUser.email,
                        "null"
                    )
            }
        }
        userViewModel.getUserById()
        userViewModel.observeUserById().observe(this) { user ->
            toolbarName.text = user.seekerName
            headerName.text = user.seekerName
            headerEmail.text = user.seekerEmail
        }
        val cHome = CustomerHomeFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.homeFragment, cHome)
            commit()
        }
    }

    /*override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        return super.getOnBackInvokedDispatcher()
    }*/

    private fun showAlertDialogue() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Sign out?")
        builder.setMessage("Are you sure you want to sign out?")
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            userViewModel.observeMethod().observe(this) { providerId ->
                if (providerId == "google.com") {
                    val googleUser: GoogleSignInClient =
                        GoogleSignIn.getClient(applicationContext, DEFAULT_SIGN_IN)
                    googleUser.signOut().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            mAuth.signOut()
                        } else {
                            Log.d("signIn", "Sign out not successful")
                        }
                    }
                } else if (providerId == "phone" || providerId == "password") {
                    mAuth.signOut()
                }
                val intent = Intent(this@HomeActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }

        builder.setNegativeButton("No") { dialogInterface, which ->
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val navigationUseCase = switchFragmentUsecase(
            this.supportFragmentManager,
            R.id.homeFragment
        )
        when (item.itemId) {

            R.id.action_home -> {
                val customerHomeFragment: Fragment = CustomerHomeFragment()
                navigationUseCase.navigateToFragment(customerHomeFragment)
            }

            R.id.action_edit_profile -> {

                val editProfile: Fragment = EditProfileFragment()
                navigationUseCase.navigateToFragment(editProfile)


            }

            R.id.action_add_product -> {
                val addJobPreferenceFragment: Fragment = AddJobPreferenceFragment()
                navigationUseCase.navigateToFragment(addJobPreferenceFragment)

            }

            R.id.action_view_complaints -> {
            }

            R.id.action_sign_out -> {
                showAlertDialogue()

            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
