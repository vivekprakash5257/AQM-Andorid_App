package Modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class loginbosch {

        @SerializedName("authToken")
        @Expose
        private String authToken;
        @SerializedName("OrgKey")
        @Expose
        private String orgKey;

        public String getAuthToken() {
            return authToken;
        }

        public void setAuthToken(String authToken) {
            this.authToken = authToken;
        }

        public String getOrgKey() {
            return orgKey;
        }

        public void setOrgKey(String orgKey) {
            this.orgKey = orgKey;
        }

}
