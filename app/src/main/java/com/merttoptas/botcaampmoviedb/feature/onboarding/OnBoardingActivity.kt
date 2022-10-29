package com.merttoptas.botcaampmoviedb.feature.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.merttoptas.botcaampmoviedb.MainActivity
import com.merttoptas.botcaampmoviedb.R
import com.merttoptas.botcaampmoviedb.databinding.ActivityOnboardingBinding
import com.merttoptas.botcaampmoviedb.feature.onboarding.adapter.OnBoardingAdapter
import com.merttoptas.botcaampmoviedb.utils.extensions.gone
import com.merttoptas.botcaampmoviedb.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {
    private val viewModel by viewModels<OnBoardingViewModel>()
    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = OnBoardingAdapter(this, prepareOnBoardingItems())
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
        }.attach()

        initViews()

    }

    private fun initViews() {
        binding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.isLastPage = position == 2
                if (position != 0) {
                    binding.btnPrev.visible()
                } else {
                    binding.btnPrev.gone()
                }
            }
        })

        binding.btnSkip.setOnClickListener {
            if (binding.viewPager.currentItem == 2) {
                skipOnBoarding()
            } else {
                binding.viewPager.setCurrentItem(binding.viewPager.currentItem.plus(1), true)
            }
        }

        binding.btnPrev.setOnClickListener {
            binding.viewPager.setCurrentItem(binding.viewPager.currentItem.minus(1), true)
        }
    }

    private fun skipOnBoarding() {
        viewModel.setOnBoardingStatus()
        navigateToMain()
    }

    private fun prepareOnBoardingItems(): List<Int> {
        return listOf(
            R.layout.item_onboarding,
            R.layout.item_onboarding_two,
            R.layout.item_onboarding_three
        )
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}