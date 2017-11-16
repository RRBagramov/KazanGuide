package ru.kpfu.kazanguide.dao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.kpfu.kazanguide.model.Guide;

/**
 * Created by rober_000 on 12.11.2017.
 */

public interface KazanGuideAPI {
    @GET("/get")
    Call<List<Guide>> getAll();
    //// TODO: 23.05.2017 добавить в запрос id категории
//    @GET("/get/br")
//    Call<Guide> getById (@Query("brainteaserId") int id);
}
