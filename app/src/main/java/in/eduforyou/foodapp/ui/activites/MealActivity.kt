package `in`.eduforyou.foodapp.ui.activites

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import `in`.eduforyou.foodapp.R
import `in`.eduforyou.foodapp.adapters.MealRecyclerAdapter
import `in`.eduforyou.foodapp.adapters.SetOnMealClickListener
import `in`.eduforyou.foodapp.data.pojo.Meal
import `in`.eduforyou.foodapp.databinding.ActivityCategoriesBinding
import `in`.eduforyou.foodapp.mvvm.MealActivityMVVM
import `in`.eduforyou.foodapp.ui.fragments.HomeFragment.Companion.CATEGORY_NAME
import `in`.eduforyou.foodapp.ui.fragments.HomeFragment.Companion.MEAL_ID
import `in`.eduforyou.foodapp.ui.fragments.HomeFragment.Companion.MEAL_STR
import `in`.eduforyou.foodapp.ui.fragments.HomeFragment.Companion.MEAL_THUMB


class MealActivity : AppCompatActivity() {
    private lateinit var mealActivityMvvm: MealActivityMVVM
    private lateinit var binding: ActivityCategoriesBinding
    private lateinit var myAdapter: MealRecyclerAdapter
    private var categoryNme = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mealActivityMvvm = ViewModelProviders.of(this)[MealActivityMVVM::class.java]
        startLoading()
        prepareRecyclerView()
        mealActivityMvvm.getMealsByCategory(getCategory())
        mealActivityMvvm.observeMeal().observe(this, object : Observer<List<Meal>> {


            @SuppressLint("SetTextI18n")
            override fun onChanged(t: List<Meal>) {
                if(t==null){
                    hideLoading()
                    Toast.makeText(applicationContext, "No meals in this category", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }else {
                    myAdapter.setCategoryList(t!!)
                    binding.tvCategoryCount.text = categoryNme + " : " + t.size.toString()
                    hideLoading()
                }
            }
        })

        myAdapter.setOnMealClickListener(object : SetOnMealClickListener {
            override fun setOnClickListener(meal: Meal) {
                val intent = Intent(applicationContext, MealDetailesActivity::class.java)
                intent.putExtra(MEAL_ID, meal.idMeal)
                intent.putExtra(MEAL_STR, meal.strMeal)
                intent.putExtra(MEAL_THUMB, meal.strMealThumb)
                startActivity(intent)
            }
        })
    }

    private fun hideLoading() {
        binding.apply {
            loadingGifMeals.visibility = View.INVISIBLE
            mealRoot.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.white))
        }    }

    private fun startLoading() {
        binding.apply {
            loadingGifMeals.visibility = View.VISIBLE
            mealRoot.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.g_loading))
        }
    }

    private fun getCategory(): String {
        val tempIntent = intent
        val x = intent.getStringExtra(CATEGORY_NAME)!!
        categoryNme = x
        return x
    }

    private fun prepareRecyclerView() {
        myAdapter = MealRecyclerAdapter()
        binding.mealRecyclerview.apply {
            adapter = myAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
    }
}