package com.example.hirecruiterapp2.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.hirecruiterapp2.R
import com.example.hirecruiterapp2.databinding.ActivityAgentBinding
import com.example.hirecruiterapp2.fragments.agent.AddApplicationFragment
import com.example.hirecruiterapp2.fragments.agent.AgentSrFragment
import com.example.hirecruiterapp2.fragments.agent.Customer_SRFragment
import com.example.hirecruiterapp2.fragments.user.AddJobPreferenceFragment
import com.example.hirecruiterapp2.fragments.user.CustomerHomeFragment
import com.example.hirecruiterapp2.fragments.user.EditProfileFragment
import com.example.hirecruiterapp2.switchFragmentUsecase
import com.example.hirecruiterapp2.viewmodel.RecruiterViewModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class AgentActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivityAgentBinding
    private lateinit var toolbar2: androidx.appcompat.widget.Toolbar
    private lateinit var nView: NavigationView
    private lateinit var hView: View
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var menu: Menu
    private lateinit var recruiterViewModel: RecruiterViewModel
    private lateinit var toolbarName: TextView
    private lateinit var headerName: TextView
    private lateinit var headerEmail: TextView
    private lateinit var initialConstraintSet: ConstraintSet
    private var isExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgentBinding.inflate(layoutInflater)
        mAuth = FirebaseAuth.getInstance()
        setContentView(binding.root)
        toolbar2 = binding.toolbar2.toolbar2
        setSupportActionBar(toolbar2)
        drawerLayout = binding.drawerLayout
        nView = binding.navView
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar2, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        nView.bringToFront()
        nView.setNavigationItemSelectedListener(this)
        menu = nView.menu
        hView = nView.getHeaderView(0)
        headerName = hView.findViewById(R.id.nav_username2)
        headerEmail = hView.findViewById(R.id.nav_userEmail2)
        toolbarName = findViewById(R.id.toolbar_name2)
     //   agentViewModel = ViewModelProvider(this)[AgentViewModel::class.java]

        recruiterViewModel.getRecruiterById()
        recruiterViewModel.observeRecruiterById().observe(this) { recruiter ->
            toolbarName.text = recruiter.recruiterName
            headerName.text = recruiter.recruiterName
            headerEmail.text = recruiter.recruiterEmail
        }

        val cSR = Customer_SRFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.userSRfragment, cSR)
            commit()
        }

        val aSR = AgentSrFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.agentSRFragment, aSR)
            commit()
        }

        initialConstraintSet = ConstraintSet()
        initialConstraintSet.clone(binding.constraintHome)
        binding.btnAddApplications.setOnClickListener{
            if (isExpanded) {
                revertLayout()
            } else {
                expandFragmentAndSwitchToNew()
            }
            isExpanded = !isExpanded
        }

    }

    private fun revertLayout() {
        // Show the second fragment again
        binding.agentSRFragment.visibility = View.VISIBLE

        // Reapply the initial constraints
        initialConstraintSet.applyTo(binding.constraintHome)

        // Optionally, replace the fragment back to the original if needed
        val agentSrFragment: Fragment = AgentSrFragment()
        val navigationUseCase = switchFragmentUsecase(
            this.supportFragmentManager,
            R.id.userSRfragment
        )
        navigationUseCase.navigateToFragment(agentSrFragment)
    }

    private fun expandFragmentAndSwitchToNew() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.constraintHome)

        // Adjusting userSRfragment to fill the entire screen
        constraintSet.connect(
            R.id.userSRfragment, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0
        )
        constraintSet.connect(
            R.id.userSRfragment, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0
        )
        constraintSet.connect(
            R.id.userSRfragment, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0
        )
        constraintSet.connect(
            R.id.userSRfragment, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0
        )

        // Hide the second fragment
        binding.agentSRFragment.visibility = View.GONE

        // Apply the changes
        constraintSet.applyTo(binding.constraintHome)

        val addApplicationFragment: Fragment = AddApplicationFragment()
        val navigationUseCase = switchFragmentUsecase(
            this.supportFragmentManager,
            R.id.userSRfragment
        )
        navigationUseCase.navigateToFragment(addApplicationFragment)

    }

    private fun showAlertDialogue() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Sign out?")
        builder.setMessage("Are you sure you want to sign out?")
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            mAuth.signOut()
            val intent = Intent(this@AgentActivity, MainActivity::class.java)
            startActivity(intent)
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

