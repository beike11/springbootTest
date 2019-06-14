package com.stevenw.demo.util;

import com.stevenw.demo.base.URShift;
import org.igniterealtime.restclient.RestApiClient;
import org.igniterealtime.restclient.entity.AuthenticationToken;
import org.igniterealtime.restclient.entity.SessionEntities;
import org.igniterealtime.restclient.entity.UserEntities;
import org.igniterealtime.restclient.entity.UserEntity;


import javax.ws.rs.POST;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author stevenw
 * @date 2019/5/23
 */
public class OpenfireUtils {
   private  static AuthenticationToken authenticationToken;
   private static String sharedSecretKet = "1HaNcaDgYrC7bZmR";
//   private static RestApiClient restApiClient;
   static {
        authenticationToken = new AuthenticationToken(sharedSecretKet);
//       restApiClient = new RestApiClient("http://" + ParamUtil.SERVERHOST,9090,authenticationToken);
   }

    public static AuthenticationToken getAuthenticationToken() {
        return authenticationToken;
    }

    /**
     * Purpose: 根据用户名称删除用户
     * @author Bruce
     * @param restApiClient
     * @param userName
     * @return
     * @return boolean
     */
    public static boolean deleteByUserName(RestApiClient restApiClient, String userName) {
        boolean deleteSuccess = false;
        try {
            UserEntity userEntity = getUserByUserName(restApiClient, userName);
            if(userEntity != null) {
                Response response = restApiClient.deleteUser(userName);
                if(response.getStatus() == 200){
                    deleteSuccess = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deleteSuccess;
    }


    /**
     * Purpose: 创建用户
     * @author Bruce
     * @param restApiClient
     * @param username
     * @param name
     * @param email
     * @param password
     * @return boolean
     */
    public static boolean createUser(RestApiClient restApiClient,String username, String name, String email, String password) {
        boolean createSuccess = false;
        try {
            UserEntity userEntity = new UserEntity(username, name, email, password);
            Response response = restApiClient.createUser(userEntity);
            if(response.getStatus() == 201){
                createSuccess = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createSuccess;
    }

    /**
     * Purpose: 修改用户信息
     * @author Bruce
     * @param restApiClient
     * @param username
     * @param name
     * @param email
     * @param password
     * @return
     * @return boolean
     */
    public static boolean udpateByUserName(RestApiClient restApiClient,String username, String name, String email, String password) {
        boolean updateSuccess = false;
        try {
            UserEntity userEntity = getUserByUserName(restApiClient, username);
            if(userEntity != null) {
                userEntity.setName(name);
                userEntity.setEmail(email);
                userEntity.setPassword(password);
                Response response = restApiClient.updateUser(userEntity);
                if(response.getStatus() == 200){
                    updateSuccess = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updateSuccess;
    }

    /**
     * Purpose: 根据用户名称获取用户
     * @author Bruce
     * @param restApiClient
     * @return
     * @return UserEntities
     */
    public static UserEntity getUserByUserName(RestApiClient restApiClient,String userName) {
        UserEntity userEntity = null;
        try {
            userEntity = restApiClient.getUser(userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userEntity;
    }

    /**
     * Purpose: 获取所有用户
     * @author Bruce
     * @param restApiClient
     * @return
     * @return UserEntities
     */
    public static UserEntities getUsers(RestApiClient restApiClient) {
        UserEntities userEntities = null;
        try {
            userEntities = restApiClient.getUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userEntities;
    }

    /**
     *  0 - 用户不存在; 1 - 用户在线; 2 - 用户离线
     * @param userName
     * @return
     * @throws Exception
     */
    public static int sessionStatus(String userName) {
        HttpURLConnection conn = null;
        String urlStr = "http://"+ ParamUtil.SERVERHOST+":9090/plugins/presence/status?jid="+userName+"@"+ParamUtil.SERVERHOST+"&type=xml";
        String result = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection)url.openConnection();
            URShift.setHttpUrlConnection(conn,"GET");
            conn.connect();
             result = URShift.readResponseContent(conn.getInputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
        System.err.println(result);
        if (result == null){
            return 0;
        }
        int shOnLineState = 0;
        if(result.indexOf("type=\"unavailable\"")>=0){
            shOnLineState = 2;
        }else if(result.indexOf("type=\"error\"")>=0){
            shOnLineState = 0;
        }else if(result.indexOf("priority")>=0 || result.indexOf("id=\"")>=0){
            shOnLineState = 1;
        }
         return  shOnLineState;
    }

}
