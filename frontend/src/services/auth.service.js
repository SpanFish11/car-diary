import { AXIOS } from "@/http-common";
import jwt_decode from "jwt-decode";
import User from "@/models/user";

class AuthService {
  login(user) {
    return AXIOS.post("auth", user).then((response) => {
      const token = response.data.token;
      if (token) {
        const { roles, sub_id } = jwt_decode(token);
        const newUser = new User(token, roles, sub_id);
        localStorage.setItem("user", JSON.stringify(newUser));
      }
      return response.data;
    });
  }

  logout() {
    localStorage.removeItem("user");
  }
}

export default new AuthService();
