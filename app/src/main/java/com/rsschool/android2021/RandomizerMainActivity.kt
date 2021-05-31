package com.rsschool.android2021

interface RandomizerMainActivity {
    fun openSecondFragment(min: Int, max: Int)
    fun openFirstFragment(previous: Int)
    fun firstFragmentSaveState(min: Int, max: Int)
    fun secondFragmentSaveState(previous: Int)
}