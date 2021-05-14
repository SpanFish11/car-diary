import { AXIOS } from "@/http-common";
import jwt_decode from "jwt-decode";
import User from "@/models/user";

class AuthService {
  login(user) {
    return AXIOS.post("auth", user).then((response) => {
      const token = response.data.token;
      const newUser = new User();
      if (token) {
        const { roles, sub_id } = jwt_decode(token);
        newUser.token = token;
        newUser.userId = sub_id;
        newUser.roles = roles;
        localStorage.setItem("user", JSON.stringify(newUser));
      }
      return newUser;
    });
  }

  logout() {
    localStorage.removeItem("user");
  }
}

export default new AuthService();
