package hector.developers.birthdaywishes.api;

import java.util.List;

import hector.developers.birthdaywishes.model.Staff;
import hector.developers.birthdaywishes.model.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("staff")
    Call<Staff> createStaff(
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("gender") String gender,
            @Field("dateOfBirth") String dateOfBirth,
            @Field("employmentDate") String employmentDate,
            @Field("phone") String phone,
            @Field("email") String email,
            @Field("designation") String designation);


    //the users login call
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("login")
    Call<User> login(
            @Field("email") String email,
            @Field("password") String password
    );
//    @Headers({"Accept: application/json"})
//    @POST("login")
//    Call<JsonObject> login2(@Body JsonObject jsonObject
//    );

    //fetching all users
    @GET("users")
    Call<List<User>> getUsers();

    //fetching all staffs
    @GET("staffs")
    Call<List<Staff>> getStaffs();


    @GET("staff/{dateOfBirth}")
    Call<List<Staff>> findByDateOfBirth(@Path("dateOfBirth") String dateOfBirth);

}