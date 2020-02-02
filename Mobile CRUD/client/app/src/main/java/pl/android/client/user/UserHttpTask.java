package pl.android.client.user;

import android.app.Application;
import android.os.AsyncTask;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import pl.android.client.user.dto.CreateUserDto;
import pl.android.client.user.dto.UserDto;
import pl.android.client.util.NotificationUtil;
import pl.android.client.util.UrlUtil;

public class UserHttpTask extends AsyncTask<String, Void, Object> {

    private Application activity;

    public UserHttpTask(Application activity) {
        this.activity = activity;
    }

    @Override
    protected Object doInBackground(String... uri) {

        final String url = uri[0];
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        switch (url) {
            case UrlUtil.ADD_USER:
                try {
                    CreateUserDto dto = new CreateUserDto(uri[1], uri[2], uri[3], uri[4]);
                    restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(dto, headers), UserDto[].class);
                    return "SUCCESS - " + UrlUtil.ADD_USER;
                } catch (Exception e) {
                    return "BAD_REQUEST - " + UrlUtil.ADD_USER;
                }
            case UrlUtil.BASE:
                return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), UserDto[].class);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {

        if (!(o instanceof String)) {
            return;
        }

        String msg = (String) o;

        if (msg.contains("BAD_REQUEST") && msg.contains(UrlUtil.ADD_USER)) {
            NotificationUtil.showErrorNotification(activity, "All fields are required AND Email should be unique!");
        } else if (msg.contains("SUCCESS") && msg.contains(UrlUtil.ADD_USER)) {
            NotificationUtil.showSuccessNotification(activity, "New user has been added!");
        }
    }
}