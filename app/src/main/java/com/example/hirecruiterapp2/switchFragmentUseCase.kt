package com.example.hirecruiterapp2

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class switchFragmentUsecase(private val fragmentManager: FragmentManager, private val containerId: Int) {
    fun navigateToFragment(fragment: Fragment, bundle: Bundle? = null) {
        fragment.arguments = bundle
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,
            R.anim.enter_left_to_right,R.anim.exit_left_to_right).replace(containerId, fragment)
            .addToBackStack("null")
        fragmentTransaction.commit()
    }
}