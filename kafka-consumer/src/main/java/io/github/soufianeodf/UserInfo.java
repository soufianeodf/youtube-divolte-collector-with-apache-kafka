package io.github.soufianeodf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private String user_country;
    private String user_state;
    private String user_city;
    private String user_postal;
    private String latitude;
    private String longitude;
    private String ip;
    private String browser_name;
    private String full_version;
    private String major_version;
    private String navigator_app_name;
    private String navigator_user_agent;
    private String os_name;
}
