package `in`.eduforyou.foodapp.data.retrofit


import `in`.eduforyou.foodapp.data.pojo.CategoryResponse
import `in`.eduforyou.foodapp.data.pojo.MealsResponse
import `in`.eduforyou.foodapp.data.pojo.RandomMealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface FoodApi {
    @GET("categories.php")
    fun getCategories(): Call<CategoryResponse>

    @GET("filter.php?")
    fun getMealsByCategory(@Query("i") category:String):Call<MealsResponse>

    @GET ("random.php")
    fun getRandomMeal():Call<RandomMealResponse>

    @GET("lookup.php?")
    fun getMealById(@Query("i") id:String):Call<RandomMealResponse>

    @GET("search.php?")
    fun getMealByName(@Query("s") s:String):Call<RandomMealResponse>
}