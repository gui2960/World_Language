package br.com.guifactory;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import entity.JsonObject;
import network.ResourceService;
import network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("br.com.guifactory", appContext.getPackageName());
    }

    @Test
    public void testGetDataSucessWhenResponseNotNull() throws IOException {
        ResourceService service = RetrofitClient.getRetrofitInstance().create(ResourceService.class);
        Call<List<JsonObject>> call = service.getWeb();
        Response<List<JsonObject>> response = call.execute();
        assertNotNull(response);
    }

}